package org.valuereporter.implemented;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
public class ImplementedMethodDao {
    private static final Logger log = LoggerFactory.getLogger(ImplementedMethodDao.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ImplementedMethodDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ImplementedMethod> findImplementedMethodsByPrefix(String prefix) {
        String sql = "SELECT prefix,methodName FROM ImplementedMethod WHERE prefix = ? ORDER BY methodName DESC ";
        Object[] parameters = new Object[] {prefix};
        List<ImplementedMethod> implementedMethods = jdbcTemplate.query(sql, parameters, new RowMapper<ImplementedMethod>() {
            @Override
            public ImplementedMethod mapRow(ResultSet resultSet, int i) throws SQLException {
                log.debug("Returned values: {},{}", resultSet.getObject(1),resultSet.getObject(2));

                ImplementedMethod implementedMethod = new ImplementedMethod(
                        resultSet.getString(1),
                        resultSet.getString(2));
                return implementedMethod;
            }
        });
        return implementedMethods;
    }
    public List<ImplementedMethod> findImplementedMethods(String prefix, String name) {
        String sql = "SELECT prefix,methodName FROM ImplementedMethod WHERE prefix = ? AND methodName = ? ORDER BY methodName DESC ";
        Object[] parameters = new Object[] {prefix,name};
        List<ImplementedMethod> implementedMethods = jdbcTemplate.query(sql, parameters, new RowMapper<ImplementedMethod>() {
            @Override
            public ImplementedMethod mapRow(ResultSet resultSet, int i) throws SQLException {
                log.debug("Returned values: {},{}", resultSet.getObject(1),resultSet.getObject(2));

                ImplementedMethod implementedMethod = new ImplementedMethod(
                        resultSet.getString(1),
                        resultSet.getString(2));
                return implementedMethod;
            }
        });
        return implementedMethods;
    }



    public void addAll(final String prefix, final List<ImplementedMethod> implementedMethods) {
        String sql = "INSERT INTO "
                + "IplemntedMethod "
                + "(prefix,methodName) "
                + "VALUES " + "(?,?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {

                ImplementedMethod observedMethod = implementedMethods.get(i);
                ps.setString(1,prefix);
                ps.setString(2, observedMethod.getName());

            }

            @Override
            public int getBatchSize() {
                return implementedMethods.size();
            }
        });


    }
}
