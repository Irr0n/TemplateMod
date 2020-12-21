package me.iron.templatemod.Gui;

import java.io.IOException;

import me.iron.templatemod.Gui.items.GuiCommons;
import me.iron.templatemod.Hud.HudRenderer;
import me.iron.templatemod.TemplateMod;
import me.iron.templatemod.config.Config;
import me.iron.templatemod.config.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiSlider;
import org.lwjgl.opengl.GL11;

public class GuiMain extends GuiScreen{

    private boolean isMouseDown;

    private int prevMouseX;

    private int prevMouseY;

    GuiCommons guiCommons = new GuiCommons();

    private GuiButton buttonSpeed;

    private GuiButton buttonBackground;

    private GuiButton buttonColor;

    private GuiSlider sliderScale;

    @Override
    public void initGui() {
        super.initGui();

        //buttons spaced by 24

        this.buttonList.add(
                this.buttonSpeed = new GuiButton(
                        0,
                        this.width / 2 - 65,
                        this.height / 2 - 48,
                        130,
                        20,
                        "Show Mod: " + guiCommons.moduleSuffix(Settings.isEnabled)
                )
        );

        this.buttonList.add(
                this.buttonBackground = new GuiButton(
                        1,
                        this.width / 2 - 65,
                        this.height / 2 - 24,
                        130,
                        20,
                        "Background: " + guiCommons.moduleSuffix(Settings.displayItem.showBackground)
                )
        );

        this.buttonList.add(
                this.buttonColor = new GuiButton(
                        2,
                        this.width / 2 - 65,
                        this.height / 2,
                        130,
                        20,
                        "Color"
                )
        );

        this.buttonList.add(
                this.sliderScale = new GuiSlider(
                        3,
                        this.width / 2 - 65,
                        this.height / 2 + 24,
                        129,
                        20,
                        "Scale: ",
                        "",
                        0.5D,
                        3.0D,
                        Settings.displayItem.scale,
                        true,
                        true
                ) {
                    //personal methods
                    public void updateSlider() {
                        //
                        super.updateSlider();
                    }
                }
        );

        this.sliderScale.updateSlider();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        GL11.glPushMatrix();

        GL11.glScalef(1.2F, 1.2F, 1.0F);

        drawCenteredString(
                this.fontRendererObj,
                "Template Mod",
                Math.round(this.width / 1.2F / 2.0F),
                Math.round(this.height / 1.2F / 2.0F) - 60,
                -1);

        GL11.glPopMatrix();

        HudRenderer.renderDisplayItem();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void actionPerformed(GuiButton button) {

        //custom action per GuiScreen
        switch (button.id) {
            case 0:
                Minecraft.getMinecraft().thePlayer.sendChatMessage("Toggle Mod");
                Settings.isEnabled = !Settings.isEnabled;
                this.buttonSpeed.displayString = "Show Mod: " + guiCommons.moduleSuffix(Settings.isEnabled);
                break;
            case 1:
                Settings.displayItem.showBackground = !Settings.displayItem.showBackground;
                this.buttonBackground.displayString = "Render background: " + guiCommons.moduleSuffix(Settings.displayItem.showBackground);
                break;
            case 2:
                Minecraft.getMinecraft().displayGuiScreen(new GuiColor());
                break;
            case 3:
                Settings.displayItem.scale = (float)this.sliderScale.getValue();
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

            Settings.displayItem.scale = (float)this.sliderScale.getValue();

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
        if (this.sliderScale.dragging) {
            Settings.displayItem.scale = (float)this.sliderScale.getValue();

        }

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

    public void updateInteractionValues() {
        this.isMouseDown = guiCommons.getIsMouseDown();
        this.prevMouseX = guiCommons.getPrevMouseX();
        this.prevMouseY = guiCommons.getPrevMouseY();
    }

     */
}
