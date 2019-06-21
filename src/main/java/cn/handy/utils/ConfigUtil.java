package cn.handy.utils;

import cn.handy.Manage;
import cn.handy.constants.BaseConfigCache;
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

        getHelpConfig();
        getLangConfig();
    }

    /**
     * 加载help文件
     */
    public static void getHelpConfig() {
        // 创建help
        if (BaseConfigCache.isHelp) {
            Manage.plugin.saveResource("help.yml", false);
            File helpFile = new File(Manage.plugin.getDataFolder(), "help.yml");
            FileConfiguration help = YamlConfiguration.loadConfiguration(helpFile);
            HelpConfig = help;
        }
    }

    /**
     * 加载lang文件
     */
    public static void getLangConfig() {
        Manage.plugin.saveResource("lang.yml", false);
        File langFile = new File(Manage.plugin.getDataFolder(), "lang.yml");
        FileConfiguration lang = YamlConfiguration.loadConfiguration(langFile);
        LangConfig = lang;
    }
}
