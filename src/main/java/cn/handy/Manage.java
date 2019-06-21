package cn.handy;

import cn.handy.command.EnableCommand;
import cn.handy.constants.BaseConfigCache;
import cn.handy.listener.ManageListener;
import cn.handy.utils.ConfigUtil;
import cn.handy.utils.MysqlManagerUtil;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author hanshuai
 * @Description: {主方法}
 * @date 2019/5/17 16:06
 */
public class Manage extends JavaPlugin {
    public static Plugin plugin;

    /**
     * 启用插件时调用
     */
    @Override
    public void onEnable() {
        plugin = this;
        // 加载配置文件
        ConfigUtil.getConfig();
        // 保存cache
        saveConfigCache();
        // 注册监听器
        Bukkit.getPluginManager().registerEvents(new ManageListener(), this);
        // 注册命令
        EnableCommand.regCommand();
        // 创建表和获取数据库链接
        if (BaseConfigCache.isMessage || BaseConfigCache.isUser) {
            MysqlManagerUtil.get().enableMySQL();
        }
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
