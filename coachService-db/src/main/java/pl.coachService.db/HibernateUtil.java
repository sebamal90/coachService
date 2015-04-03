package pl.coachService.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.File;
//import java.util.UUID;

public final class HibernateUtil {
    static final boolean LOG_SESSION_OPEN = false;

    private static SessionFactory sessionFactory;

    private HibernateUtil() {
        // checkstyle needs this
    }

    public static void init() {
        init(defaultConfig());
    }

    public static void init(Configuration configuration) {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory(configuration);
        } else {
            throw new IllegalStateException("can't init new session factory, it already exists (close old one to init new one)");
        }
    }

    public static Configuration getConfig(String resource) {
        if (resource == null) {
            return defaultConfig();
        }
        Configuration configuration = new Configuration();
        configuration.configure(resource);
        return configuration;
    }

    public static Configuration getConfig(File file) {
        Configuration configuration = new Configuration();
        configuration.configure(file);
        return configuration;
    }

    public static Configuration defaultConfig() {
        Configuration configuration = new Configuration();
        configuration.configure(/*"hibernate.cfg.xml"*/);
        return configuration;
    }

    private static SessionFactory buildSessionFactory(Configuration configuration) {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void setSessionFactory(SessionFactory sessionFactory) {
        HibernateUtil.sessionFactory = sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {

        getSessionFactory().close();

        setSessionFactory(null);
    }

    public static Session openSession() {
        //String uuid = UUID.randomUUID().toString();
        if (LOG_SESSION_OPEN) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
      /*      LOGGER.info("opening new session (" + uuid + ")" + (stackTrace.length >= 5 ?
                    " for " + stackTrace[2].getMethodName() + " at " +
                    stackTrace[3].getMethodName() + " at " +
                    stackTrace[4].getMethodName() + " at ..." : ""));
                    */
        }
        Session session = getSessionFactory().openSession();
      /*  if (LOG_SESSION_OPEN) {
            LOGGER.info("session opened (" + uuid + ")");
        }  */
        return session;
    }

}
