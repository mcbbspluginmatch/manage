package cn.handy.utils;

import cn.handy.entity.PluginVersions;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author hs
 * @Description: {插件版本管理Util}
 * @date 2019/7/15 9:51
 */
public class PluginVersionUtil {
    /**
     * 获取插件信息地址
     */
    private final static String URL_STR = "http://106.12.43.142/plugin/findByPlugin";

    public static void main(String[] args) {
        //TODO  以下的内容写到onEnable里
        // 插件版本号-保持跟目前插件版本相同
        String version = "1.0.0";
        PluginVersions pluginVersion = PluginVersionUtil.getPluginVersion("manage", "123456");
        if (pluginVersion != null) {
            // 判断版本号是否相等
            if (version.equals(pluginVersion.getVersions())) {
                //TODO 发送消息--消息内容自定义
                //this.getLogger().info("您的manage插件版本为最新版");
            } else {
                //TODO 发送消息--消息内容自定义
                //this.getLogger().info("manage插件已有最新版" + pluginVersion.getVersions() + "请前往:" + pluginVersion.getDownloadUrl() + "进行更新");
            }
        }
    }

    /**
     * 获取版本信息
     *
     * @param pluginName
     * @param passWord
     * @return
     */
    public static PluginVersions getPluginVersion(String pluginName, String passWord) {
        PluginVersions pluginVersions = null;
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("plugin=" + pluginName);
            stringBuffer.append("&password=" + passWord);
            String pluginVersion = load(URL_STR, stringBuffer.toString());
            if (pluginVersion != null && !"".equals(pluginVersion)) {
                Gson gson = new Gson();
                pluginVersions = gson.fromJson(pluginVersion, PluginVersions.class);
                return pluginVersions;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pluginVersions;
    }

    /**
     * url访问
     *
     * @param url
     * @param param
     * @throws Exception
     */
    private static String load(String url, String param) throws Exception {
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
        BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        String line, resultStr = "";
        while (null != (line = bReader.readLine())) {
            resultStr += line;
        }
        bReader.close();
        return resultStr;
    }
}


