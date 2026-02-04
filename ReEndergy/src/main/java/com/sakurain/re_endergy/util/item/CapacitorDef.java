package com.sakurain.re_endergy.util.item;


public enum CapacitorDef {
    //基岩粉电容
    CAPACITOR_GRAINY("capacitor_grainy", "基岩粉电容", 1f),
    //晶化电容
    CAPACITOR_CRYSTALLINE("capacitor_crystalline", "晶化电容", 3.5f),
    //旋律电容
    CAPACITOR_MELODIC("capacitor_melodic", "旋律电容", 4f),
    //恒星电容
    CAPACITOR_STELLAR("capacitor_stellar", "恒星电容", 5f),

    //银制电容
    CAPACITOR_SILVER("capacitor_silver", "银制电容", 1f),
    //充能银制电容
    CAPACITOR_ENERGETIC_SILVER("capacitor_energetic_silver", "充能银制电容", 2f),
    //生动合金电容
    CAPACITOR_VIVID("capacitor_vivid", "生动合金电容", 3f),
    ;
    public final String name;
    public final String zhName;
    public final float baseLevel;

    CapacitorDef(String name, String zhName, float baseLevel) {
        this.name = name;
        this.zhName = zhName;
        this.baseLevel = baseLevel;
    }
}
