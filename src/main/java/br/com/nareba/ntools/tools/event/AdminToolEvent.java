package br.com.nareba.ntools.tools.event;

import br.com.nareba.ntools.tools.AdminTool;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.player.PlayerChunkRequestEvent;

public class AdminToolEvent implements Listener {
    private final AdminTool adminTool;
    public AdminToolEvent(AdminTool adminTool)   {
        this.adminTool = adminTool;
    }
    @EventHandler
    public void onPlayerChunkRequest(PlayerChunkRequestEvent event)   {
        if (adminTool.hasVanishedPlayer())   {
            for (Player player : adminTool.getVanishedPlayers())   {
                adminTool.getPlugin().getServer().removePlayerListData(player.getUniqueId());
            }
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)   {
        if (adminTool.hasVanishedPlayer())   {
            if (adminTool.isVanishedPlayer(event.getPlayer()))   {
                event.setQuitMessage("");
                adminTool.removeVanishPlayer(event.getPlayer());
                event.getPlayer().removeAllEffects();
                event.getPlayer().setDisplayName(event.getPlayer().getName());
                adminTool.getPlugin().getLogger().info(event.getPlayer().getName() + " has been removed from vanished players list!");
            }
        }
    }
}
