package cn.handy.dao.home.impl;

import cn.handy.constants.BaseConfigCache;
import cn.handy.constants.sql.HomeSqlEnum;
import cn.handy.constants.sql.MsgSqlEnum;
import cn.handy.dao.home.IHomeService;
import cn.handy.entity.Home;
import cn.handy.utils.Beans;
import lombok.val;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/7/9 10:11
 */
public class HomeServiceImpl implements IHomeService {

    /**
     * 如果不存在表则创建表
     *
     * @return
     */
    @Override
    public Boolean create() {
        try {
            String userCmd = HomeSqlEnum.CREATE_SQ_LITE_MESSAGE.getCommand();
            if (BaseConfigCache.isUseMySql) {
                userCmd = HomeSqlEnum.CREATE_MYSQL_MESSAGE.getCommand();
            }
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            PreparedStatement ps = conn.prepareStatement(userCmd);
            val rst = ps.executeUpdate();
            ps.close();
            Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
            return rst > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置家
     *
     * @param home
     * @return
     */
    @Override
    public Boolean set(Home home) {
        try {
            // 查询有无数据
            val home1 = findByUserNameAndHomeName(home.getUserName(), home.getHomeName());
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            if (home1.getId() == null) {
                String addStr = HomeSqlEnum.ADD_DATA.getCommand();
                PreparedStatement ps = conn.prepareStatement(addStr);
                ps.setString(1, home.getUserName());
                ps.setString(2, home.getHomeName());
                ps.setDouble(3, home.getX());
                ps.setDouble(4, home.getY());
                ps.setDouble(5, home.getZ());
                ps.setFloat(6, home.getYaw());
                ps.setFloat(7, home.getPitch());
                ps.setString(8, home.getWorld());
                val rst = ps.executeUpdate();
                ps.close();
                Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
                return rst > 0;
            } else {
                String updateStr = HomeSqlEnum.UPDATE_DATA.getCommand();
                PreparedStatement ps = conn.prepareStatement(updateStr);
                ps.setDouble(1, home.getX());
                ps.setDouble(2, home.getY());
                ps.setDouble(3, home.getZ());
                ps.setFloat(4, home.getYaw());
                ps.setFloat(5, home.getPitch());
                ps.setString(6, home.getWorld());
                ps.setString(7, home.getUserName());
                val rst = ps.executeUpdate();
                ps.close();
                Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
                return rst > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据用户名和家名查询
     *
     * @param userName 用户名
     * @param homeName 家名
     * @return
     */
    @Override
    public Home findByUserNameAndHomeName(String userName, String homeName) {
        Home home = new Home();
        try {
            String selectStr = HomeSqlEnum.SELECT_BY_USER_NAME_AND_HOME_NAME.getCommand();
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            PreparedStatement ps = conn.prepareStatement(selectStr);
            ps.setString(1, userName);
            ps.setString(2, homeName);
            val rst = ps.executeQuery();
            while (rst.next()) {
                home.setId(rst.getInt(1));
                home.setUserName(rst.getString(2));
                home.setHomeName(rst.getString(3));
                home.setX(rst.getDouble(4));
                home.setY(rst.getDouble(5));
                home.setZ(rst.getDouble(6));
                home.setYaw(rst.getFloat(7));
                home.setPitch(rst.getFloat(8));
                home.setWorld(rst.getString(9));
            }
            rst.close();
            ps.close();
            Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
            return home;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return home;
    }

    /**
     * 查询有几个家
     *
     * @param userName
     * @return
     */
    @Override
    public Integer findByCount(String userName) {
        Integer count = 0;
        try {
            String selectStr = HomeSqlEnum.SELECT_COUNT.getCommand();
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            PreparedStatement ps = conn.prepareStatement(selectStr);
            ps.setString(1, userName);
            val rst = ps.executeQuery();
            while (rst.next()) {
                count = rst.getInt(1);
            }
            rst.close();
            ps.close();
            Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
