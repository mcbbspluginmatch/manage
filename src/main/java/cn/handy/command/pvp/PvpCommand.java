package cn.handy.command.pvp;

import cn.handy.Manage;
import cn.handy.constants.BaseConstants;
import cn.handy.dao.pvp.IPvpService;
import cn.handy.entity.Pvp;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.Beans;
import lombok.val;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author hanshuai
 * @Description: {pvp管理}
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
        if (args != null && args.length == 1) {
            final Player sendPlayer = (Player) sender;
            final IPvpService pvpService = Beans.getBeans().getPvpService();
            final Pvp pvp = new Pvp();
            pvp.setUserName(sendPlayer.getName());
            pvp.setParticle(false);
            if (args[0].equalsIgnoreCase("on")) {
                pvp.setPvpStatus(true);
                BaseConstants.PvpMap.put(sendPlayer.getName(), true);
            } else if (args[0].equalsIgnoreCase("off")) {
                pvp.setPvpStatus(false);
                BaseConstants.PvpMap.put(sendPlayer.getName(), false);
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
        } else if (args != null && args.length == 2) {
            final Player sendPlayer = (Player) sender;
            final IPvpService pvpService = Beans.getBeans().getPvpService();
            if (args[0].equalsIgnoreCase("lizi") && args[1].equalsIgnoreCase("on")) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        val rst = pvpService.setParticle(sendPlayer.getName(), true);
                        if (rst) {
                            BaseConstants.PvpParticleMap.put(sendPlayer.getName(), true);
                            sendPlayer.sendMessage("开启粒子效果设置成功");
                        } else {
                            sendPlayer.sendMessage("开启粒子效果设置失败");
                        }
                    }
                }.runTaskAsynchronously(Manage.plugin);
            } else if (args[0].equalsIgnoreCase("lizi") && args[1].equalsIgnoreCase("off")) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        val rst = pvpService.setParticle(sendPlayer.getName(), false);
                        if (rst) {
                            BaseConstants.PvpParticleMap.put(sendPlayer.getName(), false);
                            sendPlayer.sendMessage("关闭粒子效果设置成功");
                        } else {
                            sendPlayer.sendMessage("关闭粒子效果设置失败");
                        }
                    }
                }.runTaskAsynchronously(Manage.plugin);
            }
        } else {
            sender.sendMessage(BaseConstants.PVP_HELP);
        }
        return true;
    }
}
