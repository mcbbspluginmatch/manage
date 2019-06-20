package cn.handy.command.msg;

import cn.handy.constants.BaseConstants;
import cn.handy.constants.MsgEnum;
import cn.handy.dao.message.IMessageService;
import cn.handy.dao.message.impl.MessageServiceImpl;
import cn.handy.entity.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * @author hanshuai
 * @Description: {指令注册类}
 * @date 2019/6/20 10:42
 */
public class MsgAdminCommand extends Command {

    public MsgAdminCommand() {
        // 命令
        super("msgadmin");
        // 权限
        this.setPermission("handy.msgadmin");
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        if (!sender.hasPermission("handy.msgadmin")) {
            sender.sendMessage(ChatColor.RED + "§c你没有该命令的权限!");
            return true;
        }
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
                    sender.sendMessage(BaseConstants.MSG_ADMIN_HELP);
                    break;
            }
        } else {
            sender.sendMessage(BaseConstants.MSG_ADMIN_HELP);
        }
        return true;
    }
}
