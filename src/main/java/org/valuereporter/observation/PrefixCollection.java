package org.valuereporter.observation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author <a href="mailto:bard.lind@gmail.com">Bard Lind</a>
 */
public class PrefixCollection {

    private final String prefix;
    private final long interval;
    private ConcurrentMap<String, ObservedInterval> intervals = new ConcurrentHashMap<>();
    private final long defaultInterval = 5 * 1000;

    public PrefixCollection(String prefix) {
        this.prefix = prefix;
        this.interval = defaultInterval;
    }

    public PrefixCollection(String prefix, long duration) {
        this.interval = duration;
        this.prefix = prefix;
    }

    public void updateStatistics(ObservedMethod method) {
        ObservedInterval observedInterval;
        if (method != null) {
            if (!intervals.containsKey(method.getName())) {
                observedInterval = new ObservedInterval(method.getName(), interval);
                intervals.putIfAbsent(method.getName(), observedInterval);
            }
            observedInterval = intervals.get(method.getName());
            observedInterval.updateStatistics(method);
        }
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
