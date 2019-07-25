package org.openredstone.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.openredstone.FungeeCommandsManager;
import org.openredstone.messages.ActionMessage;
import org.openredstone.messaging.MessageProxyDispatcher;
import org.openredstone.types.Action;

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
        super("foodfight", FungeeCommandsManager.permissionFor("foodfight"));
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        ProxiedPlayer victim;

        try{
            victim = FungeeCommandsManager.getPlayer(strings[0]);
        }catch(Exception e){
            commandSender.sendMessage(new ComponentBuilder("No such player.").color(ChatColor.RED).create());
            return;
        }

        String victimName = victim.getDisplayName();
        String food;

        if ((strings.length > 1) && (isValidFood(strings[1]))) {
            food = strings[1];
        } else {
            food = getRandomFood();
        }

        ComponentBuilder foodComponent = new ComponentBuilder(((ProxiedPlayer) commandSender).getDisplayName()).color(ChatColor.DARK_PURPLE)
                .append(" threw a" + (getPrettyPrint(food).matches("^[AEIOU].*") ? "n " : " ")).color(ChatColor.YELLOW)
                .append(getPrettyPrint(food)).color(ChatColor.GOLD)
                .append(" at ").color(ChatColor.RED)
                .append(victimName).color(ChatColor.DARK_PURPLE)
                .append(".").color(ChatColor.YELLOW);

        String[] itemValues = {food};
        ActionMessage itemActionMessage = new ActionMessage(Action.ADD_ITEM_TO_INVENTORY, victim.getUniqueId(), itemValues);
        MessageProxyDispatcher.sendMessage(
                FungeeCommandsManager.proxy,
                FungeeCommandsManager.channel,
                FungeeCommandsManager.subChannel,
                (ProxiedPlayer) commandSender,
                itemActionMessage);

        if (rand.nextInt(5) == 0) {
            foodComponent.append(" Headshot!").color(ChatColor.DARK_RED);
            String[] blindnessValues = {"BLINDNESS", "40"};
            String[] confusionValues = {"CONFUSION", "40"};
            ActionMessage blindnessActionMessage = new ActionMessage(Action.ADD_POTION_EFFECT, victim.getUniqueId(), blindnessValues);
            ActionMessage confusionActionMessage = new ActionMessage(Action.ADD_POTION_EFFECT, victim.getUniqueId(), confusionValues);
            MessageProxyDispatcher.sendMessage(
                    FungeeCommandsManager.proxy,
                    FungeeCommandsManager.channel,
                    FungeeCommandsManager.subChannel,
                    (ProxiedPlayer) commandSender,
                    blindnessActionMessage);
            MessageProxyDispatcher.sendMessage(
                    FungeeCommandsManager.proxy,
                    FungeeCommandsManager.channel,
                    FungeeCommandsManager.subChannel,
                    (ProxiedPlayer) commandSender,
                    confusionActionMessage);

        }

        FungeeCommandsManager.plugin.getProxy().broadcast(foodComponent.create());

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
