package org.valuereporter.observation;

/**
 * @author <a href="mailto:bard.lind@gmail.com">Bard Lind</a>
 */
public class ObservedInterval {
    private final String methodName;
    private final long interval;
    private final long endOfInterval;

    private long count = 0; // (Long)values[0];
    private double max = 0; //(Double)values[1]; //snapshot.getMax()),
    private double mean = 0; //(Double)values[2]; //       convertDuration(snapshot.getMean()),
    private double min = 0; //(Double)values[3]; //convertDuration(snapshot.getMin()),
    private double standardDeviation = 0; //(Double)values[4]; //convertDuration(snapshot.getStdDev()),
    private double median = 0; //(Double)values[5]; //convertDuration(snapshot.getMedian()),
    private double p95 = 0; //(Double)values[7]; //convertDuration(snapshot.get95thPercentile()),
    private double p98 = 0; //(Double)values[8]; //convertDuration(snapshot.get98thPercentile()),
    private double p99 = 0; //(Double)values[9]; //convertDuration(snapshot.get99thPercentile()),

    public ObservedInterval(String methodName, long intervalInMillis) {
        this.methodName = methodName;
        this.interval = intervalInMillis;
        this.endOfInterval = now() + intervalInMillis;
    }

    private long now() {
        return System.currentTimeMillis();
    }

    public void observed(ObservedMethod method) {
        if (now() > endOfInterval){
            //Report to database

            //Flush the data
            flushData();
        } else {
            updateStatistics(method);
        }

    }

    void updateStatistics(ObservedMethod method) {

    }

    void flushData() {
         long count = 0;
         double max = 0;
         double mean = 0;
         double min = 0;
         double standardDeviation = 0;
         double median = 0;
         double p95 = 0;
         double p98 = 0;
         double p99 = 0;
    }

    public String getMethodName() {
        return methodName;
    }

    public long getInterval() {
        return interval;
    }

    public long getEndOfInterval() {
        return endOfInterval;
    }

    public long getCount() {
        return count;
    }

    public double getMax() {
        return max;
    }

    public double getMean() {
        return mean;
    }

    public double getMin() {
        return min;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public double getMedian() {
        return median;
    }

    public double getP95() {
        return p95;
    }

    public double getP98() {
        return p98;
    }

    public double getP99() {
        return p99;
    }
}
