package org.valuereporter.observation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

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
        PrefixCollection prefixCollection = getCollection(prefix);
        if (prefixCollection == null) {
            prefixCollection = new PrefixCollection(prefix);
            prefixes.put(prefix, prefixCollection);
        }
        for (ObservedMethod method : methods) {
            prefixCollection.updateStatistics(method);
        }
    }

    private PrefixCollection getCollection(String prefix) {
        return prefixes.get(prefix);
    }

    public void persistStatistics(String prefix) {
        PrefixCollection prefixCollection = getCollection(prefix);
        Collection<ObservedInterval> intervals = prefixCollection.getIntervalls();
        clearCollection(prefix);
        updateMissingKeys(prefix, intervals);
        observationDao.updateStatistics(prefix, intervals);

    }

    private void updateMissingKeys(String prefix, Collection<ObservedInterval> intervals) {
        List<String> methodNames = new ArrayList<>(intervals.size());
        for (Iterator<ObservedInterval> interval = intervals.iterator(); interval.hasNext(); ) {
            ObservedInterval next = interval.next();
            methodNames.add(next.getMethodName());
        }
        observationDao.ensureObservedKeys(prefix, methodNames);
    }

    private void clearCollection(String prefix) {
        prefixes.remove(prefix);
    }
}
