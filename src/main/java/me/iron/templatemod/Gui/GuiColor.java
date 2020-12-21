package me.iron.templatemod.Gui;

import me.iron.templatemod.Gui.items.GuiCommons;
import me.iron.templatemod.Hud.HudRenderer;
import me.iron.templatemod.TemplateMod;
import me.iron.templatemod.config.Config;
import me.iron.templatemod.config.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiSlider;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class GuiColor extends GuiScreen {

    GuiCommons guiCommons = new GuiCommons();

    private boolean isMouseDown;

    private int prevMouseX;

    private int prevMouseY;


    private GuiSlider sliderRed;

    private GuiSlider sliderGreen;

    private GuiSlider sliderBlue;

    private GuiButton buttonBack;

    @Override
    public void initGui() {
        super.initGui();

        this.buttonList.add(
                this.sliderRed = new GuiSlider(
                        1,
                        this.width / 2 - 65,
                        this.height / 2 - 24,
                        130,
                        20,
                        "Red: ",
                        "", 0.0D,
                        1.0D,
                        Settings.displayItem.red,
                        true,
                        true
                )
        );

        this.buttonList.add(
                this.sliderGreen = new GuiSlider(
                        2,
                        this.width / 2 - 65,
                        this.height / 2,
                        130,
                        20,
                        "Green: ",
                        "",
                        0.0D,
                        1.0D,
                        Settings.displayItem.green,
                        true,
                        true
                )
        );

        this.buttonList.add(
                this.sliderBlue = new GuiSlider(
                        3,
                        this.width / 2 - 65,
                        this.height / 2 + 24,
                        130,
                        20,
                        "Blue: ",
                        "",
                        0.0D,
                        1.0D,
                        Settings.displayItem.blue,
                        true,
                        true));

        this.buttonList.add(
                this.buttonBack = new GuiButton(
                        5,
                        this.width / 2 - 65,
                        this.height - 50,
                        130,
                        20,
                        "Back"));
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        drawDefaultBackground();

        GL11.glPushMatrix();

        GL11.glScalef(1.2F, 1.2F, 1.2F);

        drawCenteredString(
                this.fontRendererObj,
                "Template Mod",
                Math.round(this.width / 1.2F / 2.0F),
                Math.round(this.height / 1.2F / 2.0F) - 60,
                -1
        );

        GL11.glPopMatrix();

        HudRenderer.renderDisplayItem();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                updateSliders();
                break;
            case 5:
                Minecraft.getMinecraft().displayGuiScreen(new GuiMain());
                break;
        }

        Config.saveConfig();
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

            updateSliders();

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

    /*
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

        guiCommons.isMouseDown = this.isMouseDown;
        guiCommons.prevMouseX = this.prevMouseX;
        guiCommons.prevMouseY = this.prevMouseY;

        guiCommons.mouseClicked(mouseX, mouseY, mouseButton);

        this.updateInteractionValues();
    }

    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        guiCommons.isMouseDown = this.isMouseDown;
        guiCommons.prevMouseX = this.prevMouseX;
        guiCommons.prevMouseY = this.prevMouseY;

        guiCommons.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);

        //custom action per GuiScreen
        this.updateSliders();

        this.updateInteractionValues();
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        guiCommons.isMouseDown = this.isMouseDown;
        guiCommons.mouseReleased(mouseX, mouseY, state);
        this.updateInteractionValues();
    }

    public void onGuiClosed() {
        guiCommons.onGuiClosed();
    }

     */

    public void updateInteractionValues() {
        this.isMouseDown = guiCommons.getIsMouseDown();
        this.prevMouseX = guiCommons.getPrevMouseX();
        this.prevMouseY = guiCommons.getPrevMouseY();
    }

    public void updateSliders() {
        Settings.displayItem.red = (float)this.sliderRed.getValue();
        Settings.displayItem.green = (float)this.sliderGreen.getValue();
        Settings.displayItem.blue = (float)this.sliderBlue.getValue();
    }

}
