package cn.handy.constants.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hanshuai
 * @Description: {user表相关sql}
 * @date 2019/5/17 17:36
 */
@Getter
@AllArgsConstructor
public enum UserSqlEnum {
    /**
     * 如果没有user表,创建之
     */
    CREATE_MYSQL_USER(
            "CREATE TABLE IF NOT EXISTS `mg_user`  (" +
                    "`id` int(11) NOT NULL AUTO_INCREMENT," +
                    "`userName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL," +
                    "`realName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL," +
                    "`passWord` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL," +
                    "`loginIp` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL," +
                    "`loginDate` datetime(0) NULL DEFAULT NULL," +
                    "`regIp` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL," +
                    "`regDate` datetime(1) NULL DEFAULT NULL," +
                    "PRIMARY KEY (`id`) USING BTREE" +
                    ") ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;"
    ),
    CREATE_SQ_LITE_USER(
            "CREATE TABLE IF NOT EXISTS `mg_user` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "`userName` varchar(16) NOT NULL," +
                    "`realName` varchar(16) NOT NULL," +
                    "`passWord` varchar(32) NOT NULL," +
                    "`loginIp` varchar(40)," +
                    "`loginDate` datetime," +
                    "`regIp` varchar(40)," +
                    "`regDate` datetime" +
                    ");"
    ),
    /**
     * 新增数据
     */
    ADD_DATA(
            "INSERT INTO `mg_user`" +
                    "(`id`, `userName`, `realName`, `passWord`, `loginIp`, `loginDate`, `regIp`, `regDate`)" +
                    " VALUES (null, ?, ?, ?, ?, ?, ?, ?);"
    ),
    /**
     * 根据帐号密码查询来进行登录
     */
    SELECT_BY_USERNAME_AND_PASSWORD(
            "SELECT * FROM `mg_user` WHERE `userName` = ? AND `passWord` = ?"
    ),
    /**
     * 根据帐号和ip来进行查询
     */
    SELECT_BY_USERNAME_AND_LOGIN_IP(
            "SELECT * FROM `mg_user` WHERE `userName` = ? AND `loginIp` = ?"
    ),
    /**
     * 查询是否有该账户
     */
    SELECT_COUNT_BY_USERNAME(
            "SELECT COUNT(1) FROM `mg_user` WHERE `userName` = ?"
    ),
    UPDATE(
            "UPDATE `mg_user` SET `loginIp` = ?, `loginDate` = ? WHERE `id` = ?"
    );

    private String command;
}
