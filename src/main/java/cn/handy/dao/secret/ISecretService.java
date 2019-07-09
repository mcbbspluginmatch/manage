package cn.handy.dao.secret;

import cn.handy.entity.Secret;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/7/5 11:19
 */
public interface ISecretService {
    /**
     * 如果不存在表则创建表
     *
     * @return
     */
    Boolean create();

    /**
     * 不存在就新增,存在就更新
     *
     * @param secret
     * @return
     */
    Boolean set(Secret secret);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    Secret findById(Integer id);
}
