package cn.handy.dao.pvp.impl;

import cn.handy.constants.PvpSqlEnum;
import cn.handy.dao.pvp.IPvpService;
import cn.handy.entity.Pvp;
import cn.handy.utils.MysqlManagerUtil;
import lombok.val;

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
            String pvpCmd = PvpSqlEnum.CREATE_PVP.getCommand();
            PreparedStatement ps = MysqlManagerUtil.connection.prepareStatement(pvpCmd);
            val rst = ps.executeUpdate();
            ps.close();
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
            if (rst) {
                String addSql = PvpSqlEnum.UPDATE.getCommand();
                PreparedStatement ps = MysqlManagerUtil.connection.prepareStatement(addSql);
                ps.setBoolean(1, pvp.getPvpStatus());
                ps.setString(2, pvp.getUserName());
                val count = ps.executeUpdate();
                ps.close();
                return count > 0;
            } else {
                String addSql = PvpSqlEnum.ADD_DATA.getCommand();
                PreparedStatement ps = MysqlManagerUtil.connection.prepareStatement(addSql);
                ps.setString(1, pvp.getUserName());
                ps.setBoolean(2, pvp.getPvpStatus());
                val count = ps.executeUpdate();
                ps.close();
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
            PreparedStatement ps = MysqlManagerUtil.connection.prepareStatement(pvpCmd);
            ps.setString(1,userName);
            val rst = ps.executeQuery();
            while (rst.next()) {
                count = rst.getInt(1);
            }
            rst.close();
            ps.close();
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
        Pvp pvp = new Pvp();
        try {
            String selectStr = PvpSqlEnum.SELECT_BY_USERNAME.getCommand();
            PreparedStatement ps = MysqlManagerUtil.connection.prepareStatement(selectStr);
            ps.setString(1, userName);
            val rst = ps.executeQuery();
            while (rst.next()) {
                pvp.setId(rst.getInt(1));
                pvp.setUserName(rst.getString(2));
                pvp.setPvpStatus(rst.getBoolean(3));
            }
            rst.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return pvp;
    }
}
