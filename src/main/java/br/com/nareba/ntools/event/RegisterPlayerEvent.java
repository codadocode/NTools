package br.com.nareba.ntools.event;

import br.com.nareba.ntools.NTools;
import br.com.nareba.ntools.core.TeleportListener;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;

public class RegisterPlayerEvent implements Listener {
    private final NTools plugin;
    public RegisterPlayerEvent(NTools plugin)   {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)   {
        Player player = event.getPlayer();
        if (!plugin.getListeners().containsKey(player.getName()))   {
            TeleportListener teleportListener = new TeleportListener(player.getName());
            plugin.getListeners().put(player.getName(), teleportListener);
            plugin.getLogger().info(player.getName() + " has been added in teleport listeners");
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)   {
        Player player = event.getPlayer();
        if (plugin.getListeners().containsKey(player.getName()))   {
            plugin.getListeners().remove(player.getName());
            plugin.getLogger().info("Teleport listener from " + player.getName() + " has been removed: Player Disconnected!");
        }
    }
}
