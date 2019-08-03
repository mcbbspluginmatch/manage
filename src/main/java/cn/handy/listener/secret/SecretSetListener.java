package cn.handy.listener.secret;

import cn.handy.constants.BaseConstants;
import cn.handy.constants.secret.SecretEqualsInfoEnum;
import cn.handy.constants.secret.SecretListenerEnum;
import cn.handy.utils.secret.SecretUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanshuai
 * @Description: {武林秘籍生成拦截器}
 * @date 2019/7/1 12:24
 */
public class SecretSetListener implements Listener {

    /**
     * 玩家死亡事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        List<ItemStack> drops = event.getDrops();
        Player player = event.getEntity().getPlayer();
        // 获取随机秘籍
        List<ItemStack> ranItemStackList = new ArrayList<>();
        if (drops != null && drops.size() > 0) {
            for (ItemStack itemStack : drops) {
                // 判断是否有无字天书
                if (SecretUtil.equalsInSet(itemStack, SecretUtil.getItemStack(0), SecretEqualsInfoEnum.ABOUT_NAME)) {
                    if (SecretListenerEnum.PLAYER_DEATH.getId() == SecretUtil.getEvenId(itemStack)) {
                        ranItemStackList.add(SecretUtil.ranItemStack());
                    }
                }
            }
        }
        // 把随机秘籍保存
        BaseConstants.dropMap.put(player.getName(), ranItemStackList);
    }

    /**
     * 玩家重生事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        List<ItemStack> itemStacks = BaseConstants.dropMap.get(player.getName());
        if (itemStacks != null && itemStacks.size() > 0) {
            for (ItemStack itemStack : itemStacks) {
                PlayerInventory inventory = player.getInventory();
                // 添加秘籍到背包
                inventory.addItem(itemStack);
                player.sendMessage(ChatColor.AQUA + "生与死,轮回不止,在生死之间,你领悟到无字天书的真意");
            }
        }
    }

    /**
     * 当漏斗/漏斗矿车收起掉落的物品时触发本事件.
     *
     * @param event
     */
    @EventHandler
    public void onInventoryPickupItemEvent(InventoryPickupItemEvent event) {
        ItemStack itemStack = event.getItem().getItemStack();
        // 判断是否为无字天书
        if (SecretUtil.equalsInSet(itemStack, SecretUtil.getItemStack(0), SecretEqualsInfoEnum.ABOUT_NAME)) {
            // 判断事件是否相等
            if (SecretListenerEnum.PICKUP_ITEM.getId() == SecretUtil.getEvenId(itemStack)) {
                // 替换为随机秘籍
                event.getItem().setItemStack(SecretUtil.ranItemStack());
            }
        }
    }

    /**
     * 当玩家消耗完物品时, 此事件将触发 例如:(食物, 药水, 牛奶桶).
     *
     * @param event
     */
    @EventHandler
    public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
        // 获取被消耗的物品
        ItemStack itemStack = event.getItem();
        // 判断是否在作死吃河豚
        if (itemStack != null && itemStack.getType() == Material.PUFFERFISH) {
            Player player = event.getPlayer();
            PlayerInventory inventory = player.getInventory();
            for (ItemStack stack : inventory) {
                // 判断是否有无字天书
                if (SecretUtil.equalsInSet(stack, SecretUtil.getItemStack(0), SecretEqualsInfoEnum.ABOUT_NAME)) {
                    // 判断事件是否相等
                    if (SecretListenerEnum.ITEM_CONSUME.getId() == SecretUtil.getEvenId(stack)) {
                        // 新增随机秘籍
                        stack.setAmount(0);
                        inventory.addItem(SecretUtil.ranItemStack());
                        player.sendMessage(ChatColor.AQUA + "吃的苦中苦,方为人上人,你领悟到无字天书的真意");
                    }
                }
            }
        }
    }

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
            Boolean rst = false;
            switch (entityType) {
                // 猪人
                    //合并下？—— yys
                case PIG_ZOMBIE:
                    rst = true;
                    break;
                // 末影龙
                case ENDER_DRAGON:
                    rst = true;
                    break;
                // 凋零
                case WITHER:
                    rst = true;
                    break;
                default:
                    break;
            }
            if (rst) {
                PlayerInventory inventory = player.getInventory();
                for (ItemStack stack : inventory) {
                    // 判断是否有无字天书
                    if (SecretUtil.equalsInSet(stack, SecretUtil.getItemStack(0), SecretEqualsInfoEnum.ABOUT_NAME)) {
                        // 判断事件是否相等
                        if (SecretListenerEnum.ENTITY_DEATH.getId() == SecretUtil.getEvenId(stack)) {
                            // 新增随机秘籍
                            stack.setAmount(0);
                            inventory.addItem(SecretUtil.ranItemStack());
                            player.sendMessage(ChatColor.AQUA + "真正的勇者,当斩妖除魔!,你领悟到无字天书的真意");
                        }
                    }
                }
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
        Boolean rst = false;
        switch (entityType) {
            // 河豚
            case PUFFERFISH:
                rst = true;
                break;
            default:
                break;
        }
        if (rst) {
            PlayerInventory inventory = player.getInventory();
            for (ItemStack stack : inventory) {
                // 判断是否有无字天书
                if (SecretUtil.equalsInSet(stack, SecretUtil.getItemStack(0), SecretEqualsInfoEnum.ABOUT_NAME)) {
                    // 判断事件是否相等
                    if (SecretListenerEnum.PLAYER_FISH.getId() == SecretUtil.getEvenId(stack)) {
                        // 新增随机秘籍
                        stack.setAmount(0);
                        inventory.addItem(SecretUtil.ranItemStack());
                        player.sendMessage(ChatColor.AQUA + "姜太公钓鱼 —— 愿者上钩,你领悟到无字天书的真意");
                    }
                }
            }
        }
    }

}
