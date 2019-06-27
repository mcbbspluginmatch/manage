package cn.handy.utils.sql;

import cn.handy.Manage;
import cn.handy.constants.BaseConfigCache;
import cn.handy.constants.Beans;
import cn.handy.dao.message.IMessageService;
import cn.handy.dao.message.impl.MessageMySqlServiceImpl;
import cn.handy.dao.pvp.IPvpService;
import cn.handy.dao.pvp.impl.PvpMySqlServiceImpl;
import cn.handy.dao.user.IUserService;
import cn.handy.dao.user.impl.UserMySqlServiceImpl;
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
 * @Description: {数据库处理相关}
 * @date 2019/5/21 14:25
 */
public class MysqlManagerUtil {
    private String ip;
    private String databaseName;
    private String userName;
    private String userPassword;
    private int port;

    private int initSize = 5;
    private int maxSize = 8;
    private LinkedList<Connection> connList = new LinkedList<Connection>();
    private int currentSize = 0;

    //声明对象时自动注册驱动
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void enableMySQL() {
        ip = ConfigUtil.config.getString("mysql.ip");
        databaseName = ConfigUtil.config.getString("mysql.databasename");
        userName = ConfigUtil.config.getString("mysql.username");
        userPassword = ConfigUtil.config.getString("mysql.password");
        port = ConfigUtil.config.getInt("mysql.port");

        // 构建数据库连接池
        for (int i = 0; i < initSize; i++) {
            Connection connection = this.getConnection();
            connList.add(connection);
            currentSize++;
        }

        // 创建消息表
        if (BaseConfigCache.isMessage) {
            IMessageService messageService = new MessageMySqlServiceImpl();
            messageService.create();
        }
        // 创建用户表
        if (BaseConfigCache.isUser) {
            IUserService userService = new UserMySqlServiceImpl();
            userService.create();
        }
        // 创建pvp表
        if (BaseConfigCache.isPvp) {
            IPvpService pvpService = new PvpMySqlServiceImpl();
            pvpService.create();
        }

        // 创建一个每小时执行的心跳包
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    String selectStr = "SELECT 'X'";
                    PreparedStatement ps = Beans.getBeans().getMysqlManagerUtil().getConnFromPool().prepareStatement(selectStr);
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
     * 获取连接的方法
     *
     * @return
     */
    private Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + databaseName + "?useSSL=false", userName, userPassword);
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