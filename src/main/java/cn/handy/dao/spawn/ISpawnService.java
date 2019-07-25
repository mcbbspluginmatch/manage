package cn.handy.dao.spawn;

import cn.handy.entity.Spawn;

/**
 * @author hanshuai
 * @Description: {spawn}
 * @date 2019/7/25 11:42
 */
public interface ISpawnService {
    /**
     * 如果不存在表则创建表
     *
     * @return
     */
    Boolean create();

    /**
     * 设置spawn
     *
     * @param spawn
     * @return
     */
    Boolean set(Spawn spawn);

    /**
     * 根据id查询
     *
     * @param id ID
     * @return
     */
    Spawn findById(Integer id);
}
