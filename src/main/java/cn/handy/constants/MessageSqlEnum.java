package cn.handy.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hanshuai
 * @Description: {message表相关sql}
 * @date 2019/6/11 12:40
 */
@Getter
@AllArgsConstructor
public enum MessageSqlEnum {
    /**
     * 创建表
     */
    CREATE_MESSAGE(
            "CREATE TABLE IF NOT EXISTS `gp_message`  (" +
                    "`id` int(11) NOT NULL AUTO_INCREMENT," +
                    "`userName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL," +
                    "`joinMessage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL," +
                    "`quitMessage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL," +
                    "PRIMARY KEY (`id`) USING BTREE," +
                    "INDEX `inx_name`(`userName`) USING BTREE" +
                    ") ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;"
    ),
    ADD_DATA(
            "INSERT INTO `gp_message`" +
                    "(`id`, `userName`, `joinMessage`, `quitMessage`)" +
                    " VALUES (0, ?, ?, ?);"
    ),
    UPDATE_DATA(
            "UPDATE `gp_message` SET joinMessage = ?,quitMessage = ? WHERE userName = ?"
    ),
    DELETE_DATA(
            "DELETE FROM `gp_message` WHERE userName=?"
    ),
    SELECT_DATA(
            "SELECT * FROM `gp_message` WHERE `userName` = ?"
    );

    private String command;
}
