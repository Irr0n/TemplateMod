package me.iron.templatemod;

import me.iron.templatemod.Hud.HudRenderer;
import me.iron.templatemod.commands.TemplateModCommand;
import me.iron.templatemod.config.Config;
import me.iron.templatemod.key_input.KeyBinds;
import me.iron.templatemod.key_input.KeyInputHandler;


import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;

@Mod(
        modid = Manager.MOD_ID,
        version = Manager.VERSION,
        name = Manager.MOD_NAME,
        acceptedMinecraftVersions = "[1.8.9]",
        clientSideOnly = true
)
public class Manager {

    //todo: push info to gradle
    public static final String MOD_ID = "template_mod";
    public static final String VERSION = "0.1";
    public static final String MOD_NAME = "Template Mod";


    Config config = new Config();



    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        config.initConfig(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        MinecraftForge.EVENT_BUS.register(this);

        loadCommands();

        loadKeyBinds();

        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());

        MinecraftForge.EVENT_BUS.register(new HudRenderer());

    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {}

    private void loadCommands() {
        ClientCommandHandler.instance.registerCommand(new TemplateModCommand());
    }

    private void loadKeyBinds() {

        //custom register per KeyBinding
        new KeyBinds().setKeyGUI("SEMICOLON");

        KeyBinds.keyBindGUI = new KeyBinding(
                (EnumChatFormatting.GRAY + "Open GUI"),
                Keyboard.getKeyIndex(new KeyBinds().getKeyGUI()),
                (EnumChatFormatting.BLUE + "Template Mod")
        );


        //register all KeyBinding objects
        Class keyBindsClass = KeyBinds.class;

        for (Field field : keyBindsClass.getDeclaredFields()) {
            try {
                KeyBinding keyBind = (KeyBinding) field.get(keyBindsClass);
                //unnecessary instance check due to all castings that pass IllegalArgumentException will be instance of KeyBinding?
                if (field.get(keyBind) instanceof KeyBinding) {
                    ClientRegistry.registerKeyBinding(keyBind);
                }
            } catch (IllegalArgumentException | IllegalAccessException ignore) {}
        }
    }

}
