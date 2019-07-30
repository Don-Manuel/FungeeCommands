package org.openredstone.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.openredstone.FungeeCommandsManager;
import org.openredstone.messages.ActionMessage;
import org.openredstone.messaging.MessageProxyDispatcher;
import org.openredstone.types.Action;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.joining;

public class FoodFight extends FunCommand {
    private final Random rand = new Random();
    private final List<String> foods = Arrays.asList(
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
    );

    public FoodFight() {
        super("foodfight");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer victim;

        if ((victim = FungeeCommandsManager.getPlayer(args[0])) == null) {
            sender.sendMessage(new ComponentBuilder("No such player.").color(ChatColor.RED).create());
            return;
        }

        String victimName = victim.getDisplayName();
        String food;

        if ((args.length > 1) && (isValidFood(args[1]))) {
            food = args[1];
        } else {
            food = getRandomFood();
        }

        String senderName = ((ProxiedPlayer) sender).getDisplayName();
        String prettyFood = prettifyFood(food);

        ComponentBuilder foodComponent = new ComponentBuilder(senderName).color(ChatColor.DARK_PURPLE)
                .append(" threw a" + (prettyFood.matches("^[AEIOU].*") ? "n " : " ")).color(ChatColor.YELLOW)
                .append(prettyFood).color(ChatColor.GOLD)
                .append(" at ").color(ChatColor.RED)
                .append(victimName).color(ChatColor.DARK_PURPLE)
                .append(".").color(ChatColor.YELLOW);

        String[] itemValues = {food};
        ActionMessage itemActionMessage = new ActionMessage(Action.ADD_ITEM_TO_INVENTORY, victim.getUniqueId(), itemValues);
        MessageProxyDispatcher.sendMessage(
                FungeeCommandsManager.proxy,
                FungeeCommandsManager.channel,
                FungeeCommandsManager.subChannel,
                (ProxiedPlayer) sender,
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
                    (ProxiedPlayer) sender,
                    blindnessActionMessage);
            MessageProxyDispatcher.sendMessage(
                    FungeeCommandsManager.proxy,
                    FungeeCommandsManager.channel,
                    FungeeCommandsManager.subChannel,
                    (ProxiedPlayer) sender,
                    confusionActionMessage);

        }

        FungeeCommandsManager.plugin.getProxy().broadcast(foodComponent.create());
    }

    private boolean isValidFood(String food) {
        return foods.contains(food);
    }

    private String getRandomFood() {
        return foods.get(rand.nextInt(foods.size()));
    }

    private String prettifyFood(String food) {
        String[] words = food.split("_");
        return Arrays.stream(words)
                .map(FoodFight::capitalizeWord)
                .collect(joining(" "));
    }

    private static String capitalizeWord(String word) {
        return Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
    }
}
