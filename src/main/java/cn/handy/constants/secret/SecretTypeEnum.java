package cn.handy.constants.secret;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hanshuai
 * @Description: {功法buff类型}
 * @date 2019/7/4 17:19
 */
@Getter
@AllArgsConstructor
public enum SecretTypeEnum {
    DIAMOND_SWORD_IGNORE_ARMOR(0, "钻石剑破甲伤害", 1),
    DIAMOND_SWORD_DAMAGE(1, "钻石剑伤害", 1),
    DIAMOND_AXE_DAMAGE(2, "钻石斧伤害", 1),
    BOW_DAMAGE(3, "弓伤害", 1),
    DIAMOND_SWORD_HEALTH_ADD(4, "钻石剑吸血效果", 1),
    ARMOR(5, "护甲提升", 1),
    IGNORE_DAMAGE(6, "闪避提升", 1),
    RETURN_DAMAGE(7, "反伤提升", 1),
    MOVE(8, "移速提升", 1),
    HP(9, "血量提升", 1),
    THUNDERCLAP(10, "雷霆效果", 1),
    FEATHER_FALL(11, "羽落效果", 1),
    NOT(-1, "无", 0);

    private int id;
    private String name;
    private double result;

    public static SecretTypeEnum getEnum(int id) {
        for (SecretTypeEnum secretSectsEnum : SecretTypeEnum.values()) {
            if (secretSectsEnum.id == id) {
                return secretSectsEnum;
            }
        }
        return NOT;
    }
}
