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

        if (raw.length < 2) {
            throw new Exception("Not enough arguments provided in serialized message.");
        }

        if (!isValidAction(raw[0])) {
            throw new IllegalArgumentException("Invalid action: " + raw[0]);
        }

        if(!isValidUuid(raw[1])) {
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

    private static boolean isValidUuid(String uuid) {
        return uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");
    }

    private static boolean isValidAction(String action) {
        // TODO: this is now redundant, move try into constructor?
        try {
            Action.valueOf(action);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
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
