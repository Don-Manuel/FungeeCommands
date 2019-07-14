package org.openredstone.handlers;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.openredstone.FungeeCommandsManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
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

    public static TextComponent getDerp(CommandSender sender, String[] args){
        // TODO: implement this ComponentBuilder magic everywhere
        ComponentBuilder derpBuilder = new ComponentBuilder(" * ").color(ChatColor.GREEN)
                .append(sender.getName()).color(ChatColor.WHITE)
                .append(" DERP! ").color(ChatColor.DARK_BLUE);

        try {
            derpBuilder.append(derps.get(Integer.parseInt(args[0])));
        } catch(Exception e) {
            derpBuilder.append(derps.get(rand.nextInt(derps.size() - 1) + 1));
        }

        derpBuilder.color(ChatColor.LIGHT_PURPLE);

        return new TextComponent(derpBuilder.create());
    }

    public static List<TextComponent> getDerpList(CommandSender sender){
        // TODO: figure out a java way of doing this
        // Is this java enough?
        List<TextComponent> derpsComponents = new ArrayList<>();
        for (String derp : derps) {
            derpsComponents.add(new TextComponent(derps.indexOf(derp) + ". " + derp));
        }
        return derpsComponents;
    }
}