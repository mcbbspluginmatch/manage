package cn.handy.listener.deathpenalty;

import cn.handy.utils.BaseUtil;
import cn.handy.utils.config.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;

/**
 * @author hanshuai
 * @Description: {死亡惩罚监听器}
 * @date 2019/7/22 17:16
 */
public class DeathPenaltyListener implements Listener {

    /**
     * 当一个玩家死亡时触发本事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerRespawnEvent(PlayerDeathEvent event) {
        // 获取自定义的命令
        List<String> stringList = ConfigUtil.langConfig.getStringList("deathPenaltyCommand");
        for (String command : stringList) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), BaseUtil.replaceChatColorAndName(command, event.getEntity().getName()));
        }
    }

}
