package com.sakurain.re_endergy; // 确保包名正确

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

// 标记为客户端专用处理类
@Mod(value = ReEndergy.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = ReEndergy.MODID, value = Dist.CLIENT)
public class EndergyResurrectedClient {

    // 监听：物品提示框渲染事件
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {

    }
}
