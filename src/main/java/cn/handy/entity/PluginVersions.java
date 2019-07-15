package cn.handy.entity;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/7/15 10:21
 */
public class PluginVersions {
    private Long id;

    /**
     * 插件名
     */
    private String plugin;

    /**
     * 管理密码
     */
    private String password;

    /**
     * 插件版本
     */
    private String versions;

    /**
     * 下载地址
     */
    private String downloadUrl;

    /**
     * 更新内容
     */
    private String updateNote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlugin() {
        return plugin;
    }

    public void setPlugin(String plugin) {
        this.plugin = plugin == null ? null : plugin.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions == null ? null : versions.trim();
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl == null ? null : downloadUrl.trim();
    }

    public String getUpdateNote() {
        return updateNote;
    }

    public void setUpdateNote(String updateNote) {
        this.updateNote = updateNote == null ? null : updateNote.trim();
    }

}
