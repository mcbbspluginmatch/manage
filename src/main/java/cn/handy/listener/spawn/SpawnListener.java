package cn.handy.listener.spawn;

import cn.handy.constants.BaseConstants;
import cn.handy.entity.Spawn;
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
        // 如果有复活床的权限就复活到床
        if (event.getPlayer().hasPermission("handy.spawn.bed")) {
            return;
        }
        // 检查是否有spawn自定义权限
        Boolean rst = false;
        for (Spawn spawn : BaseConstants.spawnList) {
            if (event.getPlayer().hasPermission(spawn.getPermission())) {
                rst = true;
                event.setRespawnLocation(getSpawnLocation(spawn.getWorld(), spawn.getX(), spawn.getY(), spawn.getZ()));
            }
        }
        // 传送到默认spawn地址
        if (!rst) {
            String world = ConfigUtil.langConfig.getString("spawn.world");
            Double x = ConfigUtil.langConfig.getDouble("spawn.x");
            Double y = ConfigUtil.langConfig.getDouble("spawn.y");
            Double z = ConfigUtil.langConfig.getDouble("spawn.z");
            event.setRespawnLocation(getSpawnLocation(world, x, y, z));
        }
    }

    /**
     * 获取位置
     *
     * @param world
     * @param x
     * @param y
     * @param z
     * @return
     */
    private Location getSpawnLocation(String world, Double x, Double y, Double z) {
        return new Location(
                Bukkit.getWorld(world), x, y, z
        );
    }

}
