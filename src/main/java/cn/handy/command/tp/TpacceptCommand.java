package cn.handy.command.tp;

import cn.handy.Manage;
import cn.handy.constants.BaseConstants;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.config.ConfigUtil;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author hanshuai
 * @Description: {指令注册类}
 * @date 2019/6/20 10:42
 */
public class TpacceptCommand extends Command {

    public TpacceptCommand() {
        // 命令
        super("tpaccept");
        // 权限
        this.setPermission("handy.tpa");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("handy.tpa")) {
            sender.sendMessage(ChatColor.RED + "你没有该命令的权限!");
            return true;
        }
        val rst = BaseUtil.isPlayer(sender);
        if (rst) {
            final Player player = (Player) sender;
            if (BaseConstants.currentRequest.containsKey(player.getName())) {
                String receiveName = BaseConstants.currentRequest.get(player.getName());
                final Player receivePlayer = Bukkit.getServer().getPlayer(receiveName);
                if (receivePlayer != null) {
                    // 传送延迟
                    val tpaDelayTime = ConfigUtil.langConfig.getLong("tpaDelayTime");
                    sender.sendMessage("已接受" + receiveName + "的传送请求.");
                    if (tpaDelayTime > 0){
                        receivePlayer.sendMessage(ChatColor.GRAY + "" + tpaDelayTime + "秒后开始传送...");
                    }
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            receivePlayer.teleport(player.getLocation());
                            BaseConstants.currentRequest.remove(player.getName());
                        }
                    }.runTaskLater(Manage.plugin, tpaDelayTime * 20);
                } else {
                    sender.sendMessage(ChatColor.AQUA + receiveName + "现在不在线,传送取消.");
                }
            } else {
                sender.sendMessage(ChatColor.AQUA + "您没有任何当前的tp请求.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "控制台不能使用该命令");
        }
        return true;
    }
}
