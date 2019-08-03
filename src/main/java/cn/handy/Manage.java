package cn.handy;

import cn.handy.command.EnableCommand;
import cn.handy.entity.PluginVersions;
import cn.handy.utils.Beans;
import cn.handy.utils.PluginVersionUtil;
import cn.handy.utils.ReportUtil;
import cn.handy.utils.VaultUtil;
import cn.handy.utils.config.ConfigUtil;
import cn.handy.utils.listener.ListenerUtil;
import cn.handy.utils.secret.SecretUtil;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author hanshuai
 * @Description: {主方法}
 * @date 2019/5/17 16:06
 */
public class Manage extends JavaPlugin {
    public static Plugin plugin;
    public static Server server;
    private final static String PLUGIN_VERSION = "5.0.8";

    /**
     * 启用插件时调用
     */
    @Override
    public void onEnable() {
        plugin = this;
        //我觉得根据英语就能看懂的代码 不需要打注释吧  —— yinyangshi
        server = getServer();
        // 加载配置文件
        ConfigUtil.getConfig();
        // 加载vault插件
        VaultUtil.vault();

        // 注册监听器
        ListenerUtil.getListener();
        // 注册命令
        EnableCommand.regCommand();
        // 启动武林秘籍系统
        SecretUtil.noCharBook();
        // 创建表和获取数据库链接
        Beans.getBeans().getSqlManagerUtil().enableSql();
        // 统计插件使用情况
        ReportUtil.report();
        // 查询插件版本情况
        new BukkitRunnable() {
            @Override
            public void run() {
                PluginVersions pluginVersion = PluginVersionUtil.getPluginVersion("manage", "123", "");
                if (pluginVersion != null) {
                    // 判断版本号是否相等
                    if (PLUGIN_VERSION.equals(pluginVersion.getVersions())) {
                        Manage.plugin.getLogger().info("manage插件启动成功,您的manage插件版本为最新版");
                    } else {
                        Manage.plugin.getLogger().info("manage插件启动成功,manage插件已有最新版" + pluginVersion.getVersions() + ",请前往:" + pluginVersion.getDownloadUrl() + "进行更新" + ",更新内容为" + pluginVersion.getUpdateNote());
                    }
                }
            }
        }.runTaskAsynchronously(Manage.plugin);
    }

    /**
     * 禁用插件时调用
     */
    @Override
    public void onDisable() {
        // 断开数据库连接
        Beans.getBeans().getSqlManagerUtil().shutdown();
        this.getLogger().info("manage插件关闭");
    }
}
