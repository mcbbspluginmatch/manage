package cn.handy.command.login;

import cn.handy.constants.BaseConfigCache;
import cn.handy.constants.BaseConstants;
import cn.handy.dao.user.IUserService;
import cn.handy.dao.user.impl.UserServiceImpl;
import lombok.val;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Date;

/**
 * @author hanshuai
 * @Description: {指令注册类}
 * @date 2019/6/20 10:42
 */
public class LoginCommand extends Command {

    public LoginCommand() {
        // 命令
        super("login");
        // 别名
        this.setAliases(Arrays.asList("l"));
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
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
        return true;
    }
}