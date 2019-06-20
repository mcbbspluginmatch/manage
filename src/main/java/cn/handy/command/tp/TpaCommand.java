package cn.handy.command.tp;

import cn.handy.Manage;
import cn.handy.constants.BaseConstants;
import cn.handy.utils.BaseUtil;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * @author hanshuai
 * @Description: {指令注册类}
 * @date 2019/6/20 10:42
 */
public class TpaCommand extends Command {

    public TpaCommand() {
        // 命令
        super("tpa");
    }

    @Override
    public boolean execute(CommandSender sender, String label,  String[] args) {
        val rst = BaseUtil.isPlayer(sender);
        if (rst) {
            val sendPlayer = (Player) sender;
            // 判断是否冷却
            Long keepAlive;
            val tpaWaitTime = Manage.config.getLong("tpaWaitTime");
            if (BaseConstants.tpaWaitTime.containsKey(sendPlayer.getName())) {
                keepAlive = (System.currentTimeMillis() - BaseConstants.tpaWaitTime.get(sendPlayer.getName())) / 1000L;
                if (keepAlive < tpaWaitTime) {
                    sendPlayer.sendMessage(ChatColor.AQUA + "你必须等待" + (tpaWaitTime - keepAlive) + "秒后,才可以继续使用传送");
                    return true;
                }
            }
            // 进行传送
            if (args != null && args.length > 0) {
                val receivePlayer = Bukkit.getServer().getPlayer(args[0]);
                if (receivePlayer != null) {
                    if (sendPlayer.getName().equalsIgnoreCase(receivePlayer.getName())) {
                        sendPlayer.sendMessage(ChatColor.RED + "自己不能传送自己");
                    } else {
                        this.sendRequest(sendPlayer, receivePlayer);
                        BaseConstants.tpaWaitTime.put(sendPlayer.getName(), System.currentTimeMillis());
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

    /**
     * 发送传送请求
     *
     * @param sender
     * @param recipient
     */
    private void sendRequest(Player sender, Player recipient) {
        sender.sendMessage("传送请求已经发送给 " + recipient.getName() + ".");

        String sendTpAccept = "要接受传送,请输入" + ChatColor.GOLD + "/tpaccept" + ChatColor.RESET + ",";
        String sendTpDeny = "要拒绝传送,请输入" + ChatColor.GOLD + "/tpdeny" + ChatColor.RESET + ".";

        recipient.sendMessage(ChatColor.GOLD + sender.getName() + ChatColor.RESET + "发送了一个传送请求给你:\n"
                + sendTpAccept + sendTpDeny);

        BaseConstants.currentRequest.put(recipient.getName(), sender.getName());
    }
}
