package cn.handy;

import cn.handy.listener.MyListener;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.MysqlManagerUtil;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
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
    public static Plugin plugin;

    /**
     * 启用插件时调用
     */
    @Override
    public void onEnable() {
        // 创建配置文件文件夹和配置文件
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File file = new File(getDataFolder(), "config.yml");
        if (!(file.exists())) {
            saveDefaultConfig();
        }
        reloadConfig();
        config = getConfig();
        plugin = this;
        // 注册监听器
        Bukkit.getPluginManager().registerEvents(new MyListener(), this);

        Server server = getServer();
        // 判断是否启用数据库
        val isUseMysql = config.getBoolean("isUseMysql");
        if (isUseMysql) {
            // 利用BukkitRunnable创建新线程，防止使用SQL而堵塞主线程
            new BukkitRunnable() {
                public void run() {
                    MysqlManagerUtil.get().enableMySQL();
                }
            }.runTaskAsynchronously(this);
        }
        this.getLogger().info("gameplay插件启动成功");
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
        val executor = BaseUtil.getIExecutor(sender,label);
        return executor.command(sender, cmd, label, args);
    }

    /**
     * 禁用插件时调用
     */
    @Override
    public void onDisable() {
        val isUseMysql = config.getBoolean("isUseMysql");
        // 断开数据库连接
        if (isUseMysql) {
            MysqlManagerUtil.get().shutdown();
        }
        this.getLogger().info("gameplay插件关闭");
    }
}
