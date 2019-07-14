package org.openredstone.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.openredstone.FungeeCommandsManager;
import org.openredstone.messaging.MessageDispatcher;
import org.openredstone.types.Action;
import org.openredstone.messages.ActionMessage;

import java.util.Random;

public class FoodFight extends Command {

    private Random rand = new Random();

    private String[] foods = {
            "APPLE",
            "BAKED_POTATO",
            "BEEF",
            "BEETROOT",
            "BEETROOT_SOUP",
            "BREAD",
            "CARROT",
            "CHICKEN",
            "CHORUS_FRUIT",
            "COD",
            "COOKED_BEEF",
            "COOKED_CHICKEN",
            "COOKED_COD",
            "COOKED_MUTTON",
            "COOKED_PORKCHOP",
            "COOKED_RABBIT",
            "COOKED_SALMON",
            "COOKIE",
            "DRIED_KELP",
            "ENCHANTED_GOLDEN_APPLE",
            "GOLDEN_APPLE",
            "GOLDEN_CARROT",
            "MELON_SLICE",
            "MUSHROOM_STEW",
            "MUTTON",
            "POISONOUS_POTATO",
            "PORKCHOP",
            "POTATO",
            "PUMPKIN_PIE",
            "RABBIT",
            "RABBIT_STEW",
            "SALMON",
            "TROPICAL_FISH"
    };

    public FoodFight() {
        super("foodfight");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {

        /*if(!commandSender.hasPermission("funcommands" + this.getClass().getSimpleName())){
            commandSender.sendMessage(new TextComponent("You do not have permission to run this command!"));
            return;
        }*/

        ProxiedPlayer victim;

        try{
            victim = FungeeCommandsManager.getPlayer(strings[0]);
        }catch(Exception e){
            commandSender.sendMessage(new TextComponent(ChatColor.RED+"[ERROR] No such player."));
            return;
        }

        String victimName = "";

        try{
            victimName = victim.getName();
        }catch(Exception e){
            commandSender.sendMessage(new TextComponent(ChatColor.RED+"[ERROR] No such player."));
            return;
        }

        String food;

        if ((strings.length > 1) && (isValidFood(strings[1]))) {
            food = strings[1];
        } else {
            food = getRandomFood();
        }

        StringBuilder broadcast = new StringBuilder();

        broadcast.append(
                ChatColor.DARK_PURPLE + ((ProxiedPlayer) commandSender).getDisplayName() +
                ChatColor.YELLOW + " threw a " +
                ChatColor.GOLD + getPrettyPrint(food) +
                ChatColor.RED + " at " +
                ChatColor.DARK_PURPLE + victimName + "."
                );

        String[] itemValues = {food};
        ActionMessage itemActionMessage = new ActionMessage(Action.ADD_ITEM_TO_INVENTORY, victim.getUniqueId(), itemValues);
        MessageDispatcher.sendMessage(
                FungeeCommandsManager.proxy,
                FungeeCommandsManager.channel,
                FungeeCommandsManager.subChannel,
                (ProxiedPlayer) commandSender,
                itemActionMessage);

        if (rand.nextInt(5) == 0) {
            broadcast.append( ChatColor.DARK_RED + " Headshot!" );
            String[] blindnessValues = {"BLINDNESS", "40"};
            String[] confusionValues = {"CONFUSION", "40"};
            ActionMessage blindnessActionMessage = new ActionMessage(Action.ADD_POTION_EFFECT, victim.getUniqueId(), blindnessValues);
            ActionMessage confusionActionMessage = new ActionMessage(Action.ADD_POTION_EFFECT, victim.getUniqueId(), confusionValues);
            MessageDispatcher.sendMessage(
                    FungeeCommandsManager.proxy,
                    FungeeCommandsManager.channel,
                    FungeeCommandsManager.subChannel,
                    (ProxiedPlayer) commandSender,
                    blindnessActionMessage);
            MessageDispatcher.sendMessage(
                    FungeeCommandsManager.proxy,
                    FungeeCommandsManager.channel,
                    FungeeCommandsManager.subChannel,
                    (ProxiedPlayer) commandSender,
                    confusionActionMessage);

        }

        FungeeCommandsManager.plugin.getProxy().broadcast(new TextComponent(broadcast.toString()));

    }

    private boolean isValidFood(String food) {
        for (String availableFood : foods) {
            if (availableFood.equals(food)) {
                return true;
            }
        }
        return false;
    }

    private String getRandomFood() {
        return foods[rand.nextInt(foods.length-1)];
    }

    private String getPrettyPrint(String food) {
        StringBuilder pretty = new StringBuilder();

        Character prevChar = " ".charAt(0);
        for (int i = 0; i < food.length(); i++) {
            Character c = food.charAt(i);
            if (Character.isAlphabetic(c) && prevChar.equals(" ".charAt(0))) {
                pretty.append(c);
                prevChar = c;
            } else if (Character.isAlphabetic(c)) {
                pretty.append(Character.toLowerCase(c));
                prevChar = Character.toLowerCase(c);
            } else {
                pretty.append(" ");
                prevChar = " ".charAt(0);
            }
        }

        return pretty.toString();
    }
}
