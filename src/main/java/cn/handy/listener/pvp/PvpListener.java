package cn.handy.listener.pvp;

import cn.handy.constants.BaseConfigCache;
import cn.handy.constants.BaseConstants;
import cn.handy.dao.pvp.impl.PvpServiceImpl;
import cn.handy.entity.Pvp;
import cn.handy.utils.ParticleEffectUtil;
import lombok.val;
import org.bukkit.Color;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
        Boolean rst = false;
        if (pvp.getId() != null) {
            rst = pvp.getPvp();
        }
        BaseConstants.PvpMap.put(pvp.getUserName(), rst);
        // 粒子效果
        if (BaseConfigCache.isPvpParticle) {
            if (rst) {
                ParticleEffectUtil.particleEffect(event.getPlayer(), Color.GREEN);
            } else {
                ParticleEffectUtil.particleEffect(event.getPlayer(), Color.RED);
            }
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
}
