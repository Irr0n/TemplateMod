package me.iron.templatemod;

import me.iron.templatemod.config.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static me.iron.templatemod.config.Settings.displayItem.showBackground;

public class TemplateMod {

    public String displayItem = "owo uwu";

    private Minecraft mc = Minecraft.getMinecraft();

    public void setDisplayItem(String displayItem) {
        this.displayItem = displayItem;
    }

    public String getDisplayItem() {
        return displayItem;
    }
}
