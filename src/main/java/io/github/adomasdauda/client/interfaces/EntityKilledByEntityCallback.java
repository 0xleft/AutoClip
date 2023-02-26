package io.github.adomasdauda.client.interfaces;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface EntityKilledByEntityCallback {

    Event<EntityKilledByEntityCallback> EVENT = EventFactory.createArrayBacked(EntityKilledByEntityCallback.class,
            (listeners) -> (entity, killer) -> {
                for (EntityKilledByEntityCallback listener : listeners) {
                    ActionResult result = listener.interact(entity, killer);

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult interact(LivingEntity entity, Entity killer);
}