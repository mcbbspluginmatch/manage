package cn.handy.command.spawn;

import cn.handy.Manage;
import cn.handy.constants.BaseConstants;
import cn.handy.entity.Spawn;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.Beans;
import lombok.val;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
            if (args != null && args.length > 0) {
                if (!BaseUtil.isNumeric(args[0])) {
                    sender.sendMessage(ChatColor.RED + "子参数只能为数字(1-10)");
                    return true;
                }
                Integer num = Integer.valueOf(args[0]);
                if (num < 1 && num > 10) {
                    sender.sendMessage("子参数只能为数字(1-10)");
                    return true;
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        val player = (Player) sender;
                        Spawn spawn = new Spawn();
                        spawn.setId(num);
                        spawn.setX(player.getLocation().getX());
                        spawn.setY(player.getLocation().getY());
                        spawn.setZ(player.getLocation().getZ());
                        spawn.setYaw(player.getLocation().getYaw());
                        spawn.setPitch(player.getLocation().getPitch());
                        spawn.setWorld(player.getLocation().getWorld().getName());
                        val b = Beans.getBeans().getSpawnService().set(spawn);
                        if (b) {
                            player.sendMessage("设置spawn成功");
                        } else {
                            player.sendMessage("设置spawn失败");
                        }
                    }
                }.runTaskAsynchronously(Manage.plugin);
            } else {
                sender.sendMessage(BaseConstants.SPAWN_MSG);
            }
        } else {
            sender.sendMessage(ChatColor.RED + "控制台不能使用该命令");
        }
        return true;
    }
}
