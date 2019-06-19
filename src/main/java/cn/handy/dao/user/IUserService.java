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


    /**
     * 注册
     *
     * @param user
     * @return
     */
    Boolean register(User user);


    /**
     * 根据帐号和ip来进行查询
     *
     * @param userName 帐号
     * @param loginIp  ip
     * @return
     */
    User findByUserNameAndLoginIp(String userName, String loginIp);


    /**
     * 更新
     *
     * @param user
     * @return
     */
    Boolean update(User user);
}
