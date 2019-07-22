package cn.handy.listener.spawn;

import cn.handy.utils.config.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * @author hanshuai
 * @Description: {监听器}
 * @date 2019/7/22 17:16
 */
public class SpawnListener implements Listener {

    /**
     * 玩家重生事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
        String world = ConfigUtil.langConfig.getString("spawn.world");
        Double x = ConfigUtil.langConfig.getDouble("spawn.x");
        Double y = ConfigUtil.langConfig.getDouble("spawn.y");
        Double z = ConfigUtil.langConfig.getDouble("spawn.z");
        event.setRespawnLocation(getSpawnLocation(world, x, y, z));
    }

    private Location getSpawnLocation(String world, Double x, Double y, Double z) {
        return new Location(
                Bukkit.getWorld(world), x, y, z
        );
    }

}
