package cn.handy.constants.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hanshuai
 * @Description: {UserSecret表相关sql}
 * @date 2019/7/5 12:40
 */
@Getter
@AllArgsConstructor
public enum UserSecretSqlEnum {
    /**
     * 创建mysql表
     */
    CREATE_MYSQL_USER_SECRET(
            "CREATE TABLE IF NOT EXISTS `mg_user_secret`  (" +
                    "`id` int(11) NOT NULL AUTO_INCREMENT," +
                    "`userName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名'," +
                    "`sectsId` int(11) NULL DEFAULT NULL COMMENT '门派id'," +
                    "`sectsName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门派name'," +
                    "`secretId` int(11) NOT NULL COMMENT '功法id'," +
                    "`secretGarde` int(11) NOT NULL COMMENT '功法等级'," +
                    "PRIMARY KEY (`id`) USING BTREE," +
                    "INDEX `userName`(`userName`) USING BTREE" +
                    ") ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户武林信息表' ROW_FORMAT = Dynamic;"
    ),
    /**
     * 创建sqLite表
     */
    CREATE_SQ_LITE_USER_SECRET(
            "CREATE TABLE IF NOT EXISTS `mg_user_secret` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "`userName` varchar(16)," +
                    "`sectsId` int(11)," +
                    "`sectsName` varchar(16)," +
                    "`secretId` int(11)," +
                    "`secretGarde` int(11)" +
                    ");"
    ),
    ADD_DATA(
            "INSERT INTO `mg_user_secret`" +
                    "(`id`, `userName`, `sectsId`, `sectsName`, `secretId`, `secretGarde`)" +
                    " VALUES (null, ?, ?, ?, ?, ?);"
    ),
    UPDATE_SECRET_GARDE_AND_SECTS_BY_ID(
            "UPDATE `mg_user_secret` SET sectsId = ? , sectsName= ? ,secretGarde= ? WHERE id = ?"
    ),
    DELETE_DATA(
            "DELETE FROM `mg_user_secret` WHERE userName=?"
    ),
    SELECT_BY_USER_NAME_AND_SECRET_ID(
            "SELECT * FROM `mg_user_secret` WHERE `userName` = ? AND `secretId` = ?"
    ),
    SELECT_BY_USER_NAME(
            "SELECT mg_user_secret.id, mg_user_secret.userName, mg_user_secret.sectsName, mg_user_secret.secretGarde, mg_secret.name, mg_secret.buffId, mg_secret.buffName FROM mg_user_secret INNER JOIN mg_secret ON mg_user_secret.secretId = mg_secret.id WHERE mg_user_secret.userName = ?"
    ),
    SELECT_SECTS_NAME_BY_USER_NAME(
            "SELECT sectsName FROM mg_user_secret WHERE userName = ?"
    );
    private String command;
}
