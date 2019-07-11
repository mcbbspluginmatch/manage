package cn.handy.utils.msg;

import cn.handy.utils.BaseUtil;
import com.destroystokyo.paper.Title;
import org.bukkit.entity.Player;

/**
 * @author hanshuai
 * @Description: {消息发送}
 * @date 2019/7/1 14:13
 */
public class SendMessage {

    /**
     * 普通消息
     *
     * @param player 玩家
     * @param msg    消息
     */
    public static void sendMessage(Player player, String msg) {
        player.sendMessage(BaseUtil.replaceChatColorAndName(msg, player.getDisplayName()));
    }

    /**
     * 操作栏消息
     *
     * @param player 玩家
     * @param msg    消息
     * @return
     */
    public static void sendActionBar(Player player, String msg) {
        player.sendActionBar(BaseUtil.replaceChatColorAndName(msg, player.getDisplayName()));
    }

    /**
     * title消息
     *
     * @param player      玩家
     * @param titleMsg    title主消息
     * @param subtitleMsg 副标题
     */
    public static void sendTitle(Player player, String titleMsg, String subtitleMsg) {
        Title title = new Title(BaseUtil.replaceChatColorAndName(titleMsg, player.getDisplayName()), BaseUtil.replaceChatColorAndName(subtitleMsg, player.getDisplayName()));
        player.sendTitle(title);
    }

}
