package cn.handy.executor.impl;

import cn.handy.Main;
import cn.handy.constants.Constants;
import cn.handy.constants.MsgEnum;
import cn.handy.dao.message.IMessageService;
import cn.handy.dao.message.impl.MessageServiceImpl;
import cn.handy.entity.Message;
import cn.handy.executor.IExecutor;
import lombok.val;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author hanshuai
 * @Description: {msg命令}
 * @date 2019/6/13 14:30
 */
public class MsgExecutorImpl implements IExecutor {

    /**
     * msg命令
     *
     * @param sender 发送人
     * @param cmd    命令
     * @param label  命令
     * @param args   参数
     * @return
     */
    @Override
    public Boolean command(final CommandSender sender, Command cmd, String label, final String[] args) {
        new BukkitRunnable() {
            @Override
            public void run() {
                // 判断是否启用数据库和开启msg功能
                val isUseMysql = Main.config.getBoolean("isUseMysql");
                val isMessage = Main.config.getBoolean("isMessage");
                if (isUseMysql && isMessage) {
                    if (args != null && args.length > 0) {
                        MsgEnum msgEnum = MsgEnum.getMsgEnum(args[0]);
                        IMessageService messageService = new MessageServiceImpl();
                        Message message = new Message();
                        Boolean rst;
                        switch (msgEnum) {
                            case SET:
                                message.setUserName(args[1]);
                                message.setJoinMessage(args[2]);
                                message.setQuitMessage(args[3]);
                                rst = messageService.set(message);
                                if (rst) {
                                    sender.sendMessage("设置成功");
                                } else {
                                    sender.sendMessage("设置失败");
                                }
                                break;
                            case DEL:
                                rst = messageService.delete(args[1]);
                                if (rst) {
                                    sender.sendMessage("删除成功");
                                } else {
                                    sender.sendMessage("删除失败");
                                }
                                break;
                            case SEE:
                                Message user = messageService.findByUserName(args[1]);
                                if (user.getId() != null) {
                                    sender.sendMessage(ChatColor.GOLD + user.getUserName() + "  |  "
                                            + ChatColor.GOLD + user.getJoinMessage() + "  |  "
                                            + ChatColor.GOLD + user.getQuitMessage());
                                } else {
                                    sender.sendMessage("未查询到数据");
                                }
                                break;
                            default:
                                sender.sendMessage(Constants.MSG_HELP);
                                break;
                        }
                    } else {
                        sender.sendMessage(Constants.MSG_HELP);
                    }
                } else {
                    sender.sendMessage("未启用数据库或该功能,该命令无效");
                }
            }
        }.runTaskAsynchronously(Main.plugin);
        return true;
    }
}
