package org.valuereporter.sla;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
public class SlaStatistics {

    //Prefix and methoName is ommitted. This is to reduce MemoryFootprint from the selection.
    private long startTime;
    private long duration;
    private long count;
    private double max;
    private double min;
    private double mean;
    private double median;

    public SlaStatistics(long startTime, long duration, long count, double max, double min, double mean, double median) {
        this.startTime = startTime;
        this.duration = duration;
        this.count = count;
        this.max = max;
        this.min = min;
        this.mean = mean;
        this.median = median;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getDuration() {
        return duration;
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

    public double getMedian() {
        return median;
    }
}
