package org.valuereporter.observation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.mock;

public class ThreadSafeObservationsServiceVerification {
    private static final Logger log = LoggerFactory.getLogger(ThreadSafeObservationsServiceVerification.class);
    public static final String PREFIX = "MultiThreadVerification";

    public static final String FIRST_METHOD = "org.valuereporter.first.firstMethod";
    public static final String SECOND_METHOD = "org.valuereporter.second.secondMethod";


    public static void main(String[] args) {
        log.info("-START-");
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        ObservationDaoStub observationDao = new ObservationDaoStub(jdbcTemplate);
        ObservationsRepository repository = new ObservationsRepository(observationDao);
        boolean persistAll = false;
        int intervalInSec = 10;
        ObservationsService observationsService = new ObservationsService(observationDao, repository,persistAll, intervalInSec);
        UpdateObservationsToServiceRunner firstObservationsA = new UpdateObservationsToServiceRunner(FIRST_METHOD, observationsService);
        UpdateObservationsToServiceRunner secondObservationsA = new UpdateObservationsToServiceRunner(SECOND_METHOD, observationsService);
        UpdateObservationsToServiceRunner firstObservationsB = new UpdateObservationsToServiceRunner(FIRST_METHOD, observationsService);
        UpdateObservationsToServiceRunner secondObservationsB = new UpdateObservationsToServiceRunner(SECOND_METHOD, observationsService);

       // PersistObservationsRunner persistObservations = new PersistObservationsRunner(PREFIX, repository);
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
//            persistObservations.join();
        } catch (InterruptedException e) {
            log.warn("Interupted");
        }

        //Child processes are finished.
        //Verify
        log.info("Expect updateStatistics to be called more than once. Count: {}",observationDao.getUpdateStatisticsCount());
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
        repository.persistStatistics(PREFIX);

        log.info("-END-");
    }



}