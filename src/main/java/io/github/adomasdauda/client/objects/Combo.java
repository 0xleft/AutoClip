package io.github.adomasdauda.client.objects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class Combo {

    private final PlayerEntity player;
    private final LivingEntity entity;
    private int combo = 0;

    public Combo(PlayerEntity player, LivingEntity entity) {
        this.player = player;
        this.entity = entity;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public int getCombo() {
        return combo;
    }

    public void setCombo(int combo) {
        this.combo = combo;
    }
}