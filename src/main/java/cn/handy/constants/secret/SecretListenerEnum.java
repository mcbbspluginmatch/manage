package cn.handy.constants.secret;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hanshuai
 * @Description: {}
 * @date 2019/7/1 15:13
 */
@AllArgsConstructor
@Getter
public enum SecretListenerEnum {
    PLAYER_DEATH(0, "玩家死亡"),
    DROP_ITEM(1, "丢出物品"),
    ITEM_CONSUME(2, "消耗物品"),
    ENTITY_DEATH(3,"消灭怪物"),
    PLAYER_FISH(4,"钓鱼");

    private int id;
    private String name;

}
