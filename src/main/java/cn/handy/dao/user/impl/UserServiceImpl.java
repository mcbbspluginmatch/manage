package cn.handy.dao.user.impl;

import cn.handy.constants.BaseConfigCache;
import cn.handy.constants.sql.UserSqlEnum;
import cn.handy.dao.user.IUserService;
import cn.handy.entity.User;
import cn.handy.utils.Beans;
import lombok.val;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/6/13 14:09
 */
public class UserServiceImpl implements IUserService {

    /**
     * 如果不存在表则创建表
     *
     * @return
     */
    @Override
    public Boolean create() {
        try {
            String userCmd = UserSqlEnum.CREATE_SQ_LITE_USER.getCommand();
            if (BaseConfigCache.isUseMySql) {
                userCmd = UserSqlEnum.CREATE_MYSQL_USER.getCommand();
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
        return false;
    }

    /**
     * 查询是否有该账户
     *
     * @param userName
     * @return
     */
    @Override
    public Boolean findByUserName(String userName) {
        Long count = 0L;
        try {
            String selectStr = UserSqlEnum.SELECT_COUNT_BY_USERNAME.getCommand();
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            PreparedStatement ps = conn.prepareStatement(selectStr);
            ps.setString(1, userName.toLowerCase());
            val rst = ps.executeQuery();
            while (rst.next()) {
                count = rst.getLong(1);
            }
            rst.close();
            ps.close();
            Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count > 0;
    }

    /**
     * 登录
     *
     * @param userName
     * @param passWord
     * @return
     */
    @Override
    public User login(String userName, String passWord) {
        User user = null;
        try {
            String selectStr = UserSqlEnum.SELECT_BY_USERNAME_AND_PASSWORD.getCommand();
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            PreparedStatement ps = conn.prepareStatement(selectStr);
            ps.setString(1, userName);
            ps.setString(2, passWord);
            val rst = ps.executeQuery();
            while (rst.next()) {
                user = new User();
                user.setId(rst.getInt(1));
                user.setUserName(rst.getString(2));
                user.setRealName(rst.getString(3));
                user.setPassWord(rst.getString(4));
                user.setLoginIp(rst.getString(5));
                user.setLoginDate(rst.getDate(6));
                user.setRegIp(rst.getString(7));
                user.setRegDate(rst.getDate(8));
            }
            rst.close();
            ps.close();
            Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @Override
    public Boolean register(User user) {
        try {
            String selectStr = UserSqlEnum.ADD_DATA.getCommand();
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            PreparedStatement ps = conn.prepareStatement(selectStr);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getRealName());
            ps.setString(3, user.getPassWord());
            ps.setString(4, user.getLoginIp());
            ps.setDate(5, new Date(user.getLoginDate().getTime()));
            ps.setString(6, user.getRegIp());
            ps.setDate(7, new Date(user.getRegDate().getTime()));
            val rst = ps.executeUpdate();
            ps.close();
            Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
            return rst > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据帐号和ip来进行查询
     *
     * @param userName 帐号
     * @param loginIp  ip
     * @return
     */
    @Override
    public User findByUserNameAndLoginIp(String userName, String loginIp) {
        User user = null;
        try {
            String selectStr = UserSqlEnum.SELECT_BY_USERNAME_AND_LOGIN_IP.getCommand();
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            PreparedStatement ps = conn.prepareStatement(selectStr);
            ps.setString(1, userName);
            ps.setString(2, loginIp);
            val rst = ps.executeQuery();
            while (rst.next()) {
                user = new User();
                user.setId(rst.getInt(1));
                user.setUserName(rst.getString(2));
                user.setRealName(rst.getString(3));
                user.setPassWord(rst.getString(4));
                user.setLoginIp(rst.getString(5));
                user.setLoginDate(rst.getDate(6));
                user.setRegIp(rst.getString(7));
                user.setRegDate(rst.getDate(8));
            }
            rst.close();
            ps.close();
            Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 更新
     *
     * @param user
     * @return
     */
    @Override
    public Boolean update(User user) {
        try {
            String selectStr = UserSqlEnum.UPDATE.getCommand();
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            PreparedStatement ps = conn.prepareStatement(selectStr);
            ps.setString(1, user.getLoginIp());
            ps.setDate(2, new Date(user.getLoginDate().getTime()));
            ps.setLong(3, user.getId());
            val rst = ps.executeUpdate();
            ps.close();
            Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
            return rst > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
