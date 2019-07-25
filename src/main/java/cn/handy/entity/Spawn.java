package cn.handy.entity;

import lombok.Data;

/**
 * @author hanshuai
 * @Description: {spawn坐标}
 * @date 2019/7/22 12:39
 */
@Data
public class Spawn {
    private Integer id;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private String world;
}
