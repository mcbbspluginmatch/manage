package cn.handy.command.msg;

import cn.handy.constants.BaseConstants;
import cn.handy.entity.Message;
import cn.handy.utils.Beans;
import lombok.val;
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
        super("msgAdmin");
        // 权限
        this.setPermission("handy.msgAdmin");
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        if (!sender.hasPermission("handy.msgAdmin")) {
            sender.sendMessage(ChatColor.RED + "你没有该命令的权限!");
            return true;
        }
        if (args != null && args.length > 0) {
            val messageService = Beans.getBeans().getMessageService();
            Message message = new Message();
            Boolean rst;
            switch (args[0]) {
                case "set":
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
                case "del":
                    rst = messageService.delete(args[1]);
                    if (rst) {
                        sender.sendMessage("删除成功");
                    } else {
                        sender.sendMessage("删除失败");
                    }
                    break;
                case "see":
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
