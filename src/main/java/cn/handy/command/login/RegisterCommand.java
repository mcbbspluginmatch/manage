package cn.handy.command.login;

import cn.handy.constants.BaseConstants;
import cn.handy.entity.User;
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
public class RegisterCommand extends Command {

    public RegisterCommand() {
        // 命令
        super("register");
        // 别名
        this.setAliases(Arrays.asList("reg"));
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        val sendPlayer = (Player) sender;
        if (args != null && args.length == 2) {
            if (args[0].equals(args[1])) {
                val userService = Beans.getBeans().getUserService();
                User user = new User();
                user.setUserName(sendPlayer.getName().toLowerCase());
                user.setRealName(sendPlayer.getName());
                //没有进行哈希处理 密码明文储存 - a39
                user.setPassWord(args[0]);
                user.setRegIp(sendPlayer.getAddress().getAddress().getHostAddress());
                user.setRegDate(new Date());
                user.setLoginIp(sendPlayer.getAddress().getAddress().getHostAddress());
                user.setLoginDate(new Date());
                // 跟 Login 那个有大致相同的问题
                // 看看 AuthMe 写了多少代码，还是对用户安全多上上心 —— 754503921
                val rst = userService.register(user);
                if (rst) {
                    List<String> loginMessage = ConfigUtil.langConfig.getStringList("loginMessage");
                    for (String str : loginMessage) {
                        sendPlayer.sendMessage(BaseUtil.replaceChatColorAndName(str, sendPlayer.getName()));
                    }
                    BaseConstants.playerNameList.add(user.getUserName());
                } else {
                    sender.sendMessage("注册失败");
                }
            } else {
                sender.sendMessage("俩次密码不一致,请重新输入");
            }
        } else {
            sender.sendMessage("注册参数有误,请重新输入");
        }
        return true;
    }
}
