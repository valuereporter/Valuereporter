package org.valuereporter;

import org.valuereporter.helper.StatusType;

/**
 * This class will identify that the root of these errors are tecnical, not user-created.
 */
public class ValuereporterTechnicalException extends ValuereporterException {

    public ValuereporterTechnicalException(String msg, StatusType statusType) {
        super(msg, statusType);
    }

    public ValuereporterTechnicalException(String msg, Exception e, StatusType statusType) {
        super(msg, e, statusType);
    }
}
