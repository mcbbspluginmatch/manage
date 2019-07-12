package cn.handy.utils;

import cn.handy.Manage;
import cn.handy.constants.BaseConfigCache;
import org.bukkit.Server;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author hanshuai
 * @Description: {统计使用情况}
 * @date 2019/7/12 11:21
 */
public class ReportUtil {
    /**
     * 统计地址
     */
    private final static String URL_STR = "http://106.12.43.142/api/report";

    /**
     * 统计使用情况
     */
    public static void report() {
        // 如果开启了统计的话
        if (BaseConfigCache.isReport) {
            // 创建一个每俩小时执行的心跳包
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        Server server = Manage.plugin.getServer();
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("?port=" + server.getPort());
                        stringBuffer.append("&maxPlayers=" + server.getMaxPlayers());
                        stringBuffer.append("&motd=" + server.getMotd());
                        stringBuffer.append("&onlineMode=" + server.getOnlineMode());
                        stringBuffer.append("&version=" + server.getVersion());
                        stringBuffer.append("&onlinePlayers=" + server.getOnlinePlayers().size());
                        load(URL_STR, stringBuffer.toString());
                    } catch (Exception e) {
                        Manage.plugin.getLogger().info("网络错误,统计信息发送失败,如不需进行统计请在配置文件中关闭");
                        this.cancel();
                    }
                }
            }.runTaskTimerAsynchronously(Manage.plugin, 0, 7200 * 20);
        }
    }

    /**
     * url访问
     *
     * @param url
     * @param param
     * @throws Exception
     */
    private static void load(String url, String param) throws Exception {
        URL restURL = new URL(url);
        // 此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类 的子类HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
        // 请求方式
        conn.setRequestMethod("GET");
        // 设置是否从httpUrlConnection读入，默认情况下是true;
        conn.setDoOutput(true);
        // allowUserInteraction 如果为 true，则在允许用户交互（例如弹出一个验证对话框）的上下文中对此 URL 进行检查。
        conn.setAllowUserInteraction(false);
        PrintStream ps = new PrintStream(conn.getOutputStream());
        ps.print(param);
        ps.close();
    }

}
