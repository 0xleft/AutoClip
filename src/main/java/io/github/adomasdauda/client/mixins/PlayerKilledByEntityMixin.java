package io.github.adomasdauda.client.mixins;

import io.github.adomasdauda.client.interfaces.EntityKilledByEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
public class PlayerKilledByEntityMixin {

    @Inject(at = @At(value = "RETURN"), method = "damage", cancellable = true)
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {

        if (!(((LivingEntity) (Object) this).getHealth() <= 0.0F)) {
            return;
        }

        ActionResult result = EntityKilledByEntityCallback.EVENT.invoker().interact((LivingEntity) (Object) this, source.getSource());

        if(result == ActionResult.FAIL) {
            cir.cancel();
        }
    }
}
