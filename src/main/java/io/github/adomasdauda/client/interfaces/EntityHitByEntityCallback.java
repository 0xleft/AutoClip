package io.github.adomasdauda.client.interfaces;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;

public interface EntityHitByEntityCallback {

    Event<EntityHitByEntityCallback> EVENT = EventFactory.createArrayBacked(EntityHitByEntityCallback.class,
            (listeners) -> (entity, killer) -> {
                for (EntityHitByEntityCallback listener : listeners) {
                    ActionResult result = listener.onHit(entity, killer);

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult onHit(LivingEntity entity, Entity killer);
}