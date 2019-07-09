package cn.handy.constants.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hanshuai
 * @Description: {home表相关sql}
 * @date 2019/7/9 10:30
 */
@Getter
@AllArgsConstructor
public enum HomeSqlEnum {
    /**
     * 创建home表
     */
    CREATE_MYSQL_MESSAGE(
            "CREATE TABLE IF NOT EXISTS `mg_home`  (" +
                    "`id` int(11) NOT NULL AUTO_INCREMENT," +
                    "`userName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL," +
                    "`homeName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL," +
                    "`x` double(16, 0) NULL DEFAULT NULL," +
                    "`y` double(16, 0) NULL DEFAULT NULL," +
                    "`z` double(16, 0) NULL DEFAULT NULL," +
                    "`yaw` float(16, 0) NULL DEFAULT NULL," +
                    "`pitch` float(16, 0) NULL DEFAULT NULL," +
                    "`world` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL," +
                    "PRIMARY KEY (`id`) USING BTREE," +
                    "INDEX `name`(`userName`, `homeName`) USING BTREE" +
                    ") ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;"
    ),
    /**
     * 创建sqLite表
     */
    CREATE_SQ_LITE_MESSAGE(
            "CREATE TABLE IF NOT EXISTS `mg_home` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "`userName` varchar(16) NOT NULL," +
                    "`homeName` varchar(16)," +
                    "`x` double(16)" +
                    "`y` double(16)" +
                    "`z` double(16)" +
                    "`yaw` float(16)" +
                    "`pitch` float(16)" +
                    "`world` varchar(16)" +
                    ");"
    ),
    ADD_DATA(
            "INSERT INTO `mg_home`" +
                    "(`id`, `userName`, `homeName`, `x`, `y`, `z`, `yaw`, `pitch`, `world`)" +
                    " VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?);"
    ),
    UPDATE_DATA(
            "UPDATE `mg_home` SET x = ?, y = ?, z = ?, yaw = ?, pitch = ?, world = ? WHERE id = ?"
    ),
    DELETE_DATA(
            "DELETE FROM `mg_home` WHERE userName= ? AND homeName= ?"
    ),
    SELECT_BY_USER_NAME_AND_HOME_NAME(
            "SELECT * FROM `mg_home` WHERE `userName` = ? AND homeName= ?"
    ),
    SELECT_COUNT(
            "SELECT count(*) FROM `mg_home` WHERE `userName` = ?"
    );

    private String command;
}
