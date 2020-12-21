package me.iron.templatemod.Hud;

import me.iron.templatemod.TemplateMod;
import me.iron.templatemod.config.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static me.iron.templatemod.config.Settings.displayItem.showBackground;

public class HudRenderer extends GuiScreen {

    //make generic for any displayItem
    public static void renderDisplayItem() {

        final String displayItem = new TemplateMod().getDisplayItem();
        final int stringWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(displayItem);
        final float scale = (float) Settings.displayItem.scale;
        float red = (float)Settings.displayItem.red;
        float green = (float)Settings.displayItem.green;
        float blue = (float)Settings.displayItem.blue;

        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, 1.0F);

        if (showBackground) {
            Gui.drawRect(
                    Math.round(Settings.displayItem.posX / scale),
                    Math.round(Settings.displayItem.posY / scale),
                    Math.round(Settings.displayItem.posX / scale) + stringWidth + 8,
                    Math.round(Settings.displayItem.posY / scale) + 12,
                    1694498816
            );
        }


        int color = (new Color(red, green, blue, 1.0F)).getRGB();

        (Minecraft.getMinecraft()).fontRendererObj.drawStringWithShadow(
                displayItem,
                (Math.round(Settings.displayItem.posX / scale) + 4),
                (Math.round(Settings.displayItem.posY / scale) + 2),
                color
        );

        GL11.glPopMatrix();

    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event){
        if (Settings.isEnabled) {
            this.renderDisplayItem();
        }
    }

}
