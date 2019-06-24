package cn.handy.utils;

import cn.handy.Manage;
import cn.handy.constants.BaseConfigCache;
import cn.handy.listener.msg.MsgListener;
import cn.handy.listener.pvp.PvpListener;
import cn.handy.listener.user.UserListener;
import org.bukkit.Bukkit;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/6/24 11:15
 */
public class ListenerUtil {

    /**
     * 注册对应监听器
     */
    public static void getListener() {
        // 注册监听器
        if (BaseConfigCache.isUser) {
            Bukkit.getPluginManager().registerEvents(new UserListener(), Manage.plugin);
        }
        if (BaseConfigCache.isMessage) {
            Bukkit.getPluginManager().registerEvents(new MsgListener(), Manage.plugin);
        }
        if (BaseConfigCache.isPvp) {
            Bukkit.getPluginManager().registerEvents(new PvpListener(), Manage.plugin);
        }
    }
}
