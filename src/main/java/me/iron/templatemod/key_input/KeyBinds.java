package me.iron.templatemod.key_input;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.settings.KeyBinding;

public class KeyBinds {

    //key binding Strings
    public String keyGUI;


    //key binding KeyBind objects
    public static KeyBinding keyBindGUI;


    //add reflection to to include field name as param?
    public String getKeyGUI() {
        return this.keyGUI;
    }

    public void setKeyGUI(String keyGUI) {
        this.keyGUI = keyGUI;
    }

}
