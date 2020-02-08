package br.com.nareba.ntools.home;

import br.com.nareba.ntools.NTools;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerBedLeaveEvent;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.player.PlayerRespawnEvent;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;

import java.util.Optional;


public class RegisterHomeEvent implements Listener {
    private final NTools plugin;
    private final HomeManager homeManager;
    public RegisterHomeEvent(HomeManager homeManager, NTools plugin)   {
        this.homeManager = homeManager;
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerLeaveBed(PlayerBedLeaveEvent event)   {
        homeManager.setHome(event.getPlayer(), "default");
        event.getPlayer().sendMessage("Sua /home foi salva nessa cama.");
    }
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event)   {
        Player player = event.getPlayer();
        this.homeManager.loadHomeFile(player);
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)   {
        homeManager.delPlayer(event.getPlayer());
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event)   {
        if (homeManager.containsPlayer(event.getPlayer()))   {
            Optional<Home> optionalHome = homeManager.getHome(event.getPlayer(), "default");
            if (optionalHome.isPresent() && !event.getPlayer().isAlive())   {
                Vector3 homeDefaultPos = homeManager.getHome(event.getPlayer(), "default").get().getPosition();
                event.setRespawnPosition(new Position(homeDefaultPos.x, homeDefaultPos.y, homeDefaultPos.z));
                event.getPlayer().sendMessage("Você renasceu na sua home padrão! Tome mais cuidado pra próxima vez.");
            }
        }
    }
}
