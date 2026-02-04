package com.sakurain.re_endergy.util.datagen;

import com.sakurain.re_endergy.ReEndergy;
import com.sakurain.re_endergy.util.item.CapacitorDef;
import com.sakurain.re_endergy.util.item.GrindingBallDef;
import com.sakurain.re_endergy.util.item.MaterialDef;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ReEndergy.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // 1. 生成磨珠模型 (指定存放在 "item/grinding_ball" 目录下)
        for (GrindingBallDef def : GrindingBallDef.values()) {
            simpleItemWithFolder(ReEndergy.BALL_ITEMS.get(def), "grinding_ball");
        }

        // 2. 生成电容模型 (指定存放在 "item/capacitor" 目录下)
        for (CapacitorDef def : CapacitorDef.values()) {
            simpleItemWithFolder(ReEndergy.CAPACITOR_ITEMS.get(def), "capacitor");
        }

        // 3. 生成材料模型 (使用枚举中定义的 subFolder属性，随机应变设置路径)
        for (MaterialDef def : MaterialDef.values()) {
            simpleItemWithFolder(ReEndergy.MATERIAL_ITEMS.get(def), def.subFolder);
        }
    }

    /**
     * 自定义的物品模型生成方法，支持子文件夹分类。
     *
     * @param itemHolder 物品注册对象
     * @param subFolder  textures/item/ 下的子文件夹名称 (例如 "ball", "capacitor")
     */
    private ItemModelBuilder simpleItemWithFolder(DeferredHolder<Item, Item> itemHolder, String subFolder) {
        String name = itemHolder.getId().getPath();
        return withExistingParent(name, mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + subFolder + "/" + name));
    }
}
