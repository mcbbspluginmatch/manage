package cn.handy.listener.spawn;

import cn.handy.utils.BaseUtil;
import cn.handy.utils.config.ConfigUtil;
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
        // 传送到spawn地址
        String world = ConfigUtil.langConfig.getString("spawn.world");
        Double x = ConfigUtil.langConfig.getDouble("spawn.x");
        Double y = ConfigUtil.langConfig.getDouble("spawn.y");
        Double z = ConfigUtil.langConfig.getDouble("spawn.z");
        event.setRespawnLocation(BaseUtil.getLocation(world, x, y, z));
    }

}
