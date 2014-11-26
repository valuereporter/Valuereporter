package org.valuereporter.observation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
public class ObservedMethodJson extends ObservedMethod {

    @JsonCreator
    public ObservedMethodJson(@JsonProperty("name") String name, @JsonProperty("startTime") long startTime, @JsonProperty("endTime") long endTime) {
        super(name, startTime, endTime);
    }
}
