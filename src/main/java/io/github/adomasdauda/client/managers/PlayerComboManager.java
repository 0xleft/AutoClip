package io.github.adomasdauda.client.managers;

import io.github.adomasdauda.client.objects.Combo;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlayerComboManager implements AttackEntityCallback {

    private static PlayerComboManager instance = new PlayerComboManager();

    public static PlayerComboManager getInstance() {
        if (instance == null) {
            instance = new PlayerComboManager();
        }

        return instance;
    }

    private final List<Combo> currentCombos = new ArrayList<>();

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult hitResult) {

        if (!(entity instanceof LivingEntity livingEntity)) {
            return ActionResult.PASS;
        }

        if (!(livingEntity instanceof PlayerEntity)) {
            return ActionResult.PASS;
        }

        if (doesPlayerHaveMultipleCombos(player)) {
            removeCombosForPlayer(player);
            return ActionResult.PASS;
        }

        Combo playerCombo = getPlayerCombo(player, entity);

        if (playerCombo == null) {
            currentCombos.add(new Combo(player, livingEntity));
        } else {
            playerCombo.setCombo(playerCombo.getCombo() + 1);
        }

        return ActionResult.PASS;
    }

    private void removeCombosForPlayer(@NotNull PlayerEntity player) {
        for (Combo combo : currentCombos) {
            if (combo.getPlayer().getName().equals(player.getName())) {
                currentCombos.remove(combo);
            }
        }
    }

    private boolean doesPlayerHaveMultipleCombos(@NotNull PlayerEntity player) {
        int count = 0;

        for (Combo combo : currentCombos) {
            if (combo.getPlayer().getName().equals(player.getName())) {
                count++;
            }
        }

        return count > 1;
    }

    public @Nullable Combo getPlayerCombo(@NotNull PlayerEntity player, @NotNull Entity entity) {
        for (Combo combo : currentCombos) {
            if (combo.getPlayer().getName().equals(player.getName())) {
                if (combo.getEntity().equals(entity)) {
                    return combo;
                }
            }
        }
        return null;
    }

    public List<Combo> getCurrentCombos() {
        return currentCombos;
    }
}
