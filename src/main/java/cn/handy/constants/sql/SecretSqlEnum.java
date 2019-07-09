package cn.handy.constants.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hanshuai
 * @Description: {Secret表相关sql}
 * @date 2019/7/5 12:40
 */
@Getter
@AllArgsConstructor
public enum SecretSqlEnum {
    /**
     * 创建mysql表
     */
    CREATE_MYSQL_SECRET(
            "CREATE TABLE IF NOT EXISTS `mg_secret`  (" +
                    "`id` int(11) NOT NULL AUTO_INCREMENT," +
                    "`sectsId` int(11) NULL DEFAULT NULL COMMENT '门派id'," +
                    "`sectsName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门派name'," +
                    "`name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '功法名'," +
                    "`lore` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功法lore'," +
                    "`buffId` int(11) NULL DEFAULT NULL COMMENT '功法buffId'," +
                    "`buffName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功夫buffName'," +
                    "PRIMARY KEY (`id`) USING BTREE" +
                    ") ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '功法表' ROW_FORMAT = Dynamic;"
    ),
    /**
     * 创建sqLite表
     */
    CREATE_SQ_LITE_SECRET(
            "CREATE TABLE IF NOT EXISTS `mg_secret` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "`sectsId` int(11)," +
                    "`sectsName` varchar(16)," +
                    "`name` varchar(255)" +
                    "`lore` varchar(1000)" +
                    "`buffId` int(11)" +
                    "`buffName` varchar(255)" +
                    ");"
    ),
    ADD_DATA(
            "INSERT INTO `mg_secret`" +
                    "(`id`, `sectsId`, `sectsName`, `name`, `lore`, `buffId`, `buffName`)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?);"
    ),
    UPDATE_DATA(
            "UPDATE `mg_secret` SET sectsId = ?,sectsName = ?,name = ?,lore = ?,buffId = ?,buffName = ? WHERE id = ?"
    ),
    DELETE_DATA(
            "DELETE FROM `mg_secret` WHERE id=?"
    ),
    SELECT_DATA(
            "SELECT * FROM `mg_secret` WHERE `id` = ?"
    );

    private String command;
}
