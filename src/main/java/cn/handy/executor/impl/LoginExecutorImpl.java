package cn.handy.executor.impl;

import cn.handy.Main;
import cn.handy.constants.BaseConfigCache;
import cn.handy.constants.BaseConstants;
import cn.handy.dao.user.IUserService;
import cn.handy.dao.user.impl.UserServiceImpl;
import cn.handy.executor.IExecutor;
import lombok.val;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;

/**
 * @author hanshuai
 * @Description: {登录}
 * @date 2019/6/17 14:54
 */
public class LoginExecutorImpl implements IExecutor {

    /**
     * 登录
     *
     * @param sender 发送人
     * @param cmd    命令
     * @param label  命令
     * @param args   参数
     * @return
     */
    @Override
    public Boolean command(final CommandSender sender, Command cmd, String label, final String[] args) {
        new BukkitRunnable() {
            @Override
            public void run() {
                // 判断是否开启User功能
                if (BaseConfigCache.isUser) {
                    val sendPlayer = (Player) sender;
                    if (args != null && args.length == 1) {
                        IUserService userService = new UserServiceImpl();
                        val user = userService.login(sendPlayer.getName().toLowerCase(), args[0]);
                        if (user.getId() != null) {
                            sender.sendMessage("登录成功");
                            BaseConstants.userSet.add(user);
                            // 保存本次登录ip和时间
                            user.setLoginDate(new Date());
                            user.setLoginIp(sendPlayer.getAddress().getAddress().getHostAddress());
                            userService.update(user);
                        } else {
                            sender.sendMessage("密码错误,请重新输入");
                        }
                    } else {
                        sender.sendMessage("登录参数有误,请重新输入");
                    }
                } else {
                    sender.sendMessage("未启用数据库或该功能,该命令无效");
                }
            }
        }.runTaskAsynchronously(Main.plugin);
        return true;
    }
}
