package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL_CONNECTION = "jdbc:mysql://localhost:3306/preprojectjm";
    //            "?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static SessionFactory sessionFactory = null;

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL_CONNECTION,
                    USERNAME,
                    PASSWORD);

            return connection;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static SessionFactory getSessionFactory() {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, DRIVER);
        properties.put(Environment.URL, URL_CONNECTION);
        properties.put(Environment.USER, USERNAME);
        properties.put(Environment.PASS, PASSWORD);
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        properties.put(Environment.SHOW_SQL, "true");
//        properties.put(Environment.HBM2DDL_AUTO, "update");
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                System.out.println("Hibernate connect");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Hibernate disconnect");
            }
        }
        return sessionFactory;
    }
}
