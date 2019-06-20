package cn.handy;

import cn.handy.constants.BaseConfigCache;
import cn.handy.listener.MyListener;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.MysqlManagerUtil;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
public class Main extends JavaPlugin {
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
        Bukkit.getPluginManager().registerEvents(new MyListener(), this);

        // 使用子线程创建表和获取数据库链接
        if (BaseConfigCache.isMessage || BaseConfigCache.isUser) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    MysqlManagerUtil.get().enableMySQL();
                }
            }.runTaskAsynchronously(this);
        }
        // 创建help
        this.saveResource("help.yml", false);
        File helpFile = new File(getDataFolder(), "help.yml");
        FileConfiguration lang = YamlConfiguration.loadConfiguration(helpFile);
        HelpConfig = lang;
        this.getLogger().info("manage插件启动成功");
    }

    /**
     * 命令
     *
     * @param sender
     * @param cmd
     * @param label
     * @param args
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, final String[] args) {
        val executor = BaseUtil.getIExecutor(sender, label);
        return executor.command(sender, cmd, label, args);
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
        val isMessage = Main.config.getBoolean("isMessage");
        val isUser = Main.config.getBoolean("isUser");
        BaseConfigCache.isMessage = isMessage;
        BaseConfigCache.isUser = isUser;
    }
}
