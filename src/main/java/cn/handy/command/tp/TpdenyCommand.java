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

import java.util.Arrays;

/**
 * @author hanshuai
 * @Description: {指令注册类}
 * @date 2019/6/20 10:42
 */
public class TpdenyCommand extends Command {

    public TpdenyCommand() {
        // 命令
        super("tpdeny");
        // 权限
        this.setPermission("handy.tpa");
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        if (!sender.hasPermission("handy.tpa")) {
            sender.sendMessage(ChatColor.RED + "你没有该命令的权限!");
            return true;
        }
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
