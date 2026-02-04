package com.sakurain.re_endergy;

import com.enderio.base.api.capacitor.CapacitorData;
import com.enderio.base.api.grindingball.GrindingBallData;
import com.enderio.base.common.init.EIODataComponents;
import com.mojang.logging.LogUtils;
import com.sakurain.re_endergy.util.item.CapacitorDef;
import com.sakurain.re_endergy.util.item.GrindingBallDef;
import com.sakurain.re_endergy.util.item.MaterialDef;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

import java.util.EnumMap;
import java.util.Map;

@Mod(ReEndergy.MODID)
public class ReEndergy {
    public static final String MODID = "re_endergy";
    public static final Logger LOGGER = LogUtils.getLogger();

    // 1. 注册器初始化
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB, MODID);

    // 2. 物品 Map 初始化 (通过方法赋值)

    // --- 磨珠 (Grinding Balls)
    public static final Map<GrindingBallDef, DeferredHolder<Item, Item>> BALL_ITEMS = registerGrindingBalls();

    // --- 电容 (Capacitors)
    public static final Map<CapacitorDef, DeferredHolder<Item, Item>> CAPACITOR_ITEMS = registerCapacitors();

    // 材料 (锭、粒)
    public static final Map<MaterialDef, DeferredHolder<Item, Item>> MATERIAL_ITEMS = registerMaterials();

    // 静态辅助方法：执行磨珠注册循环
    private static Map<GrindingBallDef, DeferredHolder<Item, Item>> registerGrindingBalls() {
        Map<GrindingBallDef, DeferredHolder<Item, Item>> map = new EnumMap<>(GrindingBallDef.class);

        for (GrindingBallDef def : GrindingBallDef.values()) {
            DeferredHolder<Item, Item> itemHolder = ITEMS.register(def.name, () -> new Item(
                    new Item.Properties()
                            // 绑定 Ender IO 的磨珠数据组件
                            .component(EIODataComponents.GRINDING_BALL.get(), new GrindingBallData(
                                    (float) def.defaultOutput,
                                    (float) def.defaultBonus,
                                    (float) def.defaultPower,
                                    def.defaultDuration
                            ))
            ));
            map.put(def, itemHolder);
        }
        return map;
    }

    // 静态辅助方法：执行电容注册循环
    private static Map<CapacitorDef, DeferredHolder<Item, Item>> registerCapacitors() {
        Map<CapacitorDef, DeferredHolder<Item, Item>> map = new EnumMap<>(CapacitorDef.class);
        for (CapacitorDef def : CapacitorDef.values()) {
            DeferredHolder<Item, Item> itemHolder = ITEMS.register(def.name, () -> {
                //创建基础属性对象
                Item.Properties properties = new Item.Properties();
//                //特殊判断：如果是基岩粉电容，设置耐久度，暂时关闭，目前EIO本体还没有减少耐久度的API，等完整之后说不定就有了
//                if (def == CapacitorDef.CAPACITOR_GRAINY) {
//                    properties.durability(100);
//                }
                //构建电容数据
                CapacitorData data = CapacitorData.simple((float) def.baseLevel);
                //绑定组件并生成 Item
                return new Item(properties.component(EIODataComponents.CAPACITOR_DATA.get(), data));
            });
            map.put(def, itemHolder);
        }
        return map;
    }
    // 静态辅助方法：执行金属锭、金属粒注册循环
    private static Map<MaterialDef, DeferredHolder<Item, Item>> registerMaterials() {
        Map<MaterialDef, DeferredHolder<Item, Item>> map = new EnumMap<>(MaterialDef.class);
        for (MaterialDef def : MaterialDef.values()) {
            // 普通材料不需要特殊的 Data Components，只注册最基础的 Item
            DeferredHolder<Item, Item> itemHolder = ITEMS.register(def.name, () -> new Item(
                    new Item.Properties()
            ));
            map.put(def, itemHolder);
        }
        return map;
    }

    // 3. 创造模式标签
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOD_TAB = CREATIVE_MODE_TABS.register("main_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.ReEndergy"))
            .icon(() -> {
                // 安全检查：防止空指针
                if (CAPACITOR_ITEMS.isEmpty() || CAPACITOR_ITEMS.get(CapacitorDef.CAPACITOR_STELLAR) == null) {
                    return ItemStack.EMPTY;
                }
                return CAPACITOR_ITEMS.get(CapacitorDef.CAPACITOR_STELLAR).get().getDefaultInstance();
            })
            .displayItems((parameters, output) -> {
                // 添加磨珠到创造栏
                BALL_ITEMS.values().forEach(holder -> output.accept(holder.get()));
                // 添加电容到创造栏
                CAPACITOR_ITEMS.values().forEach(holder -> output.accept(holder.get()));
                // 添加金属锭、金属粒到创造栏
                MATERIAL_ITEMS.values().forEach(holder -> output.accept(holder.get()));
            }).build());

    // 构造函数
    public ReEndergy(IEventBus modEventBus, ModContainer modContainer) {
        // 总线注册
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        // 注册配置
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        LOGGER.info("Endergy Resurrected initialized.");
    }
}
