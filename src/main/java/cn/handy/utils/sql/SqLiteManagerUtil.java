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
import java.util.LinkedList;

/**
 * @author hanshuai
 * @Description: {sqLite连接池}
 * @date 2019/6/24 16:03
 */
public class SqLiteManagerUtil {
    private int initSize = 5;
    private int maxSize = 8;
    private int currentSize = 0;
    private LinkedList<Connection> connList = new LinkedList<Connection>();

    //声明对象时自动注册驱动
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建数据库连接池
     */
    public void enableSqLite() {
        // 创建连接池
        for (int i = 0; i < initSize; i++) {
            Connection connection = this.getConnection();
            connList.add(connection);
            currentSize++;
        }
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
     * 获取连接的方法
     *
     * @return
     */
    private Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + Manage.plugin.getDataFolder().getAbsolutePath() + "/manage.db");
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
        } else if (connList.size() == 0 && currentSize < maxSize) {
            //连接池被拿空，且连接数没有达到上限，创建新的连接
            currentSize++;
            connList.addLast(this.getConnection());
            Connection connection = connList.getFirst();
            connList.removeFirst();
            return connection;
        }
        throw new RuntimeException("连接数达到上限，请等待");
    }

    /**
     * 把用完的连接放回连接池
     *
     * @param connection
     */
    public void releaseConnection(Connection connection) {
        connList.addLast(connection);
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
