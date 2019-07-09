package cn.handy.listener.secret;

import cn.handy.utils.config.ConfigUtil;
import cn.handy.utils.secret.SecretUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.Random;

/**
 * @author hanshuai
 * @Description: {武林秘籍获取拦截器}
 * @date 2019/7/4 12:11
 */
public class SecretGetListener implements Listener {

    /**
     * 当任何一个实体死亡时触发本事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerFishEvent(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        // 判断杀手是不是玩家
        if (player != null) {
            // 死亡的物品类型
            EntityType entityType = event.getEntityType();
            int ranDom = -1;
            switch (entityType) {
                // 尸壳
                case HUSK:
                    ranDom = new Random().nextInt(ConfigUtil.secretConfig.getInt("HUSK"));
                    break;
                // 猪人
                case PIG_ZOMBIE:
                    ranDom = new Random().nextInt(ConfigUtil.secretConfig.getInt("PIG_ZOMBIE"));
                    break;
                // 烈焰人
                case BLAZE:
                    ranDom = new Random().nextInt(ConfigUtil.secretConfig.getInt("BLAZE"));
                    break;
                // 恶魂
                case GHAST:
                    ranDom = new Random().nextInt(ConfigUtil.secretConfig.getInt("GHAST"));
                    break;
                // 凋零骷髅
                case WITHER_SKELETON:
                    ranDom = new Random().nextInt(ConfigUtil.secretConfig.getInt("WITHER_SKELETON"));
                    break;
                // 蝙蝠
                case BAT:
                    ranDom = new Random().nextInt(ConfigUtil.secretConfig.getInt("BAT"));
                    break;
                // 远古守卫者
                case ELDER_GUARDIAN:
                    ranDom = new Random().nextInt(ConfigUtil.secretConfig.getInt("ELDER_GUARDIAN"));
                    break;
                // 末影龙
                case ENDER_DRAGON:
                    ranDom = new Random().nextInt(ConfigUtil.secretConfig.getInt("ENDER_DRAGON"));
                    break;
                // 凋零
                case WITHER:
                    ranDom = new Random().nextInt(ConfigUtil.secretConfig.getInt("WITHER"));
                    break;
                default:
                    break;
            }
            // 如果抽中随机数字就掉落物多一个无字天书
            if (ranDom == 0) {
                event.getDrops().add(SecretUtil.getItemStack(SecretUtil.getRandom()));
                player.sendMessage(ChatColor.AQUA + "意外之喜,您发现一本无字天书");
            }
            // 如果抽中随机数字就掉落物多一个知识之书
            if (ranDom == 1) {
                event.getDrops().add(SecretUtil.getKnowledgeBook());
                player.sendMessage(ChatColor.AQUA + "意外之喜,您发现一本知识之书");
            }
        }
    }

    /**
     * 当玩家钓鱼时触发本事件.
     *
     * @param event
     */
    @EventHandler
    public void onPlayerFishEvent(PlayerFishEvent event) {
        Player player = event.getPlayer();
        Entity caught = event.getCaught();
        if (caught == null) {
            return;
        }
        EntityType entityType = caught.getType();
        int ranDom = -1;
        switch (entityType) {
            // 鳕鱼
            case COD:
                ranDom = new Random().nextInt(ConfigUtil.secretConfig.getInt("COD"));
                break;
            // 鲑鱼
            case SALMON:
                ranDom = new Random().nextInt(ConfigUtil.secretConfig.getInt("SALMON"));
                break;
            // 河豚
            case PUFFERFISH:
                ranDom = new Random().nextInt(ConfigUtil.secretConfig.getInt("PUFFERFISH"));
                break;
            // 热带鱼
            case TROPICAL_FISH:
                ranDom = new Random().nextInt(ConfigUtil.secretConfig.getInt("TROPICAL_FISH"));
                break;
            default:
                break;
        }
        // 如果抽中随机数字就掉落物多一个无字天书
        if (ranDom == 0) {
            PlayerInventory inventory = player.getInventory();
            // 设置手上的物品
            inventory.addItem(SecretUtil.getItemStack(SecretUtil.getRandom()));
            player.sendMessage(ChatColor.AQUA + "意外之喜,您发现一本无字天书");
        }
        if (ranDom == 1) {
            PlayerInventory inventory = player.getInventory();
            // 设置手上的物品
            inventory.addItem(SecretUtil.getKnowledgeBook());
            player.sendMessage(ChatColor.AQUA + "意外之喜,您发现一本知识之书");
        }
    }
}
