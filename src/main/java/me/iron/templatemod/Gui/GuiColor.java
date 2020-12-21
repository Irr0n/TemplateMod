package me.iron.templatemod.Gui;

import me.iron.templatemod.Gui.items.GuiCommons;
import me.iron.templatemod.TemplateMod;
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

    public void initGui() {

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

        TemplateMod.renderDisplayItem();

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
    }

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
