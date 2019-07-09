package cn.handy.dao.home;

import cn.handy.entity.Home;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/7/9 10:09
 */
public interface IHomeService {

    /**
     * 如果不存在表则创建表
     *
     * @return
     */
    Boolean create();

    /**
     * 设置家
     *
     * @param home
     * @return
     */
    Boolean set(Home home);

    /**
     * 根据用户名和家名查询
     *
     * @param userName 用户名
     * @param homeName 家名
     * @return
     */
    Home findByUserNameAndHomeName(String userName, String homeName);

    /**
     * 查询有几个家
     *
     * @param userName
     * @return
     */
    Integer findByCount(String userName);
}
