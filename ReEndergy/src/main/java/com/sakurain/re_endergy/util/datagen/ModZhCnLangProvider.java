package com.sakurain.re_endergy.util.datagen;

import com.sakurain.re_endergy.ReEndergy;
import com.sakurain.re_endergy.util.item.CapacitorDef;
import com.sakurain.re_endergy.util.item.GrindingBallDef;
import com.sakurain.re_endergy.util.item.MaterialDef;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModZhCnLangProvider extends LanguageProvider {

    public ModZhCnLangProvider(PackOutput output) {
        super(output, ReEndergy.MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        // 创造模式物品栏
        add("itemGroup.ReEndergy", "末影接口:管道拓展:重生");
        // 磨珠翻译
        for (GrindingBallDef def : GrindingBallDef.values()) {
            add(ReEndergy.BALL_ITEMS.get(def).get(), def.zhName);
        }
        // 电容翻译
        for (CapacitorDef def : CapacitorDef.values()) {
            add(ReEndergy.CAPACITOR_ITEMS.get(def).get(), def.zhName);
        }
        // 通用材料翻译，比如金属锭、金属粒、金属粉
        for (MaterialDef def : MaterialDef.values()) {
            add(ReEndergy.MATERIAL_ITEMS.get(def).get(), def.zhName);
        }
    }
}
