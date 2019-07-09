package cn.handy.listener.back;

import cn.handy.constants.BaseConstants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * @author hanshuai
 * @Description: {监听器}
 * @date 2019/5/17 17:16
 */
public class BackListener implements Listener {

    /**
     * 玩家传送事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerTeleportEvent(PlayerTeleportEvent event) {
        BaseConstants.backMap.put(event.getPlayer().getName(), event.getFrom());
    }
}
