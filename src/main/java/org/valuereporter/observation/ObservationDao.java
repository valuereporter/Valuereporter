package org.valuereporter.observation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
@Component
public class ObservationDao {
    private static final Logger log = LoggerFactory.getLogger(ObservationDao.class);
    private static final String MAX_RESULTS = "1000";
    private static String usernameSql = "SELECT  id FROM UserTable where username = ?";
    private static String emailSql = "SELECT  id FROM UserTable where email = ?";
    private static String phoneSql = "SELECT  id FROM UserTable where phone = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ObservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int findOwnerId(String userId) {
        log.debug("OwnerId: Try to find single owner with userId:" + userId);
        int ownerId = 0;
        String msg = "";
        if (StringUtils.isNotEmpty(userId) && StringUtils.isNotBlank(userId)) {
            ownerId = findSingleItem(usernameSql, userId);
            if (ownerId < 1) {
                msg = "No single result from:" + usernameSql +". Parameter: " + userId +".";
                ownerId = findSingleItem(emailSql, userId);
            } if (ownerId < 1) {
                msg = msg +  ". No single result from:" + emailSql +". Parameter: " + userId +".";
                ownerId = findSingleItem(phoneSql, userId);
            }
        }
        log.debug("OwnerId:" + ownerId + ": from userId:" + userId + "." + msg);
        return ownerId;
    }

    /**
     *
     * @param sql
     * @param parameter
     * @return if of identified owner. -1 if more than one result, or 0 if no results are found.
     */
    private int findSingleItem(String sql, String parameter) {
        List<Integer> ownerIds = jdbcTemplate.queryForList(sql, Integer.class, parameter);
        if (ownerIds.size() == 1) {
            return ownerIds.get(0).intValue();
        } else if (ownerIds.size() > 1) {
            log.warn("Search on sql:" + sql + ": parameter:" + parameter + ". Returned " + ownerIds.size() + ". Elements Only one should be returned.");
            return -1;
        }
        return 0;

    }
    public List<ObservedMethod> findObservedMethods(String prefix, String name) {

        String sql = "SELECT prefix,methodName, startTime, endTime, duration  FROM ObservedMethod WHERE prefix = ? AND methodName = ? AND startTime > DATEADD(week,-1,GETDATE() ) ORDER BY startTime ASC ";
        Object[] parameters = new Object[] {prefix,name};
        List<ObservedMethod> observedMethods = jdbcTemplate.query(sql, parameters, new RowMapper<ObservedMethod>() {
            @Override
            public ObservedMethod mapRow(ResultSet resultSet, int i) throws SQLException {
                //log.debug("Returned values: {},{},{},{},{}", resultSet.getObject(1),resultSet.getObject(2),resultSet.getObject(3),resultSet.getObject(4),resultSet.getObject(5));

                ObservedMethod observedMethod = new ObservedMethod(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getTimestamp(3).getTime(),
                        resultSet.getTimestamp(4).getTime(),
                        resultSet.getLong(5));
                return observedMethod;
            }
        });
        return observedMethods;
    }

    public void addAll(final String prefix, final List<ObservedMethod> observedMethods) {
        String sql = "INSERT INTO "
                + "ObservedMethod "
                + "(prefix,methodName, startTime, endTime, duration) "
                + "VALUES " + "(?,?,?,?,?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {

                ObservedMethod observedMethod = observedMethods.get(i);
                ps.setString(1,prefix);
                ps.setString(2, observedMethod.getName());
                ps.setTimestamp(3, new Timestamp(observedMethod.getStartTime()));
                ps.setTimestamp(4, new Timestamp(observedMethod.getEndTime()));
                ps.setLong(5, observedMethod.getDuration());

            }

            @Override
            public int getBatchSize() {
                return observedMethods.size();
            }
        });


    }
}
