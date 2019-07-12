package cn.handy;

import cn.handy.command.EnableCommand;
import cn.handy.utils.Beans;
import cn.handy.utils.ReportUtil;
import cn.handy.utils.config.ConfigUtil;
import cn.handy.utils.listener.ListenerUtil;
import cn.handy.utils.secret.SecretUtil;
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
        this.getLogger().info("manage插件启动成功");
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
