package cn.handy.utils.msg;

import cn.handy.utils.BaseUtil;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author hanshuai
 * @Description: {消息发送}
 * @date 2019/7/1 14:13
 */
public class SendMessage {

    /**
     * 普通消息
     *
     * @param player 玩家
     * @param msg    消息
     */
    public static void sendMessage(Player player, String msg) {
        player.sendMessage(BaseUtil.replaceChatColor(msg));
    }

    /**
     * 物品栏上层消息
     *
     * @param player 玩家
     * @param msg    消息
     * @return
     */
    public static boolean sendActionBar(Player player, String msg) {
        if (msg == null) {
            throw new NullPointerException("Msg cannot be null.");
        }
        msg = BaseUtil.replaceChatColor(msg);
        Class<?> chatPacket = getNMSClass("PacketPlayOutChat");
        Class<?> iChatBaseClass = getNMSClass("ChatComponentText");
        try {
            Method iChatBaseClass_a = iChatBaseClass.getDeclaredClasses()[0].getMethod("a", String.class);
            iChatBaseClass_a.setAccessible(true);
            Object iChatBase = iChatBaseClass_a.invoke(null, new Object[]{"{\"text\":\"" + msg + "\"}"});
            Constructor<?> packetConstructor = chatPacket.getDeclaredConstructor(iChatBaseClass, byte.class);
            packetConstructor.setAccessible(true);
            Object packet = packetConstructor.newInstance(new Object[]{iChatBase, (byte) 2});
            sendPacket(player, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * title消息
     *
     * @param player   玩家
     * @param fadeIn   渐入
     * @param stay     停留时间
     * @param fadeOut  渐出
     * @param title    主标题
     * @param subtitle 副标题
     */
    @SuppressWarnings("rawtypes")
    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        try {
            // 要发送的title
            if (title != null) {
                title = BaseUtil.replaceChatColor(title);
                title = BaseUtil.replaceName(title, player.getDisplayName());
                Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
                Object chatTitle = getNMSClass("ChatComponentText").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke(null, new Object[]{"{\"text\":\"" + title + "\"}"});
                Constructor titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[]{getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE});
                Object titlePacket = titleConstructor.newInstance(new Object[]{enumTitle, chatTitle, fadeIn, stay, fadeOut});
                sendPacket(player, titlePacket);
            }
            // 要发送的子title
            if (subtitle != null) {
                subtitle = BaseUtil.replaceChatColor(subtitle);
                subtitle = BaseUtil.replaceName(subtitle, player.getDisplayName());
                Object enumSubtitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
                Object chatSubtitle = getNMSClass("ChatComponentText").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke(null, new Object[]{"{\"text\":\"" + subtitle + "\"}"});
                Constructor subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[]{getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE});
                Object subtitlePacket = subtitleConstructor.newInstance(new Object[]{enumSubtitle, chatSubtitle, fadeIn, stay, fadeOut});
                sendPacket(player, subtitlePacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Class<?> getNMSClass(String name) {
        String version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", new Class[]{getNMSClass("Packet")}).invoke(playerConnection, new Object[]{packet});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
