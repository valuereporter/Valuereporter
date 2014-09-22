package org.valuereporter.observation;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ObservedIntervalTest {

    ObservedInterval interval;

    @BeforeMethod
    public void setUp() throws Exception {
        interval = new ObservedInterval("stats", 1000L);

    }

    @Test
    public void testObserved() throws Exception {
        assertEquals(interval.getCount(), 0);

    }

    @Test
    public void testUpdateStatistics() throws Exception {

    }

    @Test
    public void getStatistics() throws Exception {
        addDuration(20);
        addDuration(40);
        addDuration(40);
        assertEquals(interval.getCount(), 3);
        assertEquals(interval.getMax(), 40.0);
        assertEquals(interval.getMin(), 20.0);
        assertEquals(interval.getMean(), 33.333333333333336);
        assertEquals(interval.getMedian(), 40.0);

    }

    @Test
    public void getStatisticsAll() throws Exception {
        for (int i=1; i< 99; i++) {
            addDuration(1);
        }
        addDuration(100);
        assertEquals(interval.getCount(), 99);
        assertEquals(interval.getMedian(), 1.0);
        assertEquals(interval.getP95(), 1.0);
        assertEquals(interval.getP98(), 1.0);
        assertEquals(interval.getP99(), 100.0);

    }

    private void addDuration(long duration) {
        interval.updateStatistics(new ObservedMethod("getStats", 0, duration));
    }

    @Test
    public void testFlushData() throws Exception {
        assertEquals(interval.getCount(), 0);

    }
}