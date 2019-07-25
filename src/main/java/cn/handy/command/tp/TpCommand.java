package cn.handy.command.tp;

import cn.handy.constants.BaseConstants;
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
        if (!sender.hasPermission("handy.tp")) {
            sender.sendMessage(ChatColor.RED + "你没有该命令的权限!");
            return true;
        }
        val rst = BaseUtil.isPlayer(sender);
        // tp到玩家坐标
        if (args != null && args.length == 1) {
            if (!rst) {
                sender.sendMessage(ChatColor.RED + "控制台不能使用该命令");
            }
            Player sendPlayer = (Player) sender;
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
            // tp 玩家a到玩家b
        } else if (args != null && args.length == 2) {
            Player player1 = Bukkit.getServer().getPlayer(args[0]);
            Player player2 = Bukkit.getServer().getPlayer(args[1]);
            if (player1 != null && player2 != null) {
                player1.teleport(player2.getLocation());
                sender.sendMessage(ChatColor.GOLD + "传送成功");
            } else {
                sender.sendMessage(ChatColor.RED + "你只能向在线玩家发送传送请求!");
            }
            // tp 到 xyz
        } else if (args != null && args.length == 3) {
            if (!rst) {
                sender.sendMessage(ChatColor.RED + "控制台不能使用该命令");
            }
            Player sendPlayer = (Player) sender;
            val location = BaseUtil.getLocation(sendPlayer.getLocation().getWorld().getName(), Double.valueOf(args[0]), Double.valueOf(args[1]), Double.valueOf(args[2]));
            sendPlayer.teleport(location);
            sendPlayer.sendMessage(ChatColor.GOLD + "传送成功");
            // tp 玩家到xyz
        } else if (args != null && args.length == 4) {
            Player player = Bukkit.getServer().getPlayer(args[0]);
            if (player != null) {
                val location = BaseUtil.getLocation(player.getLocation().getWorld().getName(), Double.valueOf(args[1]), Double.valueOf(args[2]), Double.valueOf(args[3]));
                player.teleport(location);
                sender.sendMessage(ChatColor.GOLD + "传送成功");
            } else {
                sender.sendMessage(ChatColor.RED + "你只能向在线玩家发送传送请求!");
            }
        } else {
            sender.sendMessage(BaseConstants.TP_MSG);
        }
        return true;
    }
}
