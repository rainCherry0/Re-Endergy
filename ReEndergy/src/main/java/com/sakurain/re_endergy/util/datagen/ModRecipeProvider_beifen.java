package com.sakurain.re_endergy.util.datagen;

import com.enderio.enderio.api.components.GrindingBallData;
import com.enderio.enderio.init.EIOItems;
import com.sakurain.re_endergy.ReEndergy;
import com.sakurain.re_endergy.util.item.CapacitorDef;
import com.sakurain.re_endergy.util.item.GrindingBallDef;
import com.sakurain.re_endergy.util.item.MaterialDef;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider_beifen extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider_beifen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        //配方模板为：剑模板
        //基岩粉电容
        swordFormat(output, i(CapacitorDef.CAPACITOR_GRAINY), EIOItems.GRAINS_OF_INFINITY,Items.IRON_NUGGET, "capacitor");
        //配方模板为：十字形，磨珠模板
        //粗钢磨珠
        buildDiamond(output, GrindingBallDef.CRUDE_STEEL, i(MaterialDef.CRUDE_STEEL_INGOT), "grinding_ball");
        //铁合金磨珠
        buildDiamond(output, GrindingBallDef.ALLOY_BALL_CONSTRUCTION_ALLOY, i(MaterialDef.CONSTRUCTION_ALLOY_INGOT), "grinding_ball");
        //末影磨珠，需要热力膨胀
        //流明磨珠，需要热力膨胀
        //信素磨珠，需要热力膨胀
        //晶化合金磨珠
        buildDiamond(output, GrindingBallDef.ALLOY_BALL_CRYSTALLINE_ALLOY, i(MaterialDef.CRYSTALLINE_ALLOY_INGOT), "grinding_ball");
        //旋律合金磨珠
        buildDiamond(output, GrindingBallDef.ALLOY_BALL_MELODIC_ALLOY, i(MaterialDef.MELODIC_ALLOY_INGOT), "grinding_ball");
        //恒星合金磨珠
        buildDiamond(output, GrindingBallDef.ALLOY_BALL_STELLAR_ALLOY, i(MaterialDef.STELLAR_ALLOY_INGOT), "grinding_ball");
        //晶化粉红史莱姆磨珠，需要工业先锋
        buildDiamond(output, GrindingBallDef.ALLOY_BALL_CRYSTALLINE_PINK_SLIME, i(MaterialDef.CRYSTALLINE_PINK_SLIME_INGOT), "grinding_ball");
        //充能银磨珠
        buildDiamond(output, GrindingBallDef.ALLOY_BALL_ENERGETIC_SILVER, i(MaterialDef.ENERGETIC_SILVER_INGOT), "grinding_ball");
        //生动合金磨珠
        buildDiamond(output, GrindingBallDef.ALLOY_BALL_VIVID_ALLOY, i(MaterialDef.VIVID_ALLOY_INGOT), "grinding_ball");

        // 配方模板为：1个物品拆9个物品
        //粗钢粒
        //粗钢粒
        buildUncompacting(output, MaterialDef.CRUDE_STEEL_INGOT, MaterialDef.CRUDE_STEEL_NUGGET, "nugget");
        //铁合金粒
        buildUncompacting(output, MaterialDef.CONSTRUCTION_ALLOY_INGOT, MaterialDef.CONSTRUCTION_ALLOY_NUGGET, "nugget");
        //晶化合金粒
        buildUncompacting(output, MaterialDef.CRYSTALLINE_ALLOY_INGOT, MaterialDef.CRYSTALLINE_ALLOY_NUGGET, "nugget");
        //旋律合金粒
        buildUncompacting(output, MaterialDef.MELODIC_ALLOY_INGOT, MaterialDef.MELODIC_ALLOY_NUGGET, "nugget");
        //恒星合金粒
        buildUncompacting(output, MaterialDef.STELLAR_ALLOY_INGOT, MaterialDef.STELLAR_ALLOY_NUGGET, "nugget");
        //晶化粉红史莱姆粒
        buildUncompacting(output, MaterialDef.CRYSTALLINE_PINK_SLIME_INGOT, MaterialDef.CRYSTALLINE_PINK_SLIME_NUGGET, "nugget");
        //充能银粒
        buildUncompacting(output, MaterialDef.ENERGETIC_SILVER_INGOT, MaterialDef.ENERGETIC_SILVER_NUGGET, "nugget");
        //生动合金粒
        buildUncompacting(output, MaterialDef.VIVID_ALLOY_INGOT, MaterialDef.VIVID_ALLOY_NUGGET, "nugget");

        //配方模板为：9个物品围一圈合成一个物品
        //粗钢锭
        buildCompacting(output, MaterialDef.CRUDE_STEEL_INGOT, MaterialDef.CRUDE_STEEL_NUGGET, "ingot");
        //铁合金锭
        buildCompacting(output, MaterialDef.CONSTRUCTION_ALLOY_INGOT, MaterialDef.CONSTRUCTION_ALLOY_NUGGET, "ingot");
        //晶化合金锭
        buildCompacting(output, MaterialDef.CRYSTALLINE_ALLOY_INGOT, MaterialDef.CRYSTALLINE_ALLOY_NUGGET, "ingot");
        //旋律合金锭
        buildCompacting(output, MaterialDef.MELODIC_ALLOY_INGOT, MaterialDef.MELODIC_ALLOY_NUGGET, "ingot");
        //恒星合金锭
        buildCompacting(output, MaterialDef.STELLAR_ALLOY_INGOT, MaterialDef.STELLAR_ALLOY_NUGGET, "ingot");
        //晶化粉红史莱姆锭
        buildCompacting(output, MaterialDef.CRYSTALLINE_PINK_SLIME_INGOT, MaterialDef.CRYSTALLINE_PINK_SLIME_NUGGET, "ingot");
        //充能银锭
        buildCompacting(output, MaterialDef.ENERGETIC_SILVER_INGOT, MaterialDef.ENERGETIC_SILVER_NUGGET, "ingot");
        //生动合金
        buildCompacting(output, MaterialDef.VIVID_ALLOY_INGOT, MaterialDef.VIVID_ALLOY_NUGGET, "ingot");

        // ===============================================
        //  场景 2: 略微复杂的模板 (围一圈)
        // ===============================================

        // 比如：泥土混合钢 (中间泥土，周围8个钢) - 假设产物是 Construction Alloy
        // 参数顺序：输出接口，产物，中间物品，周围物品，存哪儿
