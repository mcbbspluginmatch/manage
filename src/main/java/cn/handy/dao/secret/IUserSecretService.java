package cn.handy.dao.secret;

import cn.handy.entity.UserSecret;

import java.util.List;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/7/5 11:32
 */
public interface IUserSecretService {
    /**
     * 如果不存在表则创建表
     *
     * @return
     */
    Boolean create();

    /**
     * 设置-没有就新增,有的话就更新信息并把功法等级+1
     *
     * @param userSecret
     * @return
     */
    String set(UserSecret userSecret);

    /**
     * 根据用户名和技能序号查询
     *
     * @param userName 用户名
     * @param secretId 功法序号
     * @return
     */
    UserSecret findByUserNameAndSecretId(String userName, Integer secretId);

    /**
     * 根据用户名查询相关信息
     *
     * @param userName
     * @return
     */
    List<UserSecret> findByUserName(String userName);
}
