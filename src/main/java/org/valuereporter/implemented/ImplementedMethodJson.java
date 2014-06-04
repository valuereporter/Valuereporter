package org.valuereporter.implemented;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
public class ImplementedMethodJson extends ImplementedMethod{

    @JsonCreator
    public ImplementedMethodJson(@JsonProperty("name") String name) {
        super(name);
    }
}
