package cn.handy.command.vault;

import cn.handy.Manage;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.ListPageUtil;
import cn.handy.utils.VaultUtil;
import lombok.val;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanshuai
 * @Description: {vault}
 * @date 2019/7/30 17:32
 */
public class BalTopCommand extends Command {

    public BalTopCommand() {
        // 命令
        super("baltop");
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        // 玩家多了很费时,全部异步处理
        new BukkitRunnable() {
            @Override
            public void run() {
                // 全部保存为列表
                List<String> balTopList = new ArrayList<>();
                List<String> banks = VaultUtil.econ.getBanks();
                for (String bank : banks) {
                    EconomyResponse balance = VaultUtil.econ.bankBalance(bank);
                    balTopList.add(bank + "     " + balance.balance);
                }

                // 当前页
                int pageNum = 1;
                if (args != null && args.length == 1) {
                    String arg = args[0];
                    val rst = BaseUtil.isNumeric(arg);
                    if (rst) {
                        pageNum = Integer.parseInt(arg);
                    }
                }

                // 对list进行分页
                ListPageUtil listPageUtil = new ListPageUtil(balTopList, pageNum, 10);

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
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/baltop " + (pageNum - 1)));
                    TextComponent messageDown = new TextComponent(ChatColor.AQUA + "----------[下一页]");
                    messageDown.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/baltop " + (pageNum + 1)));
                    message.addExtra(messageDown);
                    sender.sendMessage(message);
                }
                // 大于总页数--出现上一页
                if (pageNum != 1 && pageNum >= totalPage) {
                    TextComponent message = new TextComponent(ChatColor.AQUA + "[上一页]----------------------------");
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/baltop " + (pageNum - 1)));
                    sender.sendMessage(message);
                }
            }
        }.runTaskAsynchronously(Manage.plugin);
        return true;
    }

}
