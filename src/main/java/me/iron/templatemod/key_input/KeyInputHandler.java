package me.iron.templatemod.key_input;

import me.iron.templatemod.Gui.GuiMain;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;


public class KeyInputHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {

        //custom action per KeyBinding
        if (KeyBinds.keyBindGUI.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiMain());
        }

    }

}
