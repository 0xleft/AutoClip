package io.github.adomasdauda.client;

import io.github.adomasdauda.client.interfaces.EntityHitByEntityCallback;
import io.github.adomasdauda.client.interfaces.EntityKilledByEntityCallback;
import io.github.adomasdauda.client.interfaces.PlayerBlockPlaceCallback;
import io.github.adomasdauda.client.listeners.BlockClutchListener;
import io.github.adomasdauda.client.listeners.KillListener;
import io.github.adomasdauda.client.managers.PlayerComboManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Environment(EnvType.CLIENT)
public class AutoClipClient implements ClientModInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger("autoclip");
    
    @Override
    public void onInitializeClient() {
        // so we can use awt Robots
        System.setProperty("java.awt.headless", "false");

        ClipHelper.init();

        EntityKilledByEntityCallback.EVENT.register(new KillListener());
        UseBlockCallback.EVENT.register(new BlockClutchListener());
        EntityHitByEntityCallback.EVENT.register(new BlockClutchListener());
        EntityHitByEntityCallback.EVENT.register(PlayerComboManager.getInstance());
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }
}
