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
            String value = "";

            System.out.println("field " + i);
            String type = mainFields[i].getGenericType().getTypeName();
            String name = mainFields[i].getName();
            System.out.println("field name " + name);
            System.out.println("field type " + type);
            for (int j = 0; j < 4; j++) {
                if (initConfigValue("general", name, type).get(j) != null) {
                    System.out.println("-start-");
                    System.out.println("type input " + type);
                    System.out.println("name input " + name);
                    System.out.println("config value " + initConfigValue("general", name, type).get(j));
                    System.out.println("-end-");
                    try {
                        System.out.println("prior int value " + Settings.intValue);
                        System.out.println("setting value");
                        mainFields[i].set(settingsClass, initConfigValue("general", name, type).get(j));
                        System.out.println("Int Value (should only change when after integer is scanned) " + Settings.intValue);
                    } catch (IllegalAccessException ignore) {

                    }

                }
            }

            //use "general" for no category
            //mainField.set(settingsClass, ());
        }

        for (Class aClass : classes) {
            Field[] subFields = aClass.getDeclaredFields();
            for (Field subField : subFields) {

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
