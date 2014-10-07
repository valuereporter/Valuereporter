package org.valuereporter.observation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
public class UpdateObservationsRunner  extends Thread {

    private static final String PREFIX = ThreadSafeObservationsRepositoryVerification.PREFIX;
    private final Logger log = LoggerFactory.getLogger(UpdateObservationsRunner.class);
    private final ObservationsRepository observationsRepository;
    private FinTrans ft;

    UpdateObservationsRunner(String methodName, ObservationsRepository observationsRepository, FinTrans ft) {
        super(methodName);
        this.observationsRepository = observationsRepository;
        this.ft = ft;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            if (getName().equals(ThreadSafeObservationsRepositoryVerification.FIRST_METHOD)) {
                observationsRepository.updateStatistics(PREFIX,
                        getFirstMethods());
               // ft.update(ThreadSafeObservationsRepositoryVerification.FIRST_METHOD, 2000.0);
                /*
                ft.transName = ThreadSafeObservationsRepositoryVerification.FIRST_METHOD;

                try {
                    Thread.sleep((int) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    //Do nothing
                }
                ft.amount = 2000.0;
                log.info("{} {}", ft.transName, ft.amount);
                */
            } else {
                observationsRepository.updateStatistics(PREFIX,
                        getSecondMethods());
               // ft.update(ThreadSafeObservationsRepositoryVerification.SECOND_METHOD, 250.0);
                /*
                ft.transName = ThreadSafeObservationsRepositoryVerification.SECOND_METHOD;

                try {
                    Thread.sleep((int) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    //Do nothing
                }
                ft.amount = 250.0;
                log.info("{} {}", ft.transName, ft.amount);
                    */
            }
        }
    }

    List<ObservedMethod> getFirstMethods() {
        List<ObservedMethod> firstMethods = new ArrayList<>();
        long endTime = System.currentTimeMillis();
        long startTime = endTime - 1000;
        long duration = 1000;
        firstMethods.add(new ObservedMethod(PREFIX,ThreadSafeObservationsRepositoryVerification.FIRST_METHOD,startTime, endTime, duration));
        return firstMethods;
    }
    List<ObservedMethod> getSecondMethods() {
        List<ObservedMethod> firstMethods = new ArrayList<>();
        long endTime = System.currentTimeMillis();
        long startTime = endTime - 200;
        long duration = 200;
        firstMethods.add(new ObservedMethod(PREFIX,ThreadSafeObservationsRepositoryVerification.SECOND_METHOD,startTime, endTime, duration));
        return firstMethods;
    }
}