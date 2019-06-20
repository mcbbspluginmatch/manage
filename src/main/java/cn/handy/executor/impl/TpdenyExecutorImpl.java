package cn.handy.executor.impl;

import cn.handy.constants.BaseConstants;
import cn.handy.executor.IExecutor;
import cn.handy.utils.BaseUtil;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/6/12 16:03
 */
public class TpdenyExecutorImpl implements IExecutor {

    @Override
    public Boolean command(CommandSender sender, Command cmd, String label, String[] args) {
        val rst = BaseUtil.isPlayer(sender);
        if (rst) {
            Player player = (Player) sender;
            if (BaseConstants.currentRequest.containsKey(player.getName())) {
                String receiveName = BaseConstants.currentRequest.get(player.getName());
                Player receivePlayer = Bukkit.getServer().getPlayer(receiveName);
                player.sendMessage("你已拒绝" + receivePlayer.getName() + "的传送");
                receivePlayer.sendMessage(ChatColor.GRAY + player.getName() + "拒绝你的传送...");
                BaseConstants.currentRequest.remove(player.getName());
            } else {
                sender.sendMessage(ChatColor.AQUA + "您没有任何当前的tp请求.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "控制台不能使用该命令");
        }
        return true;
    }
}
