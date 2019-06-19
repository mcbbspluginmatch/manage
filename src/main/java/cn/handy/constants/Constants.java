package cn.handy.constants;

import cn.handy.entity.User;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author hanshuai
 * @Description: {常量}
 * @date 2019/6/13 14:15
 */
public abstract class Constants {

    /**
     * tpa指令传送cdMap
     */
    public static Map<String, Long> tpaWaitTime = new HashMap();

    /**
     * tpa传送map
     */
    public static Map<String, String> currentRequest = new HashMap();

    /**
     * 用户登录缓存信息
     */
    public static Set<User> userSet = new HashSet<User>();

    /**
     * msg帮助语句
     */
    public final static String MSG_HELP =
            ChatColor.GOLD + "/msg set [玩家名] [进入提醒] [退出提醒]   " + ChatColor.AQUA + "设置玩家的进入和退出消息\n"
                    + ChatColor.GOLD + "/msg del [玩家名]        " + ChatColor.AQUA + "删除玩家的进入和退出消息\n"
                    + ChatColor.GOLD + "/msg see [玩家名]        " + ChatColor.AQUA + "查询玩家的进入和退出消息";

    /**
     * 包名
     */
    public final static String PACKAGE_NAME = "cn.handy.executor.impl.";

    /**
     * 命令白名单正则
     */
    public final static Pattern[] COMMAND_WHITE_LISTS = new Pattern[]{Pattern.compile("/l(ogin)?(\\z| .*)"), Pattern.compile("/reg?(\\z| .*)")};


    /**
     * 数字正则
     */
    public final static Pattern NUMERIC = Pattern.compile("[0-9]*");

}
