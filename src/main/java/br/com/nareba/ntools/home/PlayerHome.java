package br.com.nareba.ntools.home;

import cn.nukkit.Player;
import cn.nukkit.math.Vector3;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PlayerHome {
    private Map<String, Home> homeMap;
    private String playerUUID;
    private String playerName;
    public PlayerHome(Player player)   {
        this.playerUUID = player.getUniqueId().toString();
        this.playerName = player.getName();
        this.homeMap = new HashMap<String, Home>();
    }
    public boolean hasHome()   {
        if (this.homeMap.size() > 0)   {
            return true;
        }
        return false;
    }
    public boolean homeExists(String homeName)   {
        if (hasHome())   {
            if (this.homeMap.containsKey(homeName))   {
                return true;
            }
        }
        return false;
    }
    public Optional<Home> getHome(String homeName)   {
        if (homeExists(homeName))   {
            return Optional.of(this.homeMap.get(homeName));
        }
        return Optional.empty();
    }
    public boolean setHome(String homeName, Vector3 pos, String levelName)   {
        Home tmpHome = new Home(homeName, pos, levelName);
        this.homeMap.put(homeName, tmpHome);
        return true;
    }
    public boolean delHome(String homeName)   {
        if (homeExists(homeName))   {
            this.homeMap.remove(homeName);
            return true;
        }
        return false;
    }

    public Map<String, Home> getHomeMap() {
        return homeMap;
    }

    public void setHomeMap(Map<String, Home> homeMap) {
        this.homeMap = homeMap;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(String playerUUID) {
        this.playerUUID = playerUUID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
