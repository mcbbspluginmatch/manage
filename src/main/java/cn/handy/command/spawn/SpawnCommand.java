package cn.handy.command.spawn;

import cn.handy.utils.BaseUtil;
import cn.handy.utils.Beans;
import lombok.val;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            val spawn = Beans.getBeans().getSpawnService().findById(BaseUtil.getSpawnPermission(player));
            if (spawn != null) {
                player.teleport(BaseUtil.getLocation(spawn));
            } else {
                player.sendMessage("op还没有设置过spawn");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "控制台不能使用该命令");
        }
        return true;
    }
}
