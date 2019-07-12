package org.openredstone.messaging;

import java.util.Arrays;
import java.util.UUID;

public class Message {

    Action action;
    UUID uuid;
    String[] arguments;

    public Message(Action action, UUID uuid, String values) {
        this.action = action;
        this.uuid = uuid;
        this.arguments = values.split(" ");
    }

    public Message(Action action, UUID uuid, String[] values) {
        this.action = action;
        this.uuid = uuid;
        this.arguments = values;
    }

    public Message(String serializedMessage) throws Exception {
        String[] raw = serializedMessage.split(":");

        if (raw.length<2) {
            throw new Exception("Not enough arguments provided in serialized message.");
        }

        this.action = parseAction(raw);

        if (action == null) {
            throw new Exception("Invalid action.");
        }

        this.uuid = parseUniqueId(raw);

        if (uuid == null) {
            throw new Exception("Invalid UUID.");
        }

        this.arguments = Arrays.copyOfRange(raw, 2, raw.length);

    }

    public String getSerializedMessage() {
        String actionString = action.name();
        String uniqueId = uuid.toString();
        String arguments = String.join(":", this.arguments);
        return actionString + ":" + uniqueId + ":" + arguments;
    }

    public static Action parseAction(String[] values) {
        return Action.valueOf(values[0]);
    }

    public static UUID parseUniqueId(String[] values) {
        return UUID.fromString(values[1]);
    }

    public Action getAction() {
        return action;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String[] getArguments() {
        return arguments;
    }
}
