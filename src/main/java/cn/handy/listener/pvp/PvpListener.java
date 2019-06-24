package cn.handy.listener.pvp;

import cn.handy.constants.BaseConstants;
import cn.handy.dao.pvp.impl.PvpServiceImpl;
import cn.handy.entity.Pvp;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.ParticleEffectUtil;
import lombok.val;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Iterator;

/**
 * @author hanshuai
 * @Description: {监听器}
 * @date 2019/5/17 17:16
 */
public class PvpListener implements Listener {

    /**
     * 进去游戏事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerJoinGame(PlayerJoinEvent event) {
        String userName = event.getPlayer().getName().toLowerCase();
        val pvpService = new PvpServiceImpl();
        Pvp pvp = pvpService.findByUserName(userName);
        Boolean rst = true;
        if (pvp.getId() != null) {
            rst = pvp.getPvpStatus();
        }
        BaseConstants.PvpMap.put(userName, rst);
        // 粒子效果
        if (rst) {
            ParticleEffectUtil.particleEffect(event.getPlayer(), Color.RED);
        } else {
            ParticleEffectUtil.particleEffect(event.getPlayer(), Color.WHITE);
        }
    }

    /**
     * 退出游戏事件
     *
     * @param event
     */
    @EventHandler
    public void onPlayerQuitGame(PlayerQuitEvent event) {
        String userName = event.getPlayer().getName().toLowerCase();
        BaseConstants.PvpMap.remove(userName);
    }

    /**
     * 对实体造成伤害
     *
     * @param event
     */
    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!BaseUtil.isPlayer(event.getEntity())) {
            return;
        }
        // 被伤害的玩家
        Player entityPlayer = (Player) event.getEntity();
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

        if (damagePlayer != null) {
            val damageRst = BaseConstants.PvpMap.get(damagePlayer.getName().toLowerCase());
            val entityRst = BaseConstants.PvpMap.get(entityPlayer.getName().toLowerCase());
            if (entityPlayer == damagePlayer) {
                return;
            }
            if (!damageRst) {
                event.setCancelled(true);
                damagePlayer.sendMessage("您的pvp已关闭,无法进行伤害...");
            } else if (!entityRst) {
                event.setCancelled(true);
                damagePlayer.sendMessage("对方的pvp已关闭,无法进行伤害...");
            }
        }
    }

    /**
     * 燃烧事件
     *
     * @param event
     */
    @EventHandler(ignoreCancelled = true)
    public void onEntityComBustByEntity(EntityCombustByEntityEvent event) {
        if (!BaseUtil.isPlayer(event.getEntity())) {
            return;
        }
        if (event.getCombuster() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getCombuster();
            if (arrow.getShooter() instanceof Player) {
                Player damagePlayer = (Player) arrow.getShooter();
                Boolean damageRst = BaseConstants.PvpMap.get(damagePlayer.getName().toLowerCase());
                Player entityPlayer = (Player) event.getEntity();
                Boolean entityRst = BaseConstants.PvpMap.get(entityPlayer.getName().toLowerCase());
                if (!damageRst) {
                    event.setCancelled(true);
                    damagePlayer.sendMessage("您的pvp已关闭,无法进行伤害...");
                } else if (!entityRst) {
                    event.setCancelled(true);
                    damagePlayer.sendMessage("对方的pvp已关闭,无法进行伤害...");
                }
            }
        }
    }


    /**
     * 溅射药水
     *
     * @param event
     */
    @EventHandler(ignoreCancelled = true)
    public void onPotionSplash(PotionSplashEvent event) {
        if (!BaseUtil.isPlayer(event.getEntity())) {
            return;
        }
        if (event.getPotion().getShooter() instanceof Player) {
            for (LivingEntity entity : event.getAffectedEntities()) {
                if (entity instanceof Player) {
                    Player damagePlayer = (Player) event.getPotion().getShooter();
                    Boolean damageRst = BaseConstants.PvpMap.get(damagePlayer.getName().toLowerCase());
                    Player entityPlayer = (Player) event.getEntity();
                    Boolean entityRst = BaseConstants.PvpMap.get(entityPlayer.getName().toLowerCase());
                    if (!damageRst) {
                        event.setCancelled(true);
                        damagePlayer.sendMessage("您的pvp已关闭,无法进行伤害...");
                    } else if (!entityRst) {
                        event.setCancelled(true);
                        damagePlayer.sendMessage("对方的pvp已关闭,无法进行伤害...");
                    }
                }
            }
        }
    }


    /**
     * 滞留药水
     *
     * @param event
     */
    @EventHandler(ignoreCancelled = true)
    public void onCloudEffects(AreaEffectCloudApplyEvent event) {
        if (event.getEntity().getSource() instanceof Player) {
            Iterator<LivingEntity> it = event.getAffectedEntities().iterator();
            while (it.hasNext()) {
                LivingEntity entity = it.next();
                if (entity != null && entity instanceof Player) {
                    Player damagePlayer = (Player) event.getEntity().getSource();
                    Boolean damageRst = BaseConstants.PvpMap.get(damagePlayer.getName().toLowerCase());
                    Player entityPlayer = (Player) event.getEntity();
                    Boolean entityRst = BaseConstants.PvpMap.get(entityPlayer.getName().toLowerCase());
                    if (!damageRst) {
                        event.setCancelled(true);
                        damagePlayer.sendMessage("您的pvp已关闭,无法进行伤害...");
                    } else if (!entityRst) {
                        event.setCancelled(true);
                        damagePlayer.sendMessage("对方的pvp已关闭,无法进行伤害...");
                    }
                }
            }
        }
    }

    /**
     * 钓鱼
     *
     * @param event
     */
    @EventHandler(ignoreCancelled = true)
    public void onPlayerFishing(PlayerFishEvent event) {
        if (event.getCaught() instanceof Player) {
            Player damagePlayer = event.getPlayer();
            Boolean damageRst = BaseConstants.PvpMap.get(damagePlayer.getName().toLowerCase());
            Player entityPlayer = (Player) event.getCaught();
            Boolean entityRst = BaseConstants.PvpMap.get(entityPlayer.getName().toLowerCase());
            if (damagePlayer.getInventory().getItemInMainHand().getType() == Material.FISHING_ROD
                    || damagePlayer.getInventory().getItemInOffHand().getType() == Material.FISHING_ROD) {
                if (!damageRst) {
                    event.setCancelled(true);
                    damagePlayer.sendMessage("您的pvp已关闭,无法进行伤害...");
                } else if (!entityRst) {
                    event.setCancelled(true);
                    damagePlayer.sendMessage("对方的pvp已关闭,无法进行伤害...");
                }
            }
        }
    }

}
