package cn.handy;

import cn.handy.command.EnableCommand;
import cn.handy.constants.BaseConfigCache;
import cn.handy.listener.ManageListener;
import cn.handy.utils.MysqlManagerUtil;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

/**
 * @author hanshuai
 * @Description: {主方法}
 * @date 2019/5/17 16:06
 */
public class Manage extends JavaPlugin {
    public static FileConfiguration config;
    public static FileConfiguration HelpConfig;
    public static Plugin plugin;

    /**
     * 启用插件时调用
     */
    @Override
    public void onEnable() {
        plugin = this;
        // 创建配置文件文件夹和配置文件
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File configFile = new File(getDataFolder(), "config.yml");
        if (!(configFile.exists())) {
            this.saveDefaultConfig();
        }
        this.reloadConfig();
        config = getConfig();

        // 保存cache
        saveConfigCache();

        // 注册监听器
        Bukkit.getPluginManager().registerEvents(new ManageListener(), this);

        // 创建表和获取数据库链接
        if (BaseConfigCache.isMessage || BaseConfigCache.isUser) {
            MysqlManagerUtil.get().enableMySQL();
        }

        // 创建help
        if (BaseConfigCache.isHelp){
            this.saveResource("help.yml", false);
            File helpFile = new File(getDataFolder(), "help.yml");
            FileConfiguration lang = YamlConfiguration.loadConfiguration(helpFile);
            HelpConfig = lang;
        }

        // 注册命令
        EnableCommand.regCommand();

        this.getLogger().info("manage插件启动成功");
    }

    /**
     * 禁用插件时调用
     */
    @Override
    public void onDisable() {
        // 断开数据库连接
        if (BaseConfigCache.isUser || BaseConfigCache.isMessage) {
            MysqlManagerUtil.get().shutdown();
        }
        this.getLogger().info("manage插件关闭");
    }


    /**
     * 保存各个独立模块开启状态
     */
    private void saveConfigCache() {
        val isMessage = Manage.config.getBoolean("isMessage");
        val isUser = Manage.config.getBoolean("isUser");
        val isHat = Manage.config.getBoolean("isHat");
        val isHelp = Manage.config.getBoolean("isHelp");
        val isTp = Manage.config.getBoolean("isTp");
        val isGift = Manage.config.getBoolean("isGift");

        BaseConfigCache.isMessage = isMessage;
        BaseConfigCache.isUser = isUser;
        BaseConfigCache.isHat = isHat;
        BaseConfigCache.isHelp = isHelp;
        BaseConfigCache.isTp = isTp;
        BaseConfigCache.isGift = isGift;
    }
}
