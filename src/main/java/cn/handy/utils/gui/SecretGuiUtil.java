package cn.handy.utils.gui;

import cn.handy.entity.UserSecret;
import cn.handy.utils.Beans;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/7/10 10:51
 */
public class SecretGuiUtil {

    /**
     * 生成武林风云帮助menu
     *
     * @param player
     * @return
     */
    public static Inventory getInventory(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "§f[§e武林风云§f]");
        val userSecrets = Beans.getBeans().getUserSecretService().findByUserName(player.getName());
        inv.setItem(4, getItemStack(player));
        int i = 18;
        for (ItemStack itemStack : getSecretBook(userSecrets)) {
            inv.setItem(i, itemStack);
            i++;
        }
        return inv;
    }

    /**
     * 生成玩家
     *
     * @param player
     * @return
     */
    private static ItemStack getItemStack(Player player) {
        String sectsName = Beans.getBeans().getUserSecretService().findSectsNameByUserName(player.getName());
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(player.getName());
        meta.setLore(Arrays.asList("§e所属门派:§f" + sectsName));
        item.setItemMeta(meta);
        return item;
    }

    /**
     * 生成功法书
     *
     * @param userSecretList
     * @return
     */
    private static List<ItemStack> getSecretBook(List<UserSecret> userSecretList) {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (UserSecret userSecret : userSecretList) {
            ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(userSecret.getSecretName());
            meta.setLore(Arrays.asList(
                    "§e等级:§f" + userSecret.getSecretGarde(),
                    "§e效果:§f" + userSecret.getBuffName()));
            item.setItemMeta(meta);
            itemStackList.add(item);
        }
        return itemStackList;
    }
}
