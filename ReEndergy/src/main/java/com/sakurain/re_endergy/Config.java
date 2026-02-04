package com.sakurain.re_endergy;

import com.sakurain.re_endergy.util.item.GrindingBallDef;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.EnumMap;
import java.util.Map;

public class Config {
    // 1. 核心构建器 (必须第一位)
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // 2. 数据存储 Map (必须第二位)
    public static final Map<GrindingBallDef, BallConfig> BALL_CONFIGS = new EnumMap<>(GrindingBallDef.class);

    // 3. 普通配置项
    public static final ModConfigSpec.BooleanValue ENABLE_NEW_CONDUITS = BUILDER
            .comment("Whether to register the new energy conduits.")
            .define("enableNewConduits", true);

    // 4. 配置构建逻辑 (使用私有方法立即执行，规避 static 块陷阱)
    private static final boolean INITIALIZED = init();

    private static boolean init() {
        BUILDER.comment("Grinding Ball Settings").push("grinding_balls");

        for (GrindingBallDef def : GrindingBallDef.values()) {
            BUILDER.push(def.name);
            BALL_CONFIGS.put(def, new BallConfig(
                    BUILDER.defineInRange("outputMultiplier", def.defaultOutput, 0.1, 100.0),
                    BUILDER.defineInRange("bonusMultiplier", def.defaultBonus, 0.1, 100.0),
                    BUILDER.defineInRange("powerUse", def.defaultPower, 0.01, 100.0),
                    BUILDER.defineInRange("duration", def.defaultDuration, 1, Integer.MAX_VALUE)
            ));
            BUILDER.pop();
        }

        BUILDER.pop();
        return true;
    }

    // 5. 生成 SPEC (最后一位)
    public static final ModConfigSpec SPEC = BUILDER.build();

    public record BallConfig(
            ModConfigSpec.DoubleValue output,
            ModConfigSpec.DoubleValue bonus,
            ModConfigSpec.DoubleValue power,
            ModConfigSpec.IntValue duration
    ) {}
}
