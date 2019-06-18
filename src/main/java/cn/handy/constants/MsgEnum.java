package cn.handy.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hanshuai
 * @Description: {msg子命令}
 * @date 2019/6/14 10:06
 */
@Getter
@AllArgsConstructor
public enum MsgEnum {
    SET("set"),
    DEL("del"),
    SEE("see"),
    QI_TA("qi_ta");
    private String command;

    public static MsgEnum getMsgEnum(String command) {
        for (MsgEnum msgEnum : MsgEnum.values()) {
            if (msgEnum.command.equalsIgnoreCase(command)) {
                return msgEnum;
            }
        }
        return QI_TA;
    }
}
