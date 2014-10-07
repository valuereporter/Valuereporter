package org.valuereporter.observation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.valuereporter.QueryOperations;
import org.valuereporter.WriteOperations;
import org.valuereporter.scheduler.StatisticsPersister;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
@Service
public class ObservationsService implements QueryOperations, WriteOperations {
    private static final Logger log = LoggerFactory.getLogger(ObservationsService.class);
    private static final long MINUTES_15 = 15 * 60;
    private static final long INITIAL_DELAY = MINUTES_15;
    private static final long DELAY_BETWEEN_RUNS = MINUTES_15;
    private final ObservationsRepository observationsRepository;
    private int intervalSeconds = -1;
    private List<ObservedMethod> observedMethodsChache;
    private ObservationDao observationDao;
    private boolean persistMethodDetails = false;
    private List<String> scheduledPrefixes = new ArrayList<>();
    private StatisticsPersister statisticsPersister = null;

    @Autowired
    public ObservationsService(ObservationDao observationDao, ObservationsRepository observationsRepository, @Value("${observation.methods.detailed}") boolean persistMethodDetails, @Value("${observation.interval.seconds}") int intervalSeconds  ) {
        this.observationDao = observationDao;
        this.observationsRepository = observationsRepository;
        doPersistMethodDetails(persistMethodDetails);
        if (intervalSeconds > 0) {
            this.intervalSeconds = intervalSeconds;
        }
    }

    @Override
    public List<ObservedMethod> findObservationsByName(String prefix, String name) {
        List<ObservedMethod> returnResult = getObservedMethods(prefix,name);
        if (returnResult == null || returnResult.size() < 1) {
            returnResult = new ArrayList<>();
            returnResult.add(new ObservedMethod(prefix,name +"-template", System.currentTimeMillis(), System.currentTimeMillis() + 1));
        }
        return returnResult;
    }

    @Override
    public long addObservations(String prefix, List<ObservedMethod> observedMethods) {
        long size = 0;
        if (observedMethods != null) {
            if (!isScheduled(prefix)) {
                createScheduler(prefix);
            }
            observationsRepository.updateStatistics(prefix, observedMethods);
            if (isPersistMethodDetails()) {
                observationDao.addAll(prefix, observedMethods);
            }
            size = observedMethods.size();
        }
        return size;
    }

    void createScheduler(String prefix) {
        if (statisticsPersister == null) {
            long initialDelay = INITIAL_DELAY;
            long delayBetweenRuns = DELAY_BETWEEN_RUNS;
            if (intervalSeconds > 0) {
                initialDelay = intervalSeconds;
                delayBetweenRuns = intervalSeconds;
            }
            long shutdownAfter = -1; //Not used
            statisticsPersister = new StatisticsPersister(initialDelay, delayBetweenRuns, shutdownAfter);
        }
        statisticsPersister.startScheduler(observationsRepository, prefix);
        scheduledPrefixes.add(prefix);

    }

    boolean isScheduled(String prefix) {
        return scheduledPrefixes.contains(prefix);
    }

    boolean isPersistMethodDetails() {
        return persistMethodDetails;
    }

    void doPersistMethodDetails(boolean persistMethodDetails) {
        if (persistMethodDetails) {
            log.info("Will persist every method call into the database.");
        } else {
            log.info("Will only persist summary information of methods, to the database.");
        }
        this.persistMethodDetails = persistMethodDetails;
    }

    public List<ObservedMethod> getObservedMethods(String prefix, String name) {
        List<ObservedMethod> observedMethods = observationDao.findObservedMethods(prefix, name);
        return observedMethods;

    }
}
