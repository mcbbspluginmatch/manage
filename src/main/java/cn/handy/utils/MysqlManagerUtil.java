package cn.handy.utils;

import cn.handy.Manage;
import cn.handy.constants.BaseConfigCache;
import cn.handy.dao.message.IMessageService;
import cn.handy.dao.message.impl.MessageServiceImpl;
import cn.handy.dao.user.IUserService;
import cn.handy.dao.user.impl.UserServiceImpl;
import lombok.val;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author hanshuai
 * @Description: {数据库处理相关}
 * @date 2019/5/21 14:25
 */
public class MysqlManagerUtil {
    private String ip;
    private String databaseName;
    private String userName;
    private String userPassword;
    private int port;
    public static Connection connection;

    public static MysqlManagerUtil instance = null;

    public static MysqlManagerUtil get() {
        return instance == null ? instance = new MysqlManagerUtil() : instance;
    }

    public void enableMySQL() {
        ip = Manage.config.getString("mysql.ip");
        databaseName = Manage.config.getString("mysql.databasename");
        userName = Manage.config.getString("mysql.username");
        userPassword = Manage.config.getString("mysql.password");
        port = Manage.config.getInt("mysql.port");
        // 构建数据库连接
        connectMySQL();

        // 创建消息表
        if (BaseConfigCache.isMessage) {
            IMessageService messageService = new MessageServiceImpl();
            messageService.create();
        }
        // 创建用户表
        if (BaseConfigCache.isUser) {
            IUserService userService = new UserServiceImpl();
            userService.create();
        }

        // 创建一个每小时执行的心跳包
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    String selectStr = "SELECT 'X'";
                    PreparedStatement ps = MysqlManagerUtil.connection.prepareStatement(selectStr);
                    val rst = ps.executeQuery();
                    rst.close();
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskTimerAsynchronously(Manage.plugin, 0, 3600 * 20);
    }

    /**
     * 连接mysql
     */
    private void connectMySQL() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + databaseName + "?useSSL=false", userName, userPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭数据库连接
     */
    public void shutdown() {
        try {
            connection.close();
        } catch (SQLException e) {
            //断开连接失败
            e.printStackTrace();
        }
    }
}