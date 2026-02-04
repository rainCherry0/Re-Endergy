package com.sakurain.re_endergy.util.datagen;

import com.sakurain.re_endergy.ReEndergy;
import com.sakurain.re_endergy.util.item.CapacitorDef;
import com.sakurain.re_endergy.util.item.GrindingBallDef;
import com.sakurain.re_endergy.util.item.MaterialDef;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, ReEndergy.MODID, existingFileHelper);
    }


    @Override
    protected void addTags(HolderLookup.Provider provider) {
        //注册磨珠标签
        TagKey<Item> EIO_GRINDING_BALLS = TagKey.create(Registries.ITEM, ResourceLocation.parse("enderio:grinding_balls"));
        var ballTagBuilder = tag(EIO_GRINDING_BALLS);
        for (GrindingBallDef def : GrindingBallDef.values()) {
            ballTagBuilder.add(ReEndergy.BALL_ITEMS.get(def).get());
        }
        // 注册电容标签
        TagKey<Item> EIO_CAPACITORS = TagKey.create(Registries.ITEM, ResourceLocation.parse("enderio:capacitors"));
        var capacitorTagBuilder = tag(EIO_CAPACITORS);
        for (CapacitorDef def : CapacitorDef.values()) {
            capacitorTagBuilder.add(ReEndergy.CAPACITOR_ITEMS.get(def).get());
        }
        for (MaterialDef def : MaterialDef.values()) {
            // 获取对应的物品
            var item = ReEndergy.MATERIAL_ITEMS.get(def).get();
            // 遍历该材料定义的每一个标签
            for (TagKey<Item> tagKey : def.tags) {
                // 将物品加入到对应的标签中
                tag(tagKey).add(item);
            }
        }
    }
}
