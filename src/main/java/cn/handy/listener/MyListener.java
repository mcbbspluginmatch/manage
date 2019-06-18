package cn.handy.listener;

import cn.handy.Main;
import cn.handy.constants.Constants;
import cn.handy.dao.message.impl.MessageServiceImpl;
import cn.handy.dao.user.impl.UserServiceImpl;
import cn.handy.utils.BaseUtil;
import lombok.val;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;

import java.util.regex.Pattern;

/**
 * @author hanshuai
 * @Description: {监听器}
 * @date 2019/5/17 17:16
 */
public class MyListener implements Listener {

    /**
     * 进去游戏事件
     * EventHandler可以带参数,就像这样@EventHandler(priority = EventPriority.LOW)
     * 这个代表他的优先级.
     * 若有不同监听器监听同一个事件(这些监听器有可能在别的插件中!)那他们的调用顺序由优先级决定
     * 由低到高执行.
     * EventPriority.LOWEST (最先)
     * EventPriority.LOW
     * EventPriority.NORMAL
     * EventPriority.HIGH
     * EventPriority.HIGHEST
     * EventPriority.MONITOR (最后)
     *
     * @param event
     */
    @EventHandler
    public void onPlayerJoinGame(PlayerJoinEvent event) {
        String userName = event.getPlayer().getName();
        String joinMessage = Main.config.getString("joinMessage");
        val isMessage = Main.config.getBoolean("isMessage");
        if (isMessage) {
            val messageService = new MessageServiceImpl();
            val message = messageService.findByUserName(userName);
            if (message.getId() != null) {
                joinMessage = message.getJoinMessage();
            }
        }
        joinMessage = joinMessage.replace("${player}", userName);
        event.setJoinMessage(joinMessage);
    }

    /**
     * 退出游戏事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerQuitGame(PlayerQuitEvent event) {
        String userName = event.getPlayer().getName();
        String quitMessage = Main.config.getString("quitMessage");
        val isMessage = Main.config.getBoolean("isMessage");
        if (isMessage) {
            val messageService = new MessageServiceImpl();
            val message = messageService.findByUserName(userName);
            if (message.getId() != null) {
                quitMessage = message.getQuitMessage();
            }
        }
        quitMessage = quitMessage.replace("${player}", userName);
        event.setQuitMessage(quitMessage);
        BaseUtil.removeUser(userName);
    }

    /**
     * 登录游戏事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
        val isUser = Main.config.getBoolean("isUser");
        if (isUser) {
            val userName = event.getName();
            if (BaseUtil.isLogin(userName)) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "玩家 " + userName + " 已经在线了!");
            }
        }
    }

    /**
     * 这个事件是,当一个玩家执行一个命令的时候将会被触发(也就是在聊天框里面输入信息以/开头的时候，算作命令，就会触发此事件)。
     *
     * @param event
     */
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (BaseUtil.isLogin(event.getPlayer().getName())) {
            return;
        }
        String input = event.getMessage().toLowerCase();
        for (Pattern regex : Constants.COMMAND_WHITE_LISTS) {
            if (regex.matcher(input).find()) {
                return;
            }
        }
        event.setCancelled(true);

    }

    /**
     * 当玩家聊天时触发这个事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (BaseUtil.isLogin(event.getPlayer().getName())) {
            return;
        }
        event.setCancelled(true);
    }

    /**
     * 当玩家对一个对象或空气进行交互时触发本事件.
     *
     * @param event
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (BaseUtil.isLogin(event.getPlayer().getName())) {
            return;
        }
        event.setCancelled(true);
    }

    /**
     * 当玩家打开物品栏时触发本事件
     *
     * @param event
     */
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (BaseUtil.isLogin(event.getPlayer().getName())) {
            return;
        }
        event.setCancelled(true);
    }

    /**
     * 当玩家点击物品栏中的格子时触发事件事件.
     *
     * @param event
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player) || BaseUtil.isLogin(event.getWhoClicked().getName())) {
            return;
        }
        event.setCancelled(true);
    }

    /**
     * 当一个实体受到另外一个实体伤害时触发该事件
     *
     * @param event
     */
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        if (BaseUtil.isLogin(event.getDamager().getName())) {
            return;
        }
        event.setCancelled(true);
    }

    /**
     * 玩家传送事件.
     *
     * @param event
     */
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (BaseUtil.isLogin(event.getPlayer().getName())) {
            return;
        }
        event.setCancelled(true);
    }

    /**
     * 玩家丢出物品事件.
     *
     * @param event
     */
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (BaseUtil.isLogin(event.getPlayer().getName())) {
            return;
        }
        event.setCancelled(true);
    }

    /**
     * 当一个实体从地上捡起一个物品时
     *
     * @param event
     */
    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (!BaseUtil.isPlayer(event.getEntity())) {
            return;
        }
        if (BaseUtil.isLogin(event.getEntity().getName())) {
            return;
        }
        event.setCancelled(true);
    }

    /**
     * 玩家移动事件.
     *
     * @param event
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (BaseUtil.isLogin(event.getPlayer().getName())) {
            return;
        }
        if ((Math.abs(event.getFrom().getZ()) - Math.abs(event.getTo().getZ())) == 0
                && (Math.abs(event.getFrom().getX()) - Math.abs(event.getTo().getX())) == 0) {
            return;
        }
        event.setCancelled(true);
    }
}
