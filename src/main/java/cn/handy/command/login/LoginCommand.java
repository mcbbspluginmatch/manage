package cn.handy.command.login;

import cn.handy.constants.BaseConfigCache;
import cn.handy.constants.BaseConstants;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.Beans;
import cn.handy.utils.config.ConfigUtil;
import lombok.val;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
        val sendPlayer = (Player) sender;
        if (args != null && args.length == 1) {
            val userService = Beans.getBeans().getUserService();
            // 主线程上的数据库操作
            // 保存明文密码
            // 没有对暴力破解进行防护
            // 登录后可以再次登录
            // 理论上一直 /login 可以把服卡死 —— 754503921
            val user = userService.login(sendPlayer.getName().toLowerCase(), args[0]);
            if (user != null) {
                List<String> loginMessage = ConfigUtil.langConfig.getStringList("loginMessage");
                for (String str : loginMessage) {
                    sendPlayer.sendMessage(BaseUtil.replaceChatColorAndName(str, sendPlayer.getName()));
                }
                BaseConstants.playerNameList.add(user.getUserName());
                // 保存本次登录ip和时间
                user.setLoginDate(new Date());
                user.setLoginIp(sendPlayer.getAddress().getAddress().getHostAddress());
                userService.update(user);
                // 回到之前位置
                if (BaseConfigCache.isSpawn) {
                    val location = BaseConstants.userLoginLocationMap.get(sendPlayer.getName());
                    sendPlayer.teleport(location);
                    BaseConstants.userLoginLocationMap.remove(sendPlayer.getName());
                }
            } else {
                sendPlayer.sendMessage("密码错误,请重新输入");
            }
        } else {
            sendPlayer.sendMessage("登录参数有误,请重新输入");
        }
        return true;
    }
}
