package io.github.adomasdauda.client.mixins;

import io.github.adomasdauda.client.interfaces.PlayerBlockPlaceCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(PlayerEntity.class)
public class PlayerBlockPlaceMixin {

    @Inject(at = @At(value = "RETURN"), method = "canPlaceOn", cancellable = true)
    private void onPlace(BlockPos pos, Direction facing, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {

        ActionResult result = PlayerBlockPlaceCallback.EVENT.invoker().onPlace((PlayerEntity) (Object) this, stack);

        if(result == ActionResult.FAIL) {
            cir.cancel();
        }
    }
}