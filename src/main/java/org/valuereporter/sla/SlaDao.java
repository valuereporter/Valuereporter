package org.valuereporter.sla;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:bard.lind@gmail.com">Bard Lind</a>
 */
@Component
public class SlaDao {
    private static final Logger log = LoggerFactory.getLogger(SlaDao.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SlaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UsageStatistics> findUsage(String prefix, String methodFilter, DateTime toNow, DateTime oneWeekOld) {
        List<UsageStatistics> usageStatisticses = new ArrayList<>();
        usageStatisticses.add(buildStub());
        return usageStatisticses;

    }

    private UsageStatistics buildStub() {
        return new UsageStatistics("stubPrefix", "stubMethodName", 9000, new DateTime().getMillis(), 5, 50, 5, 7.50,7.0,1.25,5,7,50);
    }
}
