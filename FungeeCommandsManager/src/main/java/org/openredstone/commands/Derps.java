package org.openredstone.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import org.openredstone.handlers.DerpHandler;

import java.util.stream.IntStream;

public class Derps extends FunCommand {
    private final DerpHandler handler;

    public Derps(DerpHandler handler) {
        super("derps");
        this.handler = handler;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        IntStream.range(0, handler.derpCount())
                .mapToObj(this::createDerpLine)
                .forEach(sender::sendMessage);
    }

    private TextComponent createDerpLine(int derpIndex) {
        return new TextComponent(
                derpIndex + ". " + handler.derpByIndex(derpIndex)
        );
    }
}
