package cn.handy.listener.spawn;

import cn.handy.utils.BaseUtil;
import cn.handy.utils.Beans;
import lombok.val;
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
        val player = event.getPlayer();
        // 如果有复活床的权限就复活到床
        if (player.hasPermission("handy.spawn.bed")) {
            return;
        }
        // 查了下相关代码 主线程访问数据库??? 不缓存下么 (以及好像会有各种null问题) —— yinyangshi
        // 获取spawn地址进行复活点设置传送
        val spawn = Beans.getBeans().getSpawnService().findById(BaseUtil.getSpawnPermission(player));
        if (spawn != null) {
            event.setRespawnLocation(BaseUtil.getLocation(spawn));
        }
    }

}
