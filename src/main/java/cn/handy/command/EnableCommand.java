package cn.handy.command;

import cn.handy.Manage;
import cn.handy.command.back.BackCommand;
import cn.handy.command.gift.GiftCommand;
import cn.handy.command.hat.HatCommand;
import cn.handy.command.help.HelpCommand;
import cn.handy.command.home.HomeCommand;
import cn.handy.command.home.SetHomeCommand;
import cn.handy.command.login.LoginCommand;
import cn.handy.command.login.RegisterCommand;
import cn.handy.command.manage.ManageCommand;
import cn.handy.command.msg.MsgAdminCommand;
import cn.handy.command.msg.MsgCommand;
import cn.handy.command.pvp.PvpCommand;
import cn.handy.command.secret.SecretAdminCommand;
import cn.handy.command.secret.SecretCommand;
import cn.handy.command.spawn.SetSpawnCommand;
import cn.handy.command.spawn.SpawnCommand;
import cn.handy.command.tp.TpCommand;
import cn.handy.command.tp.TpaCommand;
import cn.handy.command.tp.TpacceptCommand;
import cn.handy.command.tp.TpdenyCommand;
import cn.handy.constants.BaseConfigCache;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.util.permissions.DefaultPermissions;

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
        commandMap.register(Manage.plugin.getDescription().getName(), new ManageCommand());
        DefaultPermissions.registerPermission("handy.manage", null, PermissionDefault.OP);

        if (BaseConfigCache.isHat) {
            commandMap.register(Manage.plugin.getDescription().getName(), new HatCommand());
            if (BaseConfigCache.isHat_hat) {
                DefaultPermissions.registerPermission("handy.hat", null, PermissionDefault.TRUE);
            } else {
                DefaultPermissions.registerPermission("handy.hat", null, PermissionDefault.OP);
            }
        }
        if (BaseConfigCache.isHelp) {
            commandMap.register(Manage.plugin.getDescription().getName(), new HelpCommand());
            if (BaseConfigCache.isHelp_help) {
                DefaultPermissions.registerPermission("handy.help", null, PermissionDefault.TRUE);
            } else {
                DefaultPermissions.registerPermission("handy.help", null, PermissionDefault.OP);
            }
        }
        if (BaseConfigCache.isUser) {
            commandMap.register(Manage.plugin.getDescription().getName(), new LoginCommand());
            commandMap.register(Manage.plugin.getDescription().getName(), new RegisterCommand());
        }
        if (BaseConfigCache.isMessage) {
            commandMap.register(Manage.plugin.getDescription().getName(), new MsgAdminCommand());
            if (BaseConfigCache.isMessage_msgAdmin) {
                DefaultPermissions.registerPermission("handy.msgAdmin", null, PermissionDefault.TRUE);
            } else {
                DefaultPermissions.registerPermission("handy.msgAdmin", null, PermissionDefault.OP);
            }
            commandMap.register(Manage.plugin.getDescription().getName(), new MsgCommand());
            if (BaseConfigCache.isMessage_msg) {
                DefaultPermissions.registerPermission("handy.msg", null, PermissionDefault.TRUE);
            } else {
                DefaultPermissions.registerPermission("handy.msg", null, PermissionDefault.OP);
            }
        }
        if (BaseConfigCache.isTp) {
            commandMap.register(Manage.plugin.getDescription().getName(), new TpCommand());
            commandMap.register(Manage.plugin.getDescription().getName(), new TpaCommand());
            commandMap.register(Manage.plugin.getDescription().getName(), new TpacceptCommand());
            commandMap.register(Manage.plugin.getDescription().getName(), new TpdenyCommand());
            if (BaseConfigCache.isTp_tp) {
                DefaultPermissions.registerPermission("handy.tp", null, PermissionDefault.TRUE);
            } else {
                DefaultPermissions.registerPermission("handy.tp", null, PermissionDefault.OP);
            }
            if (BaseConfigCache.isTp_tpa) {
                DefaultPermissions.registerPermission("handy.tpa", null, PermissionDefault.TRUE);
            } else {
                DefaultPermissions.registerPermission("handy.tpa", null, PermissionDefault.OP);
            }
        }
        if (BaseConfigCache.isGift) {
            commandMap.register(Manage.plugin.getDescription().getName(), new GiftCommand());
            if (BaseConfigCache.isGift_gift) {
                DefaultPermissions.registerPermission("handy.gift", null, PermissionDefault.TRUE);
            } else {
                DefaultPermissions.registerPermission("handy.gift", null, PermissionDefault.OP);
            }
        }
        if (BaseConfigCache.isPvp) {
            commandMap.register(Manage.plugin.getDescription().getName(), new PvpCommand());
            if (BaseConfigCache.isPvp_pvp) {
                DefaultPermissions.registerPermission("handy.pvp", null, PermissionDefault.TRUE);
            } else {
                DefaultPermissions.registerPermission("handy.pvp", null, PermissionDefault.OP);
            }
        }
        if (BaseConfigCache.isSecret) {
            commandMap.register(Manage.plugin.getDescription().getName(), new SecretAdminCommand());
            commandMap.register(Manage.plugin.getDescription().getName(), new SecretCommand());
            if (BaseConfigCache.isSecret_secret) {
                DefaultPermissions.registerPermission("handy.secret", null, PermissionDefault.TRUE);
            } else {
                DefaultPermissions.registerPermission("handy.secret", null, PermissionDefault.OP);
            }
            if (BaseConfigCache.isSecret_secretAdmin) {
                DefaultPermissions.registerPermission("handy.secretAdmin", null, PermissionDefault.TRUE);
            } else {
                DefaultPermissions.registerPermission("handy.secretAdmin", null, PermissionDefault.OP);
            }
        }
        if (BaseConfigCache.isHome) {
            commandMap.register(Manage.plugin.getDescription().getName(), new HomeCommand());
            commandMap.register(Manage.plugin.getDescription().getName(), new SetHomeCommand());
            if (BaseConfigCache.isHome_setHome) {
                DefaultPermissions.registerPermission("handy.setHome", null, PermissionDefault.TRUE);
            } else {
                DefaultPermissions.registerPermission("handy.setHome", null, PermissionDefault.OP);
            }
            if (BaseConfigCache.isHome_home) {
                DefaultPermissions.registerPermission("handy.home", null, PermissionDefault.TRUE);
            } else {
                DefaultPermissions.registerPermission("handy.home", null, PermissionDefault.OP);
            }
        }
        if (BaseConfigCache.isBack) {
            commandMap.register(Manage.plugin.getDescription().getName(), new BackCommand());
            if (BaseConfigCache.isBack_back) {
                DefaultPermissions.registerPermission("handy.back", null, PermissionDefault.TRUE);
            } else {
                DefaultPermissions.registerPermission("handy.back", null, PermissionDefault.OP);
            }
        }
        if (BaseConfigCache.isSpawn) {
            commandMap.register(Manage.plugin.getDescription().getName(), new SetSpawnCommand());
            commandMap.register(Manage.plugin.getDescription().getName(), new SpawnCommand());
            if (BaseConfigCache.isSpawn_spawn) {
                DefaultPermissions.registerPermission("handy.spawn", null, PermissionDefault.TRUE);
            } else {
                DefaultPermissions.registerPermission("handy.spawn", null, PermissionDefault.OP);
            }
            if (BaseConfigCache.isSpawn_setSpawn) {
                DefaultPermissions.registerPermission("handy.setSpawn", null, PermissionDefault.TRUE);
            } else {
                DefaultPermissions.registerPermission("handy.setSpawn", null, PermissionDefault.OP);
            }
        }
    }
}
