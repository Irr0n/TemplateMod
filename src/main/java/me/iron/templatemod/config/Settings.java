package me.iron.templatemod.config;

public class Settings {

    /*
    Valid types are:
    - boolean
    - string
    - int
    - double
     */

    public static boolean booleanValue = true;

    public static int intValue = 0;

    public static String stringValue = "text";

    public static float floatValue = 0.1F;

    public static char charValue = 'a';

    public static class SettingsCategory {

        public static int intValue2 = 0;

        public static boolean booleanValue2 = false;

    }

    public static class SettingsCategory2 {

        public static int intValue3 = 0;

        public static boolean booleanValue3 = false;

    }

}