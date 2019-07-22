package cn.handy.entity;

import lombok.Data;

/**
 * @author hanshuai
 * @Description: {spawn坐标}
 * @date 2019/7/22 12:39
 */
@Data
public class Spawn {
    private String world;
    private Double x;
    private Double y;
    private Double z;
    private String permission;
}
