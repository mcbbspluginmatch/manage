package cn.handy.listener;

import cn.handy.Manage;
import cn.handy.constants.BaseConfigCache;
import cn.handy.constants.BaseConstants;
import cn.handy.dao.message.impl.MessageServiceImpl;
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
public class ManageListener implements Listener {

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

        // 没有登录的话发送登录提醒
        if (BaseConfigCache.isUser && !BaseUtil.isLogin(userName)) {
            BaseUtil.loginRemind(event.getPlayer());
        }

        // 发送上线提醒
        if (BaseConfigCache.isMessage) {
            val messageService = new MessageServiceImpl();
            val message = messageService.findByUserName(userName);
            String joinMessage = Manage.config.getString("joinMessage");
            if (message.getId() != null) {
                joinMessage = message.getJoinMessage();
            }
            joinMessage = joinMessage.replace("${player}", userName);
            event.setJoinMessage(joinMessage);
        }
    }

    /**
     * 退出游戏事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerQuitGame(PlayerQuitEvent event) {
        String userName = event.getPlayer().getName();

        // 清空用户登录缓存
        if (BaseConfigCache.isUser) {
            BaseUtil.removeUser(userName);
        }

        // 发送下线提醒
        if (BaseConfigCache.isMessage) {
            val messageService = new MessageServiceImpl();
            val message = messageService.findByUserName(userName);
            String quitMessage = Manage.config.getString("quitMessage");
            if (message.getId() != null) {
                quitMessage = message.getQuitMessage();
            }
            quitMessage = quitMessage.replace("${player}", userName);
            event.setQuitMessage(quitMessage);
        }
    }

    /**
     * 登录游戏事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerLogin(AsyncPlayerPreLoginEvent event) {
        //TODO 暂时不处理
    }

    /**
     * 这个事件是,当一个玩家执行一个命令的时候将会被触发(也就是在聊天框里面输入信息以/开头的时候，算作命令，就会触发此事件)。
     *
     * @param event
     */
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (BaseConfigCache.isUser) {
            if (BaseUtil.isLogin(event.getPlayer().getName())) {
                return;
            }
            String input = event.getMessage().toLowerCase();
            for (Pattern regex : BaseConstants.COMMAND_WHITE_LISTS) {
                if (regex.matcher(input).find()) {
                    return;
                }
            }
            event.setCancelled(true);
        }
    }

    /**
     * 当玩家聊天时触发这个事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (BaseConfigCache.isUser) {
            if (BaseUtil.isLogin(event.getPlayer().getName())) {
                return;
            }
            event.setCancelled(true);
        }
    }

    /**
     * 当玩家对一个对象或空气进行交互时触发本事件.
     *
     * @param event
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (BaseConfigCache.isUser) {
            if (BaseUtil.isLogin(event.getPlayer().getName())) {
                return;
            }
            event.setCancelled(true);
        }
    }

    /**
     * 当玩家打开物品栏时触发本事件
     *
     * @param event
     */
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (BaseConfigCache.isUser) {
            if (BaseUtil.isLogin(event.getPlayer().getName())) {
                return;
            }
            event.setCancelled(true);
        }
    }

    /**
     * 当玩家点击物品栏中的格子时触发事件事件.
     *
     * @param event
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (BaseConfigCache.isUser) {
            if (!(event.getWhoClicked() instanceof Player) || BaseUtil.isLogin(event.getWhoClicked().getName())) {
                return;
            }
            event.setCancelled(true);
        }
    }

    /**
     * 当一个实体受到另外一个实体伤害时触发该事件
     *
     * @param event
     */
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (BaseConfigCache.isUser) {
            if (!(event.getDamager() instanceof Player)) {
                return;
            }
            if (BaseUtil.isLogin(event.getDamager().getName())) {
                return;
            }
            event.setCancelled(true);
        }
    }

    /**
     * 玩家传送事件.
     *
     * @param event
     */
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (BaseConfigCache.isUser) {
            if (BaseUtil.isLogin(event.getPlayer().getName())) {
                return;
            }
            event.setCancelled(true);
        }
    }

    /**
     * 玩家丢出物品事件.
     *
     * @param event
     */
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (BaseConfigCache.isUser) {
            if (BaseUtil.isLogin(event.getPlayer().getName())) {
                return;
            }
            event.setCancelled(true);
        }
    }

    /**
     * 当一个实体从地上捡起一个物品时
     *
     * @param event
     */
    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (BaseConfigCache.isUser) {
            if (!BaseUtil.isPlayer(event.getEntity())) {
                return;
            }
            if (BaseUtil.isLogin(event.getEntity().getName())) {
                return;
            }
            event.setCancelled(true);
        }
    }

    /**
     * 玩家移动事件.
     *
     * @param event
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (BaseConfigCache.isUser) {
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
}
