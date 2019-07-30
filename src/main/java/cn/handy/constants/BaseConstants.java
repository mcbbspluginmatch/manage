package cn.handy.constants;

import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     * spawn指令传送cdMap
     */
    public static Map<String, Long> spawnWaitTime = new HashMap();

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
    public static List<String> playerNameList = new ArrayList<>();

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
            "§e§m一一一一一一一§f[§eMANAGE管理§f]§e§m一一一一一一一\n" +
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
            "§e§m一一一一一一一§f[§e家设置§f]§e§m一一一一一一一\n" +
                    "§e/home [家名]     " + "§f回到对应的家中\n" +
                    "§e/setHome [家名]     " + "§f设置家";
    public final static String SPAWN_MSG =
            "§e§m一一一一一一一§f[§eSpawn设置§f]§e§m一一一一一一一\n" +
                    "§e/setSpawn [1-10]     " + "§f设置spawn位置1到10\n" +
                    "§e/子参数1,权限:     " + "§fhandy.spawn.one\n" +
                    "§e/子参数2,权限:     " + "§fhandy.spawn.two\n" +
                    "§e/子参数3,权限:     " + "§fhandy.spawn.three\n" +
                    "§e/子参数4,权限:     " + "§fhandy.spawn.four\n" +
                    "§e/子参数5,权限:     " + "§fhandy.spawn.five\n" +
                    "§e/子参数6,权限:     " + "§fhandy.spawn.six\n" +
                    "§e/子参数7,权限:     " + "§fhandy.spawn.seven\n" +
                    "§e/子参数8,权限:     " + "§fhandy.spawn.eight\n" +
                    "§e/子参数9,权限:     " + "§fhandy.spawn.nine\n" +
                    "§e/子参数10,权限:     " + "§fhandy.spawn.ten\n" +
                    "§e§m一一一一一一一§f[§e不给权限默认回到1§f]§e§m一一一一一一一";
    public final static String TP_MSG =
            "§e§m一一一一一一一§f[§eTp功能§f]§e§m一一一一一一一\n" +
                    "§e/tp [玩家名]     " + "§传送到玩家地点\n" +
                    "§e/tp [x] [y] [z]    " + "§传送到xyz坐标\n" +
                    "§e/tp [玩家A] [玩家B]     " + "§传送玩家A到玩家B坐标\n" +
                    "§e/tp [玩家名] [x] [y] [z]    " + "§传送玩家到xyz坐标";
    /**
     * 命令白名单正则
     */
    public final static Pattern[] COMMAND_WHITE_LISTS = new Pattern[]{Pattern.compile("/l(ogin)?(\\z| .*)"), Pattern.compile("/reg?(\\z| .*)")};

    /**
     * 数字正则
     */
    public final static Pattern NUMERIC = Pattern.compile("^-?\\d+(\\.\\d+)?$");

    /**
     * 金额正则
     */
    public final static Pattern MONEY = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
}
