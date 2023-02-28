package io.github.adomasdauda.client.listeners;

import io.github.adomasdauda.client.AutoClipClient;
import io.github.adomasdauda.client.ClipHelper;
import io.github.adomasdauda.client.constants.ClipReason;
import io.github.adomasdauda.client.data.ConfigurationData;
import io.github.adomasdauda.client.interfaces.EntityKilledByEntityCallback;
import io.github.adomasdauda.client.managers.PlayerComboManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

import java.util.Objects;

public class KillListener implements EntityKilledByEntityCallback {

    @Override
    public ActionResult interact(LivingEntity entity, Entity killer) {

        if (!ConfigurationData.enableKillClip) {
            return ActionResult.PASS;
        }

        // check if both are players
        if (!(killer instanceof PlayerEntity killerPlayerEntity)) {
            return ActionResult.PASS;
        }

        if (MinecraftClient.getInstance().player == null) {
            return ActionResult.PASS;
        }

        if (!Objects.equals(killerPlayerEntity.getName().getString(), MinecraftClient.getInstance().player.getName().getString())) {
            return ActionResult.PASS;
        }

        if (ConfigurationData.enableComboClip) {
            if (ConfigurationData.comboLengthForClip <= PlayerComboManager.getInstance().getComboLength(entity)) {
                AutoClipClient.getLOGGER().info("Player killed another player: " + entity.getName().getString());
                ClipHelper.pressClipButton(ClipReason.ENEMY_KILLED);
            } else {
                AutoClipClient.getLOGGER().warn("The combo was not long enough to clip length: " + PlayerComboManager.getInstance().getComboLength(entity) + " required: " + ConfigurationData.comboLengthForClip);
            }
            return ActionResult.PASS;
        }

        AutoClipClient.getLOGGER().info("Player killed another player: " + entity.getName().getString());
        ClipHelper.pressClipButton(ClipReason.ENEMY_KILLED);
        return ActionResult.PASS;
    }
}
