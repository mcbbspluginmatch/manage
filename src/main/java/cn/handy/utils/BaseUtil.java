package cn.handy.utils;

import cn.handy.constants.BaseConstants;
import cn.handy.entity.Home;
import cn.handy.entity.Spawn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;

/**
 * @author hanshuai
 * @Description: {常用方法}
 * @date 2019/6/14 10:15
 */
public class BaseUtil {

    /**
     * 判断是否为玩家
     *
     * @param sender 发送者
     * @return true是
     */
    public static Boolean isPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     *
     * @param str 字符串
     * @return
     */
    public static Boolean isNumeric(String str) {
        Matcher isNum = BaseConstants.NUMERIC.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 颜色代码转换
     *
     * @param msg 消息
     * @return
     */
    public static String replaceChatColor(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    /**
     * 名字代码替换
     *
     * @param msg  消息
     * @param name 名称
     * @return
     */
    public static String replaceName(String msg, String name) {
        return msg.replace("${player}", name);
    }

    /**
     * 颜色代码和名字代码替换
     *
     * @param msg
     * @param name
     * @return
     */
    public static String replaceChatColorAndName(String msg, String name) {
        return ChatColor.translateAlternateColorCodes('&', msg).replace("${player}", name);
    }

    /**
     * 获取位置
     *
     * @param home
     * @return
     */
    public static Location getLocation(Home home) {
        return new Location(
                Bukkit.getWorld(home.getWorld()), home.getX(), home.getY(), home.getZ(), home.getYaw(), home.getPitch()
        );
    }

    /**
     * 获取位置
     *
     * @param spawn
     * @return
     */
    public static Location getLocation(Spawn spawn) {
        return new Location(
                Bukkit.getWorld(spawn.getWorld()), spawn.getX(), spawn.getY(), spawn.getZ(), spawn.getYaw(), spawn.getPitch()
        );
    }

    /**
     * 获取位置
     *
     * @param world
     * @param x
     * @param y
     * @param z
     * @return
     */
    public static Location getLocation(String world, Double x, Double y, Double z) {
        return new Location(
                Bukkit.getWorld(world), x, y, z
        );
    }

    /**
     * 获取传送spawn对应权限
     *
     * @param player
     * @return
     */
    public static Integer getSpawnPermission(Player player) {
        Integer num = 1;
        if (player.hasPermission("handy.spawn.one")) {
            num = 1;
        }
        if (player.hasPermission("handy.spawn.two")) {
            num = 2;
        }
        if (player.hasPermission("handy.spawn.three")) {
            num = 3;
        }
        if (player.hasPermission("handy.spawn.four")) {
            num = 4;
        }
        if (player.hasPermission("handy.spawn.five")) {
            num = 5;
        }
        if (player.hasPermission("handy.spawn.six")) {
            num = 6;
        }
        if (player.hasPermission("handy.spawn.seven")) {
            num = 7;
        }
        if (player.hasPermission("handy.spawn.eight")) {
            num = 8;
        }
        if (player.hasPermission("handy.spawn.nine")) {
            num = 9;
        }
        if (player.hasPermission("handy.spawn.ten")) {
            num = 10;
        }
        return num;
    }
}
