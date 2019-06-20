package cn.handy.utils;

import cn.handy.Main;
import cn.handy.constants.BaseConfigCache;
import cn.handy.constants.CommandEnum;
import cn.handy.constants.BaseConstants;
import cn.handy.dao.user.impl.UserServiceImpl;
import cn.handy.entity.User;
import cn.handy.executor.IExecutor;
import lombok.val;
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
     * @param sender
     * @return true是
     */
    public static Boolean isPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    /**
     * 根据命令反射获取对应方法
     *
     * @param label
     * @return
     */
    public static IExecutor getIExecutor(CommandSender sender, String label) {
        try {
            // 判断是否为玩家,是玩家需要走权限
            val rst = isPlayer(sender);

            CommandEnum commandEnum;
            if (rst) {
                commandEnum = CommandEnum.getCommandEnum(sender, label);
            } else {
                commandEnum = CommandEnum.getCommandEnum(label);
            }
            // 动态获取类
            Class<?> aClass = Class.forName(BaseConstants.PACKAGE_NAME + commandEnum.getClassName());
            IExecutor executor = (IExecutor) aClass.newInstance();
            return executor;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断玩家是否有权限
     *
     * @param sender
     * @param permission
     * @return true有
     */
    public static Boolean isHasPermission(CommandSender sender, String permission) {
        val rst = isPlayer(sender);
        if (rst) {
            Player player = (Player) sender;
            if (!player.hasPermission(permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否登录
     *
     * @param userName
     * @return true是
     */
    public static Boolean isLogin(String userName) {
        // 判断是否启用登录功能
        if (!BaseConfigCache.isUser) {
            return true;
        }
        for (User user : BaseConstants.userSet) {
            if (user.getUserName().equals(userName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 下线后删除缓存信息
     *
     * @param userName
     */
    public static void removeUser(String userName) {
        for (User user : BaseConstants.userSet) {
            if (user.getUserName().equals(userName.toLowerCase())) {
                BaseConstants.userSet.remove(user);
                break;
            }
        }
    }

    /**
     * 登录提醒
     */
    public static void loginRemind(Player player) {
        val userService = new UserServiceImpl();
        // 判断该用户是否存在
        String userName = player.getName().toLowerCase();
        val rst = userService.findByUserName(userName);
        if (rst) {
            // 判断是否免密码登陆
            val user = userService.findByUserNameAndLoginIp(userName, player.getAddress().getAddress().getHostAddress());
            if (user.getId() != null) {
                player.sendMessage("§aip跟上次登录ip相同,免密码登录成功!");
                BaseConstants.userSet.add(user);
            } else {
                player.sendMessage("§a请输入§e/l 密码 §a来登录游戏");
            }
        } else {
            player.sendMessage("§a请输入§e/reg 密码 重复密码 §a来注册游戏");
        }
    }

    /**
     * 利用正则表达式判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public static Boolean isNumeric(String str) {
        Matcher isNum = BaseConstants.NUMERIC.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
