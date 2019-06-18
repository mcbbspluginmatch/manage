package cn.handy.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author hanshuai
 * @Description: {命令枚举}
 * @date 2019/6/11 12:40
 */
@Getter
@AllArgsConstructor
public enum CommandEnum {
    TP("tp", "TpExecutorImpl", "handy.tp"),
    TPA("tpa", "TpaExecutorImpl", ""),
    TPACCEPT("tpaccept", "TpacceptExecutorImpl", ""),
    TPDENY("tpdeny", "TpdenyExecutorImpl", ""),
    HAT("hat", "HatExecutorImpl", "handy.hat"),
    MSG("msg", "MsgExecutorImpl", "handy.msg"),
    LOGIN("l", "LoginExecutorImpl", ""),
    NOT_HASPERMISSION("not", "ErrorExecutorImpl", "");

    private String command;
    private String className;
    private String permission;

    public static CommandEnum getCommandEnum(String command) {
        for (CommandEnum commandEnum : CommandEnum.values()) {
            if (commandEnum.command.equalsIgnoreCase(command)) {
                return commandEnum;
            }
        }
        return NOT_HASPERMISSION;
    }

    public static CommandEnum getCommandEnum(CommandSender sender, String command) {
        Player player = (Player) sender;
        for (CommandEnum commandEnum : CommandEnum.values()) {
            if (commandEnum.permission.equalsIgnoreCase("")) {
                if (commandEnum.command.equalsIgnoreCase(command)) {
                    return commandEnum;
                }
            } else {
                if (commandEnum.command.equalsIgnoreCase(command) && player.hasPermission(commandEnum.getPermission())) {
                    return commandEnum;
                }
            }
        }
        return NOT_HASPERMISSION;
    }
}
