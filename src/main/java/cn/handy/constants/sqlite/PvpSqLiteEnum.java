package cn.handy.constants.sqlite;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hanshuai
 * @Description: {pvp表相关sql}
 * @date 2019/6/24 10:36
 */
@Getter
@AllArgsConstructor
public enum PvpSqLiteEnum {
    /**
     * 如果没有pvp表,创建之
     */
    CREATE_PVP(
            "CREATE TABLE IF NOT EXISTS `mg_pvp` (" +
                    "`id` int(11) NOT NULL," +
                    "`userName` varchar(16) NOT NULL," +
                    "`pvpStatus` bit(1) NOT NULL," +
                    "`particle` bit(1) NOT NULL," +
                    "PRIMARY KEY (`id`)" +
                    ");"
    ),
    /**
     * 新增数据
     */
    ADD_DATA(
            "INSERT INTO `mg_pvp`" +
                    "(`id`, `userName`, `pvpStatus`, `particle`)" +
                    " VALUES (0, ?, ?, ?);"
    ),
    /**
     * 根据帐号查询
     */
    SELECT_BY_USERNAME(
            "SELECT * FROM `mg_pvp` WHERE `userName` = ?"
    ),
    /**
     * 查询是否有该账户
     */
    SELECT_COUNT_BY_USERNAME(
            "SELECT COUNT(1) FROM `mg_pvp` WHERE `userName` = ?"
    ),
    /**
     * 更新pvpStatus
     */
    UPDATE_PVP_STATUS(
            "UPDATE `mg_pvp` SET `pvpStatus` = ? WHERE `userName` = ?"
    ),
    /**
     * 更新particle
     */
    UPDATE_PARTICLE(
            "UPDATE `mg_pvp` SET `particle` = ? WHERE `userName` = ?"
    );
    private String command;
}
