package io.github.adomasdauda.client.mixins;

import io.github.adomasdauda.client.ClipHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class ScreenMixin {

    @Inject(at = @At("RETURN"), method = "render")
    private void onRender(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (ClipHelper.isRecentlyClipped()) {

            float x = (float) ((System.currentTimeMillis() - ClipHelper.getLastClipTime()) / 10f);
            ((InGameHud) (Object) this).getTextRenderer().drawWithShadow(matrices, "Clipped!", (float) Math.sin(Math.toRadians(x)) * 20 - 20, 2, 0xFFFFFFFF);
        }
    }
}
