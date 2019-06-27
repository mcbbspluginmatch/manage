package cn.handy.dao.message.impl;

import cn.handy.constants.Beans;
import cn.handy.constants.sqlite.MsgSqLiteEnum;
import cn.handy.dao.message.IMessageService;
import cn.handy.entity.Message;
import lombok.val;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/6/13 13:34
 */
public class MessageSqLiteServiceImpl implements IMessageService {

    /**
     * 如果不存在表则创建表
     *
     * @return
     */
    @Override
    public Boolean create() {
        try {
            String msgCmd = MsgSqLiteEnum.CREATE_MESSAGE.getCommand();
            Connection conn = Beans.getBeans().getSqLiteManagerUtil().getConnFromPool();
            PreparedStatement ps = conn.prepareStatement(msgCmd);
            val rst = ps.executeUpdate();
            ps.close();
            Beans.getBeans().getSqLiteManagerUtil().releaseConnection(conn);
            return rst > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 不存在该用户就新增,存在就更新
     *
     * @param message
     * @return
     */
    @Override
    public Boolean set(Message message) {
        try {
            // 查询有无数据
            val msg = findByUserName(message.getUserName());
            Connection conn = Beans.getBeans().getSqLiteManagerUtil().getConnFromPool();
            if (msg.getId() == null) {
                String addStr = MsgSqLiteEnum.ADD_DATA.getCommand();
                PreparedStatement ps = conn.prepareStatement(addStr);
                ps.setString(1, message.getUserName());
                ps.setString(2, message.getJoinMessage());
                ps.setString(3, message.getQuitMessage());
                val rst = ps.executeUpdate();
                ps.close();
                Beans.getBeans().getSqLiteManagerUtil().releaseConnection(conn);
                return rst > 0;
            } else {
                String updateStr = MsgSqLiteEnum.UPDATE_DATA.getCommand();
                PreparedStatement ps = conn.prepareStatement(updateStr);
                ps.setString(1, message.getJoinMessage());
                ps.setString(2, message.getQuitMessage());
                ps.setString(3, message.getUserName());
                val rst = ps.executeUpdate();
                ps.close();
                Beans.getBeans().getSqLiteManagerUtil().releaseConnection(conn);
                return rst > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除
     *
     * @param userName
     * @return
     */
    @Override
    public Boolean delete(String userName) {
        try {
            String deleteStr = MsgSqLiteEnum.DELETE_DATA.getCommand();
            Connection conn = Beans.getBeans().getSqLiteManagerUtil().getConnFromPool();
            PreparedStatement ps = conn.prepareStatement(deleteStr);
            ps.setString(1, userName);
            val rst = ps.executeUpdate();
            Beans.getBeans().getSqLiteManagerUtil().releaseConnection(conn);
            ps.close();
            return rst > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查询
     *
     * @param userName
     * @return
     */
    @Override
    public Message findByUserName(String userName) {
        Message message = new Message();
        try {
            String selectStr = MsgSqLiteEnum.SELECT_DATA.getCommand();
            Connection conn = Beans.getBeans().getSqLiteManagerUtil().getConnFromPool();
            PreparedStatement ps = conn.prepareStatement(selectStr);
            ps.setString(1, userName);
            val rst = ps.executeQuery();
            while (rst.next()) {
                message.setId(rst.getInt(1));
                message.setUserName(rst.getString(2));
                message.setJoinMessage(rst.getString(3));
                message.setQuitMessage(rst.getString(4));
            }
            rst.close();
            ps.close();
            Beans.getBeans().getSqLiteManagerUtil().releaseConnection(conn);
            return message;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return message;
    }
}
