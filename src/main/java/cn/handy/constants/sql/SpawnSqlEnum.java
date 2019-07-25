package cn.handy.constants.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hanshuai
 * @Description: {spawn表相关sql}
 * @date 2019/7/9 10:30
 */
@Getter
@AllArgsConstructor
public enum SpawnSqlEnum {
    /**
     * 创建spawn表
     */
    CREATE_MYSQL(
            "CREATE TABLE IF NOT EXISTS `mg_spawn`  (" +
                    "`id` int(11) NOT NULL AUTO_INCREMENT," +
                    "`x` double(16, 0) NULL DEFAULT NULL," +
                    "`y` double(16, 0) NULL DEFAULT NULL," +
                    "`z` double(16, 0) NULL DEFAULT NULL," +
                    "`yaw` float(16, 0) NULL DEFAULT NULL," +
                    "`pitch` float(16, 0) NULL DEFAULT NULL," +
                    "`world` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL," +
                    "PRIMARY KEY (`id`) USING BTREE" +
                    ") ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;"
    ),
    /**
     * 创建sqLite表
     */
    CREATE_SQ_LITE(
            "CREATE TABLE IF NOT EXISTS `mg_spawn` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "`x` double(16)," +
                    "`y` double(16)," +
                    "`z` double(16)," +
                    "`yaw` float(16)," +
                    "`pitch` float(16)," +
                    "`world` varchar(16)" +
                    ");"
    ),
    ADD_DATA(
            "INSERT INTO `mg_spawn`" +
                    "(`id`, `x`, `y`, `z`, `yaw`, `pitch`, `world`)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?);"
    ),
    UPDATE_DATA(
            "UPDATE `mg_spawn` SET x = ?, y = ?, z = ?, yaw = ?, pitch = ?, world = ? WHERE id = ?"
    ),
    DELETE_DATA(
            "DELETE FROM `mg_spawn` WHERE id = ?"
    ),
    SELECT_BY_ID(
            "SELECT * FROM `mg_spawn` WHERE `id` = ?"
    );

    private String command;
}
