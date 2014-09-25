package org.valuereporter.sla;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class SlaDaoTest {

    SlaDao slaDao;
    JdbcTemplate mockJdbcTemplaate;

    @BeforeMethod
    public void setUp() {
        mockJdbcTemplaate = mock(JdbcTemplate.class);
        slaDao = new SlaDao(mockJdbcTemplaate);
    }

    @Test
    public void testIsInvalidDate() throws Exception {
        assertTrue(slaDao.isValidDate(new DateTime()));
        assertFalse(slaDao.isValidDate(null));
    }



    @Test
    public void testIsInvalid() throws Exception {
        assertTrue(slaDao.isValid("adfas"));
        assertFalse(slaDao.isValid(""));
        assertFalse(slaDao.isValid("   "));
        assertFalse(slaDao.isValid(null));
    }
}