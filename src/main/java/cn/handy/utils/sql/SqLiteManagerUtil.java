package cn.handy.utils.sql;

import cn.handy.Manage;
import cn.handy.constants.BaseConfigCache;
import cn.handy.dao.message.IMessageService;
import cn.handy.dao.message.impl.MessageSqLiteServiceImpl;
import cn.handy.dao.pvp.IPvpService;
import cn.handy.dao.pvp.impl.PvpSqLiteServiceImpl;
import cn.handy.dao.user.IUserService;
import cn.handy.dao.user.impl.UserSqLiteServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/6/24 16:03
 */
public class SqLiteManagerUtil {
    public static Connection sqLiteConnection;
    public static SqLiteManagerUtil instance = null;

    public static SqLiteManagerUtil get() {
        return instance == null ? instance = new SqLiteManagerUtil() : instance;
    }

    public void enableSqLite() {
        // 创建连接
        connectSqLite();
        // 创建消息表
        if (BaseConfigCache.isMessage) {
            IMessageService messageService = new MessageSqLiteServiceImpl();
            messageService.create();
        }
        // 创建用户表
        if (BaseConfigCache.isUser) {
            IUserService userService = new UserSqLiteServiceImpl();
            userService.create();
        }
        // 创建pvp表
        if (BaseConfigCache.isPvp) {
            IPvpService pvpService = new PvpSqLiteServiceImpl();
            pvpService.create();
        }
    }

    /**
     * //建立一个数据库名manage.db的连接，如果不存在就在当前目录下创建之
     */
    private void connectSqLite() {
        try {
            Class.forName("org.sqlite.JDBC");
            sqLiteConnection = DriverManager.getConnection("jdbc:sqlite:" + Manage.plugin.getDataFolder().getAbsolutePath() + "/manage.db");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭数据库连接
     */
    public void shutdown() {
        try {
            sqLiteConnection.close();
        } catch (SQLException e) {
            //断开连接失败
            e.printStackTrace();
        }
    }
}
