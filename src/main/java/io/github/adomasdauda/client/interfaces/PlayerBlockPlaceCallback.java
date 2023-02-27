package io.github.adomasdauda.client.interfaces;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public interface PlayerBlockPlaceCallback {

    Event<PlayerBlockPlaceCallback> EVENT = EventFactory.createArrayBacked(PlayerBlockPlaceCallback.class,
            (listeners) -> (entity, block) -> {
                for (PlayerBlockPlaceCallback listener : listeners) {
                    ActionResult result = listener.onPlace(entity, block);

                    if(result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult onPlace(LivingEntity entity, ItemStack block);
}