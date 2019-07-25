package cn.handy.dao.pvp.impl;

import cn.handy.constants.BaseConfigCache;
import cn.handy.constants.sql.PvpSqlEnum;
import cn.handy.dao.pvp.IPvpService;
import cn.handy.entity.Pvp;
import cn.handy.utils.Beans;
import lombok.val;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/6/24 10:48
 */
public class PvpServiceImpl implements IPvpService {

    /**
     * 创建表
     *
     * @return
     */
    @Override
    public Boolean create() {
        try {
            String pvpCmd = PvpSqlEnum.CREATE_SQ_LITE_PVP.getCommand();
            if (BaseConfigCache.isUseMySql) {
                pvpCmd = PvpSqlEnum.CREATE_MYSQL_PVP.getCommand();
            }
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            PreparedStatement ps = conn.prepareStatement(pvpCmd);
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
     * 设置
     *
     * @param pvp
     * @return
     */
    @Override
    public Boolean set(Pvp pvp) {
        try {
            val rst = findCountByUserName(pvp.getUserName());
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            if (rst) {
                String addSql = PvpSqlEnum.UPDATE_PVP_STATUS.getCommand();
                PreparedStatement ps = conn.prepareStatement(addSql);
                ps.setBoolean(1, pvp.getPvpStatus());
                ps.setString(2, pvp.getUserName());
                val count = ps.executeUpdate();
                ps.close();
                Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
                return count > 0;
            } else {
                String addSql = PvpSqlEnum.ADD_DATA.getCommand();
                PreparedStatement ps = conn.prepareStatement(addSql);
                ps.setString(1, pvp.getUserName());
                ps.setBoolean(2, pvp.getPvpStatus());
                ps.setBoolean(3, pvp.getParticle());
                val count = ps.executeUpdate();
                ps.close();
                Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据用户查询是否存在
     *
     * @param userName
     * @return
     */
    @Override
    public Boolean findCountByUserName(String userName) {
        int count = 0;
        try {
            String pvpCmd = PvpSqlEnum.SELECT_COUNT_BY_USERNAME.getCommand();
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            PreparedStatement ps = conn.prepareStatement(pvpCmd);
            ps.setString(1, userName);
            val rst = ps.executeQuery();
            while (rst.next()) {
                count = rst.getInt(1);
            }
            rst.close();
            ps.close();
            Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count > 0;
    }

    /**
     * 查询
     *
     * @param userName
     * @return
     */
    @Override
    public Pvp findByUserName(String userName) {
        Pvp pvp = null;
        try {
            String selectStr = PvpSqlEnum.SELECT_BY_USERNAME.getCommand();
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            PreparedStatement ps = conn.prepareStatement(selectStr);
            ps.setString(1, userName);
            val rst = ps.executeQuery();
            while (rst.next()) {
                pvp = new Pvp();
                pvp.setId(rst.getInt(1));
                pvp.setUserName(rst.getString(2));
                pvp.setPvpStatus(rst.getBoolean(3));
                pvp.setParticle(rst.getBoolean(4));
            }
            rst.close();
            ps.close();
            Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pvp;
    }

    /**
     * 设置粒子特效开关
     *
     * @param userName
     * @param particle
     * @return
     */
    @Override
    public Boolean setParticle(String userName, Boolean particle) {
        try {
            String sql = PvpSqlEnum.UPDATE_PARTICLE.getCommand();
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, particle);
            ps.setString(2, userName);
            val count = ps.executeUpdate();
            ps.close();
            Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
