package cn.handy.dao.secret.impl;

import cn.handy.constants.BaseConfigCache;
import cn.handy.constants.sql.SecretSqlEnum;
import cn.handy.dao.secret.ISecretService;
import cn.handy.entity.Secret;
import cn.handy.utils.Beans;
import lombok.val;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/7/5 11:24
 */
public class SecretServiceImpl implements ISecretService {

    @Override
    public Boolean create() {
        try {
            String msgCmd = SecretSqlEnum.CREATE_SQ_LITE_SECRET.getCommand();
            if (BaseConfigCache.isUseMySql) {
                msgCmd = SecretSqlEnum.CREATE_MYSQL_SECRET.getCommand();
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

    @Override
    public Boolean set(Secret secret) {
        try {
            // 查询有无数据
            Secret sec = findById(secret.getId());
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            if (sec.getId() == null) {
                String addStr = SecretSqlEnum.ADD_DATA.getCommand();
                PreparedStatement ps = conn.prepareStatement(addStr);
                ps.setInt(1, secret.getId());
                ps.setInt(2, secret.getSectsId());
                ps.setString(3, secret.getSectsName());
                ps.setString(4, secret.getName());
                ps.setString(5, secret.getLore());
                ps.setInt(6, secret.getBuffId());
                ps.setString(7, secret.getBuffName());
                val rst = ps.executeUpdate();
                ps.close();
                Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
                return rst > 0;
            } else {
                String updateStr = SecretSqlEnum.UPDATE_DATA.getCommand();
                PreparedStatement ps = conn.prepareStatement(updateStr);
                ps.setInt(1, secret.getSectsId());
                ps.setString(2, secret.getSectsName());
                ps.setString(3, secret.getName());
                ps.setString(4, secret.getLore());
                ps.setInt(5, secret.getBuffId());
                ps.setString(6, secret.getBuffName());
                ps.setInt(7, secret.getId());
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

    @Override
    public Secret findById(Integer id) {
        Secret secret = new Secret();
        try {
            String selectStr = SecretSqlEnum.SELECT_DATA.getCommand();
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            PreparedStatement ps = conn.prepareStatement(selectStr);
            ps.setInt(1, id);
            val rst = ps.executeQuery();
            while (rst.next()) {
                secret.setId(rst.getInt(1));
                secret.setSectsId(rst.getInt(2));
                secret.setSectsName(rst.getString(3));
                secret.setName(rst.getString(4));
                secret.setLore(rst.getString(5));
                secret.setBuffId(rst.getInt(6));
                secret.setBuffName(rst.getString(7));
            }
            rst.close();
            ps.close();
            Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
            return secret;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return secret;
    }
}
