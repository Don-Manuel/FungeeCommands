package org.openredstone.handlers;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import org.openredstone.FungeeCommandsManager;
import org.openredstone.utils.FileArrayProvider;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;

public class DerpHandler  {

    private static final File derpsFile =  new File(FungeeCommandsManager.pluginFolder + File.separator + "Derps.txt");
    private static String[] derps;
    private static Random rand = new Random();

    public static void loadDerps(){

        if(!(derpsFile.exists())) {
            FungeeCommandsManager.logger.log(Level.WARNING, "No Derps.txt Detected! This is an ERROR on your part!");
        }else{
            try {
                derps = FileArrayProvider.readLines(derpsFile);
            } catch (IOException e) {
                FungeeCommandsManager.logger.log(Level.WARNING, "Error reading Derps.txt", e);
            }
        }

    }

    public static void sendDerp(CommandSender sender, String[] args){
        String derpPrefix = ChatColor.DARK_GREEN + " * " + ChatColor.WHITE + sender.getName() + ChatColor.DARK_BLUE + " DERP!  " + ChatColor.LIGHT_PURPLE;
        try {
            sender.sendMessage(new TextComponent(derpPrefix + derps[Integer.parseInt(args[0])]));
        } catch(Exception e) {
            sender.sendMessage(new TextComponent(derpPrefix + derps[rand.nextInt(derps.length-1) + 1]));
        }
    }

    public static void getDerpList(CommandSender sender){
        for(int temp = 0; temp<=derps.length-1;temp++){
            sender.sendMessage(new TextComponent(temp + ". " + derps[temp]));
        }
    }

}