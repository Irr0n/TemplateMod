package me.iron.templatemod;

import me.iron.templatemod.commands.TemplateModCommand;
import me.iron.templatemod.config.Config;


import me.iron.templatemod.config.Settings;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = TemplateModManager.MOD_ID, version = TemplateModManager.VERSION, name = TemplateModManager.MOD_NAME, acceptedMinecraftVersions = "[1.8.9]", clientSideOnly = true)
public class TemplateModManager {

    public static final String MOD_ID = "template_mod";
    public static final String VERSION = "0.1";
    public static final String MOD_NAME = "Template Mod";

    Config config = new Config();

    File file;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        config.initConfig(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        MinecraftForge.EVENT_BUS.register(this);

        loadCommands();


    }

    private void loadCommands() {
        ClientCommandHandler.instance.registerCommand(new TemplateModCommand());
    }

}
