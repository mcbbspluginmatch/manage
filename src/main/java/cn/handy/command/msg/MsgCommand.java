package cn.handy.command.msg;

import cn.handy.Manage;
import cn.handy.constants.BaseConstants;
import cn.handy.dao.message.IMessageService;
import cn.handy.entity.Message;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.Beans;
import lombok.val;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
        if (!sender.hasPermission("handy.msg")) {
            sender.sendMessage(ChatColor.RED + "你没有该命令的权限!");
            return true;
        }
        val isPlayer = BaseUtil.isPlayer(sender);
        if (isPlayer) {
            if (args != null && args.length > 0) {
                final Player sendPlayer = (Player) sender;
                final IMessageService messageService = Beans.getBeans().getMessageService();
                final Message message = new Message();
                switch (args[0]) {
                    case "set":
                        if (args.length != 3) {
                            sender.sendMessage(BaseConstants.MSG_HELP);
                            return true;
                        }
                        message.setUserName(sendPlayer.getName());
                        message.setJoinMessage(BaseUtil.replaceChatColor(args[1]));
                        message.setQuitMessage(BaseUtil.replaceChatColor(args[2]));
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                val rst = messageService.set(message);
                                if (rst) {
                                    sendPlayer.sendMessage("设置成功");
                                } else {
                                    sendPlayer.sendMessage("设置失败");
                                }
                            }
                        }.runTaskAsynchronously(Manage.plugin);
                        break;
                    case "del":
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                val rst = messageService.delete(sendPlayer.getName());
                                if (rst) {
                                    sendPlayer.sendMessage("删除成功");
                                } else {
                                    sendPlayer.sendMessage("删除失败");
                                }
                            }
                        }.runTaskAsynchronously(Manage.plugin);
                        break;
                    case "see":
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                val user = messageService.findByUserName(sendPlayer.getName());
                                if (user != null) {
                                    sendPlayer.sendMessage(ChatColor.GOLD + user.getUserName() + "进入游戏提醒为:\n"
                                            + ChatColor.GOLD + user.getJoinMessage() + "退出游戏提醒为:\n"
                                            + ChatColor.GOLD + user.getQuitMessage());
                                } else {
                                    sendPlayer.sendMessage("未查询到数据");
                                }
                            }
                        }.runTaskAsynchronously(Manage.plugin);
                        break;
                    default:
                        sendPlayer.sendMessage(BaseConstants.MSG_HELP);
                        break;
                }
            } else {
                sender.sendMessage(BaseConstants.MSG_HELP);
            }
        } else {
            sender.sendMessage(ChatColor.RED + "控制台不能使用该命令");
        }
        return true;
    }
}
