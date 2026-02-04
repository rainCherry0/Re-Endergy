package com.sakurain.re_endergy.util.datagen;

import com.sakurain.re_endergy.ReEndergy;
import com.sakurain.re_endergy.util.datagen.alloy_smelter.AlloyRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ReEndergy.MODID)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        // 1. 物品模型
        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));
        // 2. 语言文件
        generator.addProvider(event.includeClient(), new ModZhCnLangProvider(packOutput));
        // 3. 标签生成
        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(
                packOutput,
                lookupProvider,
                blockTagsProvider.contentsGetter(),
                existingFileHelper
        ));
        // 4. 工作台配方生成
        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput, lookupProvider));
        // 5. 合金炉Alloy Smelter 配方生成
        generator.addProvider(event.includeServer(), new AlloyRecipeProvider(packOutput));
    }
}
