package io.github.adomasdauda.client.mixins;

import io.github.adomasdauda.client.interfaces.EntityHitByEntityCallback;
import io.github.adomasdauda.client.interfaces.EntityKilledByEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class EntityKilledByEntityMixin {

    @Inject(at = @At(value = "RETURN"), method = "onDeath", cancellable = true)
    private void onHit(DamageSource damageSource, CallbackInfo ci) {

        if (((LivingEntity) (Object) this).getHealth() > 0) {
            return;
        }

        ActionResult result = EntityKilledByEntityCallback.EVENT.invoker().interact((LivingEntity) (Object) this, damageSource.getSource());

        if(result == ActionResult.FAIL) {
            ci.cancel();
        }
    }
}