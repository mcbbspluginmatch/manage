package cn.handy.command.reload;

import cn.handy.Manage;
import cn.handy.constants.BaseConstants;
import cn.handy.utils.ConfigUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/6/20 17:39
 */
public class ReloadCommand extends Command {

    public ReloadCommand() {
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
                File helpFile = new File(Manage.plugin.getDataFolder(), "help.yml");
                FileConfiguration lang = YamlConfiguration.loadConfiguration(helpFile);
                ConfigUtil.HelpConfig = lang;
                sender.sendMessage(ChatColor.AQUA + "manage重载本插件的help文本成功!");
            } else {
                sender.sendMessage(BaseConstants.MANAGE_MSG);
            }
        } else {
            sender.sendMessage(BaseConstants.MANAGE_MSG);
        }
        return true;
    }
}
