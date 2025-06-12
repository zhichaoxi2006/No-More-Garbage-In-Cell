package com.zhichaoxi.nmgic;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = Nmgic.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue IS_MERGE_ITEMSTACK = BUILDER
            .comment("Merge Empty Components ItemStack to Non Components ItemStack")
            .define("isMergeItemStack", true);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean isMergeItemStack;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        isMergeItemStack = IS_MERGE_ITEMSTACK.get();
    }
}
