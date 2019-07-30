package cn.handy.command.spawn;

import cn.handy.Manage;
import cn.handy.constants.BaseConstants;
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
            val player = (Player) sender;
            // 判断是否冷却
            Long keepAlive;
            val spawnWaitTime = ConfigUtil.langConfig.getLong("spawnWaitTime");
            if (BaseConstants.spawnWaitTime.containsKey(player.getName())) {
                keepAlive = (System.currentTimeMillis() - BaseConstants.spawnWaitTime.get(player.getName())) / 1000L;
                if (keepAlive < spawnWaitTime) {
                    player.sendMessage(ChatColor.AQUA + "你必须等待" + (spawnWaitTime - keepAlive) + "秒后,才可以继续使用传送");
                    return true;
                }
            }

            val spawn = Beans.getBeans().getSpawnService().findById(BaseUtil.getSpawnPermission(player));
            if (spawn != null) {
                // 传送延迟
                val spawnDelayTime = ConfigUtil.langConfig.getLong("spawnDelayTime");
                if (spawnDelayTime > 0) {
                    player.sendMessage(ChatColor.GRAY + "" + spawnDelayTime + "秒后开始传送...");
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.teleport(BaseUtil.getLocation(spawn));
                        BaseConstants.spawnWaitTime.put(player.getName(), System.currentTimeMillis());
                    }
                }.runTaskLater(Manage.plugin, spawnDelayTime * 20);

            } else {
                player.sendMessage("op还没有设置过spawn");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "控制台不能使用该命令");
        }
        return true;
    }
}
