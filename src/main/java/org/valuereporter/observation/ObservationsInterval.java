package org.valuereporter.observation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
@Repository
public class ObservationsInterval {
    private static final Logger log = LoggerFactory.getLogger(ObservationsInterval.class);
    private ObservationDao observationDao;
    //private Map<String, Map<String,ObservableMethod>>

    public ObservationsInterval(ObservationDao observationDao) {
        this.observationDao = observationDao;
    }

    public void updateStatistics(String prefix, List<ObservedMethod> methods) {

    }
}
