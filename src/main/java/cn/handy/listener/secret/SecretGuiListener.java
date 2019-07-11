package cn.handy.listener.secret;

import cn.handy.constants.BaseConstants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/7/10 11:53
 */
public class SecretGuiListener implements Listener {

    /**
     * gui打开箱子监听
     *
     * @param event
     */
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();

        if (inventory.equals(BaseConstants.InventoryMap.get(player.getName()))) {
            event.setCancelled(true);
        }
    }

    /**
     * gui关闭箱子监听
     *
     * @param event
     */
    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        if (inventory.equals(BaseConstants.InventoryMap.get(player.getName()))) {
            BaseConstants.InventoryMap.remove(player.getName());
        }
    }

    /**
     * 退出游戏事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerQuitGame(PlayerQuitEvent event) {
        BaseConstants.InventoryMap.remove(event.getPlayer().getName());
    }
}
