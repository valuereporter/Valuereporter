package org.valuereporter.sla;

import org.joda.time.DateTime;

import java.util.List;

/**
 * @author <a href="mailto:bard.lind@gmail.com">Bard Lind</a>
 */
public interface QueryOperations {

    List<UsageStatistics> findUsage(String prefix, String filter, DateTime from, DateTime to);
}
