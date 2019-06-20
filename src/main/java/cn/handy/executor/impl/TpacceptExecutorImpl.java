package cn.handy.executor.impl;

import cn.handy.Main;
import cn.handy.constants.BaseConstants;
import cn.handy.executor.IExecutor;
import cn.handy.utils.BaseUtil;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/6/12 15:54
 */
public class TpacceptExecutorImpl implements IExecutor {

    @Override
    public Boolean command(CommandSender sender, Command cmd, String label, String[] args) {
        val rst = BaseUtil.isPlayer(sender);
        if (rst) {
            final Player player = (Player) sender;
            if (BaseConstants.currentRequest.containsKey(player.getName())) {
                String receiveName = BaseConstants.currentRequest.get(player.getName());
                final Player receivePlayer = Bukkit.getServer().getPlayer(receiveName);
                if (receivePlayer != null){
                    // 传送延迟
                    val tpaDelayTime = Main.config.getLong("tpaDelayTime");
                    sender.sendMessage("已接受"+receiveName+"的传送请求.");
                    receivePlayer.sendMessage(ChatColor.GRAY + "" + tpaDelayTime + "秒后开始传送...");
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            receivePlayer.teleport(player.getLocation());
                            BaseConstants.currentRequest.remove(player.getName());
                        }
                    }.runTaskLater(Main.plugin, tpaDelayTime * 20);
                }else {
                    sender.sendMessage(ChatColor.AQUA + receiveName+"现在不在线,传送取消.");
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
