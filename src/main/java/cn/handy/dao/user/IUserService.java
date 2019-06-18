package cn.handy.dao.user;

import cn.handy.entity.User;

/**
 * @author hanshuai
 * @Description: {用户表}
 * @date 2019/6/13 14:08
 */
public interface IUserService {
    /**
     * 如果不存在表则创建表
     *
     * @return
     */
    Boolean create();

    /**
     * 查询是否有该账户
     *
     * @param userName
     * @return
     */
    Boolean findByUserName(String userName);

    /**
     * 登录
     *
     * @param userName
     * @param passWord
     * @return
     */
    User login(String userName, String passWord);
}
