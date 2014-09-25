package org.valuereporter.sla;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
public class SlaStatistics {

    //Prefix and methoName is ommitted. This is to reduce MemoryFootprint from the selection.
    private long startTime;
    private long interval;
    private long count;
    private double max;
    private double min;
    private double mean;
    private double p95;

    public SlaStatistics(long startTime, long interval, long count, double max, double min, double mean, double p95) {
        this.startTime = startTime;
        this.interval = interval;
        this.count = count;
        this.max = max;
        this.min = min;
        this.mean = mean;
        this.p95 = p95;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getInterval() {
        return interval;
    }

    public long getCount() {
        return count;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public double getMean() {
        return mean;
    }

    public double getP95() {
        return p95;
    }
}
