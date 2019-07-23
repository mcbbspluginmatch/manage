package cn.handy.command.home;

import cn.handy.Manage;
import cn.handy.constants.BaseConstants;
import cn.handy.entity.Home;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.Beans;
import cn.handy.utils.config.ConfigUtil;
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
public class SetHomeCommand extends Command {
    public SetHomeCommand() {
        // 命令
        super("setHome");
        // 权限
        this.setPermission("handy.setHome");
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        if (!sender.hasPermission("handy.setHome")) {
            sender.sendMessage(ChatColor.RED + "你没有该命令的权限!");
            return true;
        }
        val rst = BaseUtil.isPlayer(sender);
        if (rst) {
            if (args != null && args.length > 0) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Player player = (Player) sender;
                        Integer count = Beans.getBeans().getHomeService().findByCount(player.getName());
                        val homeCount = ConfigUtil.langConfig.getInt("homeCount");
                        if (count <= homeCount) {
                            Home home = new Home();
                            home.setUserName(player.getName());
                            home.setHomeName(args[0]);
                            home.setX(player.getLocation().getX());
                            home.setY(player.getLocation().getY());
                            home.setZ(player.getLocation().getZ());
                            home.setYaw(player.getLocation().getYaw());
                            home.setPitch(player.getLocation().getPitch());
                            home.setWorld(player.getLocation().getWorld().getName());
                            Boolean rst = Beans.getBeans().getHomeService().set(home);
                            if (rst) {
                                player.sendMessage("设置成功");
                            } else {
                                player.sendMessage("设置失败");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "您的家已经超出上限");
                        }
                    }
                }.runTaskAsynchronously(Manage.plugin);
            } else {
                sender.sendMessage(BaseConstants.HOME_MSG);
            }
        } else {
            sender.sendMessage(ChatColor.RED + "控制台不能使用该命令");
        }
        return true;
    }
}
