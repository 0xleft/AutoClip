package io.github.adomasdauda.client;

import io.github.adomasdauda.client.interfaces.EntityKilledByEntityCallback;
import io.github.adomasdauda.client.listeners.BlockClutchListener;
import io.github.adomasdauda.client.listeners.KillListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Environment(EnvType.CLIENT)
public class AutoClipClient implements ClientModInitializer {

    private static KeyBinding clipKey;
    private static final Logger LOGGER = LoggerFactory.getLogger("autoclip");
    
    @Override
    public void onInitializeClient() {

        System.setProperty("java.awt.headless", "false");

        clipKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "Clip Key",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F9,
                "AutoClip"
        ));


        EntityKilledByEntityCallback.EVENT.register(new KillListener());
        UseBlockCallback.EVENT.register(new BlockClutchListener());
    }

    public static KeyBinding getClipKey() {
        return clipKey;
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }
}
