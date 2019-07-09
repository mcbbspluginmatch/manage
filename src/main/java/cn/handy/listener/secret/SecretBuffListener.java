package cn.handy.listener.secret;

import cn.handy.constants.secret.SecretTypeEnum;
import cn.handy.entity.UserSecret;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.Beans;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

/**
 * @author hanshuai
 * @Description: {武林秘籍Buff拦截器}
 * @date 2019/7/4 12:11
 */
public class SecretBuffListener implements Listener {

    /**
     * 对实体造成伤害
     *
     * @param event
     */
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getEntity() instanceof LivingEntity) {
            // 伤害者
            Player damagePlayer = null;
            // 如果造成伤害的是玩家
            if (BaseUtil.isPlayer(event.getDamager())) {
                damagePlayer = (Player) event.getDamager();
            }
            // 如果造成伤害的是抛射物
            if (event.getDamager() instanceof Projectile) {
                Projectile arrow = (Projectile) event.getDamager();
                if (arrow.getShooter() instanceof Player) {
                    damagePlayer = (Player) arrow.getShooter();
                }
            }
            // 如果造成伤害的是药水
            if (event.getDamager() instanceof ThrownPotion) {
                ThrownPotion potion = (ThrownPotion) event.getDamager();
                if (potion.getShooter() instanceof Player) {
                    damagePlayer = (Player) potion.getShooter();
                }
            }
            LivingEntity livingEntity = (LivingEntity) event.getEntity();

            // 攻击者buff加成
            if (damagePlayer != null) {
                PlayerInventory inv = damagePlayer.getInventory();
                ItemStack itemStack = inv.getItemInMainHand();
                if (Material.DIAMOND_SWORD.equals(itemStack.getType())) {
                    // 钻石系列
                    List<UserSecret> damageSecret = Beans.getBeans().getUserSecretService().findByUserName(damagePlayer.getName());
                    for (UserSecret userSecret : damageSecret) {
                        // 钻石剑提升
                        if (SecretTypeEnum.DIAMOND_SWORD_DAMAGE.getId() == userSecret.getBuffId()) {
                            double damage = SecretTypeEnum.DIAMOND_SWORD_DAMAGE.getResult() * userSecret.getSecretGarde();
                            event.setDamage(event.getDamage() + damage);
                        }
                        // 钻石剑破甲提升
                        if (SecretTypeEnum.DIAMOND_SWORD_IGNORE_ARMOR.getId() == userSecret.getBuffId()) {
                            double damage = SecretTypeEnum.DIAMOND_SWORD_IGNORE_ARMOR.getResult() * userSecret.getSecretGarde();
                            if (damage > 0) {
                                double health = livingEntity.getHealth();
                                double last = health - damage;
                                if (last <= 0) {
                                    livingEntity.setHealth(0);
                                } else {
                                    livingEntity.setHealth(last);
                                }
                            }
                        }
                    }
                }
                // 弓系列
                if (Material.BOW.equals(itemStack.getType())) {
                    List<UserSecret> damageSecret = Beans.getBeans().getUserSecretService().findByUserName(damagePlayer.getName());
                    for (UserSecret userSecret : damageSecret) {
                        // 弓提升
                        if (SecretTypeEnum.BOW_DAMAGE.getId() == userSecret.getBuffId()) {
                            double damage = SecretTypeEnum.BOW_DAMAGE.getResult() * userSecret.getSecretGarde();
                            event.setDamage(event.getDamage() + damage);
                        }
                    }
                }
                // 钻石斧系列
                if (Material.DIAMOND_AXE.equals(itemStack.getType())) {
                    List<UserSecret> damageSecret = Beans.getBeans().getUserSecretService().findByUserName(damagePlayer.getName());
                    for (UserSecret userSecret : damageSecret) {
                        // 钻石斧提升
                        if (SecretTypeEnum.DIAMOND_AXE_DAMAGE.getId() == userSecret.getBuffId()) {
                            double damage = SecretTypeEnum.DIAMOND_AXE_DAMAGE.getResult() * userSecret.getSecretGarde();
                            event.setDamage(event.getDamage() + damage);
                        }
                    }
                }
            }
        }
        // 被攻击者是玩家添加防御类buff
        if (BaseUtil.isPlayer(event.getEntity())) {
            Player entityPlayer = (Player) event.getEntity();
            // 被攻击者buff加成
            List<UserSecret> entitySecret = Beans.getBeans().getUserSecretService().findByUserName(entityPlayer.getName());
            for (UserSecret userSecret : entitySecret) {
                if (SecretTypeEnum.ARMOR.getId() == userSecret.getBuffId()) {
                    double armor = SecretTypeEnum.ARMOR.getResult() * userSecret.getSecretGarde();
                    event.setDamage((event.getDamage() - armor) < 0 ? 0 : (event.getDamage() - armor));
                }
            }
        }
    }

}