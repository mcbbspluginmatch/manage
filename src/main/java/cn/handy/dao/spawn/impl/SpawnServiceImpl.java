package cn.handy.dao.spawn.impl;

import cn.handy.constants.BaseConfigCache;
import cn.handy.constants.sql.SpawnSqlEnum;
import cn.handy.dao.spawn.ISpawnService;
import cn.handy.entity.Spawn;
import cn.handy.utils.Beans;
import lombok.val;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/7/25 11:44
 */
public class SpawnServiceImpl implements ISpawnService {


    /**
     * 创建表
     *
     * @return
     */
    @Override
    public Boolean create() {
        try {
            String userCmd = SpawnSqlEnum.CREATE_SQ_LITE.getCommand();
            if (BaseConfigCache.isUseMySql) {
                userCmd = SpawnSqlEnum.CREATE_MYSQL.getCommand();
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
     * 设置spawn
     *
     * @param spawn
     * @return
     */
    @Override
    public Boolean set(Spawn spawn) {
        try {
            // 查询有无数据
            val spawn1 = findById(spawn.getId());
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            if (spawn1 == null) {
                String addStr = SpawnSqlEnum.ADD_DATA.getCommand();
                PreparedStatement ps = conn.prepareStatement(addStr);
                ps.setDouble(1, spawn.getId());
                ps.setDouble(2, spawn.getX());
                ps.setDouble(3, spawn.getY());
                ps.setDouble(4, spawn.getZ());
                ps.setFloat(5, spawn.getYaw());
                ps.setFloat(6, spawn.getPitch());
                ps.setString(7, spawn.getWorld());
                val rst = ps.executeUpdate();
                ps.close();
                Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
                return rst > 0;
            } else {
                String updateStr = SpawnSqlEnum.UPDATE_DATA.getCommand();
                PreparedStatement ps = conn.prepareStatement(updateStr);
                ps.setDouble(1, spawn.getX());
                ps.setDouble(2, spawn.getY());
                ps.setDouble(3, spawn.getZ());
                ps.setFloat(4, spawn.getYaw());
                ps.setFloat(5, spawn.getPitch());
                ps.setString(6, spawn.getWorld());
                ps.setInt(7, spawn1.getId());
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
     * 根据id查询
     *
     * @param id ID
     * @return
     */
    @Override
    public Spawn findById(Integer id) {
        Spawn spawn = null;
        try {
            String selectStr = SpawnSqlEnum.SELECT_BY_ID.getCommand();
            Connection conn = Beans.getBeans().getSqlManagerUtil().getConnFromPool();
            PreparedStatement ps = conn.prepareStatement(selectStr);
            ps.setInt(1, id);
            val rst = ps.executeQuery();
            while (rst.next()) {
                spawn = new Spawn();
                spawn.setId(rst.getInt(1));
                spawn.setX(rst.getDouble(2));
                spawn.setY(rst.getDouble(3));
                spawn.setZ(rst.getDouble(4));
                spawn.setYaw(rst.getFloat(5));
                spawn.setPitch(rst.getFloat(6));
                spawn.setWorld(rst.getString(7));
            }
            rst.close();
            ps.close();
            Beans.getBeans().getSqlManagerUtil().releaseConnection(conn);
            return spawn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spawn;
    }
}
