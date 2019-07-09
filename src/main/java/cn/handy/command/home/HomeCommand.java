package cn.handy.command.home;

import cn.handy.Manage;
import cn.handy.constants.BaseConstants;
import cn.handy.entity.Home;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.Beans;
import cn.handy.utils.config.ConfigUtil;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/7/9 10:04
 */
public class HomeCommand extends Command {

    public HomeCommand() {
        // 命令
        super("home");
        // 权限
        this.setPermission("handy.home");
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        if (!sender.hasPermission("handy.home")) {
            sender.sendMessage(ChatColor.RED + "你没有该命令的权限!");
            return true;
        }
        val rst = BaseUtil.isPlayer(sender);
        if (rst) {
            if (args != null && args.length > 0) {
                Player player = (Player) sender;
                // 判断是否冷却
                Long keepAlive;
                val homeWaitTime = ConfigUtil.langConfig.getLong("homeWaitTime");
                if (BaseConstants.homeWaitTime.containsKey(player.getName())) {
                    keepAlive = (System.currentTimeMillis() - BaseConstants.homeWaitTime.get(player.getName())) / 1000L;
                    if (keepAlive < homeWaitTime) {
                        player.sendMessage(ChatColor.AQUA + "你必须等待" + (homeWaitTime - keepAlive) + "秒后,才可以继续使用传送");
                        return true;
                    }
                }
                // 传送延迟
                val homeDelayTime = ConfigUtil.langConfig.getLong("homeDelayTime");
                player.sendMessage(ChatColor.GRAY + "" + homeDelayTime + "秒后开始传送...");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Home home = Beans.getBeans().getHomeService().findByUserNameAndHomeName(player.getName(), args[0]);
                        if (home.getId() > 0) {
                            player.teleport(getHomeLocation(home));
                            player.sendMessage("回家成功");
                            BaseConstants.homeWaitTime.put(player.getName(), System.currentTimeMillis());
                        } else {
                            player.sendMessage("你还没有设置过家");
                        }
                    }
                }.runTaskLater(Manage.plugin, homeDelayTime * 20);
            } else {
                sender.sendMessage(BaseConstants.HOME_MSG);
            }
        } else {
            sender.sendMessage(ChatColor.RED + "控制台不能使用该命令");
        }
        return true;
    }

    private Location getHomeLocation(Home home) {
        return new Location(
                Bukkit.getWorld(home.getWorld()), home.getX(), home.getY(), home.getZ(), home.getYaw(), home.getPitch()
        );
    }
}
