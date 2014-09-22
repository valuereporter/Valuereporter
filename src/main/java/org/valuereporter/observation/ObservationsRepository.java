package org.valuereporter.observation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
@Repository
public class ObservationsRepository {
    private static final Logger log = LoggerFactory.getLogger(ObservationsRepository.class);
    private ObservationDao observationDao;
    private Map<String, PrefixCollection> prefixes = new HashMap();

    @Autowired
    public ObservationsRepository(ObservationDao observationDao) {
        this.observationDao = observationDao;
    }

    public void updateStatistics(String prefix, List<ObservedMethod> methods) {
        PrefixCollection prefixCollection = prefixes.get(prefix);
        if (prefixCollection == null) {
            prefixCollection = new PrefixCollection(prefix);
            prefixes.put(prefix, prefixCollection);
        }
        for (ObservedMethod method : methods) {
            prefixCollection.updateStatistics(method);
        }
    }
}