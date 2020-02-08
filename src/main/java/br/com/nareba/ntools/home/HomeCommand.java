package br.com.nareba.ntools.home;

import br.com.nareba.ntools.NTools;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;

import java.util.Optional;

public class HomeCommand {
    private final HomeManager homeManager;
    public HomeCommand(HomeManager homeManager)   {
        this.homeManager = homeManager;
    }
    public void onHomeCommand(CommandSender sender, Command command, String label, String[] args)   {
        String lowerCommand = command.getName().toLowerCase();
        if (sender.isPlayer())   {
            Player player = (Player) sender;
            switch (lowerCommand)   {
                case "sethome":
                    if (args.length > 0 && player.hasPermission("ntools.sethome.multiple"))   {
                        String homeName = args[0];
                        boolean created = homeManager.setHome(player, homeName);
                        if (created)   {
                            player.sendMessage("Home '" + homeName + "' foi criada com sucesso!");
                        }
                    }else if (args.length == 0)   {
                        boolean created = homeManager.setHome(player, "default");
                        if (created)   {
                            player.sendMessage("Home " + "'default'" + " foi criada com sucesso!");
                        }
                    }else   {
                        player.sendMessage("Você não tem permissão para usar esse comando!");
                    }
                    break;
                case "home":
                    if (args.length > 0 && player.hasPermission("ntools.home.multiple"))   {
                        String homeName = args[0];
                        Optional<Home> optionalHome = homeManager.getHome(player, homeName);
                        if (optionalHome.isPresent())   {
                            Level homeLevel = homeManager.getPlugin().getServer().getLevelByName(optionalHome.get().getLevelName());
                            if (homeLevel != null)   {
                                /*
                                if (!homeManager.getPlugin().getServer().isLevelLoaded(homeLevel.getName()))   {
                                    homeManager.getPlugin().getServer().loadLevel(homeLevel.getName());
                                }
                                 */
                                Position homePosition = new Position(optionalHome.get().getPosX(), optionalHome.get().getPosY(), optionalHome.get().getPosZ(), homeLevel);
                                player.teleport(homePosition);
                                player.sendMessage("Você foi teleportado para sua " + optionalHome.get().getHomeName() + " home.");
                            }
                        }else   {
                            player.sendMessage("Home '" + homeName + "' não existe!");
                        }
                    }else   {
                        Optional<Home> optionalHome = homeManager.getHome(player, "default");
                        if (optionalHome.isPresent())   {
                            Level homeLevel = homeManager.getPlugin().getServer().getLevelByName(optionalHome.get().getLevelName());
                            if (homeLevel != null)   {
                                /*
                                if (!homeManager.getPlugin().getServer().isLevelLoaded(homeLevel.getName()))   {
                                    homeManager.getPlugin().getServer().loadLevel(homeLevel.getName());
                                }
                                 */
                                Position homePosition = new Position(optionalHome.get().getPosX(), optionalHome.get().getPosY(), optionalHome.get().getPosZ(), homeLevel);
                                player.teleport(homePosition);
                                player.sendMessage("Você foi teleportado para sua " + optionalHome.get().getHomeName() + " home.");
                            }
                        }else   {
                            player.sendMessage("Home 'default' não existe!");
                        }
                    }
                    break;
            }
        }
    }
}