//        buildSurround(output, MaterialDef.CONSTRUCTION_ALLOY_INGOT, Items.DIRT, i(MaterialDef.CRUDE_STEEL_INGOT), "ingot");


        // ===============================================
        //  场景 3: 高度自定义形状 (如：粗钢剑)
        //  完全不需要声明变量名！
        // ===============================================

        // 假设你要做一个 "Crude Steel Sword" (这里用 Construction Alloy 举例，因为你还没注册剑)
//        // 注意看：这里使用了链式调用，一气呵成
//        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, i(MaterialDef.CONSTRUCTION_ALLOY_INGOT)) // 这里填 output
//                .pattern(" X ")
//                .pattern(" X ")
//                .pattern(" S ")
//                .define('X', i(MaterialDef.CRUDE_STEEL_INGOT)) // 直接调用 i() 获取输入
//                .define('S', Items.STICK)
//                .unlockedBy("has_material", has(i(MaterialDef.CRUDE_STEEL_INGOT))) // 自动判断解锁
//                .save(output, path("tools", "crude_steel_sword")); // 自定义存放：tools/crude_steel_sword.json
    }


    // =================================================================
    //            工具箱 (Helper Methods) - 把脏活累活藏在这里
    // =================================================================
    /**
     * 【极简取物】取 Material
     */
    private ItemLike i(MaterialDef def) {
        return ReEndergy.MATERIAL_ITEMS.get(def).get();
    }

    /**
     * 【极简取物】取 Grinding Ball
     */
    private ItemLike i(GrindingBallDef def) {
        return ReEndergy.BALL_ITEMS.get(def).get();
    }

    /**
     * 【极简取物】取 Capacitor (电容)
     */
    private ItemLike i(CapacitorDef def) {
        return ReEndergy.CAPACITOR_ITEMS.get(def).get();
    }

    /**
     * 【路径生成器】
     * 自动加前缀 re_endergy: 并拼接文件夹
     */
    private ResourceLocation path(String folder, String name) {
        return ResourceLocation.fromNamespaceAndPath(ReEndergy.MODID, folder + "/" + name);
    }

    // 重载方法：直接从 Item 对象取名
    private ResourceLocation path(String folder, ItemLike item) {
        // 使用 RecipeProvider 自带的 getItemName 方法，它只返回 "crude_steel_ball"
        return path(folder, getItemName(item));
    }

    // --- 模板 1: 十字配方 (磨珠专用) ---
    private void buildDiamond(RecipeOutput out, GrindingBallDef result, ItemLike input, String folder) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i(result),24)
                .pattern(" X ")
                .pattern("XXX")
                .pattern(" X ")
                .define('X', input)
                .unlockedBy("has_item", has(input))
                .save(out, path(folder, i(result)));
    }

    // --- 模板 2: 3x3 压缩 (9个B -> 1个A) ---
    private void buildCompacting(RecipeOutput out, MaterialDef result, MaterialDef input, String folder) {
        // 使用 Shapeless (无序) 还是 Shaped (有序) 取决于你，通常压缩块是 3x3 有序
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i(result))
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .define('X', i(input))
                .unlockedBy("has_item", has(i(input)))
                .save(out, path(folder, i(result)));
    }

    // --- 模板 3: 1拆9 (1个A -> 9个B) ---
    private void buildUncompacting(RecipeOutput out, MaterialDef result, MaterialDef input, String folder) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, i(result), 9)
                .requires(i(input))
                .unlockedBy("has_item", has(i(input)))
                .save(out, path(folder, i(result)));
    }
    // 钻石剑格式，以最顶上的物品为合成书解锁条件
    private void swordFormat(RecipeOutput out, ItemLike result, ItemLike top,ItemLike body, String folder) {
        // 使用 Shapeless (无序) 还是 Shaped (有序) 取决于你，通常压缩块是 3x3 有序
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .pattern(" X ")
                .pattern(" Y ")
                .pattern(" Y ")
                .define('X', top)
                .define('Y', body)
                .unlockedBy("has_item", has(top))
                .save(out, path(folder, result));
    }
//    // --- 模板 4: 围一圈 (Surround) ---
//    private void buildSurround(RecipeOutput out, MaterialDef result, ItemLike center, ItemLike surround, String folder) {
//        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, i(result))
//                .pattern("SSS")
//                .pattern("SCS")
//                .pattern("SSS")
//                .define('S', surround)
//                .define('C', center)
//                .unlockedBy("has_item", has(surround))
//                .save(out, path(folder, i(result)));
//    }
}
