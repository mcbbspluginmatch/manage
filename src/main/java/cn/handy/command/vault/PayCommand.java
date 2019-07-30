package cn.handy.command.vault;

import cn.handy.utils.BaseUtil;
import cn.handy.utils.VaultUtil;
import lombok.val;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/7/30 17:32
 */
public class PayCommand extends Command {

    public PayCommand() {
        // 命令
        super("pay");
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        val rst = BaseUtil.isPlayer(sender);
        if (rst) {
            if (args != null && args.length == 2) {
                val senderPlayer = (Player) sender;
                val player = Bukkit.getServer().getPlayer(args[0]);
                if (player != null) {
                    if (BaseUtil.isMoney(args[1])) {
                        // 判断有钱没
                        val has = VaultUtil.econ.has(senderPlayer, Double.valueOf(args[1]));
                        if (has) {
                            // 转移金额-先扣钱
                            EconomyResponse r = VaultUtil.econ.withdrawPlayer(senderPlayer, Double.valueOf(args[1]));
                            if (r.transactionSuccess()) {
                                player.sendMessage(String.format("你失去了 %s 元. 现在你有 %s", VaultUtil.econ.format(r.amount), VaultUtil.econ.format(r.balance)));
                            } else {
                                sender.sendMessage(String.format("发生了一个错误: %s", r.errorMessage));
                            }
                            // 转移金额-在加钱
                            EconomyResponse r2 = VaultUtil.econ.depositPlayer(player, Double.valueOf(args[2]));
                            if (r2.transactionSuccess()) {
                                player.sendMessage(String.format("你得到了 %s 元. 现在你有 %s", VaultUtil.econ.format(r2.amount), VaultUtil.econ.format(r2.balance)));
                            } else {
                                sender.sendMessage(String.format("发生了一个错误: %s", r2.errorMessage));
                            }
                        } else {
                            sender.sendMessage("你没有这么多金钱!");
                        }
                    } else {
                        sender.sendMessage("金额格式错误!");
                    }
                } else {
                    sender.sendMessage("该玩家不在线!");
                }
            } else {
                sender.sendMessage("子参数错误! 正确格式:/pay [玩家名] [金额]");
            }

        } else {
            sender.sendMessage(ChatColor.RED + "控制台不能使用该命令");
        }
        return true;
    }
}
