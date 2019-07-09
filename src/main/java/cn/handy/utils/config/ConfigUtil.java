package cn.handy.utils.config;

import cn.handy.Manage;
import cn.handy.constants.BaseConfigCache;
import cn.handy.constants.BaseConstants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.val;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

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
        String jsonArray = ConfigUtil.helpConfig.getString("helps");
        Gson gson = new Gson();
        try {
            BaseConstants.helpList = gson.fromJson(jsonArray, new TypeToken<List<String>>() {
            }.getType());
        } catch (Exception e) {
            BaseConstants.helpList.add(ChatColor.RED + "help文本加载错误,请联系腐竹修改");
            Manage.plugin.getLogger().info("help文本加载错误,请修改后输入/manage reload help重新加载");
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
        val isUseMySql = ConfigUtil.config.getBoolean("isUseMySql");
        val isMessage = ConfigUtil.config.getBoolean("isMessage");
        val isUser = ConfigUtil.config.getBoolean("isUser");
        val isHat = ConfigUtil.config.getBoolean("isHat");
        val isHelp = ConfigUtil.config.getBoolean("isHelp");
        val isTp = ConfigUtil.config.getBoolean("isTp");
        val isGift = ConfigUtil.config.getBoolean("isGift");
        val isPvp = ConfigUtil.config.getBoolean("isPvp");
        val isSecret = ConfigUtil.config.getBoolean("isSecret");
        val isHome = ConfigUtil.config.getBoolean("isHome");
        val isBack = ConfigUtil.config.getBoolean("isBack");
        val isSignChange = ConfigUtil.config.getBoolean("isSignChange");

        BaseConfigCache.isUseMySql = isUseMySql;
        BaseConfigCache.isMessage = isMessage;
        BaseConfigCache.isUser = isUser;
        BaseConfigCache.isHat = isHat;
        BaseConfigCache.isHelp = isHelp;
        BaseConfigCache.isTp = isTp;
        BaseConfigCache.isGift = isGift;
        BaseConfigCache.isPvp = isPvp;
        BaseConfigCache.isSecret = isSecret;
        BaseConfigCache.isHome = isHome;
        BaseConfigCache.isBack = isBack;
        BaseConfigCache.isSignChange = isSignChange;
    }
}
