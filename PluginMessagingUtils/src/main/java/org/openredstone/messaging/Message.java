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

        if (!isValidAction(raw)) {
            throw new IllegalArgumentException("Invalid action: " + raw[0]);
        }

        if(!isValidUuid(raw)) {
            throw new IllegalArgumentException("Invalid uuid: " + raw[1]);
        }

        this.action = Action.valueOf(raw[0]);
        this.uuid = UUID.fromString(raw[1]);
        this.arguments = Arrays.copyOfRange(raw, 2, raw.length);

    }

    public String getSerializedMessage() {
        String actionString = action.name();
        String uniqueId = uuid.toString();
        String arguments = String.join(":", this.arguments);
        return actionString + ":" + uniqueId + ":" + arguments;
    }

    public static boolean isValidUuid(String[] raw) {
        if (raw[1].matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")) {
            return true;
        }
        return false;
    }

    public static boolean isValidAction(String[] raw) {
        if (Action.valueOf(raw[0]) == null) {
            return false;
        }
        return true;
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
