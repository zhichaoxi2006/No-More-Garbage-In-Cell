package com.zhichaoxi.nmgic.mixin;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.me.cells.BasicCellInventory;
import com.zhichaoxi.nmgic.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BasicCellInventory.class)
public abstract class BasicCellInventoryMixin {
    @Shadow public abstract long insert(AEKey what, long amount, Actionable mode, IActionSource source);

    @Inject(method = "insert", at = @At("HEAD"), cancellable = true)
    public void BasicCellInventoryMixin$insert(AEKey what, long amount, Actionable mode, IActionSource source, CallbackInfoReturnable<Long> cir) {
        if (what instanceof AEItemKey itemKey) {
            var stack = itemKey.toStack();

            boolean bool;
            var componentsPatch = stack.getComponentsPatch();
            var set = componentsPatch.entrySet();


            bool = set.stream()
                    .anyMatch(c -> c.getValue().isPresent());

            if (Config.isMergeItemStack && !componentsPatch.isEmpty()) {
                AEItemKey key = AEItemKey.of(stack.getItem());
                long result = insert(key, amount, mode, source);
                cir.setReturnValue(result);
            }

            if (bool) {
                cir.setReturnValue(0L);
            }
        }
    }
}
