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
    private final long defaultInterval = 5 * 1000;

    public PrefixCollection(String prefix) {
        this.prefix = prefix;
    }

    public void updateStatistics(ObservedMethod method) {
        ObservedInterval observedInterval = intervals.get(method.getName());
        if (observedInterval == null) {
            observedInterval = new ObservedInterval(method.getName(), defaultInterval);
            intervals.put(method.getName(), observedInterval);
        }
        observedInterval.updateStatistics(method);
    }


    public List<ObservedInterval> getIntervalls() {
        List<ObservedInterval> intervalList = new ArrayList<>(intervals.values());
        return intervalList;
    }

}
