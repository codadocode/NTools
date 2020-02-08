package br.com.nareba.ntools.tools;

import br.com.nareba.ntools.NTools;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.TextFormat;

public class ToolCommand {
    private final NTools plugin;
    private final AdminTool adminTool;
    public ToolCommand(AdminTool adminTool, NTools plugin)   {
        this.adminTool = adminTool;
        this.plugin = plugin;
    }
    public void onCommand(CommandSender sender, Command command, String label, String[] args)   {
        String lowerCmd = command.getName().toLowerCase();
        if (sender.isPlayer())   {
            Player player = (Player) sender;
            switch (lowerCmd)   {
                case "vanish":
                    adminTool.addVanishPlayer(player);
                    player.addEffect(Effect.getEffect(Effect.INVISIBILITY).setDuration(720000).setVisible(false));
                    player.setDisplayName("");
                    plugin.getServer().removePlayerListData(player.getUniqueId());
                    for (Player tmpPlayer : plugin.getServer().getOnlinePlayers().values())   {
                        tmpPlayer.sendMessage(TextFormat.YELLOW + player.getName() + " saiu do jogo.");
                    }
                    break;
                case "spy":
                    if (args.length > 0)   {
                        String playerName = args[0];
                        Player targetPlayer = plugin.getServer().getPlayer(playerName);
                        if (targetPlayer != null)   {
                            player.teleport(targetPlayer);
                        }
                    }
                    break;
                case "see":
                    player.removeAllEffects();
                    player.setDisplayName(player.getName());
                    adminTool.removeVanishPlayer(player);
                    break;
                case "invget":
                    if (args.length > 0)   {
                        String playerName = args[0];
                        Player targetPlayer = plugin.getServer().getPlayer(playerName);
                        if (targetPlayer != null)   {
                            targetPlayer.getInventory().sendContents(player);
                            targetPlayer.getInventory().clearAll();
                            player.sendMessage("Você pegou o inventário de " + targetPlayer.getName());
                        }
                    }
                    break;
                case "invsend":
                    if (args.length > 0)   {
                        String playerName = args[0];
                        Player targetPlayer = plugin.getServer().getPlayer(playerName);
                        if (targetPlayer != null)   {
                            player.getInventory().sendContents(targetPlayer);
                            player.getInventory().clearAll();
                            player.sendMessage("Você enviou seu inventário para " + targetPlayer.getName());
                        }
                    }
                    break;
                case "clear":
                    if (args.length == 0)   {
                        player.getInventory().clearAll();
                        player.sendMessage("Você limpou seu inventário!");
                    }else if (args.length == 1)   {
                        String playerName = args[0];
                        Player targetPlayer = plugin.getServer().getPlayer(playerName);
                        if (targetPlayer != null)   {
                            player.getInventory().clearAll();
                            player.sendMessage("Você limpou o inventário de " + targetPlayer.getName());
                        }
                    }
            }
        }
    }
}
