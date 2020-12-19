package me.iron.templatemod.config;

import me.iron.templatemod.config.Settings;


import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Config {

    public Class settingsClass = Settings.class;

    public static Configuration config;


    Settings settings = new Settings();

    public void saveConfigValue(String category, String key, String value) {
        config.get(category, key, 0).set(String.valueOf(value));
    }
    public void saveConfigValue(String category, String key, int value) {
        config.get(category, key, 0).set(value);
    }
    public void saveConfigValue(String category, String key, double value) {
        config.get(category, key, 0).set(value);
    }
    public void saveConfigValue(String category, String key, boolean value) {
        config.get(category, key, 0).set(value);
    }


    public void saveConfigSettingsFields() {

        Class[] classes = this.settingsClass.getClasses();

        Field[] mainFields = this.settingsClass.getDeclaredFields();

        for (int i = 0; i < mainFields.length; i++) {
            String value = "";

            try {
                value = String.valueOf(mainFields[i].get(this.settingsClass));
            } catch (IllegalAccessException e) {

            }
            //use "general" for no category
            saveConfigValue("general", mainFields[i].getName(), value);
        }

        for (int i = 0; i < classes.length; i++) {
            Field[] subFields = classes[i].getDeclaredFields();
            for (int j = 0; j < subFields.length; j++) {
                String value = "";
                try {
                    value = String.valueOf(subFields[i].get(classes[i]));
                } catch (IllegalAccessException e) {

                }
                saveConfigValue(classes[i].getSimpleName(), subFields[i].getName(), value));
            }
        }

    }


    public static void initConfig(File file) {
        (config = new Configuration(file, true)).load();
        Settings.booleanValue = config.get("general", "booleanValue", true).getBoolean();
        config.save();
    }

    public static void saveConfig() {
        new Config().saveConfigSettingsFields();
        config.save();
    }

}
