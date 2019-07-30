package cn.handy.utils.config;

import cn.handy.Manage;
import cn.handy.constants.BaseConfigCache;
import cn.handy.constants.BaseConstants;
import lombok.val;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/6/21 9:54
 */
public class ConfigUtil {
    public static FileConfiguration config, helpConfig, langConfig, secretConfig;

    /**
     * 初始化加载文件
     */
    public static void getConfig() {
        // 创建配置文件文件夹和配置文件
        if (!Manage.plugin.getDataFolder().exists()) {
            Manage.plugin.getDataFolder().mkdir();
        }
        File configFile = new File(Manage.plugin.getDataFolder(), "config.yml");
        if (!(configFile.exists())) {
            Manage.plugin.saveDefaultConfig();
        }
        Manage.plugin.reloadConfig();
        config = Manage.plugin.getConfig();
        // 保存cache
        saveConfigCache();

        // 加载lang
        getLangConfig();

        // 加载help
        if (BaseConfigCache.isHelp) {
            getHelpConfig();
        }
        // 加载Secret
        if (BaseConfigCache.isSecret) {
            getSecretConfig();
        }
    }

    /**
     * 加载help文件
     */
    public static void getHelpConfig() {
        // 创建help
        if (BaseConfigCache.isHelp) {
            File helpFile = new File(Manage.plugin.getDataFolder(), "help.yml");
            if (!(helpFile.exists())) {
                Manage.plugin.saveResource("help.yml", false);
            }
            helpConfig = YamlConfiguration.loadConfiguration(helpFile);
        }
        //加载 help缓存
        BaseConstants.helpList = ConfigUtil.helpConfig.getStringList("helps");
    }

    /**
     * 加载lang文件
     */
    public static void getLangConfig() {
        File langFile = new File(Manage.plugin.getDataFolder(), "lang.yml");
        if (!(langFile.exists())) {
            Manage.plugin.saveResource("lang.yml", false);
        }
        langConfig = YamlConfiguration.loadConfiguration(langFile);
    }

    /**
     * 加载secret文件
     */
    public static void getSecretConfig() {
        File secretFile = new File(Manage.plugin.getDataFolder(), "secret.yml");
        if (!(secretFile.exists())) {
            Manage.plugin.saveResource("secret.yml", false);
        }
        secretConfig = YamlConfiguration.loadConfiguration(secretFile);
    }

    /**
     * 保存各个独立模块开启状态
     */
    private static void saveConfigCache() {
        BaseConfigCache.isUseMySql = ConfigUtil.config.getBoolean("isUseMySql");
        BaseConfigCache.isReport = ConfigUtil.config.getBoolean("isReport");

        BaseConfigCache.isMessage = ConfigUtil.config.getBoolean("isMessage.isUse");
        BaseConfigCache.isMessage_msg = ConfigUtil.config.getBoolean("isMessage.permission.msg");;
        BaseConfigCache.isMessage_msgAdmin = ConfigUtil.config.getBoolean("isMessage.permission.msgAdmin");;

        BaseConfigCache.isUser = ConfigUtil.config.getBoolean("isUser.isUse");

        BaseConfigCache.isHat = ConfigUtil.config.getBoolean("isHat.isUse");
        BaseConfigCache.isHat_hat = ConfigUtil.config.getBoolean("isHat.permission.hat");

        BaseConfigCache.isHelp = ConfigUtil.config.getBoolean("isHelp.isUse");
        BaseConfigCache.isHelp_help = ConfigUtil.config.getBoolean("isHelp.permission.help");

        BaseConfigCache.isTp = ConfigUtil.config.getBoolean("isTp.isUse");
        BaseConfigCache.isTp_tp = ConfigUtil.config.getBoolean("isTp.permission.tp");
        BaseConfigCache.isTp_tpa = ConfigUtil.config.getBoolean("isTp.permission.tpa");

        BaseConfigCache.isGift =  ConfigUtil.config.getBoolean("isGift.isUse");
        BaseConfigCache.isGift_gift =  ConfigUtil.config.getBoolean("isGift.permission.gift");

        BaseConfigCache.isPvp = ConfigUtil.config.getBoolean("isPvp.isUse");
        BaseConfigCache.isPvp_pvp = ConfigUtil.config.getBoolean("isPvp.permission.pvp");

        BaseConfigCache.isSecret = ConfigUtil.config.getBoolean("isSecret.isUse");
        BaseConfigCache.isSecret_secret = ConfigUtil.config.getBoolean("isSecret.permission.secret");
        BaseConfigCache.isSecret_secretAdmin = ConfigUtil.config.getBoolean("isSecret.permission.secretAdmin");

        BaseConfigCache.isHome = ConfigUtil.config.getBoolean("isHome.isUse");
        BaseConfigCache.isHome_home = ConfigUtil.config.getBoolean("isHome.permission.home");
        BaseConfigCache.isHome_setHome = ConfigUtil.config.getBoolean("isHome.permission.setHome");

        BaseConfigCache.isBack = ConfigUtil.config.getBoolean("isBack.isUse");
        BaseConfigCache.isBack_back = ConfigUtil.config.getBoolean("isBack.permission.back");

        BaseConfigCache.isSpawn = ConfigUtil.config.getBoolean("isSpawn.isUse");
        BaseConfigCache.isSpawn_setSpawn = ConfigUtil.config.getBoolean("isSpawn.permission.setSpawn");
        BaseConfigCache.isSpawn_spawn = ConfigUtil.config.getBoolean("isSpawn.permission.spawn");

        BaseConfigCache.isColor = ConfigUtil.config.getBoolean("isColor.isUse");

        BaseConfigCache.isShowDamage = ConfigUtil.config.getBoolean("isShowDamage.isUse");

        BaseConfigCache.isDeathPenalty = ConfigUtil.config.getBoolean("isDeathPenalty.isUse");

        BaseConfigCache.isVault = ConfigUtil.config.getBoolean("isVault.isUse");
    }
}
