package io.github.adomasdauda.client.managers;

import io.github.adomasdauda.client.AutoClipClient;
import io.github.adomasdauda.client.data.ConfigurationData;
import io.github.adomasdauda.client.interfaces.EntityHitByEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class PlayerComboManager implements EntityHitByEntityCallback {

    private static PlayerComboManager instance = new PlayerComboManager();
    private static final HashMap<Entity, Integer> currentCombos = new HashMap<>();


    public static PlayerComboManager getInstance() {
        if (instance == null) {
            instance = new PlayerComboManager();
        }

        return instance;
    }

    @Override
    public ActionResult onHit(LivingEntity entity, Entity killer) {

        if (!(killer instanceof PlayerEntity killerPlayer)) {
            return ActionResult.PASS;
        }

        if (MinecraftClient.getInstance().player == null) {
            return ActionResult.PASS;
        }

        if (!killerPlayer.getName().equals(MinecraftClient.getInstance().player.getName())) {
            return ActionResult.PASS;
        }

        if (!ConfigurationData.debug) {
            if (!(entity instanceof PlayerEntity)) {
                return ActionResult.PASS;
            }
        }

        int comboLength = getComboLength(entity);

        if (comboLength == 0) {
            currentCombos.clear();
            AutoClipClient.getLOGGER().info("New combo started for player: " + entity.getName().getString());
            currentCombos.put(entity, comboLength + 1);
            return ActionResult.PASS;
        } else {
            AutoClipClient.getLOGGER().info("Combo continued for player: " + entity.getName().getString() + " length: " + (comboLength));
            currentCombos.put(entity, comboLength + 1);
            return ActionResult.PASS;
        }
    }

    public int getComboLength(@NotNull Entity entity) {

        currentCombos.putIfAbsent(entity, 0);

        return currentCombos.get(entity);
    }
}
