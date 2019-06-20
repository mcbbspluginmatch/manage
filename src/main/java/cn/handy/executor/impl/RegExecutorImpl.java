package cn.handy.executor.impl;

import cn.handy.Main;
import cn.handy.constants.BaseConfigCache;
import cn.handy.constants.BaseConstants;
import cn.handy.dao.user.IUserService;
import cn.handy.dao.user.impl.UserServiceImpl;
import cn.handy.entity.User;
import cn.handy.executor.IExecutor;
import lombok.val;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/6/18 12:48
 */
public class RegExecutorImpl implements IExecutor {

    /**
     * 注册
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
                // 判断是否开启user功能
                if (BaseConfigCache.isUser) {
                    val sendPlayer = (Player) sender;
                    if (args != null && args.length == 2) {
                        if (args[0].equals(args[1])) {
                            IUserService userService = new UserServiceImpl();
                            User user = new User();
                            user.setUserName(sendPlayer.getName().toLowerCase());
                            user.setRealName(sendPlayer.getName());
                            user.setPassWord(args[0]);
                            user.setRegIp(sendPlayer.getAddress().getAddress().getHostAddress());
                            user.setRegDate(new Date());
                            user.setLoginIp(sendPlayer.getAddress().getAddress().getHostAddress());
                            user.setLoginDate(new Date());
                            val rst = userService.register(user);
                            if (rst) {
                                sender.sendMessage("注册成功");
                                BaseConstants.userSet.add(user);
                            } else {
                                sender.sendMessage("注册失败");
                            }
                        } else {
                            sender.sendMessage("俩次密码不一致,请重新输入");
                        }
                    } else {
                        sender.sendMessage("注册参数有误,请重新输入");
                    }
                } else {
                    sender.sendMessage("未启用数据库或该功能,该命令无效");
                }
            }
        }.runTaskAsynchronously(Main.plugin);
        return true;
    }
}
