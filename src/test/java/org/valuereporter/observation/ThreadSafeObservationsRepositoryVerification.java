package org.valuereporter.observation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;

public class ThreadSafeObservationsRepositoryVerification {
    private static final Logger log = LoggerFactory.getLogger(ThreadSafeObservationsRepositoryVerification.class);
    public static final String PREFIX = "MultiThreadVerification";

    public static final String FIRST_METHOD = "org.valuereporter.first.firstMethod";
    public static final String SECOND_METHOD = "org.valuereporter.second.secondMethod";


    public static void main(String[] args) {
        log.info("-START-");
        ObservationDao observationDao = mock(ObservationDao.class);
        ObservationsRepository repository = new ObservationsRepository(observationDao);
        FinTrans ft = new FinTrans();
        UpdateObservationsRunner firstObservations = new UpdateObservationsRunner(FIRST_METHOD, repository, ft);
        UpdateObservationsRunner secondObservations = new UpdateObservationsRunner(SECOND_METHOD, repository, ft);
        firstObservations.start();
        secondObservations.start();
        try {
          firstObservations.join();
          secondObservations.join();
        } catch (InterruptedException e) {
            log.warn("Interupted");
        }

        //Child processes are finished.
        //Verify
        Map<String, PrefixCollection> prefixes = repository.getPrefixes();
        log.info("Expect size of 1. Size is {}", prefixes.keySet().size());
        for (String prefixKey : prefixes.keySet()) {
            log.info("Found prefix {}", prefixKey);
        }

        PrefixCollection collection = prefixes.get(PREFIX);
        List<ObservedInterval> intervals = collection.getIntervals();
        for (ObservedInterval interval : intervals) {
            log.info("ObserveInterval. name {}, count {} mean {} " , interval.getMethodName(), interval.getCount(), interval.getMean());
        }

        log.info("-END-");
    }



}