package cn.handy.listener.user;

import cn.handy.constants.BaseConfigCache;
import cn.handy.constants.BaseConstants;
import cn.handy.entity.Home;
import cn.handy.entity.User;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.Beans;
import cn.handy.utils.config.ConfigUtil;
import cn.handy.utils.secret.SecretUtil;
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
public class UserListener implements Listener {

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
        // 发送登录提醒
        Player player = event.getPlayer();
        val userService = Beans.getBeans().getUserService();
        // 判断该用户是否存在
        String userName = player.getName().toLowerCase();
        val rst = userService.findByUserName(userName);
        if (rst) {
            // 判断是否免密码使用ip登陆
            val ipLogin = ConfigUtil.langConfig.getBoolean("ipLogin");
            val user = userService.findByUserNameAndLoginIp(userName, player.getAddress().getAddress().getHostAddress());
            if (ipLogin && user.getId() != null) {
                player.sendMessage("§aip跟上次登录ip相同,免密码登录成功!");
                BaseConstants.userSet.add(user);
            } else {
                // 如果开启了spawn,登录的时候回到spawn,登录成功后回到当前位置
                if (BaseConfigCache.isSpawn) {
                    BaseConstants.userLoginLocationStatus.put(player.getName(), true);
                    // 保存当前位置
                    Home home = new Home();
                    home.setX(player.getLocation().getX());
                    home.setY(player.getLocation().getY());
                    home.setZ(player.getLocation().getZ());
                    home.setYaw(player.getLocation().getYaw());
                    home.setPitch(player.getLocation().getPitch());
                    home.setWorld(player.getLocation().getWorld().getName());
                    val location = BaseUtil.getLocation(home);
                    BaseConstants.userLoginLocationMap.put(player.getName(), location);
                    // 传送到spawn
                    String world = ConfigUtil.langConfig.getString("spawn.world");
                    Double x = ConfigUtil.langConfig.getDouble("spawn.x");
                    Double y = ConfigUtil.langConfig.getDouble("spawn.y");
                    Double z = ConfigUtil.langConfig.getDouble("spawn.z");
                    player.teleport(BaseUtil.getLocation(world, x, y, z));
                }
                player.sendMessage("§a请输入§e/l 密码 §a来登录游戏");
            }
        } else {
            if (BaseConfigCache.isSecret) {
                // 第一次进入游戏赠送一本武林风云说明
                player.getInventory().addItem(SecretUtil.getSecretHelp());
            }
            if (BaseConfigCache.isSpawn) {
                BaseConstants.userLoginLocationStatus.put(player.getName(), true);
                String world = ConfigUtil.langConfig.getString("spawn.world");
                Double x = ConfigUtil.langConfig.getDouble("spawn.x");
                Double y = ConfigUtil.langConfig.getDouble("spawn.y");
                Double z = ConfigUtil.langConfig.getDouble("spawn.z");
                player.teleport(BaseUtil.getLocation(world, x, y, z));
            }
            player.sendMessage("§a请输入§e/reg 密码 重复密码 §a来注册游戏");
        }
    }

    /**
     * 退出游戏事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerQuitGame(PlayerQuitEvent event) {
        // 清空用户登录缓存
        for (User user : BaseConstants.userSet) {
            if (user.getUserName().equals(event.getPlayer().getName().toLowerCase())) {
                BaseConstants.userSet.remove(user);
                break;
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
        if (isLogin(event.getPlayer().getName())) {
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

    /**
     * 当玩家聊天时触发这个事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (isLogin(event.getPlayer().getName())) {
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
        if (isLogin(event.getPlayer().getName())) {
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
        if (isLogin(event.getPlayer().getName())) {
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
        if (!(event.getWhoClicked() instanceof Player) || isLogin(event.getWhoClicked().getName())) {
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
        if (isLogin(event.getDamager().getName())) {
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
        // 如果登录传送状态
        if (BaseConstants.userLoginLocationStatus.get(event.getPlayer().getName()) != null) {
            BaseConstants.userLoginLocationStatus.remove(event.getPlayer().getName());
            return;
        }
        // 判断是否登录
        if (isLogin(event.getPlayer().getName())) {
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
        if (isLogin(event.getPlayer().getName())) {
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
        if (isLogin(event.getEntity().getName())) {
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
        if (isLogin(event.getPlayer().getName())) {
            return;
        }
        if ((Math.abs(event.getFrom().getZ()) - Math.abs(event.getTo().getZ())) == 0
                && (Math.abs(event.getFrom().getX()) - Math.abs(event.getTo().getX())) == 0) {
            return;
        }
        event.setCancelled(true);
    }


    /**
     * 判断是否登录
     *
     * @param userName
     * @return true是
     */
    private Boolean isLogin(String userName) {
        for (User user : BaseConstants.userSet) {
            if (user.getUserName().equals(userName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
