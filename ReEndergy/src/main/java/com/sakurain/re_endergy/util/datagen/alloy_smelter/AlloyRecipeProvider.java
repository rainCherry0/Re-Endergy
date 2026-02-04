package com.sakurain.re_endergy.util.datagen.alloy_smelter;

import com.enderio.enderio.init.EIOItems;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sakurain.re_endergy.ReEndergy;
import com.sakurain.re_endergy.util.item.MaterialDef;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 专门用于生成 Ender IO 合金炉 (Alloy Smelter) 配方 JSON 的 Provider
 * 绕过了原版 RecipeProvider 的 Serializer 检查
 */
public class AlloyRecipeProvider implements DataProvider {
    private final PackOutput packOutput;

    public AlloyRecipeProvider(PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        // 合金炉制造物品配方
        // 粗钢锭: 沙砾 + 黏土球 + 圆石通用tag
        new AlloyBuilder(i(MaterialDef.CRUDE_STEEL_INGOT))
                .setEnergy(10000).setExp(0.5f)
                .addInput(Items.IRON_INGOT)
                .addInput(Items.CLAY_BALL)
                .addInput(tag("c:cobblestones"))
                .save(futures, cache);

        // 铁合金锭: 铜 + 铁 + 沙砾
        new AlloyBuilder(i(MaterialDef.CONSTRUCTION_ALLOY_INGOT))
                .setEnergy(10000).setExp(0.3f)
                .addInput(Items.COPPER_INGOT)
                .addInput(Items.IRON_INGOT)
                .addInput(Items.GRAVEL)
                .save(futures, cache);

        // 充能银锭: 红石 + 铁锭(后续用银锭通用标签) + 萤石
        new AlloyBuilder(i(MaterialDef.ENERGETIC_SILVER_INGOT))
                .setEnergy(10000).setExp(0.8f)
                .addInput(Items.REDSTONE)
                .addInput(Items.IRON_INGOT)
                .addInput(Items.GLOWSTONE_DUST)
                .save(futures, cache);

        // 晶化合金锭: 金锭 + 脉冲水晶粉
        new AlloyBuilder(i(MaterialDef.CRYSTALLINE_ALLOY_INGOT))
                .setEnergy(10000).setExp(1.0f)
                .addInput(EIOItems.PULSATING_POWDER)
                .addInput(EIOItems.PULSATING_ALLOY_INGOT)
                .save(futures, cache);

        // 旋律合金锭: 爆裂紫颂果 + 末影钢
        new AlloyBuilder(i(MaterialDef.MELODIC_ALLOY_INGOT))
                .setEnergy(10000).setExp(1.5f)
                .addInput(Items.POPPED_CHORUS_FRUIT)
                .addInput(EIOItems.END_STEEL_INGOT)
                .save(futures, cache);

        // 恒星合金锭: 下界之星 + 旋律合金 + 黏土球*4
        new AlloyBuilder(i(MaterialDef.STELLAR_ALLOY_INGOT))
                .setEnergy(80000).setExp(3.0f)
                .addInput(Items.NETHER_STAR)
                .addInput(i(MaterialDef.MELODIC_ALLOY_INGOT))
                .addInput(Items.CLAY_BALL,4)
                .save(futures, cache);

        // 晶化粉红史莱姆锭: 晶化合金 + 粉红史莱姆，需要工业先锋，请找时间补合成表
        // [修改] 增加了条件判断，只有检测到工业先锋时才加载此配方
        new AlloyBuilder(i(MaterialDef.CRYSTALLINE_PINK_SLIME_INGOT))
                .setEnergy(10000).setExp(1.2f)
                .addInput(i(MaterialDef.CRYSTALLINE_ALLOY_INGOT))
                .addInput("industrialforegoing:pink_slime", 1)
                .onlyIfModLoaded("industrialforegoing")
                .save(futures, cache);

        // [新增] 晶化粉红史莱姆锭 (备用配方)
        // 晶化粉红史莱姆锭: 晶化合金 + 史莱姆球*4 + 粉红色染料or品红色染料 (为了避免没有工业先锋，导致做不出来)
        // 保存时加了后缀 "_synthetic" 防止文件名冲突
        new AlloyBuilder(i(MaterialDef.CRYSTALLINE_PINK_SLIME_INGOT))
                .setEnergy(15000).setExp(1.0f)
                .addInput(i(MaterialDef.CRYSTALLINE_ALLOY_INGOT))
                .addInput(Items.SLIME_BALL, 4)
                .addInput(1, tag("c:dyes/pink"), tag("c:dyes/magenta")) // 支持多选
                .save(futures, cache, "_synthetic");

        // 生动合金锭: 充能银 + 末影珍珠
        new AlloyBuilder(i(MaterialDef.VIVID_ALLOY_INGOT))
                .setEnergy(10000).setExp(1.0f)
                .addInput(i(MaterialDef.ENERGETIC_SILVER_INGOT))
                .addInput(Items.ENDER_PEARL)
                .save(futures, cache);

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    @Override
    public String getName() {
        return "Alloy Smelter Recipes (Custom JSON)";
    }

    // --- 极简辅助方法 ---
    private ItemLike i(MaterialDef def) { return ReEndergy.MATERIAL_ITEMS.get(def).get(); }
    private TagKey<Item> tag(String location) { return net.minecraft.tags.ItemTags.create(ResourceLocation.parse(location)); }

    // ================== 内部构建器 ==================

    private class AlloyBuilder {
        private final ItemLike result;
        private int count = 1;
        private int energy = 2000;
        private float experience = 0.5f;
        private final List<InputData> inputs = new ArrayList<>();
        // 存储 NeoForge 条件
        private final JsonArray conditions = new JsonArray();

        public AlloyBuilder(ItemLike result) { this.result = result; }

        public AlloyBuilder setCount(int c) { this.count = c; return this; }
        public AlloyBuilder setEnergy(int e) { this.energy = e; return this; }
        public AlloyBuilder setExp(float e) { this.experience = e; return this; }

        public AlloyBuilder addInput(ItemLike item) { return addInput(item, 1); }
        public AlloyBuilder addInput(ItemLike item, int c) { inputs.add(new InputData(item, null, null, null, c)); return this; }

        public AlloyBuilder addInput(TagKey<Item> tag) { return addInput(tag, 1); }
        public AlloyBuilder addInput(TagKey<Item> tag, int c) { inputs.add(new InputData(null, tag, null, null, c)); return this; }

        public AlloyBuilder addInput(String id, int c) { inputs.add(new InputData(null, null, id, null, c)); return this; }

        // [New] 支持多个标签 (OR 逻辑)
        @SafeVarargs
        public final AlloyBuilder addInput(int c, TagKey<Item>... tags) {
            inputs.add(new InputData(null, null, null, List.of(tags), c));
            return this;
        }

        // [New] 添加 Mod 加载条件
        public AlloyBuilder onlyIfModLoaded(String modid) {
            JsonObject condition = new JsonObject();
            condition.addProperty("type", "neoforge:mod_loaded");
            condition.addProperty("modid", modid);
            this.conditions.add(condition);
            return this;
        }

        public void save(List<CompletableFuture<?>> futures, CachedOutput cache) {
            save(futures, cache, "");
        }

        // [New] 增加 suffix 参数，允许自定义文件名后缀，防止同一物品的多个配方冲突
        public void save(List<CompletableFuture<?>> futures, CachedOutput cache, String suffix) {
            ResourceLocation resultId = BuiltInRegistries.ITEM.getKey(result.asItem());
            String pathName = resultId.getPath() + suffix; // 拼接后缀

            // 构造保存路径: data/re_endergy/recipe/alloy_smelter/xxx.json
            Path jsonPath = packOutput.getOutputFolder(PackOutput.Target.DATA_PACK)
                    .resolve(ReEndergy.MODID)
                    .resolve("recipe")
                    .resolve("alloy_smelter")
                    .resolve(pathName + ".json");

            // 构建 JSON
            JsonObject json = new JsonObject();

            // 写入条件
            if (!conditions.isEmpty()) {
                json.add("neoforge:conditions", conditions);
            }

            json.addProperty("type", "enderio:alloy_smelting");
            json.addProperty("energy", energy);
            json.addProperty("experience", experience);

            JsonArray inputsArray = new JsonArray();
            for (InputData in : inputs) {
                JsonObject entry = new JsonObject();
                entry.addProperty("count", in.count);

                // 判断类型写入
                if (in.orTags != null && !in.orTags.isEmpty()) {
                    // 多选标签逻辑
                    JsonArray options = new JsonArray();
                    for (TagKey<Item> t : in.orTags) {
                        JsonObject tagObj = new JsonObject();
                        tagObj.addProperty("tag", t.location().toString());
                        options.add(tagObj);
                    }
                    entry.add("ingredient", options);
                } else if (in.tag != null) {
                    entry.addProperty("tag", in.tag.location().toString());
                } else if (in.item != null) {
                    entry.addProperty("item", BuiltInRegistries.ITEM.getKey(in.item.asItem()).toString());
                } else if (in.rawId != null) {
                    entry.addProperty("item", in.rawId);
                }
                inputsArray.add(entry);
            }
            json.add("inputs", inputsArray);

            JsonObject outputObj = new JsonObject();
            outputObj.addProperty("count", count);
            outputObj.addProperty("id", resultId.toString());
            json.add("output", outputObj);

            // 保存
            futures.add(DataProvider.saveStable(cache, json, jsonPath));
        }
    }

    // [New] 更新 Record 以支持多标签列表
    private record InputData(
            @Nullable ItemLike item,
            @Nullable TagKey<Item> tag,
            @Nullable String rawId,
            @Nullable List<TagKey<Item>> orTags,
            int count
    ) {}
}
