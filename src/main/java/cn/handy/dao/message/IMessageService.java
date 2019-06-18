package cn.handy.dao.message;

import cn.handy.entity.Message;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/6/13 13:24
 */
public interface IMessageService {

    /**
     * 如果不存在表则创建表
     * @return
     */
    Boolean create();
    /**
     * 不存在该用户就新增,存在就更新
     *
     * @param message
     * @return
     */
    Boolean set(Message message);

    /**
     * 删除
     *
     * @param userName
     * @return
     */
    Boolean delete(String userName);

    /**
     * 查询
     *
     * @param userName
     * @return
     */
    Message findByUserName(String userName);
}
