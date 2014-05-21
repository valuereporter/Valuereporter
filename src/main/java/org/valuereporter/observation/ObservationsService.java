package org.valuereporter.observation;

import org.valuereporter.QueryOperations;
import org.valuereporter.WriteOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
@Service
public class ObservationsService implements QueryOperations, WriteOperations {
    List<ObservedMethod> observedMethodsChache;
    ObservationDao observationDao;

    @Autowired
    public ObservationsService(ObservationDao observationDao) {
        this.observationDao = observationDao;
        //observedMethods = getObservedMethods();
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
            //getObservedMethods().addAll(observedMethods);
            observationDao.addAll(prefix,observedMethods);
            size = observedMethods.size();
        }
        return size;
    }

    List<ObservedMethod> getObservedMethods(String prefix, String name) {
        List<ObservedMethod> observedMethods = observationDao.findObservedMethods(prefix, name);
        return observedMethods;

    }
}
