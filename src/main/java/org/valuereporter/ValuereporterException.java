package org.valuereporter;

import org.valuereporter.helper.StatusType;

import java.util.UUID;

/**
 *
 */
public class ValuereporterException extends RuntimeException{

    private String statusTypeText;
    private Enum<StatusType> statusType = null;
    private final UUID uuid = UUID.randomUUID();

    public ValuereporterException(String msg, StatusType statusType) {
        super(msg);
        this.statusType = statusType;
    }

    /**
     * @param msg
     * @param e
     * @param statusType
     */
    public ValuereporterException(String msg, Exception e, Enum<StatusType> statusType) {
        super(msg, e);
        this.statusType = statusType;
    }

    public ValuereporterException(String msg, Throwable t, StatusType statusType) {
        super(msg,t);
        this.statusType = statusType;
    }

    public ValuereporterException(String msg, Exception e, StatusType statusType) {
        super(msg, e);
        this.statusType = statusType;
    }

    public String getStatusTypeText() {
        String statusTypeText = "";
        if (statusType != null) {
            statusTypeText = statusType.name();
        }
        return statusTypeText;
    }

    public String  getUuid() {
        return uuid.toString();
    }

    public Enum<StatusType> getStatusType() {
        return statusType;
    }
}
