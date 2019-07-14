package org.openredstone.messages;

import net.md_5.bungee.api.ChatColor;
import org.openredstone.types.Report;

import java.util.Arrays;

public class ReportMessage extends Message {

    Report report;

    public ReportMessage(Report report, String[] arguments) {
        super(arguments);
        this.report = report;
    }

    public ReportMessage(String serializedMessage) throws Exception {
        String[] raw = serializedMessage.split(":");

        if (raw.length < 1) {
            throw new Exception("Not enough arguments provided in serialized message.");
        }

        if (!isValidReport(raw[0])) {
            throw new IllegalArgumentException("Invalid action: " + raw[0]);
        }

        this.report = Report.valueOf(raw[0]);
        this.arguments = Arrays.copyOfRange(raw, 2, raw.length);

    }

    @Override
    public String getSerializedMessage() {
        String reportString = report.name();
        return reportString + ":" + super.getSerializedMessage();
    }

    public ChatColor getColor() {
        switch (report) {
            case INFO:
                return ChatColor.WHITE;
            case WARNING:
                return ChatColor.YELLOW;
            case ERROR:
                return ChatColor.RED;
        }
        return ChatColor.RESET;
    }

    private static boolean isValidReport(String report) {
        try {
            Report.valueOf(report);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public Report getReport() {
        return report;
    }

}
