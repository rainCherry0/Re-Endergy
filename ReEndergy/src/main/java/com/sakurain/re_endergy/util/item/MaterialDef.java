package com.sakurain.re_endergy.util.item;

import com.sakurain.re_endergy.ReEndergy;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.List;

/**
 * 通用材料定义枚举
 * 用于注册锭(Ingot)、粒(Nugget)等基础材料
 */
public enum MaterialDef {
    /*
     * =============== 如何添加标签 (How to add tags) ===============
     * 目前所有物品默认没有标签（空列表）。
     * 如果以后需要添加标签，请在最后面使用逗号分隔，调用 helper 方法。
     *
     * 示例 (Example):
     * CRUDE_STEEL_INGOT(..., "ingot", commonTag("ingots"), commonTag("ingots/crude_steel")),
     *
     * 解释:
     * commonTag("ingots") -> 生成 c:ingots 标签
     * eioTag("gears")     -> 生成 enderio:gears 标签
     * ============================================================
     */

    // 格式: 注册名, 中文名，要存放的文件夹，标签列表(留空即为无标签)

    // 粗钢锭、粗钢粒
    CRUDE_STEEL_INGOT("crude_steel_ingot", "粗钢锭","ingot"),
    CRUDE_STEEL_NUGGET("crude_steel_nugget", "粗钢粒","nugget"),

    //铁合金锭、铁合金粒
    CONSTRUCTION_ALLOY_INGOT("construction_alloy_ingot", "铁合金锭","ingot"),
    CONSTRUCTION_ALLOY_NUGGET("construction_alloy_nugget", "铁合金粒","nugget"),

//    //末影磨珠，需要热力膨胀
//    GRINDING_BALL_ENDERIUM("grinding_ball_enderium","末影磨珠", 1.65, 1.45, 1.25, 120000),
//    //流明磨珠，需要热力膨胀
//    GRINDING_BALL_LUMIUM("grinding_ball_lumium","流明磨珠", 1.1, 2.1, 0.9, 100000),
//    //信素磨珠，需要热力膨胀
//    GRINDING_BALL_SIGNALUM("grinding_ball_signalum","信素磨珠", 1.2, 1.65, 0.35, 100000),

    //晶化合金锭、晶化合金粒
    CRYSTALLINE_ALLOY_INGOT("crystalline_alloy_ingot", "晶化合金锭","ingot"),
    CRYSTALLINE_ALLOY_NUGGET("crystalline_alloy_nugget", "晶化合金粒","nugget"),

    //旋律合金锭、旋律合金粒
    MELODIC_ALLOY_INGOT("melodic_alloy_ingot", "旋律合金锭","ingot"),
    MELODIC_ALLOY_NUGGET("melodic_alloy_nugget", "旋律合金粒","nugget"),

    //恒星合金锭、恒星合金粒
    STELLAR_ALLOY_INGOT("stellar_alloy_ingot", "恒星合金锭","ingot"),
    STELLAR_ALLOY_NUGGET("stellar_alloy_nugget", "恒星合金粒","nugget"),

    //晶化粉红史莱姆锭、晶化粉红史莱姆粒，需要工业先锋
    CRYSTALLINE_PINK_SLIME_INGOT("crystalline_pink_slime_ingot", "晶化粉红史莱姆锭","ingot"),
    CRYSTALLINE_PINK_SLIME_NUGGET("crystalline_pink_slime_nugget", "晶化粉红史莱姆粒","nugget"),

    //充能银锭、充能银粒
    ENERGETIC_SILVER_INGOT("energetic_silver_ingot", "充能银锭","ingot"),
    ENERGETIC_SILVER_NUGGET("energetic_silver_nugget", "充能银粒","nugget"),

    //生动合金锭、生动合金粒
    VIVID_ALLOY_INGOT("vivid_alloy_ingot", "生动合金锭","ingot"),
    VIVID_ALLOY_NUGGET("vivid_alloy_nugget", "生动合金粒","nugget"),
    ;

    public final String name;
    public final String zhName;
    public final String subFolder;
    public final List<TagKey<Item>> tags;

    // 添加 @SafeVarargs 是为了消除 "Possible heap pollution" 警告，因为这是泛型可变参数
    @SafeVarargs
    MaterialDef(String name, String zhName, String subFolder, TagKey<Item>... tags) {
        this.name = name;
        this.zhName = zhName;
        this.subFolder= subFolder;
        // 将数组转为不可变列表存储，性能高且安全
        // 如果没有传递 tags 参数，这里会生成一个空的 List，完全符合你的需求
        this.tags = List.of(tags);
    }

    // 辅助：获取翻译键
    public String getTranslationKey() {
        return "item." + ReEndergy.MODID + "." + name;
    }
    // --- 静态辅助方法：快速生成 TagKey ---

    /**
     * 生成 "c" 命名空间下的通用标签 (Common Tags)
     * 用法: commonTag("ingots") 或 commonTag("ingots/copper")
     */
    private static TagKey<Item> commonTag(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", path));
    }

    /**
     * 生成 "enderio" 命名空间下的标签 (如果需要)
     */
    private static TagKey<Item> eioTag(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("enderio", path));
    }
}
