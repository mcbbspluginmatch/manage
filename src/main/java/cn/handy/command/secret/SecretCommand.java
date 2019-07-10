package cn.handy.command.secret;

import cn.handy.Manage;
import cn.handy.constants.BaseConstants;
import cn.handy.entity.UserSecret;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.Beans;
import cn.handy.utils.gui.SecretGuiUtil;
import lombok.val;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/7/8 13:34
 */
public class SecretCommand extends Command {
    public SecretCommand() {
        // 命令
        super("secret");
        // 权限
        this.setPermission("handy.secret");
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        if (!sender.hasPermission("handy.secret")) {
            sender.sendMessage(ChatColor.RED + "你没有该命令的权限!");
            return true;
        }
        val rst = BaseUtil.isPlayer(sender);
        if (rst) {
            Player player = (Player) sender;
            Inventory inv = SecretGuiUtil.getInventory(player);
            BaseConstants.InventoryMap.put(player.getName(),inv);
            player.openInventory(inv);
        } else {
            if (args != null && args.length > 0) {
                String str = getStr(Beans.getBeans().getUserSecretService().findByUserName(args[0]));
                sender.sendMessage(str);
            }
        }
        return true;
    }

    private static String getStr(List<UserSecret> userSecretList) {
        if (userSecretList != null && userSecretList.size() > 0) {
            StringBuffer str = new StringBuffer();
            str.append("§e§m一一一一一一一§f[§e武林风云§f]§e§m一一一一一一一\n");
            str.append("§e门派:" + "§f" + userSecretList.get(0).getSectsName() + "\n");
            for (UserSecret userSecret : userSecretList) {
                str.append("§e功法:§f" + userSecret.getSecretName() + "     §e等级:§f" + userSecret.getSecretGarde() + "     §e效果:§f" + userSecret.getBuffName() + "\n");
            }
            return str.toString();
        }
        return "未查询到数据";
    }

}
