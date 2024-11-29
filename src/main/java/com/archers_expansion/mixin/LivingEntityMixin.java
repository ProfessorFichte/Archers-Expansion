package com.archers_expansion.mixin;

import com.archers_expansion.effect.Effects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow
    public abstract boolean hasStatusEffect(RegistryEntry<StatusEffect> effect);

    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    public void noJumpPinDown(CallbackInfo ci) {
        if (this.hasStatusEffect(Effects.PIN_DOWN.registryEntry)) {
            ci.cancel();
        }
    }
}
