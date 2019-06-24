package cn.handy.utils;

import cn.handy.Manage;
import cn.handy.constants.BaseConfigCache;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author hanshuai
 * @Description: {粒子效果}
 * @date 2019/6/24 12:24
 */
public class ParticleEffectUtil {

    private static float radius = .75f;

    /**
     * 粒子效果
     *
     * @param player
     * @param color
     */
    public static void particleEffect(final Player player, final Color color) {
        if (BaseConfigCache.isPvpParticle) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!player.isOnline()) {
                        this.cancel();
                    } else if (!player.isDead()) {
                        double angle = 0;
                        Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1);
                        Location location = player.getLocation();

                        for (int i = 0; i < 25; i++) {
                            double x = (radius * Math.sin(angle));
                            double z = (radius * Math.cos(angle));
                            angle += 0.251;
                            player.getWorld().spawnParticle(Particle.REDSTONE, location.getX() + x, location.getY(), location.getZ() + z, 0, 0, 1, 0, dustOptions);
                        }
                    }
                }
            }.runTaskTimer(Manage.plugin, 0L, 2L);
        }
    }

}
