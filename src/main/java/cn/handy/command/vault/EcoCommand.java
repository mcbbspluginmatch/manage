package cn.handy.command.vault;

import cn.handy.utils.BaseUtil;
import cn.handy.utils.VaultUtil;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/7/30 16:58
 */
public class EcoCommand extends Command {

    public EcoCommand() {
        // 命令
        super("eco");
        // 权限
        this.setPermission("handy.eco");
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        if (!sender.hasPermission("handy.eco")) {
            sender.sendMessage(ChatColor.RED + "你没有该命令的权限!");
            return true;
        }
        if (args != null && args.length == 2) {
            if ("give".equals(args[0])) {
                Player player = Bukkit.getServer().getPlayer(args[1]);
                if (player != null) {
                    if (BaseUtil.isMoney(args[2])) {
                        EconomyResponse r = VaultUtil.econ.depositPlayer(player, Double.valueOf(args[2]));
                        if (r.transactionSuccess()) {
                            player.sendMessage(String.format("你得到了 %s 元. 现在你有 %s", VaultUtil.econ.format(r.amount), VaultUtil.econ.format(r.balance)));
                        } else {
                            sender.sendMessage(String.format("发生了一个错误: %s", r.errorMessage));
                        }
                    } else {
                        sender.sendMessage("金额错误!");
                    }
                } else {
                    sender.sendMessage("该玩家不在线!");
                }
            }else if ("take".equals(args[0])){
                Player player = Bukkit.getServer().getPlayer(args[1]);
                if (player != null) {
                    if (BaseUtil.isMoney(args[2])) {
                        EconomyResponse r = VaultUtil.econ.withdrawPlayer(player, Double.valueOf(args[2]));
                        if (r.transactionSuccess()) {
                            player.sendMessage(String.format("你失去了 %s 元. 现在你有 %s", VaultUtil.econ.format(r.amount), VaultUtil.econ.format(r.balance)));
                        } else {
                            sender.sendMessage(String.format("发生了一个错误: %s", r.errorMessage));
                        }
                    } else {
                        sender.sendMessage("金额格式错误!");
                    }
                } else {
                    sender.sendMessage("该玩家不在线!");
                }
            }

        } else {
            sender.sendMessage("子参数错误!正确格式/eco give|take [玩家名] [金额]");
        }
        return true;
    }
}
