package cn.handy.command.manage;

import cn.handy.constants.BaseConstants;
import cn.handy.utils.config.ConfigUtil;
import cn.handy.utils.secret.SecretUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/6/20 17:39
 */
public class ManageCommand extends Command {

    public ManageCommand() {
        // 命令
        super("manage");
        // 权限
        this.setPermission("handy.manage");
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        if (!sender.hasPermission("handy.manage")) {
            sender.sendMessage(ChatColor.RED + "你没有该命令的权限!");
            return true;
        }
        if (args != null && args.length == 2) {
            if (args[0].equals("reload") && args[1].equals("help")) {
                ConfigUtil.getHelpConfig();
                sender.sendMessage(ChatColor.AQUA + "manage重载help.yml成功!");
            } else if (args[0].equals("reload") && args[1].equals("lang")) {
                ConfigUtil.getLangConfig();
                sender.sendMessage(ChatColor.AQUA + "manage重载lang.yml成功!");
            } else if (args[0].equals("reload") && args[1].equals("secret")) {
                ConfigUtil.getSecretConfig();
                SecretUtil.getSecretConfig();
                sender.sendMessage(ChatColor.AQUA + "manage重载secret.yml成功!");
            }  else if (args[0].equals("reload") && args[1].equals("spawn")) {
                ConfigUtil.getSpawnConfig();
                sender.sendMessage(ChatColor.AQUA + "manage重载spawn.yml成功!");
            }else {
                sender.sendMessage(BaseConstants.MANAGE_MSG);
            }
        } else {
            sender.sendMessage(BaseConstants.MANAGE_MSG);
        }
        return true;
    }
}
