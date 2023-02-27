package io.github.adomasdauda.client.listeners;

import io.github.adomasdauda.client.AutoClipClient;
import io.github.adomasdauda.client.ClipHelper;
import io.github.adomasdauda.client.constants.ClipReason;
import io.github.adomasdauda.client.constants.NotPlacableBlockKeys;
import io.github.adomasdauda.client.data.ConfigurationData;
import io.github.adomasdauda.client.interfaces.EntityHitByEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public class BlockClutchListener implements UseBlockCallback, EntityHitByEntityCallback {

    private static double blockExtention = 0;
    private static boolean isClutching = false;

    private static Thread clutchThread = null;

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {

        if (!ConfigurationData.enableBlockClutchClip) {
            return ActionResult.PASS;
        }

        if (MinecraftClient.getInstance().player == null) {
            return ActionResult.PASS;
        }

        if (!Objects.equals(player.getName().getString(), MinecraftClient.getInstance().player.getName().getString())) {
            return ActionResult.PASS;
        }

        if (NotPlacableBlockKeys.contains(player.getInventory().getMainHandStack().getTranslationKey())) {
            AutoClipClient.getLOGGER().info("The player clicked on block and had item in hand that is not placable");
            return ActionResult.PASS;
        }

        if (isClutching) {
            for (int i = 0; i < 10; i++) {
                if (!world.getBlockState(new BlockPos(player.getX(), player.getY() - i, player.getZ())).isAir()) {
                    return ActionResult.PASS;
                }
            }
            System.out.println("added to clutch extension");

            refreshClutchThread();
            blockExtention++;
        }

        return ActionResult.PASS;
    }

    @Override
    public ActionResult onHit(LivingEntity entity, Entity killer) {

        if (killer == null) {
            return ActionResult.PASS;
        }

        if (!ConfigurationData.debug) {
            if (!(killer instanceof PlayerEntity)) {
                return ActionResult.PASS;
            }
        }

        if (!(entity instanceof PlayerEntity playerEntity) ) {
            return ActionResult.PASS;
        }

        if (MinecraftClient.getInstance().player == null) {
            return ActionResult.PASS;
        }

        if (!Objects.equals(playerEntity.getName().getString(), MinecraftClient.getInstance().player.getName().getString())) {
            return ActionResult.PASS;
        }

        System.out.println("Started clutching");
        isClutching = true;
        refreshClutchThread();
        return ActionResult.PASS;
    }

    private static void refreshClutchThread() {

        if (clutchThread != null) {
            clutchThread.interrupt();
        }

        clutchThread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
            if (isClutching) {
                if (blockExtention > 0) {
                    ClipHelper.pressClipButton(ClipReason.BLOCK_CLUTCH);
                    blockExtention = 0;
                }
            }
            isClutching = false;
            System.out.println("Stopped clutching");
        });
        clutchThread.start();
    }



}
