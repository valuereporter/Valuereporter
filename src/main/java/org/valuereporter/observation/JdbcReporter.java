package org.valuereporter.observation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
public class JdbcReporter  {
    private static final Logger log = LoggerFactory.getLogger(JdbcReporter.class);

    private JdbcTemplate jdbcTemplate;

    public JdbcReporter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private void report(long timestamp, String name, String header, String line, Object... values) {
        Integer countKeys = new Integer(0);
        Integer countIntervals = new Integer(0);
        try {
            String prefix = name.split("-")[0];
            String methodName = name.split("-")[1];
            if (methodName != null) {
                methodName = methodName.trim();
            }
            log.trace("Update for prefix [{}], methodName [{}]", prefix, methodName);
            int rowsUpdated = insertObservedInterval(prefix, methodName,timestamp, values);
            if (rowsUpdated < 1) {
                String updateKeysql = "insert ignore into ObservedKeys (prefix, methodName) values ('" + prefix+ "','" + methodName +"')";
                jdbcTemplate.update(updateKeysql);
                rowsUpdated = insertObservedInterval(prefix, methodName, timestamp, values);
            }
            log.trace("Updated {} rows", rowsUpdated);
            countIntervals = jdbcTemplate.queryForObject("select count(*) from ObservedInterval", Integer.class);
        } catch (Exception e) {
            log.error("error", e.getMessage(), e);
        }

    }

    private int insertObservedInterval(String prefix, String methodName, long timestamp, Object[] values) {
        String sql = JdbcHelper.insertObservedInterval(prefix, methodName, timestamp, values);
        return jdbcTemplate.update(sql);
    }

}
