package cn.handy.entity;

import lombok.Data;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/6/13 13:26
 */
@Data
public class Message {
    /**
     * id
     */
    private Integer id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 加入消息
     */
    private String joinMessage;
    /**
     * 退出消息
     */
    private String quitMessage;
}
