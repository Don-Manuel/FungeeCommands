package org.openredstone.messages;

public class Message {

    String[] arguments;

    public Message() { }

    public Message(String[] arguments) {
        this.arguments = arguments;
    }

    public String getSerializedMessage() {
        return String.join(":", this.arguments);
    }

    public String[] getArguments() {
        return arguments;
    }

}
