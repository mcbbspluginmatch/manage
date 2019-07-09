package cn.handy.entity;

import lombok.Data;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/7/5 11:21
 */
@Data
public class UserSecret {
    /**
     * id
     */
    private Integer id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 门派id
     */
    private Integer sectsId;
    /**
     * 门派name
     */
    private String sectsName;
    /**
     * 功法id
     */
    private Integer secretId;
    /**
     * 功法等级
     */
    private Integer secretGarde;
    /**
     * 功法名称
     */
    private String secretName;
    /**
     * buffId
     */
    private Integer buffId;
    /**
     * buff名称
     */
    private String buffName;
}