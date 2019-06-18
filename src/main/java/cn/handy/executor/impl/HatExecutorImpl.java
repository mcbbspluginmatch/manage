package cn.handy.executor.impl;

import cn.handy.executor.IExecutor;
import cn.handy.utils.BaseUtil;
import lombok.val;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * @author hanshuai
 * @Description: {hat指令}
 * @date 2019/6/12 17:25
 */
public class HatExecutorImpl implements IExecutor {

    @Override
    public Boolean command(CommandSender sender, Command cmd, String label, String[] args) {
        val rst = BaseUtil.isPlayer(sender);
        if (rst) {
            Player player = (Player) sender;
            PlayerInventory inv = player.getInventory();
            // 获取主手的物品
            ItemStack held = inv.getItemInMainHand();
            // 获取头盔的物品
            ItemStack helm = inv.getHelmet();
            if (held.getAmount() != 1 && held.getType() != Material.AIR) {
                sender.sendMessage(ChatColor.RED + "你的主手上必须有1个可以佩戴的物品");
            } else {
                // 设置头盔的物品
                inv.setHelmet(held);
                // 设置手上的物品
                inv.setItemInMainHand(helm);
                player.updateInventory();
            }
        } else {
            sender.sendMessage(ChatColor.RED + "控制台不能使用该命令");
        }
        return true;
    }
}
