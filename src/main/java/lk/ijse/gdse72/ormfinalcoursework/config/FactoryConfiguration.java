package lk.ijse.gdse72.ormfinalcoursework.config;

import lk.ijse.gdse72.ormfinalcoursework.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.management.relation.Role;
import java.io.IOException;
import java.util.Properties;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private static SessionFactory sessionFactory;

    private FactoryConfiguration() {
        try {
            Configuration configuration = new Configuration();
            Properties properties = new Properties();

            // Load properties from hibernate.properties
            properties.load(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("hibernate.properties"));

            configuration.setProperties(properties);

            // Add all entity classes here
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Patient.class);
            configuration.addAnnotatedClass(Therapist.class);
            configuration.addAnnotatedClass(TherapyProgram.class);
            configuration.addAnnotatedClass(TherapySession.class);
            configuration.addAnnotatedClass(Payment.class);
            // .addAnnotatedClass(OtherEntity.class)

            sessionFactory = configuration.buildSessionFactory();

            // Add shutdown hook
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (sessionFactory != null) {
                    sessionFactory.close();
                }
            }));

        } catch (IOException e) {
            System.err.println("Failed to load hibernate properties: " + e.getMessage());
            throw new RuntimeException("Failed to initialize Hibernate configuration", e);
        }
    }

    public static synchronized FactoryConfiguration getInstance() {
        if (factoryConfiguration == null) {
            factoryConfiguration = new FactoryConfiguration();
        }
        return factoryConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}