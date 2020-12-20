package me.iron.templatemod.config;

public class Settings {

    /*
    Valid types are:
    - boolean
    - string
    - int
    - double

    Only 1 child class to be permitted
    this will be used as a category
     */


    public static boolean booleanValue = true;

    public static int intValue = 0;

    public static double doubleValue = 0.2D;

    public static String stringValue = "text";

    public static float floatValue = 0.1F;

    public static char charValue = 'a';

    public static class SettingsCategory {

        public static int category1IntValue = 0;

        public static boolean category1BooleanValue = false;

    }

    public static class SettingsCategory2 {

        public static int category2IntValue = 0;

        public static boolean category2BooleanValue = false;

        public static String category2StringValue = "text2";

    }

}
