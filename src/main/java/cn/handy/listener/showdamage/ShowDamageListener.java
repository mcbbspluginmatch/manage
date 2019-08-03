package cn.handy.listener.showdamage;
//我认为不需要单独一个包 —— yys
import cn.handy.constants.MonsterEnum;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.msg.SendMessage;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * @author hanshuai
 * @Description: {伤害显示}
 * @date 2019/7/11 14:44
 */
public class ShowDamageListener implements Listener {

    /**
     * 对实体造成伤害
     *
     * @param event
     */
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        // 被伤害者
        Entity entity = event.getEntity();
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
        if (damagePlayer != null && entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            int finalDamage = (int) (livingEntity.getHealth() - event.getFinalDamage());
            if (finalDamage < 0) {
                finalDamage = 0;
            }
            String name = livingEntity.getName();
            if (livingEntity.getCustomName() != null) {
                name = livingEntity.getCustomName();
            }
            double maxHealth = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            SendMessage.sendActionBar(damagePlayer,
                    ChatColor.GREEN + MonsterEnum.getChName(name) + ":" + ChatColor.WHITE + finalDamage + " / " + maxHealth);
        }
    }
}
