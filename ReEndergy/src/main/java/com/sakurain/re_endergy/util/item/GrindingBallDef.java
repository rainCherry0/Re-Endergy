package com.sakurain.re_endergy.util.item;

import com.sakurain.re_endergy.ReEndergy;

/**
 * 磨珠定义枚举
 *在此处添加一行枚举，即可自动完成：物品注册 + 配置文件生成 + 模型关联(需配合DataGen)
 */
public enum GrindingBallDef {
    // 格式: 注册名, 主产物倍率, 副产物倍率, 能耗倍率, 耐久度
    //粗钢磨珠
    CRUDE_STEEL("crude_steel_ball","粗钢磨珠", 1.2, 1.2, 0.85, 24000),
    //铁合金磨珠
    ALLOY_BALL_CONSTRUCTION_ALLOY("alloy_ball_construction_alloy","铁合金磨珠", 1, 0.33, 0.25, 12000),
//    //末影磨珠，需要热力膨胀
//    GRINDING_BALL_ENDERIUM("grinding_ball_enderium","末影磨珠", 1.65, 1.45, 1.25, 120000),
//    //流明磨珠，需要热力膨胀
//    GRINDING_BALL_LUMIUM("grinding_ball_lumium","流明磨珠", 1.1, 2.11, 0.9, 100000),
//    //信素磨珠，需要热力膨胀
//    GRINDING_BALL_SIGNALUM("grinding_ball_signalum","信素磨珠", 1.2, 1.65, 0.35, 100000),
    //晶化合金磨珠
    ALLOY_BALL_CRYSTALLINE_ALLOY("alloy_ball_crystalline_alloy","晶化合金磨珠", 1.8, 1.4, 1.45, 80000),
    //旋律合金磨珠
    ALLOY_BALL_MELODIC_ALLOY("alloy_ball_melodic_alloy","旋律合金磨珠", 2, 1.45, 1.55, 80000),
    //恒星合金磨珠
    ALLOY_BALL_STELLAR_ALLOY("alloy_ball_stellar_alloy","恒星合金磨珠", 2.3, 2.25, 2.2, 160000),
    //晶化粉红史莱姆磨珠
    ALLOY_BALL_CRYSTALLINE_PINK_SLIME("alloy_ball_crystalline_pink_slime","晶化粉红史莱姆磨珠", 1.75, 1.55, 1.55, 80000),
    //充能银磨珠
    ALLOY_BALL_ENERGETIC_SILVER("alloy_ball_energetic_silver","充能银磨珠", 1.6, 1.6, 1.1, 80000),
    //生动合金磨珠
    ALLOY_BALL_VIVID_ALLOY("alloy_ball_vivid_alloy","生动合金磨珠", 1.75, 1.35, 1.35, 80000),
    //下面是原创磨珠时间了
    //空间坍塌磨珠

    ;
;

    public final String name;   // 必须是英文，对应贴图文件名
    public final String zhName; // 这里存中文

    public final double defaultOutput;
    public final double defaultBonus;
    public final double defaultPower;
    public final int defaultDuration;

    GrindingBallDef(String name, String zhName, double defaultOutput, double defaultBonus, double defaultPower, int defaultDuration) {
        this.name = name;
        this.zhName = zhName;
        this.defaultOutput = defaultOutput;
        this.defaultBonus = defaultBonus;
        this.defaultPower = defaultPower;
        this.defaultDuration = defaultDuration;
    }

    // 获取翻译键 (便于 DataGen 使用)
    public String getTranslationKey() {
        return "item." + ReEndergy.MODID + "." + name;
    }
}
