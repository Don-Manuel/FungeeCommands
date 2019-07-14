package org.openredstone.messages;

import org.openredstone.types.Action;

import java.util.Arrays;
import java.util.UUID;

public class ActionMessage extends Message {

    Action action;
    UUID uuid;

    public ActionMessage(Action action, UUID uuid, String[] arguments) {
        super(arguments);
        this.action = action;
        this.uuid = uuid;
    }

    public ActionMessage(String serializedMessage) throws Exception {
        String[] raw = serializedMessage.split(":");

        if (raw.length < 2) {
            throw new Exception("Not enough arguments provided in serialized message.");
        }

        this.action = Action.valueOf(raw[0]);
        this.uuid = UUID.fromString(raw[1]);

        if (!isValidAction(raw[0])) {
            throw new IllegalArgumentException("Invalid action: " + raw[0]);
        }

        if(!isValidUuid()) {
            throw new IllegalArgumentException("Invalid uuid: " + raw[1]);
        }
        this.arguments = Arrays.copyOfRange(raw, 2, raw.length);

    }

    @Override
    public String getSerializedMessage() {
        String actionString = action.name();
        String uuidString = uuid.toString();
        return actionString + ":" + uuidString + ":" + super.getSerializedMessage();
    }

    public boolean isValidUuid() {
        return uuid.toString().matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");
    }

    private static boolean isValidAction(String action) {
        try {
            Action.valueOf(action);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public Action getAction() {
        return action;
    }

}
