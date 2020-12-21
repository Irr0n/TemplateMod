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

    public static boolean isEnabled = true;


    //make a DisplayItem class? --conflict with config processes
    //--conflict with config processes
    /*
    add second class check to config
    and iterate through second subclass methods with category title as primary subsclass
     */
    public static class displayItem {

        public static double scale = 1.0D;

        public static int posX = 0;

        public static int posY = 0;

        public static boolean showBackground = false;

        public static double red = 1.0D;
        public static double green = 1.0D;
        public static double blue = 1.0D;

    }

}
