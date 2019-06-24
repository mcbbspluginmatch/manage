package cn.handy.command.pvp;

import cn.handy.constants.BaseConstants;
import cn.handy.dao.pvp.IPvpService;
import cn.handy.dao.pvp.impl.PvpServiceImpl;
import cn.handy.entity.Pvp;
import cn.handy.utils.BaseUtil;
import lombok.val;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/6/21 13:51
 */
public class PvpCommand extends Command {

    public PvpCommand() {
        // 命令
        super("pvp");
        // 权限
        this.setPermission("handy.pvp");
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        if (!sender.hasPermission("handy.pvp")) {
            sender.sendMessage(ChatColor.RED + "你没有该命令的权限!");
            return true;
        }
        val isPlayer = BaseUtil.isPlayer(sender);
        if (!isPlayer) {
            sender.sendMessage(ChatColor.RED + "控制台不能使用该命令");
        }
        if (label.equalsIgnoreCase("pvp")) {
            if (args != null && args.length == 1) {
                Player sendPlayer = (Player) sender;
                IPvpService pvpService = new PvpServiceImpl();
                Pvp pvp = new Pvp();
                pvp.setUserName(sendPlayer.getName());
                if (args[0].equalsIgnoreCase("on")) {
                    pvp.setPvp(true);
                } else if (args[0].equalsIgnoreCase("off")) {
                    pvp.setPvp(false);
                }
                pvp.setParticles(true);
                val rst = pvpService.set(pvp);
                if (rst) {
                    sender.sendMessage("设置成功");
                } else {
                    sender.sendMessage("设置失败");
                }
            } else {
                sender.sendMessage(BaseConstants.PVP_HELP);
            }
        } else {
            sender.sendMessage(BaseConstants.PVP_HELP);
        }
        return true;
    }
}
