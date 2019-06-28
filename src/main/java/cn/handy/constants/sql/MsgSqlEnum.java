package cn.handy.constants.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hanshuai
 * @Description: {message表相关sql}
 * @date 2019/6/11 12:40
 */
@Getter
@AllArgsConstructor
public enum MsgSqlEnum {
    /**
     * 创建mysql表
     */
    CREATE_MYSQL_MESSAGE(
            "CREATE TABLE IF NOT EXISTS `mg_message`  (" +
                    "`id` int(11) NOT NULL AUTO_INCREMENT," +
                    "`userName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL," +
                    "`joinMessage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL," +
                    "`quitMessage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL," +
                    "PRIMARY KEY (`id`) USING BTREE," +
                    "INDEX `inx_name`(`userName`) USING BTREE" +
                    ") ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;"
    ),
    /**
     * 创建sqLite表
     */
    CREATE_SQ_LITE_MESSAGE(
            "CREATE TABLE IF NOT EXISTS `mg_message` (" +
                    "`id` int(11) NOT NULL," +
                    "`userName` varchar(16) NOT NULL," +
                    "`joinMessage` varchar(255)," +
                    "`quitMessage` varchar(255)," +
                    "PRIMARY KEY (`id`)" +
                    ");"
    ),
    ADD_DATA(
            "INSERT INTO `mg_message`" +
                    "(`id`, `userName`, `joinMessage`, `quitMessage`)" +
                    " VALUES (0, ?, ?, ?);"
    ),
    UPDATE_DATA(
            "UPDATE `mg_message` SET joinMessage = ?,quitMessage = ? WHERE userName = ?"
    ),
    DELETE_DATA(
            "DELETE FROM `mg_message` WHERE userName=?"
    ),
    SELECT_DATA(
            "SELECT * FROM `mg_message` WHERE `userName` = ?"
    );

    private String command;
}
