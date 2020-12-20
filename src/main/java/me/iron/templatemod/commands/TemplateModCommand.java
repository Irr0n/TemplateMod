package me.iron.templatemod.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.iron.templatemod.config.Config;


import me.iron.templatemod.config.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class TemplateModCommand extends CommandBase {

    Config config = new Config();

    private final List aliases;

    private final String command = "template";

    public TemplateModCommand() {

        this.aliases = new ArrayList();
        this.aliases.add("template1");
        this.aliases.add("template2");
        this.aliases.add("template3");

    }


    public String getCommandName() {
        return this.command;
    }

    public String getCommandUsage(ICommandSender sender) {
        return this.command;
    }

    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    public List getCommandAliases() {
        return this.aliases;
    }

    public void processCommand(ICommandSender sender, String[] args) throws CommandException {

        if (args.length == 0) {
            System.out.println(Settings.intValue);
            System.out.println(Settings.booleanValue);
            System.out.println(Settings.charValue);
            System.out.println(Settings.doubleValue);
            System.out.println(Settings.floatValue);
            System.out.println(Settings.stringValue);
        } else {
            Settings.intValue = 69;
            Settings.doubleValue = 0.69D;
            Settings.stringValue = "nice";
        }

        Config.saveConfig();

    }

}

