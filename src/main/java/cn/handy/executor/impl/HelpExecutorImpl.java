package cn.handy.executor.impl;

import cn.handy.Main;
import cn.handy.executor.IExecutor;
import com.google.gson.Gson;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * @author hanshuai
 * @Description: {help指令}
 * @date 2019/6/18 13:47
 */
public class HelpExecutorImpl implements IExecutor {

    /**
     * 帮助
     *
     * @param sender 发送人
     * @param cmd    命令
     * @param label  命令
     * @param args   参数
     * @return
     */
    @Override
    public Boolean command(CommandSender sender, Command cmd, String label, String[] args) {
        String helps = Main.HelpConfig.getString("helps");
        Gson gson = new Gson();
        String[] help = gson.fromJson(helps, String[].class);
        for (String msg : help) {
            sender.sendMessage(msg);
        }
        return true;
    }
}
