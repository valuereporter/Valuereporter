package org.valuereporter.observation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.mock;

public class ThreadSafeObservationsRepositoryVerification {
    private static final Logger log = LoggerFactory.getLogger(ThreadSafeObservationsRepositoryVerification.class);
    public static final String PREFIX = "MultiThreadVerification";

    public static final String FIRST_METHOD = "org.valuereporter.first.firstMethod";
    public static final String SECOND_METHOD = "org.valuereporter.second.secondMethod";


    public static void main(String[] args) {
        log.info("-START-");
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        ObservationDao observationDao = new ObservationDaoStub(jdbcTemplate);
        ObservationsRepository repository = new ObservationsRepository(observationDao);
        UpdateObservationsRunner firstObservationsA = new UpdateObservationsRunner(FIRST_METHOD, repository);
        UpdateObservationsRunner secondObservationsA = new UpdateObservationsRunner(SECOND_METHOD, repository);
        UpdateObservationsRunner firstObservationsB = new UpdateObservationsRunner(FIRST_METHOD, repository);
        UpdateObservationsRunner secondObservationsB = new UpdateObservationsRunner(SECOND_METHOD, repository);

        PersistObservationsRunner persistObservations = new PersistObservationsRunner(PREFIX, repository);
        firstObservationsA.start();
        secondObservationsA.start();
        firstObservationsB.start();
        secondObservationsB.start();
      //  persistObservations.start();
        try {
            firstObservationsA.join();
            secondObservationsA.join();
            firstObservationsB.join();
            secondObservationsB.join();
            persistObservations.join();
        } catch (InterruptedException e) {
            log.warn("Interupted");
        }

        //Child processes are finished.
        //Verify
        Map<String, PrefixCollection> prefixes = repository.getPrefixes();
        Set<String> keys = prefixes.keySet();
        log.info("Expect size of 1. Size is {}", keys.size());
        for (String prefixKey : keys) {
            log.info("Found prefix {}", prefixKey);
        }

        PrefixCollection collection = prefixes.get(PREFIX);
        List<ObservedInterval> intervals = collection.getIntervals();
        for (ObservedInterval interval : intervals) {
            log.info("ObserveInterval. name {}, count {} mean {} " , interval.getMethodName(), interval.getCount(), interval.getMean());
        }
        repository.persistAndResetStatistics(PREFIX, 1L);

        log.info("-END-");
    }



}