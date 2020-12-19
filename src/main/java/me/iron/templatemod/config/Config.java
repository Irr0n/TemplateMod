package me.iron.templatemod.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.lang.reflect.Field;

public class Config {

    public Class settingsClass = Settings.class;

    public static Configuration config;

    public <T> void saveConfigValue(String category, String key, String type, T value) {
        switch (type) {
            case "int": {
                config.get(category, key, 0).set(String.valueOf(value));
            }
            case "double": {
                config.get(category, key, 0.0D).set(String.valueOf(value));
            }
            case "String": {
                config.get(category, key, "").set(String.valueOf(value));
            }
            case "boolean": {
                config.get(category, key, false).set(String.valueOf(value));
            }
            default: {
                //
            }
        }

    }

    public void saveConfigSettingsFields() {

        Class[] classes = this.settingsClass.getClasses();

        Field[] mainFields = this.settingsClass.getDeclaredFields();

        for (Field mainField : mainFields) {
            String value = "";

            try {
                value = String.valueOf(mainField.get(this.settingsClass));
            } catch (IllegalAccessException ignored) {

            }
            //use "general" for no category
            saveConfigValue("general", mainField.getName(), mainField.getType().getSimpleName(), value);
        }

        for (Class aClass : classes) {
            Field[] subFields = aClass.getDeclaredFields();
            for (Field subField : subFields) {
                String value = "";
                try {
                    value = String.valueOf(subField.get(aClass));
                } catch (IllegalAccessException ignored) {

                }
                saveConfigValue(aClass.getSimpleName(), subField.getName(), subField.getType().getSimpleName(), value);
            }
        }

    }

    public static void initConfig(File file) {
        (config = new Configuration(file, true)).load();
        config.save();
    }

    public static void saveConfig() {
        new Config().saveConfigSettingsFields();
        config.save();
    }

}
