package cn.handy.command.vault;

import cn.handy.utils.BaseUtil;
import cn.handy.utils.VaultUtil;
import lombok.val;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/7/30 17:36
 */
public class MoneyCommand extends Command {

    public MoneyCommand() {
        // 命令
        super("money");
    }

    @Override
    public boolean execute(CommandSender sender, String label, final String[] args) {
        val rst = BaseUtil.isPlayer(sender);
        if (rst) {
            Player player = (Player) sender;
            double balance = VaultUtil.econ.getBalance(player);
            player.sendMessage("金额: " + balance);
        } else {
            sender.sendMessage(ChatColor.RED + "控制台不能使用该命令");
        }
        return true;
    }
}
