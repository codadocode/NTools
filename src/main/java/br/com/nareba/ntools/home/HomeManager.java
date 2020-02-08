package br.com.nareba.ntools.home;

import br.com.nareba.ntools.NTools;
import cn.nukkit.Player;
import cn.nukkit.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class HomeManager {
    private final String homeDirectory = "/home";
    private final HomeCommand homeCommand;
    private final NTools plugin;
    private Map<UUID, PlayerHome> homeMap;
    public HomeManager(NTools plugin)   {
        this.plugin = plugin;
        this.homeCommand = new HomeCommand(this);
        this.homeMap = new HashMap<UUID, PlayerHome>();
    }
    public boolean addPlayer(Player player)   {
        if (!containsPlayer(player))   {
            PlayerHome tmpPlayerHome = new PlayerHome(player);
            this.homeMap.put(player.getUniqueId(), tmpPlayerHome);
            return true;
        }
        return false;
    }
    public boolean delPlayer(Player player)   {
        if (containsPlayer(player))   {
            this.homeMap.remove(player.getUniqueId());
            if (!this.homeMap.containsKey(player.getUniqueId()))   {
                return true;
            }
        }
        return false;
    }
    public boolean containsPlayer(Player player)   {
        if (this.homeMap.containsKey(player.getUniqueId()))   {
            return true;
        }
        return false;
    }
    public boolean setHome(Player player, String homeName)   {
        if (containsPlayer(player))   {
            PlayerHome tmpPlayerHome = this.homeMap.get(player.getUniqueId());
            tmpPlayerHome.setHome(homeName, player.getPosition(), player.level.getName());
            saveHomeFile(tmpPlayerHome);
            return true;
        }
        return false;
    }
    public Optional<Home> getHome(Player player, String homeName)   {
        if (containsPlayer(player))   {
            return this.homeMap.get(player.getUniqueId()).getHome(homeName);
        }
        return Optional.empty();
    }
    private void saveHomeFile(PlayerHome playerHome)   {
        try   {
            File directory = new File(plugin.getDataFolder() + this.homeDirectory);
            if (!directory.exists())   {
                directory.mkdirs();
            }
            File homeFile = new File(plugin.getDataFolder() + this.homeDirectory, playerHome.getPlayerUUID() + ".json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String homeJson = gson.toJson(playerHome);
            Utils.writeFile(homeFile, homeJson);
        }catch (Exception e)   {
            e.printStackTrace();;
        }
    }
    public void loadHomeFile(Player player)   {
        try   {
            File directory = new File(plugin.getDataFolder() + this.homeDirectory);
            if (!directory.exists())   {
                directory.mkdirs();
            }
            File homeFile = new File(plugin.getDataFolder() + this.homeDirectory, player.getUniqueId() + ".json");
            if (homeFile.exists())   {
                String playerHomeJson = Utils.readFile(homeFile);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Type playerHomeType = new TypeToken<PlayerHome>(){}.getType();
                PlayerHome tmpPlayerHome = gson.fromJson(playerHomeJson, playerHomeType);
                this.homeMap.put(player.getUniqueId(), tmpPlayerHome);
            }else   {
                plugin.getLogger().info("Data home not finded! Creating data home for " + player.getName());
                PlayerHome tmpPlayerHome = new PlayerHome(player);
                this.homeMap.put(player.getUniqueId(), tmpPlayerHome);
                saveHomeFile(tmpPlayerHome);
                plugin.getLogger().info("Data created!");
            }
        } catch (Exception e)   {
            e.printStackTrace();
        }
    }
    public HomeCommand getHomeCommand()   {
        return this.homeCommand;
    }
    public NTools getPlugin()   {
        return this.plugin;
    }
}
