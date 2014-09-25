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
public class ObservedIntervalsService implements QueryOperations{
    private static final Logger log = LoggerFactory.getLogger(ObservedIntervalsService.class);

    private SlaDao slaDao;

    @Autowired
    public ObservedIntervalsService(SlaDao slaDao) {
        this.slaDao = slaDao;
    }

    @Override
    public List<UsageStatistics> findUsage(String prefix, String filter, DateTime from, DateTime to) {
        String methodFilter = filter;
        return slaDao.findUsage(prefix, methodFilter, from, to);
    }
}
