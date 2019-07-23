package cn.handy.command.help;

import cn.handy.constants.BaseConstants;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.ListPageUtil;
import lombok.val;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author hanshuai
 * @Description: {指令注册类}
 * @date 2019/6/20 10:42
 */
public class HelpCommand extends Command {

    public HelpCommand() {
        // 命令
        super("help");
        // 权限
        this.setPermission("handy.help");
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        if (!sender.hasPermission("handy.help")) {
            sender.sendMessage(ChatColor.RED + "你没有该命令的权限!");
            return true;
        }
        int pageNum = 1;
        if (args != null && args.length == 1) {
            String arg = args[0];
            val rst = BaseUtil.isNumeric(arg);
            if (rst) {
                pageNum = Integer.parseInt(arg);
            }
        }
        // 对list进行分页
        ListPageUtil listPageUtil = new ListPageUtil(BaseConstants.helpList, pageNum, 10);

        // 总页数
        int totalPage = listPageUtil.getTotalPage();
        // 分页数据
        List<String> data = listPageUtil.getData();
        for (String datum : data) {
            sender.sendMessage(BaseUtil.replaceChatColor(datum));
        }

        // 大于1并小于总页数--出现上一页跟下一页
        if (pageNum < totalPage) {
            TextComponent message = new TextComponent(ChatColor.AQUA + "[上一页]----------");
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/help " + (pageNum - 1)));
            TextComponent messageDown = new TextComponent(ChatColor.AQUA + "----------[下一页]");
            messageDown.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/help " + (pageNum + 1)));
            message.addExtra(messageDown);
            sender.sendMessage(message);
        }
        // 大于总页数--出现上一页
        if (pageNum != 1 && pageNum >= totalPage) {
            TextComponent message = new TextComponent(ChatColor.AQUA + "[上一页]----------------------------");
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/help " + (pageNum - 1)));
            sender.sendMessage(message);
        }
        return true;
    }
}
