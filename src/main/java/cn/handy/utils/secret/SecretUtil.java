package cn.handy.utils.secret;

import cn.handy.Manage;
import cn.handy.constants.BaseConfigCache;
import cn.handy.constants.BaseConstants;
import cn.handy.constants.secret.SecretEqualsInfoEnum;
import cn.handy.constants.secret.SecretListenerEnum;
import cn.handy.constants.secret.SecretSectsEnum;
import cn.handy.constants.secret.SecretTypeEnum;
import cn.handy.entity.IdentifyItem;
import cn.handy.entity.Secret;
import cn.handy.utils.BaseUtil;
import cn.handy.utils.Beans;
import cn.handy.utils.LotteryUtil;
import cn.handy.utils.config.ConfigUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.KnowledgeBookMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author hanshuai
 * @Description: {生成默认的武林秘笈}
 * @date 2019/7/1 11:25
 */
public class SecretUtil {

    /**
     * 生成无字天书的配方-默认死亡重生事件
     */
    public static void noCharBook() {
        if (BaseConfigCache.isSecret) {
            ShapedRecipe identifyRecipe
                    = new ShapedRecipe(new NamespacedKey(Manage.plugin, "noCharBook"), SecretUtil.getItemStack(SecretListenerEnum.PLAYER_DEATH.getId()));
            identifyRecipe.shape("PPP", "PNP", "PPP");
            identifyRecipe.setIngredient('P', Material.PAPER);
            identifyRecipe.setIngredient('N', Material.DIAMOND_BLOCK);
            Bukkit.addRecipe(identifyRecipe);
            // 保存自定义的秘籍到缓存
            getSecretConfig();
        }
    }

    /**
     * 保存自定义的秘籍到缓存
     */
    public static void getSecretConfig() {
        String jsonArray = ConfigUtil.secretConfig.getString("secrets");
        Gson gson = new Gson();
        List<IdentifyItem> identifyItemList = gson.fromJson(jsonArray, new TypeToken<List<IdentifyItem>>() {
        }.getType());

        for (IdentifyItem identifyItem : identifyItemList) {
            // 生成秘籍
            identifyItem.setMaterial(Material.WRITTEN_BOOK);
            identifyItem.getLoreList().add("§0序号:" + identifyItem.getId() + ",门派:" + identifyItem.getSectsId());
            BaseConstants.itemStackList.add(getItemStack(identifyItem));
            // 保存生成概率
            BaseConstants.probabilityList.add(identifyItem.getProbability());
        }
        // 使用线程保存秘籍信息到数据库
        new BukkitRunnable() {
            @Override
            public void run() {
                for (IdentifyItem identifyItem : identifyItemList) {
                    Secret secret = new Secret();
                    secret.setId(identifyItem.getId());
                    secret.setSectsId(SecretSectsEnum.getEnum(identifyItem.getSectsId()).getId());
                    secret.setSectsName(SecretSectsEnum.getEnum(identifyItem.getSectsId()).getName());
                    secret.setName(identifyItem.getName());
                    secret.setLore(identifyItem.getLoreList().toString());
                    secret.setBuffId(SecretTypeEnum.getEnum(identifyItem.getBuffId()).getId());
                    secret.setBuffName(SecretTypeEnum.getEnum(identifyItem.getBuffId()).getName());
                    Beans.getBeans().getSecretService().set(secret);
                }
            }
        }.runTaskAsynchronously(Manage.plugin);
    }

    /**
     * 生成无字天书
     *
     * @param random 事件
     * @return
     */
    public static ItemStack getItemStack(int random) {
        val identifyItem = new IdentifyItem();
        identifyItem.setMaterial(Material.WRITTEN_BOOK);
        identifyItem.setName("§f[§e无字天书§f]" + "§0");
        identifyItem.setAuthor("佚名");
        identifyItem.setTitle(" ");
        identifyItem.setLoreList(
                Arrays.asList("§0事件:" + random,
                        ChatColor.AQUA + "鬼谷子的师傅升仙而去时",
                        ChatColor.AQUA + "曾留下这卷竹简"));
        return getItemStack(identifyItem);
    }

    /**
     * 生成自定义成书
     *
     * @param identifyItem
     * @return
     */
    private static ItemStack getItemStack(IdentifyItem identifyItem) {
        ItemStack item = new ItemStack(identifyItem.getMaterial());
        BookMeta meta = (BookMeta) item.getItemMeta();
        meta.setAuthor(identifyItem.getAuthor());
        meta.setTitle(identifyItem.getTitle());
        if (identifyItem.getPageList() != null && identifyItem.getPageList().size() > 0) {
            meta.setPages(identifyItem.getPageList());
        } else {
            meta.setPages(Arrays.asList(""));
        }
        meta.setGeneration(BookMeta.Generation.ORIGINAL);
        meta.setDisplayName(identifyItem.getName());
        meta.setLore(identifyItem.getLoreList());
        item.setItemMeta(meta);
        return item;
    }

