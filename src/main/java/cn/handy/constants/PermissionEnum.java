package cn.handy.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hanshuai
 * @Description: {权限枚举}
 * @date 2019/7/16 18:03
 */
@Getter
@AllArgsConstructor
public enum PermissionEnum {
    manage("");
    public String permission;
}
