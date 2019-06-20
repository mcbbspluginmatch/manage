package cn.handy.command.gift;

import cn.handy.utils.BaseUtil;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
public class GiftCommand extends Command {

    public GiftCommand() {
        // 命令
        super("gift");
        // 权限
        this.setPermission("handy.gift");
        // 别名
        this.setAliases(Arrays.asList("g"));
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        if (!sender.hasPermission("handy.gift")){
            sender.sendMessage(ChatColor.RED + "§c你没有该命令的权限!");
            return true;
        }
        val rst = BaseUtil.isPlayer(sender);
        if (rst) {
            Player sendPlayer = (Player) sender;
            PlayerInventory inv = sendPlayer.getInventory();
            // 获取主手的物品
            ItemStack gift = inv.getItemInMainHand();
            if (gift.getAmount() < 1) {
                sendPlayer.sendMessage(ChatColor.RED + "你的主手上必须有物品");
            } else {
                // 如果有子参数为玩家就给这个玩家发送礼物
                if (args != null && args.length > 0){
                    val player = Bukkit.getServer().getPlayer(args[0]);
                    PlayerInventory inventory = player.getInventory();
                    inventory.addItem(gift);
                    player.updateInventory();
                    sender.sendMessage(ChatColor.AQUA+"礼物成功发给了"+player.getName());
                    player.sendMessage(ChatColor.AQUA+"你收到了"+sender.getName()+"的礼物");
                }else{
                    // 不然就读取现在的玩家给现在的玩家都发该礼物
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        PlayerInventory inventory = player.getInventory();
                        // 设置手上的物品
                        inventory.addItem(gift);
                        player.updateInventory();
                        player.sendMessage(ChatColor.AQUA+"你收到了"+sender.getName()+"的礼物");
                    }
                    sender.sendMessage(ChatColor.AQUA+"礼物成功发给了在线全部玩家");
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "控制台不能使用该命令");
        }
        return true;
    }
}
