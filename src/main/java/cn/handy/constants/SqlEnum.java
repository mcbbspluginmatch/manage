package cn.handy.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hanshuai
 * @Description: {数据库类型}
 * @date 2019/6/25 10:23
 */
@Getter
@AllArgsConstructor
public enum SqlEnum {
    MYSQL("mySql"),
    SQLITE("sqLite");
    private String sql;
}
