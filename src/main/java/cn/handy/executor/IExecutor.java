package cn.handy.executor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * @author hanshuai
 * @Description: {命令接口}
 * @date 2019/6/12 14:41
 */
public interface IExecutor {

    /**
     * 命令接口
     *
     * @param sender 发送人
     * @param cmd    命令
     * @param label  命令
     * @param args   参数
     * @return
     */
    Boolean command(CommandSender sender, Command cmd, String label, final String[] args);
}
