package org.valuereporter.sla;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        String sql = "select ok.prefix, ok.methodName, oi.duration,oi.startTime, oi.count, oi.max, oi.min, oi.mean, oi.median, oi.stdDev," +
                "oi.p95, oi.p98, oi.p99  from ObservedInterval oi, ObservedKeys ok where ok.prefix=? and ok.methodName=? and ok.id = oi.observedKeysId order by oi.count desc, oi.max desc";

        Object[] parameters = new Object[] {prefix,methodFilter};
        List<UsageStatistics> usageStatisticses = jdbcTemplate.query(sql, parameters, new RowMapper<UsageStatistics>() {
            @Override
            public UsageStatistics mapRow(ResultSet resultSet, int i) throws SQLException {
                //log.debug("Returned values: {},{},{},{},{}", resultSet.getObject(1),resultSet.getObject(2),resultSet.getObject(3),resultSet.getObject(4),resultSet.getObject(5));

                UsageStatistics observedMethod = new UsageStatistics(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getLong(3),
                        resultSet.getTimestamp(4).getTime(),
                        resultSet.getLong(5),
                        resultSet.getLong(6),
                        resultSet.getLong(7),
                        resultSet.getDouble(8),
                        resultSet.getDouble(9),
                        resultSet.getDouble(10),
                        resultSet.getDouble(11),
                        resultSet.getDouble(12),
                        resultSet.getDouble(13));
                return observedMethod;
            }
        });
        return usageStatisticses;

    }

    private UsageStatistics buildStub() {
        return new UsageStatistics("stubPrefix", "stubMethodName", 9000, new DateTime().getMillis(), 5, 50, 5, 7.50,7.0,1.25,5,7,50);
    }
}
