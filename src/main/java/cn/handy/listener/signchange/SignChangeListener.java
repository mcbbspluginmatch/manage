package cn.handy.listener.signchange;

import cn.handy.utils.BaseUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 * @author hanshuai
 * @Description: {牌子支持彩色字}
 * @date 2019/7/9 16:30
 */
public class SignChangeListener implements Listener {

    /**
     * 在玩家设置牌子上的内容子时触发.
     *
     * @param event
     */
    @EventHandler
    public void onSignChangeEvent(SignChangeEvent event) {
        String[] lines = event.getLines();
        for (int i = 0; i < lines.length; i++) {
            String colorLine = BaseUtil.replaceChatColor(lines[i]);
            event.setLine(i, colorLine);
        }
    }
}
