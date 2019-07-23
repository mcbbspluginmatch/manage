package cn.handy.utils;

import cn.handy.constants.BaseConstants;
import cn.handy.entity.Home;
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
}
