package io.github.adomasdauda.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ClipHelper {

    public static void pressClipButton() {

        if (MinecraftClient.getInstance().player == null) {
            AutoClipClient.getLOGGER().warn("Failed to send message to player. Player is null.");
            return;
        }

        AutoClipClient.getLOGGER().info("Clipped!");
        MinecraftClient.getInstance().player.sendMessage(Text.literal("Clipped!").setStyle(Style.EMPTY.withColor(255000)), false);

        try {
            AutoClipClient.getLOGGER().info(KeyEvent.getKeyText(AutoClipClient.getClipKey().getDefaultKey().getCode()));
            new Robot().keyPress(KeyEvent.VK_F9);
            new Robot().keyRelease(KeyEvent.VK_F9);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

}