    /**
     * 知识之书-无字天书合成配方
     */
    public static ItemStack getKnowledgeBook() {
        //获取知识之书的ItemStack对象
        ItemStack book = new ItemStack(Material.KNOWLEDGE_BOOK);
        KnowledgeBookMeta bookMeta = (KnowledgeBookMeta) book.getItemMeta();
        //添加合成配方，可以多个
        bookMeta.addRecipe(new NamespacedKey(Manage.plugin, "noCharBook"));
        //设置Meta数据
        book.setItemMeta(bookMeta);
        return book;
    }

    /**
     * 武林风云帮助之书
     *
     * @return
     */
    public static ItemStack getSecretHelp() {
        val identifyItem = new IdentifyItem();
        identifyItem.setMaterial(Material.WRITTEN_BOOK);
        identifyItem.setName("§f[§e武林风云§f]");
        identifyItem.setAuthor("xiongliu");
        identifyItem.setTitle("§f[§e武林风云§f]");

        identifyItem.setLoreList(
                Arrays.asList(
                        ChatColor.AQUA + "今天下动荡,各大门派纷纷出世",
                        ChatColor.AQUA + "武学秘籍层出不穷"));
        identifyItem.setPageList(Arrays.asList(
                "在《武林风云》的世界中\n你除了需要扮演神秘的“史蒂夫传人”\n还将以不同的处世立场——或善、或恶、或中庸——投身于纷繁复杂的江湖之中\n你不仅可以拜访世界各地的武林门派，学习种类繁多的功法绝艺"
                , "在这个神秘的世界,有着数不清的门派跟功法\n当然每种功法都有不同的作用\n但是每种功法都尘封于世,只能靠不同的手段才会展现真面目"
                , "据说三百六十行,行行出状元\n,一些勤恳磨练自己的技艺中的人\n将会更容易成就一番事业!"));
        return getItemStack(identifyItem);
    }

    /**
     * 获取事件随机数来对应事件
     *
     * @return
     */
    public static Integer getRandom() {
        return new Random().nextInt(SecretListenerEnum.values().length);
    }

    /**
     * 根据保存的概率获取随机秘籍
     *
     * @return
     */
    public static ItemStack ranItemStack() {
        val lotteryUtil = new LotteryUtil(BaseConstants.probabilityList);
        val index = lotteryUtil.randomColunmIndex();
        return BaseConstants.itemStackList.get(index);
    }

    /**
     * 判断俩个物品是否相等
     *
     * @param one   第一个
     * @param other 第二个
     * @param set   需要判断的种类
     * @return
     */
    public static Boolean equalsInSet(ItemStack one, ItemStack other, SecretEqualsInfoEnum... set) {
        if (!one.hasItemMeta()) {
            return false;
        }
        if (one.getItemMeta().getDisplayName() == null) {
            return false;
        }
        if (one.getItemMeta().getLore() == null) {
            return false;
        }
        boolean nameEquals = true;
        boolean loreEquals = true;
        boolean materialEquals = true;
        boolean amountEquals = true;
        for (SecretEqualsInfoEnum info : set) {
            switch (info) {
                case ABOUT_AMOUNT:
                    amountEquals = one.getAmount() == other.getAmount();
                    break;
                case ABOUT_LORE:
                    loreEquals = one.getItemMeta().getLore().equals(other.getItemMeta().getLore());
                    break;
                case ABOUT_NAME:
                    nameEquals = one.getItemMeta().getDisplayName().equals(other.getItemMeta().getDisplayName());
                    break;
                case ABOUT_MATERIAL:
                    materialEquals = one.getType() == other.getType() || one.getType().equals(other.getType());
                    break;
                default:
                    break;
            }
        }
        return nameEquals && loreEquals && materialEquals && amountEquals;
    }

    /**
     * 获取天书携带的事件
     *
     * @param itemStack
     * @return
     */
    public static int getEvenId(ItemStack itemStack) {
        int rst = -1;
        List<String> loreList = itemStack.getLore();
        if (loreList != null && loreList.size() > 0) {
            String loreStr = loreList.get(0);
            String[] lore = loreStr.split(",");
            String eventId = lore[0].substring(5);
            if (BaseUtil.isNumeric(eventId)) {
                rst = Integer.parseInt(eventId);
            }
        }
        return rst;
    }
}
