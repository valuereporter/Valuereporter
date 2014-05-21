package org.valuereporter;

import org.valuereporter.helper.DatabaseMigrationHelper;
import org.valuereporter.helper.StatusType;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;


public class Main {
    public static final int DEFAULT_PORT_NO = 4901;
    public static final String CONTEXT_PATH = "/reporter";
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private Server server;
    private String resourceBase;
    private int jettyPort = -1;

    public static void main(String[] arguments) throws Exception {
        // Jersey uses java.util.logging - bridge to slf4
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        LogManager.getLogManager().getLogger("").setLevel(Level.FINEST);

        Main main = new Main();
        try {
            main.start();
            main.join();
        } catch (ValuereporterException e) {
            log.error("Failed to start the server. " + e.getMessage());
            main.stop();
        }

        /*
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        log.info("Machine Update Service started and ready for action!");

        try {
            // wait forever...
            Thread.currentThread().join();
        } catch (InterruptedException ignored) {
            // Intentionally ignored.
        }
        */

    }

    /**
     * http://wiki.eclipse.org/Jetty/Tutorial/Embedding_Jetty#Setting_a_ServletContext
     * https://github.com/ivarconr/jersey2-spring3-webapp
     */
    public Main() {
        Properties resources = findProperties();

        try{
            new DatabaseMigrationHelper(resources).upgradeDatabase();
            jettyPort = findHttpPort(resources);
        } catch (ValuereporterException tde) {
            log.error("Could not initalize the service. Exiting. ", tde);
            System.exit(0);
        }

        server = new Server(jettyPort);

        URL url = ClassLoader.getSystemResource("webapp/WEB-INF/web.xml");
        resourceBase = url.toExternalForm().replace("/WEB-INF/web.xml", "");
    }
    protected static int findHttpPort(Properties resoruces) throws ValuereporterException {
        int retPort = -1;
        String httpPort = resoruces.getProperty("jetty.http.port");

        if (httpPort == null || httpPort.length() == 0) {
            log.error("Missing property 'jetty.http.port', can not start the application.");
            throw new ValuereporterTechnicalException("Missing property 'jetty.http.port', can not start the application.", StatusType.RETRY_NOT_POSSIBLE);
        } else {
            try {
               retPort = new Integer(httpPort).intValue();
            } catch (NumberFormatException nfe) {
                log.error("Could not convert {} to int. No jetty port is set.", httpPort);
                throw new ValuereporterTechnicalException("Proprerty 'jetty.http.port' with value "+ httpPort +" could not be cast to int.", StatusType.RETRY_NOT_POSSIBLE);
            }
        }
        return retPort;
    }


    public void start() throws RuntimeException {
        WebAppContext context = new WebAppContext();
        log.debug("Start Jetty using resourcebase={}", resourceBase);
        context.setDescriptor(resourceBase + "/WEB-INF/web.xml");
        context.setResourceBase(resourceBase);
        context.setContextPath(CONTEXT_PATH);
        context.setParentLoaderPriority(true);

//        RequestLogHandler requestLogHandler = new RequestLogHandler();
//        requestLogHandler.setRequestLog(new RequestLogImpl());

        HandlerCollection handlers = new HandlerCollection();
        RequestLogHandler requestLogHandler = new RequestLogHandler();
        handlers.setHandlers(new Handler[]{context,new DefaultHandler(),requestLogHandler});
        server.setHandler(handlers);

        NCSARequestLog requestLog = new NCSARequestLog("./logs/jetty-yyyy_mm_dd.request.log");
        requestLog.setRetainDays(90);
        requestLog.setAppend(true);
        requestLog.setExtended(false);
        requestLog.setLogLatency(true);
        requestLog.setLogTimeZone("GMT");
        requestLogHandler.setRequestLog(requestLog);


       // server.setHandler(logHandler);

       // server.setHandler(context);

        /*
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath(CONTEXT_PATH);

        //ServletContainer jerseyServlet = new ServletContainer();
        SpringServlet springServlet = new SpringServlet();
        ServletHolder servletHolder = new ServletHolder(springServlet);
        servletHolder.setInitParameter(ServerProperties.PROVIDER_PACKAGES, REST_ENDPOINT_PACKAGE);
        servletHolder.setInitOrder(1);
        handler.addServlet(servletHolder, "/*");

        server.setHandler(handler);
        */
        try {
            server.start();
        } catch (Exception e) {
            throw new ValuereporterException("Failed to start." ,e, StatusType.RETRY_NOT_POSSIBLE);
        }
        Throwable springStartupFailed = context.getUnavailableException();
        if (springStartupFailed != null) {
            throw new ValuereporterException("Failed to initialize Spring Application Context." , springStartupFailed, StatusType.RETRY_NOT_POSSIBLE);
        }
        int localPort = getPortNumber();
        log.info("Jetty server started on port {}, context path {}", localPort, CONTEXT_PATH);
    }

    public void stop() throws Exception {
        server.stop();
    }

    public void join() throws InterruptedException {
        server.join();
    }

    //TODO
    public String getBasePath() {
        String path = "http://localhost:" + jettyPort + CONTEXT_PATH;
        return path;
    }
    public int getPortNumber() {
        return ((ServerConnector) server.getConnectors()[0]).getLocalPort();
    }

    public void setResourceBase(String resourceBase) {
        this.resourceBase = resourceBase;
    }

    public String getResourceBase() {
        return resourceBase;
    }
    protected static FileReader findPropertiesFile(String filename) {
        FileReader propertiesFile = null;
        try {
            propertiesFile = new FileReader(filename);
        }catch (Exception e) {
            log.warn("Could not find properties file: " + filename + ", reason: " + e.getMessage());
        }
        return propertiesFile;
    }
    protected static Properties findProperties() {
        Properties properties = new Properties();
        String classpathFileName = "valuereporter.properties";
        String overrideFileName = "./config_override/valuereporter.properties";
        FileReader classpathFile = findPropertiesFile(classpathFileName);
        FileReader overrideFile = findPropertiesFile(overrideFileName);
        if (overrideFile == null && classpathFile == null) {
            throw new ValuereporterException("Failed to load properties. Neither " + classpathFile + " nor " +overrideFile +" were found.",StatusType.RETRY_NOT_POSSIBLE );
        }
        try {
            if (classpathFile != null) {
                log.info("Loading properties from {}", classpathFileName);
                properties.load(classpathFile);
            }
            if (overrideFile != null) {
                log.info("Loading properties from {}", overrideFileName);
                properties.load(overrideFile);
            }
            log.debug("Properties loaded: {}", properties.toString());
        } catch (IOException e) {
            throw new ValuereporterException("Could not load properties from file.", e,StatusType.RETRY_NOT_POSSIBLE);
        }
        return properties;
    }
}
