package cn.handy.constants.secret;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hanshuai
 * @Description: {门派}
 * @date 2019/7/4 17:27
 */
@Getter
@AllArgsConstructor
public enum SecretSectsEnum {
    E_MEI(0, "峨眉派"),
    RAN_SHAN(1, "然山派"),
    WU_XIAN(2, "五仙教"),
    YUAN_SHAN(3, "元山派"),
    KON_SAN(4, "空桑派"),
    XUE_HOU(5, "血犼教"),
    BAI_HUA(6, "百花谷"),
    XUANG_NV(7, "璇女派"),
    JIE_QING(8, "界青门"),
    WU_DAN(9, "武当派"),
    ZHU_JIANG(10, "铸剑山庄"),
    FU_LON_TAN(11, "伏龙潭"),
    NOT(-1, "无");
    private int id;
    private String name;

    public static SecretSectsEnum getEnum(int id) {
        for (SecretSectsEnum secretSectsEnum : SecretSectsEnum.values()) {
            if (secretSectsEnum.id == id) {
                return secretSectsEnum;
            }
        }
        return NOT;
    }
}
