package org.valuereporter.observation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.mock;

/**
 * @author <a href="mailto:bard.lind@gmail.com">Bard Lind</a>
 */
public class ObservationsRepositoryManualTest {
    private static final Logger log = LoggerFactory.getLogger(ObservationsRepositoryManualTest.class);

    ObservationDao observationDao;

    public ObservationsRepositoryManualTest(ObservationDao observationDao) {
        this.observationDao = observationDao;
    }

    public static void main(String[] args) {
        ObservationDao observationDaoMock = mock(ObservationDao.class);
        ObservationsRepositoryManualTest repository = new ObservationsRepositoryManualTest(observationDaoMock);
        //TODO add and persist
        //TODO add and persist with Spring see http://blog.frankel.ch/database-unit-testing-with-dbunit-spring-and-testng
    }
}
