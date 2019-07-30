package cn.handy.utils;

import cn.handy.Manage;
import cn.handy.constants.BaseConfigCache;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * @author hanshuai
 * @Description: {Vault支持类}
 * @date 2019/7/30 16:48
 */
public class VaultUtil {
    public static Economy econ = null;
    public static Permission perms = null;
    public static Chat chat = null;

    /**
     * 默认加载vault
     */
    public static void vault() {
        if (BaseConfigCache.isVault) {
            if (!setupEconomy()) {
                Manage.plugin.getLogger().info("没有找到vault插件,已经默认关闭相关模块");
                BaseConfigCache.isVault = false;
            }
            //setupPermissions();
            //setupChat();
        }
    }

    private static boolean setupEconomy() {
        if (Manage.server.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Manage.server.getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private static boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = Manage.server.getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private static boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = Manage.server.getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

}
