package cn.handy.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hanshuai
 * @Description: {pvp表相关sql}
 * @date 2019/6/24 10:36
 */
@Getter
@AllArgsConstructor
public enum PvpSqlEnum {
    /**
     * 如果没有pvp表,创建之
     */
    CREATE_PVP(
            "CREATE TABLE IF NOT EXISTS `mg_pvp`  (" +
                    "`id` int(11) NOT NULL AUTO_INCREMENT," +
                    "`userName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL," +
                    "`pvp` bit(1) NOT NULL DEFAULT b'0'," +
                    " `particles` bit(1) NOT NULL DEFAULT b'0'," +
                    "PRIMARY KEY (`id`) USING BTREE" +
                    ") ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;"
    ),
    /**
     * 新增数据
     */
    ADD_DATA(
            "INSERT INTO `mg_pvp`" +
                    "(`id`, `userName`, `pvp`, `particles`)" +
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
     * 更新
     */
    UPDATE(
            "UPDATE `mg_pvp` SET `pvp` = ?, `particles` = ? WHERE `userName` = ?"
    );
    private String command;
}
