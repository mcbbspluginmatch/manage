package cn.handy.command.spawn;

import cn.handy.utils.BaseUtil;
import cn.handy.utils.config.ConfigUtil;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/7/9 10:04
 */
public class SpawnCommand extends Command {

    public SpawnCommand() {
        // 命令
        super("spawn");
        // 权限
        this.setPermission("handy.spawn");
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        if (!sender.hasPermission("handy.spawn")) {
            sender.sendMessage(ChatColor.RED + "你没有该命令的权限!");
            return true;
        }
        val rst = BaseUtil.isPlayer(sender);
        if (rst) {
            Player player = (Player) sender;
            String world = ConfigUtil.langConfig.getString("spawn.world");
            Double x = ConfigUtil.langConfig.getDouble("spawn.x");
            Double y = ConfigUtil.langConfig.getDouble("spawn.y");
            Double z = ConfigUtil.langConfig.getDouble("spawn.z");
            player.teleport(getSpawnLocation(world, x, y, z));
        } else {
            sender.sendMessage(ChatColor.RED + "控制台不能使用该命令");
        }
        return true;
    }

    private Location getSpawnLocation(String world, Double x, Double y, Double z) {
        return new Location(
                Bukkit.getWorld(world), x, y, z
        );
    }
}
