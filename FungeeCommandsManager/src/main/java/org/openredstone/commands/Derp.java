package org.openredstone.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import org.openredstone.handlers.DerpHandler;

public class Derp extends FunCommand {
    private final ProxyServer proxy;

    public Derp(ProxyServer proxy) {
        super("derp");
        this.proxy = proxy;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        proxy.broadcast(DerpHandler.getDerp(sender, args));
    }
}
