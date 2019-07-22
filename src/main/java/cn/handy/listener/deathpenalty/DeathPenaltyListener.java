package cn.handy.listener.deathpenalty;

import cn.handy.utils.config.ConfigUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.List;

/**
 * @author hanshuai
 * @Description: {死亡惩罚监听器}
 * @date 2019/7/22 17:16
 */
public class DeathPenaltyListener implements Listener {

    /**
     * 玩家重生事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
        // 获取自定义的命令
        String jsonArray = ConfigUtil.langConfig.getString("eeathPenalty");
        Gson gson = new Gson();
        String[] strings = gson.fromJson(jsonArray, String[].class);
        for (String str : strings) {
            Bukkit.dispatchCommand(event.getPlayer(), str);
        }
    }

}
