package cn.handy.dao.secret.impl;

import cn.handy.constants.BaseConfigCache;
import cn.handy.constants.sql.UserSecretSqlEnum;
import cn.handy.dao.secret.IUserSecretService;
import cn.handy.entity.UserSecret;
import cn.handy.utils.Beans;
import lombok.val;
import org.bukkit.ChatColor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/7/5 11:33
 */
public class UserSecretServiceImpl implements IUserSecretService {

    /**
     * 创建表
     *
     * @return
     */
    @Override
    public Boolean create() {
        try {
            String msgCmd = UserSecretSqlEnum.CREATE_SQ_LITE_USER_SECRET.getCommand();
            if (BaseConfigCache.isUseMySql) {
                msgCmd = UserSecretSqlEnum.CREATE_MYSQL_USER_SECRET.getCommand();
            }
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            PreparedStatement ps = conn.prepareStatement(msgCmd);
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
     * 设置-没有就新增,有的话就更新信息并把功法等级+1
     *
     * @param userSecret
     * @return
     */
    @Override
    public String set(UserSecret userSecret) {
        try {
            val uSecret = findByUserNameAndSecretId(userSecret.getUserName(), userSecret.getSecretId());
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            if (uSecret.getId() == null) {
                String addStr = UserSecretSqlEnum.ADD_DATA.getCommand();
                PreparedStatement ps = conn.prepareStatement(addStr);
                ps.setString(1, userSecret.getUserName());
                ps.setInt(2, userSecret.getSectsId());
                ps.setString(3, userSecret.getSectsName());
                ps.setInt(4, userSecret.getSecretId());
                ps.setInt(5, userSecret.getSecretGarde());
                val rst = ps.executeUpdate();
                ps.close();
                Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
                return rst > 0 ? ChatColor.AQUA + "恭喜你,学习功法成功" : ChatColor.RED + "学习功法失败,请联系腐竹";
            } else {
                String addStr = UserSecretSqlEnum.UPDATE_SECRET_GARDE_AND_SECTS_BY_ID.getCommand();
                PreparedStatement ps = conn.prepareStatement(addStr);
                ps.setInt(1, userSecret.getSecretId());
                ps.setString(2, userSecret.getSectsName());
                ps.setInt(3, uSecret.getSecretGarde() + 1);
                ps.setInt(4, uSecret.getId());
                val rst = ps.executeUpdate();
                ps.close();
                Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
                return rst > 0 ? ChatColor.AQUA + "恭喜你,功法等级提升" : ChatColor.RED + "功法等级提升失败,请联系腐竹";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "学习功法失败,请联系腐竹";
    }

    /**
     * 根据用户名和技能序号查询
     *
     * @param userName 用户名
     * @param secretId 功法序号
     * @return
     */
    @Override
    public UserSecret findByUserNameAndSecretId(String userName, Integer secretId) {
        UserSecret userSecret = new UserSecret();
        try {
            String selectStr = UserSecretSqlEnum.SELECT_BY_USER_NAME_AND_SECRET_ID.getCommand();
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            PreparedStatement ps = conn.prepareStatement(selectStr);
            ps.setString(1, userName);
            ps.setInt(2, secretId);
            val rst = ps.executeQuery();
            while (rst.next()) {
                userSecret.setId(rst.getInt(1));
                userSecret.setUserName(rst.getString(2));
                userSecret.setSectsId(rst.getInt(3));
                userSecret.setSectsName(rst.getString(4));
                userSecret.setSecretId(rst.getInt(5));
                userSecret.setSecretGarde(rst.getInt(6));
            }
            rst.close();
            ps.close();
            Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
            return userSecret;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userSecret;
    }

    /**
     * 根据用户名查询相关信息
     *
     * @param userName
     * @return
     */
    @Override
    public List<UserSecret> findByUserName(String userName) {
        List<UserSecret> userSecretList = new ArrayList<>();
        try {
            String selectStr = UserSecretSqlEnum.SELECT_BY_USER_NAME.getCommand();
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            PreparedStatement ps = conn.prepareStatement(selectStr);
            ps.setString(1, userName);
            val rst = ps.executeQuery();
            while (rst.next()) {
                UserSecret userSecret = new UserSecret();
                userSecret.setId(rst.getInt(1));
                userSecret.setUserName(rst.getString(2));
                userSecret.setSectsName(rst.getString(3));
                userSecret.setSecretGarde(rst.getInt(4));
                userSecret.setSecretName(rst.getString(5));
                userSecret.setBuffId(rst.getInt(6));
                userSecret.setBuffName(rst.getString(7));
                userSecretList.add(userSecret);
            }
            rst.close();
            ps.close();
            Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
            return userSecretList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userSecretList;
    }

    /**
     * 查询门派
     *
     * @param userName
     * @return
     */
    @Override
    public String findSectsNameByUserName(String userName) {
        String sectsName = "无";
        try {
            String selectStr = UserSecretSqlEnum.SELECT_SECTS_NAME_BY_USER_NAME.getCommand();
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            PreparedStatement ps = conn.prepareStatement(selectStr);
            ps.setString(1, userName);
            val rst = ps.executeQuery();
            while (rst.next()) {
                sectsName = rst.getString(1);
            }
            rst.close();
            ps.close();
            Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
            return sectsName;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sectsName;
    }
}
