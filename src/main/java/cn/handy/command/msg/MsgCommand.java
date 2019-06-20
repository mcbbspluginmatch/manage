package cn.handy.command.msg;

import cn.handy.constants.BaseConfigCache;
import cn.handy.constants.BaseConstants;
import cn.handy.constants.MsgEnum;
import cn.handy.dao.message.IMessageService;
import cn.handy.dao.message.impl.MessageServiceImpl;
import cn.handy.entity.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

/**
 * @author hanshuai
 * @Description: {指令注册类}
 * @date 2019/6/20 10:42
 */
public class MsgCommand extends Command {

    public MsgCommand() {
        // 命令
        super("msg");
        // 权限
        this.setPermission("handy.msg");
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        // 判断是否开启msg功能
        if (BaseConfigCache.isMessage) {
            if (args != null && args.length > 0) {
                if (!sender.hasPermission("handy.msg")){
                    sender.sendMessage(ChatColor.RED + "§c你没有该命令的权限!");
                    return true;
                }
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
                        sender.sendMessage(BaseConstants.MSG_HELP);
                        break;
                }
            } else {
                sender.sendMessage(BaseConstants.MSG_HELP);
            }
        } else {
            sender.sendMessage("未启用数据库或该功能,该命令无效");
        }
        return true;
    }
}