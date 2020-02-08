package br.com.nareba.ntools;

import br.com.nareba.ntools.core.TeleportListener;
import br.com.nareba.ntools.event.RegisterPlayerEvent;
import br.com.nareba.ntools.home.RegisterHomeEvent;
import br.com.nareba.ntools.home.HomeManager;
import br.com.nareba.ntools.tools.AdminTool;
import br.com.nareba.ntools.tools.ToolCommand;
import br.com.nareba.ntools.tools.event.AdminToolEvent;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

import java.util.HashMap;
import java.util.Map;

public class NTools extends PluginBase {
    private final String homeDirectory = "/home";
    private Map<String, TeleportListener> listeners;
    private AdminTool adminTool;
    private HomeManager homeManager;
    @Override
    public void onEnable() {
        getLogger().info(TextFormat.DARK_GREEN + "NTools has initializing.");
        this.homeManager = new HomeManager(this);
        this.adminTool = new AdminTool(this);
        getServer().getPluginManager().registerEvents(new RegisterPlayerEvent(this), this);
        getServer().getPluginManager().registerEvents(new RegisterHomeEvent(this.homeManager,this),this);
        getServer().getPluginManager().registerEvents(new AdminToolEvent(this.adminTool), this);
        this.listeners = new HashMap<String, TeleportListener>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String lowerCommand = command.getName().toLowerCase();
        getHomeManager().getHomeCommand().onHomeCommand(sender, command, label, args);
        adminTool.getCommand().onCommand(sender, command, label, args);
        if (lowerCommand.equals("tpa"))   {
            if (sender.isPlayer())   {
                if (args.length > 0)   {
                    Player playerSender = (Player) sender;
                    Player playerTarget = getServer().getPlayer(args[0]);
                    if (playerTarget != null)   {
                        if (listeners.containsKey(playerTarget.getName()))   {
                            TeleportListener listener = this.listeners.get(playerTarget.getName());
                            if (!listener.isHasRequest())   {
                                listener.setPlayerRequest(playerSender);
                                listener.setHasRequest(true);
                                playerSender.sendMessage("Você enviou um pedido de teleporte para " + playerTarget.getName());
                                playerTarget.sendMessage("Você recebeu um pedido de teleporte de " + playerSender.getName());
                                playerTarget.sendMessage("Digita /taceitar para aceitar ou /trecusar para recusar o pedido!");
                            }else   {
                                playerSender.sendMessage("O jogador " + playerTarget.getName() + " já tem um pedido de teleporte em espera. Tente mais tarde.");
                            }
                        }
                    }else   {
                        playerSender.sendMessage("O jogador " + args[0] + " não existe!");
                    }
                }
            }
        }else if (lowerCommand.equals("taceitar"))   {
            if (sender.isPlayer())   {
                Player player = (Player) sender;
                if (this.listeners.containsKey(player.getName()))   {
                    TeleportListener listener = this.listeners.get(player.getName());
                    if (listener.isHasRequest())   {
                        Player playerRequested = listener.getPlayerRequest();
                        if (getServer().getPlayer(playerRequested.getName()) != null)   {
                            playerRequested.teleport(player.getPosition());
                            player.sendMessage("Você aceitou o pedido de teleporte de " + playerRequested.getName());
                            listener.setPlayerRequest(null);
                            listener.setHasRequest(false);
                        }
                    }
                }
            }
        }else if (lowerCommand.equals("trecusar"))   {
            if (sender.isPlayer())   {
                Player player = (Player) sender;
                if (this.listeners.containsKey(player.getName()))   {
                    TeleportListener listener = this.listeners.get(player.getName());
                    if (listener.isHasRequest())   {
                        listener.setPlayerRequest(null);
                        listener.setHasRequest(false);
                        player.sendMessage("Você recusou um pedido de teleport");
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void onDisable() {
        getLogger().info(TextFormat.DARK_GREEN + "NTools has initializing.");
    }
    public Map<String, TeleportListener> getListeners()   {
        return this.listeners;
    }
    public HomeManager getHomeManager()   {
        return this.homeManager;
    }
}
