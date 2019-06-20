package cn.handy.command;

import cn.handy.Manage;
import cn.handy.command.gift.GiftCommand;
import cn.handy.command.hat.HatCommand;
import cn.handy.command.help.HelpCommand;
import cn.handy.command.login.LoginCommand;
import cn.handy.command.login.RegisterCommand;
import cn.handy.command.msg.MsgCommand;
import cn.handy.command.tp.TpCommand;
import cn.handy.command.tp.TpaCommand;
import cn.handy.command.tp.TpacceptCommand;
import cn.handy.command.tp.TpdenyCommand;
import cn.handy.constants.BaseConfigCache;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author hanshuai
 * @Description: {指令注册方法}
 * @date 2019/6/20 10:59
 */
public class EnableCommand {

    private static CommandMap commandMap;

    private static void getCommandMap() {
        try {
            final Class<?> c = Bukkit.getServer().getClass();
            for (final Method method : c.getDeclaredMethods()) {
                if (method.getName().equals("getCommandMap")) {
                    commandMap = (CommandMap) method.invoke(Bukkit.getServer(), new Object[0]);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void regCommand() {
        // 获取
        getCommandMap();
        // 注册命令
        if (BaseConfigCache.isHat){
            commandMap.register(Manage.plugin.getDescription().getName(), new HatCommand());
        }
        if (BaseConfigCache.isHelp){
            commandMap.register(Manage.plugin.getDescription().getName(), new HelpCommand());
        }
        if (BaseConfigCache.isUser){
            commandMap.register(Manage.plugin.getDescription().getName(), new LoginCommand());
            commandMap.register(Manage.plugin.getDescription().getName(), new RegisterCommand());
        }
        if (BaseConfigCache.isMessage){
            commandMap.register(Manage.plugin.getDescription().getName(), new MsgCommand());
        }
        if (BaseConfigCache.isTp){
            commandMap.register(Manage.plugin.getDescription().getName(), new TpCommand());
            commandMap.register(Manage.plugin.getDescription().getName(), new TpaCommand());
            commandMap.register(Manage.plugin.getDescription().getName(), new TpacceptCommand());
            commandMap.register(Manage.plugin.getDescription().getName(), new TpdenyCommand());
        }
        if (BaseConfigCache.isGift){
            commandMap.register(Manage.plugin.getDescription().getName(), new GiftCommand());
        }
    }
}