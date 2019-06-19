package cn.handy.constants;

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
    CREATE_USER(
            "CREATE TABLE IF NOT EXISTS `gp_user`  (" +
                    "`id` bigint(11) NOT NULL AUTO_INCREMENT," +
                    "`userName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL," +
                    "`realName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL," +
                    "`passWord` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL," +
                    "`loginIp` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL," +
                    "`loginDate` datetime(0) NULL DEFAULT NULL," +
                    "`regIp` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL," +
                    "`regDate` datetime(1) NULL DEFAULT NULL," +
                    "PRIMARY KEY (`id`) USING BTREE" +
                    ") ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;"
    ),
    /**
     * 新增数据
     */
    ADD_DATA(
            "INSERT INTO `gp_user`" +
                    "(`id`, `userName`, `realName`, `passWord`, `loginIp`, `loginDate`, `regIp`, `regDate`)" +
                    " VALUES (0, ?, ?, ?, ?, ?, ?, ?);"
    ),
    /**
     * 根据帐号密码查询来进行登录
     */
    SELECT_BY_USERNAME_AND_PASSWORD(
            "SELECT * FROM `gp_user` WHERE `userName` = ? AND `passWord` = ?"
    ),
    /**
     * 根据帐号和ip来进行查询
     */
    SELECT_BY_USERNAME_AND_LOGIN_IP(
            "SELECT * FROM `gp_user` WHERE `userName` = ? AND `loginIp` = ?"
    ),
    /**
     * 查询是否有该账户
     */
    SELECT_COUNT_BY_USERNAME(
            "SELECT COUNT(1) FROM `gp_user` WHERE `userName` = ?"
    ),
    UPDATE(
            "UPDATE `gp_user` SET `loginIp` = '?', `loginDate` = '?' WHERE `id` = ?"
    );

    private String command;
}
