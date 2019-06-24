package cn.handy.dao.pvp;

import cn.handy.entity.Pvp;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/6/24 10:48
 */
public interface IPvpService {

    /**
     * 创建表
     *
     * @return
     */
    Boolean create();

    /**
     * 设置
     *
     * @param pvp
     * @return
     */
    Boolean set(Pvp pvp);

    /**
     * 根据用户查询是否存在
     *
     * @param userName
     * @return
     */
    Boolean findCountByUserName(String userName);


    /**
     * 查询
     *
     * @param userName
     * @return
     */
    Pvp findByUserName(String userName);
}

