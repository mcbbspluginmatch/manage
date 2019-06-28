package cn.handy.utils.sql;

import cn.handy.Manage;
import cn.handy.constants.BaseConfigCache;
import cn.handy.dao.message.IMessageService;
import cn.handy.dao.message.impl.MessageServiceImpl;
import cn.handy.dao.pvp.IPvpService;
import cn.handy.dao.pvp.impl.PvpServiceImpl;
import cn.handy.dao.user.IUserService;
import cn.handy.dao.user.impl.UserServiceImpl;
import cn.handy.utils.Beans;
import cn.handy.utils.config.ConfigUtil;
import lombok.val;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @author hanshuai
 * @Description: {Sql管理}
 * @date 2019/6/28 10:32
 */
public class SqlManagerUtil {
    private static String ip;
    private static String databaseName;
    private static String userName;
    private static String userPassword;
    private static int port;
    private static String mysqlDriver = "com.sql.jdbc.Driver";
    private static String sqLiteDriver = "org.sqlite.JDBC";
    private int initSize = ConfigUtil.LangConfig.getInt("initSize");
    private int maxActive = ConfigUtil.LangConfig.getInt("maxActive");
    private LinkedList<Connection> connList = new LinkedList<Connection>();

    //声明对象时自动注册驱动
    static {
        try {
            if (BaseConfigCache.isUseMySql) {
                Class.forName(mysqlDriver);
                ip = ConfigUtil.config.getString("sql.ip");
                databaseName = ConfigUtil.config.getString("sql.databasename");
                userName = ConfigUtil.config.getString("sql.username");
                userPassword = ConfigUtil.config.getString("sql.password");
                port = ConfigUtil.config.getInt("sql.port");
            } else {
                Class.forName(sqLiteDriver);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void enableSql() {
        // 构建数据库连接池
        for (int i = 0; i < initSize; i++) {
            Connection connection = this.getConnection();
            connList.add(connection);
        }
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
        // 创建pvp表
        if (BaseConfigCache.isPvp) {
            IPvpService pvpService = new PvpServiceImpl();
            pvpService.create();
        }
        if (BaseConfigCache.isUseMySql) {
            // 创建一个每小时执行的心跳包
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        String selectStr = "SELECT 'X'";
                        PreparedStatement ps = Beans.getBeans().getSqlManagerUtil().getConnFromPool().prepareStatement(selectStr);
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
    }

    /**
     * 获取连接的方法
     *
     * @return
     */
    private Connection getConnection() {
        Connection conn = null;
        try {
            if (BaseConfigCache.isUseMySql) {
                conn = DriverManager.getConnection("jdbc:sql://" + ip + ":" + port + "/" + databaseName + "?useSSL=false", userName, userPassword);
            } else {
                conn = DriverManager.getConnection("jdbc:sqlite:" + Manage.plugin.getDataFolder().getAbsolutePath() + "/manage.db");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 获取连接池中的一个连接对象
     *
     * @return
     */
    public Connection getConnFromPool() {
        //当连接池还没空
        if (connList.size() > 0) {
            Connection connection = connList.getFirst();
            connList.removeFirst();
            return connection;
        } else {
            //连接池被拿空，且连接数没有达到上限，创建新的连接
            connList.addLast(this.getConnection());
            Connection connection = connList.getFirst();
            connList.removeFirst();
            return connection;
        }
    }

    /**
     * 把用完的连接放回连接池
     *
     * @param connection
     */
    public void releaseConnection(Connection connection) {
        connList.addLast(connection);
        // 如果连接数量大于配置的数量就销毁
        if (connList.size() > maxActive) {
            try {
                Connection conn = connList.getFirst();
                connList.removeFirst();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭数据库连接
     */
    public void shutdown() {
        try {
            for (Connection connection : connList) {
                connection.close();
            }
        } catch (SQLException e) {
            //断开连接失败
            e.printStackTrace();
        }
    }
}
