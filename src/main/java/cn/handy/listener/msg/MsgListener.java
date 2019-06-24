package cn.handy.listener.msg;

import cn.handy.dao.message.impl.MessageServiceImpl;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.ConfigUtil;
import lombok.val;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author hanshuai
 * @Description: {监听器}
 * @date 2019/5/17 17:16
 */
public class MsgListener implements Listener {

    /**
     * 进入游戏事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerJoinGame(PlayerJoinEvent event) {
        String userName = event.getPlayer().getName().toLowerCase();
        val messageService = new MessageServiceImpl();
        val message = messageService.findByUserName(userName);
        String joinMessage = ConfigUtil.LangConfig.getString("joinMessage");
        if (message.getId() != null) {
            joinMessage = message.getJoinMessage();
        }
        joinMessage = joinMessage.replace("${player}", userName);
        event.setJoinMessage(BaseUtil.replaceChatColor(joinMessage));
    }

    /**
     * 退出游戏事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerQuitGame(PlayerQuitEvent event) {
        String userName = event.getPlayer().getName().toLowerCase();
        val messageService = new MessageServiceImpl();
        val message = messageService.findByUserName(userName);
        String quitMessage = ConfigUtil.LangConfig.getString("quitMessage");
        if (message.getId() != null) {
            quitMessage = message.getQuitMessage();
        }
        quitMessage = quitMessage.replace("${player}", userName);
        event.setQuitMessage(BaseUtil.replaceChatColor(quitMessage));
    }
}
