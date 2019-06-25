package cn.handy.constants.sqlite;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hanshuai
 * @Description: {message表相关sql}
 * @date 2019/6/11 12:40
 */
@Getter
@AllArgsConstructor
public enum MsgSqLiteEnum {
    /**
     * 创建表
     */
    CREATE_MESSAGE(
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
