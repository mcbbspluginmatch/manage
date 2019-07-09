package cn.handy.entity;

import lombok.Data;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/7/9 10:09
 */
@Data
public class Home {
    private Integer id;
    private String userName;
    private String homeName;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private String world;
}
