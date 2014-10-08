package org.valuereporter.observation;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertTrue;

public class ObservationsServiceTest {

    private ObservationDao observationDao;
    private ObservationsRepository observationsRepository;
    private boolean persistAllMethodCalls = false;
    private static final String PREFiX = "ObservationsServiceTest";

    @BeforeMethod
    public void setUp() throws Exception {
        observationDao = mock(ObservationDao.class);
        observationsRepository = mock(ObservationsRepository.class);

    }



    @Test
    public void testFindObservationsByName() throws Exception {

    }

    @Test
    public void testAddObservations() throws Exception {

    }

    @Test
    public void testCreateScheduler() throws Exception {

    }

    @Test
    public void testIsScheduled() throws Exception {
        int inteval = 10;
        ObservationsService observationService = new ObservationsService(observationDao, observationsRepository, persistAllMethodCalls, inteval);
        observationService.createScheduler(PREFiX);
        assertTrue(observationService.isScheduled(PREFiX));

    }



    @Test
    public void testIsPersistMethodDetails() throws Exception {

    }

    @Test
    public void testDoPersistMethodDetails() throws Exception {

    }

    @Test
    public void testGetObservedMethods() throws Exception {

    }
}