package cn.handy.command.spawn;

import cn.handy.Manage;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.config.ConfigUtil;
import lombok.val;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/7/9 10:04
 */
public class SetSpawnCommand extends Command {
    public SetSpawnCommand() {
        // 命令
        super("setSpawn");
        // 权限
        this.setPermission("handy.setSpawn");
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        if (!sender.hasPermission("handy.setSpawn")) {
            sender.sendMessage(ChatColor.RED + "你没有该命令的权限!");
            return true;
        }
        val rst = BaseUtil.isPlayer(sender);
        if (rst) {
            try {
                val player = (Player) sender;
                ConfigUtil.langConfig.set("spawn.world", player.getLocation().getWorld().getName());
                ConfigUtil.langConfig.set("spawn.x", player.getLocation().getX());
                ConfigUtil.langConfig.set("spawn.y", player.getLocation().getY());
                ConfigUtil.langConfig.set("spawn.z", player.getLocation().getZ());
                File langFile = new File(Manage.plugin.getDataFolder(), "lang.yml");
                ConfigUtil.langConfig.save(langFile);
                ConfigUtil.getLangConfig();
                player.sendMessage("设置spawn成功");
            } catch (IOException e) {
                e.printStackTrace();
                sender.sendMessage("设置spawn失败,请联系开发者");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "控制台不能使用该命令");
        }
        return true;
    }
}
