package cn.handy.command.secret;

import cn.handy.constants.BaseConstants;
import cn.handy.constants.secret.SecretListenerEnum;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.secret.SecretUtil;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * @author hanshuai
 * @Description: {功法命令}
 * @date 2019/7/8 12:39
 */
public class SecretAdminCommand extends Command {

    public SecretAdminCommand() {
        // 命令
        super("secretadmin");
        // 权限
        this.setPermission("handy.secretadmin");
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        if (!sender.hasPermission("handy.secretadmin")) {
            sender.sendMessage(ChatColor.RED + "你没有该命令的权限!");
            return true;
        }
        if (args != null && args.length > 0) {
            if (args[0].equalsIgnoreCase("give")) {
                val player = Bukkit.getServer().getPlayer(args[1]);
                PlayerInventory inventory = player.getInventory();
                inventory.addItem(BaseConstants.itemStackList.get(Integer.parseInt(args[2])));
            } else if (args[0].equalsIgnoreCase("giveHelp")) {
                val player = Bukkit.getServer().getPlayer(args[1]);
                PlayerInventory inventory = player.getInventory();
                inventory.addItem(SecretUtil.getSecretHelp());
            } else if (args[0].equalsIgnoreCase("giveKnowBook")) {
                val player = Bukkit.getServer().getPlayer(args[1]);
                PlayerInventory inventory = player.getInventory();
                inventory.addItem(SecretUtil.getKnowledgeBook());
            } else if (args[0].equalsIgnoreCase("giveNoCharBook")) {
                val player = Bukkit.getServer().getPlayer(args[1]);
                PlayerInventory inventory = player.getInventory();
                inventory.addItem(SecretUtil.getItemStack(SecretUtil.getRandom()));
            } else if (args[0].equalsIgnoreCase("see")) {
                val rst = BaseUtil.isPlayer(sender);
                if (rst) {
                    Player sendPlayer = (Player) sender;
                    PlayerInventory inv = sendPlayer.getInventory();
                    // 获取主手的物品
                    ItemStack itemStack = inv.getItemInMainHand();
                    val secretListenerEnum = SecretListenerEnum.getEnum(SecretUtil.getEvenId(itemStack));
                    if (secretListenerEnum != null) {
                        sender.sendMessage("该秘籍解锁事件为:" + secretListenerEnum.getName());
                    } else {
                        sender.sendMessage("未查询到对应的解锁事件");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "控制台不能使用该子命令");
                }
            } else {
                sender.sendMessage(BaseConstants.SECRET_MSG);
            }
        } else {
            sender.sendMessage(BaseConstants.SECRET_MSG);
        }
        return true;
    }
}
