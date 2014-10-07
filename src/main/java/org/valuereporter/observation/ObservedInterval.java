package org.valuereporter.observation;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * @author <a href="mailto:bard.lind@gmail.com">Bard Lind</a>
 */
public class ObservedInterval {
    private final String methodName;
    private final long interval;
    private final long endOfInterval;
    private final long startTime;
    private DescriptiveStatistics stats;

    public ObservedInterval(String methodName, long intervalInMillis) {
       this(methodName, -1, intervalInMillis);
    }

    public ObservedInterval(String methodName, final long startTime, long intervalInMillis) {
        this.methodName = methodName;
        this.interval = intervalInMillis;
        this.endOfInterval = now() + intervalInMillis;
        stats = new DescriptiveStatistics();
        if (startTime < 0) {
            this.startTime = now();
        } else {
            this.startTime = startTime;
        }
    }

    private long now() {
        return System.currentTimeMillis();
    }

    public void observed(ObservedMethod method) {
        updateStatistics(method);
        /*
        if (now() > endOfInterval){
            //Report to database
//TODO move to repository
            //Flush the data
            flushData();
        } else {
           updateStatistics(method);
        }
        */

    }

    void updateStatistics(ObservedMethod method) {
        getStats().addValue(method.getDuration());
    }

    private DescriptiveStatistics getStats() {
        if (stats == null) {
            stats = new DescriptiveStatistics();
        }
        return stats;
    }

    void flushData() {
        stats = new DescriptiveStatistics();
    }

    public String getMethodName() {
        return methodName;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getInterval() {
        return interval;
    }

    public long getEndOfInterval() {
        return endOfInterval;
    }

    public long getCount() {
        return getStats().getN();
    }

    public double getMax() {
        return getStats().getMax();
    }

    public double getMean() {
        return getStats().getMean();
    }

    public double getMin() {
        return getStats().getMin();
    }

    public double getStandardDeviation() {
        return getStats().getStandardDeviation();
    }

    public double getMedian() {
        return getStats().getPercentile(50);
    }

    public double getP95() {
        return getStats().getPercentile(95);
    }

    public double getP98() {
        return getStats().getPercentile(98);
    }

    public double getP99() {
        return getStats().getPercentile(99);
    }

    @Override
    public String toString() {
        return "ObservedInterval{" +
                "methodName='" + methodName + '\'' +
                ", interval=" + interval +
                ", endOfInterval=" + endOfInterval +
                ", startTime=" + startTime +
                ", stats=" + stats +
                '}';
    }
}
