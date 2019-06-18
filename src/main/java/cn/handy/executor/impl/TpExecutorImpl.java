package cn.handy.executor.impl;

import cn.handy.executor.IExecutor;
import cn.handy.utils.BaseUtil;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author hanshuai
 * @Description: {tp方法}
 * @date 2019/6/12 14:43
 */
public class TpExecutorImpl implements IExecutor {

    @Override
    public Boolean command(CommandSender sender, Command cmd, String label, final String[] args) {
        val rst = BaseUtil.isPlayer(sender);
        if (rst) {
            Player sendPlayer = (Player) sender;
            if (args != null && args.length > 0) {
                Player receivePlayer = Bukkit.getServer().getPlayer(args[0]);
                if (receivePlayer != null) {
                    if (sendPlayer.getName().equalsIgnoreCase(receivePlayer.getName())) {
                        sendPlayer.sendMessage(ChatColor.RED + "自己不能传送自己");
                    } else {
                        Location location = receivePlayer.getLocation();
                        sendPlayer.teleport(location);
                        sendPlayer.sendMessage(ChatColor.GOLD + "传送成功");
                    }
                } else {
                    sendPlayer.sendMessage(ChatColor.AQUA + "你只能向在线玩家发送传送请求!");
                }
            } else {
                return false;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "控制台不能使用该命令");
        }
        return true;
    }
}
