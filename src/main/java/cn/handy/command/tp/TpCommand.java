package cn.handy.command.tp;

import cn.handy.utils.BaseUtil;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * @author hanshuai
 * @Description: {指令注册类}
 * @date 2019/6/20 10:42
 */
public class TpCommand extends Command {

    public TpCommand() {
        // 命令
        super("tp");
        // 权限
        this.setPermission("handy.tp");
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        if (!sender.hasPermission("handy.tp")){
            sender.sendMessage(ChatColor.RED + "§c你没有该命令的权限!");
            return true;
        }
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
