package org.openredstone.handlers;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import org.openredstone.FungeeCommandsManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public class DerpHandler  {

    private static final File derpsFile =  new File(FungeeCommandsManager.pluginFolder, "Derps.txt");
    private static List<String> derps;
    private static Random rand = new Random();

    public static void loadDerps(){
        if (!derpsFile.exists()) {
            FungeeCommandsManager.logger.log(Level.WARNING, "No Derps.txt Detected! This is an ERROR on your part!");
            return;
        }
        try {
            derps = Files.readAllLines(derpsFile.toPath());
        } catch (IOException e) {
            FungeeCommandsManager.logger.log(Level.WARNING, "Error reading Derps.txt", e);
        }

    }

    public static void sendDerp(CommandSender sender, String[] args){
        String derpPrefix = ChatColor.DARK_GREEN + " * " + ChatColor.WHITE + sender.getName() + ChatColor.DARK_BLUE + " DERP!  " + ChatColor.LIGHT_PURPLE;
        try {
            sender.sendMessage(new TextComponent(derpPrefix + derps.get(Integer.parseInt(args[0]))));
        } catch(Exception e) {
            sender.sendMessage(new TextComponent(derpPrefix + derps.get(rand.nextInt(derps.size() - 1) + 1)));
        }
    }

    public static void getDerpList(CommandSender sender){
        // TODO: figure out a java way of doing this
        int index = 0;
        for (String derp : derps) {
            sender.sendMessage(new TextComponent(index + ". " + derp));
            ++index;
        }
    }
}