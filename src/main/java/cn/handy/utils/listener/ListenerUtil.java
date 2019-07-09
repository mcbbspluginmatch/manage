package cn.handy.utils.listener;

import cn.handy.Manage;
import cn.handy.constants.BaseConfigCache;
import cn.handy.listener.back.BackListener;
import cn.handy.listener.msg.MsgListener;
import cn.handy.listener.pvp.PvpListener;
import cn.handy.listener.secret.SecretBuffListener;
import cn.handy.listener.secret.SecretGetListener;
import cn.handy.listener.secret.SecretSetListener;
import cn.handy.listener.secret.SecretUseListener;
import cn.handy.listener.signchange.SignChangeListener;
import cn.handy.listener.user.UserListener;
import org.bukkit.Bukkit;

/**
 * @author hanshuai
 * @Description: {注册对应监听器}
 * @date 2019/6/24 11:15
 */
public class ListenerUtil {

    /**
     * 注册对应监听器
     */
    public static void getListener() {
        if (BaseConfigCache.isUser) {
            Bukkit.getPluginManager().registerEvents(new UserListener(), Manage.plugin);
        }
        if (BaseConfigCache.isMessage) {
            Bukkit.getPluginManager().registerEvents(new MsgListener(), Manage.plugin);
        }
        if (BaseConfigCache.isPvp) {
            Bukkit.getPluginManager().registerEvents(new PvpListener(), Manage.plugin);
        }
        if (BaseConfigCache.isSecret) {
            Bukkit.getPluginManager().registerEvents(new SecretSetListener(), Manage.plugin);
            Bukkit.getPluginManager().registerEvents(new SecretGetListener(), Manage.plugin);
            Bukkit.getPluginManager().registerEvents(new SecretUseListener(), Manage.plugin);
            Bukkit.getPluginManager().registerEvents(new SecretBuffListener(), Manage.plugin);
        }
        if (BaseConfigCache.isSignChange) {
            Bukkit.getPluginManager().registerEvents(new SignChangeListener(), Manage.plugin);
        }
        if (BaseConfigCache.isBack) {
            Bukkit.getPluginManager().registerEvents(new BackListener(), Manage.plugin);
        }
    }
}
