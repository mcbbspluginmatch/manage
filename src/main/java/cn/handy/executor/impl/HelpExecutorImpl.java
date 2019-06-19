package cn.handy.executor.impl;

import cn.handy.Main;
import cn.handy.executor.IExecutor;
import cn.handy.utils.ListPageUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.val;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

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
        // 判断是否启用Help列表替换
        val isHelp = Main.config.getBoolean("isHelp");
        if (!isHelp){
            sender.sendMessage("未启用help指令帮助");
            return true;
        }
        String jsonArray = Main.HelpConfig.getString("helps");
        Gson gson = new Gson();
        List<String> help = gson.fromJson(jsonArray, new TypeToken<List<String>>() {
        }.getType());
        int pageNum = 1;
        if (args != null && args.length == 1) {
            String arg = args[0];
            pageNum = Integer.parseInt(arg);
        }
        // 对list进行分页
        ListPageUtil listPageUtil = new ListPageUtil(help, pageNum, 10);

        // 总页数
        int totalPage = listPageUtil.getTotalPage();
        // 分页数据
        List<String> data = listPageUtil.getData();
        for (String datum : data) {
            sender.sendMessage(datum);
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
        if (pageNum >= totalPage) {
            TextComponent message = new TextComponent(ChatColor.AQUA + "[上一页]----------------------------");
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/help " + (pageNum - 1)));
            sender.sendMessage(message);
        }
        return true;
    }
}
