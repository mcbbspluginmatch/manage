package cn.handy.dao.user.impl;

import cn.handy.constants.UserSqlEnum;
import cn.handy.dao.user.IUserService;
import cn.handy.entity.User;
import cn.handy.utils.MysqlManagerUtil;
import lombok.val;

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
            String userCmd = UserSqlEnum.CREATE_USER.getCommand();
            PreparedStatement ps = MysqlManagerUtil.connection.prepareStatement(userCmd);
            val rst = ps.executeUpdate();
            ps.close();
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
            PreparedStatement ps = MysqlManagerUtil.connection.prepareStatement(selectStr);
            ps.setString(1, userName.toLowerCase());
            val rst = ps.executeQuery();
            while (rst.next()) {
                count = rst.getLong(1);
            }
            rst.close();
            ps.close();
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
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
        User user = new User();
        try {
            String selectStr = UserSqlEnum.SELECT_BY_USERNAME_AND_PASSWORD.getCommand();
            PreparedStatement ps = MysqlManagerUtil.connection.prepareStatement(selectStr);
            ps.setString(1, userName);
            ps.setString(2, passWord);
            val rst = ps.executeQuery();
            while (rst.next()) {
                user.setId(rst.getLong(1));
                user.setUserName(rst.getString(2));
                user.setRealName(rst.getString(3));
                user.setPassWord(rst.getString(4));
                user.setLoginIp(rst.getString(5));
                user.setLoginDate(rst.getDate(6));
                user.setLoginStatus(rst.getBoolean(7));
                user.setRegIp(rst.getString(8));
                user.setRegDate(rst.getDate(9));
            }
            rst.close();
            ps.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return user;
    }
}
