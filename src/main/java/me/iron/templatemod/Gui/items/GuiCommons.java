package me.iron.templatemod.Gui.items;

import me.iron.templatemod.TemplateMod;
import me.iron.templatemod.config.Config;
import me.iron.templatemod.config.Settings;
import me.iron.templatemod.key_input.KeyBinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.opengl.GL11;
import scala.reflect.internal.Trees;

import java.io.IOException;
import java.lang.reflect.Field;

/*
Common Methods and Fields for GuiScreen Classes
 */
public class GuiCommons extends GuiScreen {

    public boolean isMouseDown;

    public int prevMouseX;

    public int prevMouseY;

    public int getPrevMouseX() {
        return this.prevMouseX;
    }

    public int getPrevMouseY() {
        return this.prevMouseY;
    }

    public boolean getIsMouseDown() {
        return this.isMouseDown;
    }

    public String moduleSuffix(boolean state) {
        if (state) {
            return EnumChatFormatting.GREEN + "ON";
        }
        return EnumChatFormatting.RED + "OFF";
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

        super.mouseClicked(mouseX, mouseY, mouseButton);

        String displayItem = new TemplateMod().getDisplayItem();

        //non modified displayItem attributes
        final int stringWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(displayItem);
        final float scale = (float)Settings.displayItem.scale;

        GL11.glPushMatrix();

        GL11.glScalef(
                (float)Settings.displayItem.scale,
                (float)Settings.displayItem.scale,
                1.0F
        );

        if (
                Math.round(Settings.displayItem.posX / scale) <= Math.round(mouseX / scale) &&
                        Math.round(mouseX / scale) <= Math.round(Settings.displayItem.posX / scale) + stringWidth + 8 &&
                        Math.round(Settings.displayItem.posY / scale) <= Math.round(mouseY / scale) && Math.round(mouseY / scale) <= Math.round(Settings.displayItem.posY / scale) + 12
        ) {
            this.isMouseDown = true;
            this.prevMouseX = mouseX;
            this.prevMouseY = mouseY;
        }

        GL11.glPopMatrix();
    }

    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (this.isMouseDown) {
            GL11.glPushMatrix();

            GL11.glScalef(
                    (float) Settings.displayItem.scale,
                    (float) Settings.displayItem.scale,
                    1.0F
            );

            Settings.displayItem.posX += mouseX - this.prevMouseX;
            Settings.displayItem.posY += mouseY - this.prevMouseY;

            GL11.glPopMatrix();

            updatePosition();

            this.prevMouseX = mouseX;
            this.prevMouseY = mouseY;
        }

    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.isMouseDown = false;
    }

    public void updatePosition() {

        //non modified displayItem attributes
        final int stringWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(new TemplateMod().getDisplayItem());
        final float scale = (float)Settings.displayItem.scale;

        if (Settings.displayItem.posX < 0) {
            Settings.displayItem.posX = 0;

            //side margin: 8 provides padding for scale 1 text, ""
        } else if (Settings.displayItem.posX + (int)(stringWidth * scale) + (int)(8.0F * scale) > this.width) {
            Settings.displayItem.posX = this.width - (int)(7.0F * scale) - (int)(stringWidth * scale);
        }

        if (Settings.displayItem.posY < 0) {
            Settings.displayItem.posY = 0;


            //bottom margin: 12 provides padding for scale 1 text.
        } else if (Settings.displayItem.posY + (int)(12.0D * scale) > this.height) {
            Settings.displayItem.posY = this.height - (int)(12.0D * scale);
        }
    }

    public void onGuiClosed() {
        Config.saveConfig();
    }

}

