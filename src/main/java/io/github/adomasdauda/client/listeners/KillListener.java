package io.github.adomasdauda.client.listeners;

import io.github.adomasdauda.client.AutoClipClient;
import io.github.adomasdauda.client.ClipHelper;
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

        if (!(killer instanceof LivingEntity killerLivingEntity)) {
            return ActionResult.PASS;
        }

        // check if both of them are players
        if (!(killerLivingEntity instanceof PlayerEntity killerPlayerEntity) || !(entity instanceof PlayerEntity)) {
            return ActionResult.PASS;
        }

        if (MinecraftClient.getInstance().player == null) {
            return ActionResult.PASS;
        }

        if (!Objects.equals(killerPlayerEntity.getName().getString(), MinecraftClient.getInstance().player.getName().getString())) {
            return ActionResult.PASS;
        }

        if (PlayerComboManager.getInstance().getPlayerCombo(killerPlayerEntity, entity) != null); {
            if (PlayerComboManager.getInstance().getPlayerCombo(killerPlayerEntity, entity).getCombo() < ConfigurationData.comboCountForClip) {
                return ActionResult.PASS;
            }
        }

        AutoClipClient.getLOGGER().info("Player killed another player: " + entity.getName().getString());
        ClipHelper.pressClipButton();
        return ActionResult.PASS;
    }
}
