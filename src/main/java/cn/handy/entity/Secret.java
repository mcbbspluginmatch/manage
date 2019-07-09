package cn.handy.entity;

import lombok.Data;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/7/5 11:20
 */
@Data
public class Secret {
    private Integer id;
    private Integer sectsId;
    private String sectsName;
    private String name;
    private String lore;
    private Integer buffId;
    private String buffName;
}
