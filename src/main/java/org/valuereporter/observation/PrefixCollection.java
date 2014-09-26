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

    public void updateStatistics(ObservedMethod method, long startTime, long interval) {
        ObservedInterval observedInterval = intervals.get(method.getName());
        if (observedInterval == null) {
            observedInterval = new ObservedInterval(method.getName(), startTime, interval);
            intervals.put(method.getName(), observedInterval);
        }
        observedInterval.updateStatistics(method);
    }

    public void updateStatisticsList(List<ObservedMethod> methods, long startTime, long interval) {
        for (ObservedMethod method : methods) {
            updateStatistics(method, startTime,interval);
        }
    }


    public List<ObservedInterval> getIntervals() {
        List<ObservedInterval> intervalList = new ArrayList<>(intervals.values());
        return intervalList;
    }

    @Override
    public String toString() {
        return "PrefixCollection{" +
                "prefix='" + prefix + '\'' +
                ", intervals=" + intervals +
                ", defaultInterval=" + defaultInterval +
                '}';
    }
}
