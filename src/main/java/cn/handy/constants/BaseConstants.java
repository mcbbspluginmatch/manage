package cn.handy.constants;

import cn.handy.entity.Spawn;
import cn.handy.entity.User;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
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
     * home指令传送cdMap
     */
    public static Map<String, Long> homeWaitTime = new HashMap();

    /**
     * back传送map
     */
    public static Map<String, Location> backMap = new HashMap();

    /**
     * back指令传送cdMap
     */
    public static Map<String, Long> backWaitTime = new HashMap();

    /**
     * 用户登录缓存信息
     */
    public static Set<User> userSet = new HashSet<User>();

    /**
     * 用户PVP缓存信息
     */
    public static Map<String, Boolean> PvpMap = new HashMap();

    /**
     * 用户PVP粒子开启状态缓存信息
     */
    public static Map<String, Boolean> PvpParticleMap = new HashMap();

    /**
     * 自定义的物品缓存信息
     */
    public static List<ItemStack> itemStackList = new ArrayList<>();

    /**
     * 自定义的物品缓存概率信息
     */
    public static List<Double> probabilityList = new ArrayList<>();

    /**
     * 死亡重生保存的武林秘籍缓存信息
     */
    public static Map<String, List<ItemStack>> dropMap = new HashMap<>();

    /**
     * help文本缓存信息
     */
    public static List<String> helpList = new ArrayList<>();

    /**
     * 用户功法箱子缓存信息
     */
    public static Map<String, Inventory> InventoryMap = new HashMap<>();

    /**
     * 用户登录位置缓存
     */
    public static Map<String, Location> userLoginLocationMap = new HashMap<>();

    /**
     * 用户登录状态
     */
    public static Map<String, Boolean> userLoginLocationStatus = new HashMap<>();

    /**
     * msgAdmin帮助语句
     */
    public final static String MSG_ADMIN_HELP =
            "§e§m一一一一一一一§f[§e个性消息§f]§e§m一一一一一一一\n" +
                    "§e/msgAdmin set [玩家] [进入提醒] [退出提醒]     " + "§f设置玩家进/退消息\n" +
                    "§e/msgAdmin del [玩家]     " + "§f删除玩家进/出消息\n" +
                    "§e/msgAdmin see [玩家]     " + "§f查询玩家进/出消息\n" +
                    "§f[进入/退出提醒]中可使用${player}来代替你的名字\n" +
                    "§e§m一一一一一一一§f[§e个性消息§f]§e§m一一一一一一一";
    /**
     * msg帮助语句
     */
    public final static String MSG_HELP =
            "§e§m一一一一一一一§f[§e个性消息§f]§e§m一一一一一一一\n" +
                    "§e/msg set [进入提醒] [退出提醒]     " + "§f设置自己进/退消息\n" +
                    "§e/msg del     " + "§f删除自己进/出消息\n" +
                    "§e/msg see     " + "§f查询自己进/出消息\n" +
                    "§f[进入/退出提醒]中可使用${player}来代替你的名字\n" +
                    "§e§m一一一一一一一§f[§e个性消息§f]§e§m一一一一一一一";

    public final static String MANAGE_MSG =
            "§e§m一一一一一一一§f[§eMANAGE§f]§e§m一一一一一一一\n" +
                    "§e/manage reload help     " + "§f重载本插件的help文本\n" +
                    "§e/manage reload lang     " + "§f重载本插件的lang文本\n" +
                    "§e/manage reload secret   " + "§f重载本插件的secret文本";

    public final static String PVP_HELP =
            "§e§m一一一一一一一§f[§ePVP管理§f]§e§m一一一一一一一\n" +
                    "§e/pvp on     " + "§f设置自己PVP为开启状态\n" +
                    "§e/pvp off     " + "§f设置自己PVP为关闭状态\n" +
                    "§e/pvp lizi on     " + "§f设置自己PVP粒子效果为开启状态\n" +
                    "§e/pvp lizi off     " + "§f设置自己PVP粒子效果为关闭状态";

    public final static String SECRET_MSG =
            "§e§m一一一一一一一§f[§e武林风云§f]§e§m一一一一一一一\n" +
                    "§e/secret give [玩家] [功法序号]     " + "§f给玩家对应序号的功法\n" +
                    "§e/secret giveHelp [玩家]     " + "§f给玩家武林风云帮助书\n" +
                    "§e/secret giveKnowBook [玩家]   " + "§f给玩家知识之书(合成配方书)\n" +
                    "§e/secret giveNoCharBook [玩家]   " + "§f给玩家随机事件的无字天书\n" +
                    "§e/secret see   " + "§f查询手上无字天书的解锁事件";

    public final static String HOME_MSG =
            "§e§m一一一一一一一§f[§e临江仙§f]§e§m一一一一一一一\n" +
                    "§e/home [家名]     " + "§f回到对应的家中\n" +
                    "§e/setHome [家名]     " + "§f设置家";
    /**
     * 命令白名单正则
     */
    public final static Pattern[] COMMAND_WHITE_LISTS = new Pattern[]{Pattern.compile("/l(ogin)?(\\z| .*)"), Pattern.compile("/reg?(\\z| .*)")};

    /**
     * 数字正则
     */
    public final static Pattern NUMERIC = Pattern.compile("^-?\\d+(\\.\\d+)?$");
}
