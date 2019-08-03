package cn.handy.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hanshuai
 * @Description: {怪物名称转换枚举}
 * @date 2019/7/16 18:03
 */
@Getter
@AllArgsConstructor
public enum MonsterEnum {
    CHICKEN("Chicken", "鸡"),
    Cow("Cow", "牛"),
    Horse("Horse", "马"),
    Ocelot("Chicken", "豺猫"),
    Pig("Pig", "猪"),
    Sheep("Sheep", "羊"),
    rabbit("rabbit", "兔子"),
    Bat("Bat", "蝙蝠"),
    Moosh_room("Mooshroom", "哞菇"),
    Squid("Squid", "鱿鱼"),
    Villager("Villager", "村民"),
    Mooshroom("Mooshroom", "哞菇"),
    Cave_Spider("CaveSpider", "洞穴蜘蛛"),
    Enderman("Enderman", "末影人"),
    Spider("Spider", "蜘蛛"),
    Wolf("Wolf", "狼"),
    Zombie_Pigman("Zombie_Pigman", "僵尸猪人"),
    Blaze("Blaze", "烈焰人"),
    Creeper("Creeper", "爬行者"),
    Ghast("Ghast", "恶魂"),
    Magma_Cube("Magma_Cube", "岩浆怪"),
    Silverfish("Silverfish", "蠹虫"),
    Skeleton("Skeleton", "骷髅射手"),
    Slime("Slime", "史莱姆"),
    Spider_Jockey("Spider_Jockey", "蜘蛛骑士"),
    Witch("Witch", "女巫"),
    Wither_Skeleton("WitherSkeleton", "凋零骷髅"),
    Zombie("Zombie", "僵尸"),
    Endermite("Endermite", "末影螨"),
    Zombie_Villager("Zombie_Villager", "僵尸村民"),
    Guardian("Guardian", "守卫者"),
    Elder_Guardian("Elder_Guardian", "远古守卫者"),
    Chicken_Jockey("Chicken_Jockey", "鸡骑士"),
    Snow_Golem("Snow_Golem", "雪傀儡"),
    Iron_Golem("Iron_Golem", "铁傀儡"),
    Ender_Dragon("Ender_Dragon", "末影龙"),
    Wither("Wither", "凋零"),
    Giant("Giant ", "巨人"),
    Undead_Horse("Undead_Horse", "僵尸马"),
    Skeleton_Horse("Skeleton_Horse", "骷髅马");
    //不支持自定义？ —— yys
    public String monsterEnName;
    public String monsterChName;

    public static String getChName(String enName) {
        for (MonsterEnum monsterEnum : MonsterEnum.values()) {
            if (monsterEnum.getMonsterEnName().equals(enName)) {
                return monsterEnum.getMonsterChName();
            }
        }
        return enName;
    }
}
