package cn.handy.constants;

/**
 * @author hanshuai
 * @Description: {模块化功能保存,配置功能是否开启保存}
 * @date 2019/6/20 10:01
 */
public abstract class BaseConfigCache {

    /**
     * 是否开启mysql数据库
     */
    public static Boolean isUseMySql = false;

    /**
     * 是否上报信息
     */
    public static Boolean isReport = false;

    public static Boolean isUser = false;
    public static Boolean isUser_login = false;
    public static Boolean isUser_register = false;

    public static Boolean isMessage = false;
    public static Boolean isMessage_msg = false;
    public static Boolean isMessage_msgAdmin = false;

    public static Boolean isHat = false;
    public static Boolean isHat_hat = false;

    public static Boolean isHelp = false;
    public static Boolean isHelp_help = false;

    public static Boolean isTp = false;
    public static Boolean isTp_tp = false;
    public static Boolean isTp_tpa = false;

    public static Boolean isGift = false;
    public static Boolean isGift_gift = false;

    public static Boolean isPvp = false;
    public static Boolean isPvp_pvp = false;

    public static Boolean isSecret = false;
    public static Boolean isSecret_secret = false;
    public static Boolean isSecret_secretAdmin = false;

    public static Boolean isHome = false;
    public static Boolean isHome_setHome = false;
    public static Boolean isHome_home = false;

    public static Boolean isBack = false;
    public static Boolean isBack_back = false;

    public static Boolean isSpawn = false;
    public static Boolean isSpawn_setSpawn = false;
    public static Boolean isSpawn_spawn = false;

    public static Boolean isColor = false;
    public static Boolean isShowDamage = false;
    public static Boolean isDeathPenalty = false;

}

