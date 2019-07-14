package org.openredstone.messages;

import org.openredstone.types.Action;

import java.util.Arrays;
import java.util.UUID;

public class ActionMessage extends Message {

    Action action;

    public ActionMessage(Action action, UUID uuid, String[] arguments) {
        super(uuid, arguments);
        this.action = action;
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
        return actionString + ":" + super.getSerializedMessage();
    }

    private static boolean isValidAction(String action) {
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

}
