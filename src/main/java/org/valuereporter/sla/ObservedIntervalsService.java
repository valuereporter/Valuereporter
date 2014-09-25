package org.valuereporter.sla;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:bard.lind@gmail.com">Bard Lind</a>
 */
@Service
public class ObservedIntervalsService implements ObservedQueryOperations {
    private static final Logger log = LoggerFactory.getLogger(ObservedQueryOperations.class);

    private SlaDao slaDao;

    @Autowired
    public ObservedIntervalsService(SlaDao slaDao) {
        this.slaDao = slaDao;
    }

    @Override
    public List<UsageStatistics> findUsage(String prefix, String filter, DateTime from, DateTime to) {
        log.trace("findUsage for prefix {}, filter {}", prefix, filter);
        String methodFilter = filter;
        List<UsageStatistics> usage = slaDao.findUsage(prefix, methodFilter, from, to);
        log.trace("findUsage result for prefix {}, filter {}, from {}, to {}. Result count: {} ",prefix, filter, from, to,usage.size() );
        return usage;
    }




}
