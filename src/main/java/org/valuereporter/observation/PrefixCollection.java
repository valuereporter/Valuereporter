package org.valuereporter.observation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:bard.lind@gmail.com">Bard Lind</a>
 */
public class PrefixCollection {

    private final String prefix;
    private Map<String, ObservedInterval> intervals = new HashMap<>();

    public PrefixCollection(String prefix) {
        this.prefix = prefix;
    }

    public void updateStatistics(ObservedMethod method) {
        ObservedInterval observedInterval = intervals.get(method.getName());
        observedInterval.updateStatistics(method);
    }


    public List<ObservedInterval> getIntervalls() {
        List<ObservedInterval> intervalList = new ArrayList<>(intervals.values());
        return intervalList;
    }

}
