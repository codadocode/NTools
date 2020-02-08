package br.com.nareba.ntools.tools;

import br.com.nareba.ntools.NTools;
import cn.nukkit.Player;

import java.util.ArrayList;
import java.util.List;

public class AdminTool {
    private final NTools plugin;
    private final ToolCommand command;
    private List<Player> vanishedPlayers;
    public AdminTool(NTools plugin)   {
        this.plugin = plugin;
        this.command = new ToolCommand(this, plugin);
        this.vanishedPlayers = new ArrayList<Player>();
    }
    public ToolCommand getCommand()   {
        return this.command;
    }
    public boolean addVanishPlayer(Player player)   {
        if (!this.vanishedPlayers.contains(player))   {
            this.vanishedPlayers.add(player);
            return true;
        }
        return false;
    }
    public boolean removeVanishPlayer(Player player)   {
        if (this.vanishedPlayers.contains(player))   {
            this.vanishedPlayers.remove(player);
            if (!this.vanishedPlayers.contains(player))   {
                return true;
            }
        }
        return false;
    }
    public boolean hasVanishedPlayer()   {
        if (this.vanishedPlayers.size() > 0)   {
            return true;
        }
        return false;
    }
    public boolean isVanishedPlayer(Player player)   {
        if (hasVanishedPlayer())   {
            if (this.vanishedPlayers.contains(player))   {
                return true;
            }
        }
        return false;
    }
    public NTools getPlugin()   {
        return this.plugin;
    }
    public List<Player> getVanishedPlayers()   {
        return this.vanishedPlayers;
    }
}
