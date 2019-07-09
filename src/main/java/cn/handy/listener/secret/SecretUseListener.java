package cn.handy.listener.secret;

import cn.handy.Manage;
import cn.handy.constants.BaseConstants;
import cn.handy.constants.secret.SecretEqualsInfoEnum;
import cn.handy.constants.secret.SecretSectsEnum;
import cn.handy.entity.UserSecret;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.Beans;
import cn.handy.utils.secret.SecretUtil;
import lombok.val;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

/**
 * @author hanshuai
 * @Description: {无名天书使用拦截器}
 * @date 2019/7/4 12:11
 */
public class SecretUseListener implements Listener {

    /**
     * 当玩家对一个对象或空气进行交互时触发本事件.
     *
     * @param event
     */
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        // 不是左键点击空气和方块取消事件
        if (event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }
        ItemStack item = event.getItem();
        if (item == null) {
            return;
        }
        // 循环判断是否为自定义秘籍
        for (ItemStack itemStack : BaseConstants.itemStackList) {
            if (SecretUtil.equalsInSet(item, itemStack, SecretEqualsInfoEnum.ABOUT_NAME, SecretEqualsInfoEnum.ABOUT_LORE)) {
                // 获取秘籍信息
                List<String> loreList = itemStack.getLore();
                if (loreList != null && loreList.size() > 0) {
                    String loreStr = loreList.get(loreList.size() - 1);
                    String[] lore = loreStr.split(",");
                    String secretId = lore[0].substring(5);
                    String sectsId = lore[1].substring(3);
                    if (BaseUtil.isNumeric(secretId) && BaseUtil.isNumeric(sectsId)) {
                        UserSecret userSecret = new UserSecret();
                        userSecret.setUserName(event.getPlayer().getName());
                        userSecret.setSectsId(SecretSectsEnum.getEnum(Integer.parseInt(sectsId)).getId());
                        userSecret.setSectsName(SecretSectsEnum.getEnum(Integer.parseInt(sectsId)).getName());
                        userSecret.setSecretId(Integer.parseInt(secretId));
                        userSecret.setSecretGarde(1);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                val rst = Beans.getBeans().getUserSecretService().set(userSecret);
                                event.getPlayer().sendMessage(rst);
                            }
                        }.runTaskAsynchronously(Manage.plugin);
                        item.setAmount(0);
                    } else {
                        event.getPlayer().sendMessage("这是本错误的功法,您学习功法失败,请联系腐竹");
                    }
                }
            }
        }
    }
}