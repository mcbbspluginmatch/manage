package cn.handy.utils;

import cn.handy.Manage;
import cn.handy.constants.BaseConfigCache;
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
    public static FileConfiguration config, HelpConfig, LangConfig;


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
        // 加载help
        getHelpConfig();
        // 加载lang
        getLangConfig();
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
            FileConfiguration help = YamlConfiguration.loadConfiguration(helpFile);
            HelpConfig = help;
        }
    }

    /**
     * 加载lang文件
     */
    public static void getLangConfig() {
        File langFile = new File(Manage.plugin.getDataFolder(), "lang.yml");
        if (!(langFile.exists())) {
            Manage.plugin.saveResource("lang.yml", false);
        }
        FileConfiguration lang = YamlConfiguration.loadConfiguration(langFile);
        LangConfig = lang;
    }


    /**
     * 保存各个独立模块开启状态
     */

    private static void saveConfigCache() {
        val isMessage = ConfigUtil.config.getBoolean("isMessage");
        val isUser = ConfigUtil.config.getBoolean("isUser");
        val isHat = ConfigUtil.config.getBoolean("isHat");
        val isHelp = ConfigUtil.config.getBoolean("isHelp");
        val isTp = ConfigUtil.config.getBoolean("isTp");
        val isGift = ConfigUtil.config.getBoolean("isGift");

        BaseConfigCache.isMessage = isMessage;
        BaseConfigCache.isUser = isUser;
        BaseConfigCache.isHat = isHat;
        BaseConfigCache.isHelp = isHelp;
        BaseConfigCache.isTp = isTp;
        BaseConfigCache.isGift = isGift;
    }
}
