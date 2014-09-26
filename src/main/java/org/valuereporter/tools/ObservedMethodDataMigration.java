package org.valuereporter.tools;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.valuereporter.Main;
import org.valuereporter.observation.ObservationDao;
import org.valuereporter.observation.ObservedInterval;
import org.valuereporter.observation.ObservedMethod;
import org.valuereporter.observation.PrefixCollection;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
public class ObservedMethodDataMigration {
    private static final Logger log = LoggerFactory.getLogger(ObservedMethodDataMigration.class);

    private ObservationDao observationDao = null;
    private PrefixCollection prefixCollection = null;

    public ObservedMethodDataMigration(ObservationDao observationDao, PrefixCollection prefixCollection) {
        this.observationDao = observationDao;
        this.prefixCollection = prefixCollection;
    }

    public static void main(String[] args) {
        log.info("Initializing Spring context.");

        Properties properties = findProperties();
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/tools/applicationContext-datamigration.xml");
        ObservationDao dao = (ObservationDao) applicationContext.getBean("observationDao");
        String prefix = properties.getProperty("datamigration.prefix");
        PrefixCollection collection = new PrefixCollection(prefix);
        ObservedMethodDataMigration migrator = new ObservedMethodDataMigration(dao, collection);
        List<String> methodNames = findMethodsToParse(properties);
        migrator.createSummary(prefix, methodNames);


        log.info("Spring context initialized.");




    }

    private void createSummary(String prefix, List<String> methodNames) {
        //(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute)
        DateTime start = new DateTime(2014,9, 24, 13,0,0 );
        DateTime end = start.plusMinutes(15);
        String methodName = methodNames.get(0);
        log.trace("Find ObservedMethods. prefix {}, methodName {}, start {}, end {}");
        List<ObservedMethod> observedMethods = observationDao.findObservedMethods(prefix, methodName, start, end);
        log.trace("Found {} methods. ", observedMethods.size());
        long interval = end.getMillis() - start.getMillis();
        prefixCollection.updateStatisticsList(observedMethods,start.getMillis(), interval);
        List<ObservedInterval> observedIntervals = prefixCollection.getIntervals();

        log.trace("Found {} intervals.", observedIntervals.size());
        int[] count = observationDao.updateStatistics(prefix, observedIntervals);
        log.trace("Updated interval to statistics. count: {}" + count);

    }

    private static List<String> findMethodsToParse(Properties properties) {
        String propValue = properties.getProperty("datamigration.methodNames");
        String[] methodNames = propValue.split(",");
        return new ArrayList<>(Arrays.asList(methodNames));
    }

    protected static Properties findProperties() {
        Properties resourceBundle = findPropertiesFile("/valuereporter.properties");
        Properties overridesBundle = findPropertiesFile("/config_override/valuereporter.properties");
        Properties properties = new Properties();
        if (resourceBundle != null) {
            Enumeration<Object> keys = resourceBundle.keys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                properties.put(key, resourceBundle.getProperty(key));
            }
        }
        if (overridesBundle != null) {
            Enumeration<Object> keys = overridesBundle.keys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                properties.put(key,overridesBundle.getProperty(key));
            }
        }
        return properties;
    }

    protected static Properties findPropertiesFile(String filename) {
        Properties prop = new Properties();
        InputStream inputFile = null;
        try {
            inputFile = Main.class.getResourceAsStream(filename);
            if (inputFile != null) {
                prop.load(inputFile);
            } else {
                log.info("Could not find properties file: {}", filename);
            }

        } catch (Exception e) {
            log.warn("Could not load data from properties file: {}", filename, e);
        } finally {
            if (inputFile != null) {
                try {
                    inputFile.close();
                } catch (IOException e) {
                    //Do nothing.
                    log.debug("Failed to close stream.");
                }
            }
        }
        return prop;
    }

}
