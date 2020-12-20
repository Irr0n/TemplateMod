package me.iron.templatemod.config;

import com.ibm.icu.text.ArabicShaping;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

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


    public ArrayList initConfigValue(String category, String key, String type) {
        ArrayList valueArray = new ArrayList<Object>();

        valueArray.add(null);
        valueArray.add(null);
        valueArray.add(null);
        valueArray.add(null);

        if (type == "int") {
            valueArray.set(0, config.get(category, key, 0).getInt());
        } else if (type == "double") {
            valueArray.set(1, config.get(category, key, 0.0D).getDouble());
        } else if (type == "String") {
            valueArray.set(1, config.get(category, key, 0.0D).getDouble());
        } else if (type == "boolean") {
            valueArray.set(3, config.get(category, key, false).getBoolean());
        }

        return valueArray;

    }

    public void initConfigSettingsFields() {

        Class[] classes = this.settingsClass.getClasses();

        Field[] mainFields = this.settingsClass.getDeclaredFields();

        for (int i = 0; i < mainFields.length; i++) {

            String type = mainFields[i].getGenericType().getTypeName();
            String name = mainFields[i].getName();

            //4 value return types
            for (int j = 0; j < 4; j++) {

                //use "general" for no category
                if (initConfigValue("general", name, type).get(j) != null) {

                    try {
                        //use "general" for no category
                        mainFields[i].set(settingsClass, initConfigValue("general", name, type).get(j));
                    } catch (IllegalAccessException ignore) {}
                }
            }
        }

        for (Class aClass : classes) {
            Field[] subFields = aClass.getDeclaredFields();
            for (int i = 0; i < subFields.length; i++) {
                String fieldType = subFields[i].getGenericType().getTypeName();
                String fieldName = subFields[i].getName();
                String className = aClass.getSimpleName();

                //4 value return types
                for (int j = 0; j < 4; j++) {

                    if (initConfigValue(className, fieldName, fieldType).get(j) != null) {

                        try {
                            subFields[i].set(aClass, initConfigValue(className, fieldName, fieldType).get(j));
                        } catch (IllegalAccessException ignore) {}

                    }
                }
            }
        }

    }

    public static void initConfig(File file) {
        (config = new Configuration(file, true)).load();
        new Config().initConfigSettingsFields();
        config.save();
    }

    public static void saveConfig() {
        new Config().saveConfigSettingsFields();
        config.save();
    }

}
