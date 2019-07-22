package cn.handy.command.spawn;

import cn.handy.utils.BaseUtil;
import cn.handy.utils.config.ConfigUtil;
import lombok.val;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            val player = (Player) sender;
            ConfigUtil.langConfig.set("spawn.world", player.getLocation().getWorld().getName());
            ConfigUtil.langConfig.set("spawn.x", player.getLocation().getX());
            ConfigUtil.langConfig.set("spawn.y", player.getLocation().getY());
            ConfigUtil.langConfig.set("spawn.z", player.getLocation().getZ());
            ConfigUtil.getLangConfig();
        } else {
            sender.sendMessage(ChatColor.RED + "控制台不能使用该命令");
        }
        return true;
    }
}
