package cn.handy.entity;

import lombok.Data;
import org.bukkit.Material;

import java.util.List;

/**
 * @author hanshuai
 * @Description: {生成秘籍}
 * @date 2019/7/1 10:58
 */
@Data
public class IdentifyItem {

    /**
     * 物品ID
     */
    private Integer id;

    /**
     * 物品类型
     */
    private Material material;

    /**
     * 物品名称
     */
    private String name;

    /**
     * lore
     */
    private List<String> loreList;

    /**
     * 作者
     */
    private String author;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容(每页256字符,最多50页)
     */
    private List<String> pageList;

    /**
     * 概率
     */
    private Double probability;

    /**
     * 门派id
     */
    private Integer sectsId;

    /**
     * buff
     */
    private Integer buffId;
}
