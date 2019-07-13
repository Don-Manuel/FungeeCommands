package org.openredstone.messages;

import java.util.UUID;

public class Message {

    UUID uuid;
    String[] arguments;

    public Message() { }

    public Message(UUID uuid, String[] arguments) {
        this.uuid = uuid;
        this.arguments = arguments;
    }

    public String getSerializedMessage() {
        String uniqueId = uuid.toString();
        String arguments = String.join(":", this.arguments);
        return uniqueId + ":" + arguments;
    }

    public boolean isValidUuid() {
        return uuid.toString().matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");
    }

    public UUID getUuid() {
        return uuid;
    }

    public String[] getArguments() {
        return arguments;
    }

}
