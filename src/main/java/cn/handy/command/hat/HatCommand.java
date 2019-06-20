package cn.handy.command.hat;

import cn.handy.utils.BaseUtil;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;

/**
 * @author hanshuai
 * @Description: {指令注册类}
 * @date 2019/6/20 10:42
 */
public class HatCommand extends Command {

    public HatCommand() {
        // 命令
        super("hat");
        // 权限
        this.setPermission("handy.hat");
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        val rst = BaseUtil.isPlayer(sender);
        if (rst) {
            Player player = (Player) sender;
            if (!player.hasPermission("handy.hat")){
                player.sendMessage(ChatColor.RED + "§c你没有该命令的权限!");
                return true;
            }
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
