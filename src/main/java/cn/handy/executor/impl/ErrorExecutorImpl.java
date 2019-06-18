package cn.handy.executor.impl;

import cn.handy.executor.IExecutor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * @author hanshuai
 * @Description: {没有权限的处理}
 * @date 2019/6/13 12:31
 */
public class ErrorExecutorImpl implements IExecutor {

    @Override
    public Boolean command(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage(ChatColor.RED + "你还没有该命令的权限哦~");
        return true;
    }
}
