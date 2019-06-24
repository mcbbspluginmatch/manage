package cn.handy.constants;

import cn.handy.entity.User;

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
public abstract class BaseConstants {

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
     * 用户PVP缓存信息
     */
    public static Map<String, Boolean> PvpMap = new HashMap();

    /**
     * msgAdmin帮助语句
     */
    public final static String MSG_ADMIN_HELP =
            "§e§m一一一一一一一§f[§e个性消息§f]§e§m一一一一一一一\n" +
                    "§e/msgadmin set [玩家] [进入提醒] [退出提醒]     " + "§f设置玩家进/退消息\n" +
                    "§e/msgadmin del [玩家]     " + "§f删除玩家进/出消息\n" +
                    "§e/msgadmin see [玩家]     " + "§f查询玩家进/出消息\n" +
                    "§e§m一一一一一一一§f[§e个性消息§f]§e§m一一一一一一一";
    /**
     * msg帮助语句
     */
    public final static String MSG_HELP =
            "§e§m一一一一一一一§f[§e个性消息§f]§e§m一一一一一一一\n" +
                    "§e/msg set [进入提醒] [退出提醒]     " + "§f设置自己进/退消息\n" +
                    "§e/msg del     " + "§f删除自己进/出消息\n" +
                    "§e/msg see     " + "§f查询自己进/出消息\n" +
                    "§e§m一一一一一一一§f[§e个性消息§f]§e§m一一一一一一一";

    public final static String MANAGE_MSG =
            "§e§m一一一一一一一§f[§eMANAGE§f]§e§m一一一一一一一\n" +
                    "§e/manage reload help     " + "§f重载本插件的help文本\n" +
                    "§e/manage reload lang     " + "§f重载本插件的lang文本";


    public final static String PVP_HELP =
            "§e§m一一一一一一一§f[§ePVP管理§f]§e§m一一一一一一一\n" +
                    "§e/pvp on     " + "§f设置自己PVP为开启状态\n" +
                    "§e/pvp off     " + "§f设置自己PVP为关闭状态";

    /**
     * 命令白名单正则
     */
    public final static Pattern[] COMMAND_WHITE_LISTS = new Pattern[]{Pattern.compile("/l(ogin)?(\\z| .*)"), Pattern.compile("/reg?(\\z| .*)")};


    /**
     * 数字正则
     */
    public final static Pattern NUMERIC = Pattern.compile("[0-9]*");

}
