package cn.handy.command.secret;

import cn.handy.constants.BaseConstants;
import cn.handy.utils.secret.SecretUtil;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
        if (args != null && args.length > 1) {
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
            } else {
                sender.sendMessage(BaseConstants.SECRET_MSG);
            }
        } else {
            sender.sendMessage(BaseConstants.SECRET_MSG);
        }
        return true;
    }
}
