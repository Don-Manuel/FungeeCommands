package org.openredstone.messages;

import org.openredstone.types.Report;

import java.util.Arrays;
import java.util.UUID;

public class ReportMessage extends Message {

    Report report;

    public ReportMessage(Report report, UUID uuid, String[] arguments) {
        super(uuid, arguments);
        this.report = report;
    }

    public ReportMessage(String serializedMessage) throws Exception {
        String[] raw = serializedMessage.split(":");

        if (raw.length < 2) {
            throw new Exception("Not enough arguments provided in serialized message.");
        }

        if (!isValidReport(raw[0])) {
            throw new IllegalArgumentException("Invalid action: " + raw[0]);
        }

        if(!isValidUuid()) {
            throw new IllegalArgumentException("Invalid uuid: " + raw[1]);
        }

        this.report = Report.valueOf(raw[0]);
        this.uuid = UUID.fromString(raw[1]);
        this.arguments = Arrays.copyOfRange(raw, 2, raw.length);

    }

    @Override
    public String getSerializedMessage() {
        String reportString = report.name();
        return reportString + ":" + super.getSerializedMessage();
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
