package cn.handy.utils;

import cn.handy.constants.BaseConfigCache;
import cn.handy.constants.BaseConstants;
import cn.handy.dao.user.impl.UserServiceImpl;
import cn.handy.entity.User;
import lombok.val;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;

/**
 * @author hanshuai
 * @Description: {常用方法}
 * @date 2019/6/14 10:15
 */
public class BaseUtil {

    /**
     * 判断是否为玩家
     *
     * @param sender
     * @return true是
     */
    public static Boolean isPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public static Boolean isNumeric(String str) {
        Matcher isNum = BaseConstants.NUMERIC.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
