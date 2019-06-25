package cn.handy.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/6/17 15:30
 */
@Data
public class User {
    private Integer id;
    private String userName;
    private String realName;
    private String passWord;
    private String loginIp;
    private Date loginDate;
    private String regIp;
    private Date regDate;
}
