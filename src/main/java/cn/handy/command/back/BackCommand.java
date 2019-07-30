package cn.handy.command.back;

import cn.handy.Manage;
import cn.handy.constants.BaseConstants;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.config.ConfigUtil;
import lombok.val;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author hanshuai
 * @Description: {指令注册类}
 * @date 2019/6/20 10:42
 */
public class BackCommand extends Command {

    public BackCommand() {
        // 命令
        super("back");
        // 权限
        this.setPermission("handy.back");
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        if (!sender.hasPermission("handy.back")) {
            sender.sendMessage(ChatColor.RED + "你没有该命令的权限!");
            return true;
        }
        val rst = BaseUtil.isPlayer(sender);
        if (rst) {
            Player player = (Player) sender;
            // 判断是否冷却
            Long keepAlive;
            val backWaitTime = ConfigUtil.langConfig.getLong("backWaitTime");
            if (BaseConstants.backWaitTime.containsKey(player.getName())) {
                keepAlive = (System.currentTimeMillis() - BaseConstants.backWaitTime.get(player.getName())) / 1000L;
                if (keepAlive < backWaitTime) {
                    player.sendMessage(ChatColor.AQUA + "你必须等待" + (backWaitTime - keepAlive) + "秒后,才可以继续使用传送");
                    return true;
                }
            }
            Location location = BaseConstants.backMap.get(player.getName());
            if (location != null) {
                // 传送延迟
                val backDelayTime = ConfigUtil.langConfig.getLong("backDelayTime");
                if (backDelayTime > 0) {
                    player.sendMessage(ChatColor.GRAY + "" + backDelayTime + "秒后开始传送...");
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.teleport(location);
                        BaseConstants.backWaitTime.put(player.getName(), System.currentTimeMillis());
                    }
                }.runTaskLater(Manage.plugin, backDelayTime * 20);
            } else {
                sender.sendMessage(ChatColor.AQUA + "没有需要回去的位置");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "控制台不能使用该命令");
        }
        return true;
    }
}
