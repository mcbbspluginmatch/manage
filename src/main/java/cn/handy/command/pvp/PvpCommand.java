package cn.handy.command.pvp;

import cn.handy.Manage;
import cn.handy.constants.BaseConstants;
import cn.handy.constants.Beans;
import cn.handy.dao.pvp.IPvpService;
import cn.handy.entity.Pvp;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.particleEffect.ParticleEffectUtil;
import lombok.val;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
            return true;
        }
        if (label.equalsIgnoreCase("pvp")) {
            if (args != null && args.length == 1) {
                final Player sendPlayer = (Player) sender;
                final IPvpService pvpService = Beans.getBeans().getPvpService();
                final Pvp pvp = new Pvp();
                pvp.setUserName(sendPlayer.getName());
                pvp.setParticle(true);
                if (args[0].equalsIgnoreCase("on")) {
                    pvp.setPvpStatus(true);
                    BaseConstants.PvpMap.put(sendPlayer.getName().toLowerCase(), true);
                    ParticleEffectUtil.particleEffect(sendPlayer, Color.RED);
                } else if (args[0].equalsIgnoreCase("off")) {
                    pvp.setPvpStatus(false);
                    BaseConstants.PvpMap.put(sendPlayer.getName().toLowerCase(), false);
                    ParticleEffectUtil.particleEffect(sendPlayer, Color.WHITE);
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        val rst = pvpService.set(pvp);
                        if (rst) {
                            sendPlayer.sendMessage("设置成功");
                        } else {
                            sendPlayer.sendMessage("设置失败");
                        }
                    }
                }.runTaskAsynchronously(Manage.plugin);
            } else {
                sender.sendMessage(BaseConstants.PVP_HELP);
            }
        } else {
            sender.sendMessage(BaseConstants.PVP_HELP);
        }
        return true;
    }
}
