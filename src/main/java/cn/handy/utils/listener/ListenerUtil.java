package cn.handy.utils.listener;

import cn.handy.Manage;
import cn.handy.constants.BaseConfigCache;
import cn.handy.listener.back.BackListener;
import cn.handy.listener.deathpenalty.DeathPenaltyListener;
import cn.handy.listener.msg.MsgListener;
import cn.handy.listener.pvp.PvpListener;
import cn.handy.listener.secret.*;
import cn.handy.listener.showdamage.ShowDamageListener;
import cn.handy.listener.color.ColorListener;
import cn.handy.listener.spawn.SpawnListener;
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
    //get??? 不应该是register吗 (算了 小细节不在意) - a39
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
            Bukkit.getPluginManager().registerEvents(new SecretGuiListener(), Manage.plugin);
        }
        if (BaseConfigCache.isColor) {
            Bukkit.getPluginManager().registerEvents(new ColorListener(), Manage.plugin);
        }
        if (BaseConfigCache.isBack) {
            Bukkit.getPluginManager().registerEvents(new BackListener(), Manage.plugin);
        }
        if (BaseConfigCache.isShowDamage) {
            Bukkit.getPluginManager().registerEvents(new ShowDamageListener(), Manage.plugin);
        }
        if (BaseConfigCache.isSpawn) {
            Bukkit.getPluginManager().registerEvents(new SpawnListener(), Manage.plugin);
        }
        if (BaseConfigCache.isDeathPenalty){
            Bukkit.getPluginManager().registerEvents(new DeathPenaltyListener(), Manage.plugin);
        }
    }
}
