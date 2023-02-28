package io.github.adomasdauda.client;

import com.ibm.icu.impl.ICUService;
import com.ibm.icu.impl.StaticUnicodeSets;
import com.sun.jna.platform.win32.WinUser;
import io.github.adomasdauda.client.constants.ClipReason;
import io.github.adomasdauda.client.mixins.ScreenMixin;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.search.SearchManager;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;

public class ClipHelper {

    private static double lastClipTime = 0;
    private static KeyBinding clipKey;

    public static void init() {
        lastClipTime = System.currentTimeMillis();

        clipKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Clip Key",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F9,
                "AutoClip"
        ));
    }

    public static void pressClipButton(@NotNull ClipReason clipReason) {

        if (System.currentTimeMillis() - lastClipTime < 1000) {
            AutoClipClient.getLOGGER().warn("Clipping too fast");
            return;
        }

        if (MinecraftClient.getInstance().player == null) {
            AutoClipClient.getLOGGER().warn("Failed to send message to player. Player is null.");
            return;
        }

        // info
        AutoClipClient.getLOGGER().info("Clipped! " + clipReason);
        MinecraftClient.getInstance().player.sendMessage(Text.literal("Clipped! " + clipReason).setStyle(Style.EMPTY.withColor(230000)), false);

        // so you dont clip too often
        lastClipTime = System.currentTimeMillis();

        // press the button
        try {
            System.out.println(clipKey.getDefaultKey().getTranslationKey());
            new Robot().keyPress(getKeyIndex(clipKey.getDefaultKey().getTranslationKey().split("\\.")[2].toUpperCase()));
            new Robot().keyRelease(getKeyIndex(clipKey.getDefaultKey().getTranslationKey().split("\\.")[2].toUpperCase()));
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static KeyBinding getClipKey() {
        return clipKey;
    }

    public static int getKeyIndex(String keyName) {
        // loop over all keys in java.awt.event.KeyEvent and find the key matching the name
        for (int i = 0; i < KeyEvent.class.getFields().length; i++) {
            if (KeyEvent.class.getFields()[i].getName().contains(keyName)) {
                try {
                    return KeyEvent.class.getFields()[i].getInt(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return 0;
    }

    public static double getLastClipTime() {
        return lastClipTime;
    }

    public static boolean isRecentlyClipped() {
        return System.currentTimeMillis() - lastClipTime < 1800;
    }
}
