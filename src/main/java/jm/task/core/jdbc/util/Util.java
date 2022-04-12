package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
/*

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
*/

public class Util {
       // реализуйте настройку соеденения с БД
       private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
       private static final String URL = "jdbc:mysql://localhost:3306/db";
       private static final String USER = "root";
       private static final String PASSWORD = "password";

       // public static Connection connection;
      /* public static Connection getConnection() {
              try {
                     Class.forName(DRIVER);
                     connection = DriverManager.getConnection (URL, USER, PASSWORD);
              } catch (SQLException | ClassNotFoundException e) {
                     e.printStackTrace();
              }
              return connection;
       }*/
       public static SessionFactory sessionFactory;
       public static SessionFactory getConnection() {
              try {
                     Configuration configuration = new Configuration()
                             .setProperty("hibernate.connection.driver_class", DRIVER)
                             .setProperty("hibernate.connection.url", URL)
                             .setProperty("hibernate.connection.username", USER)
                             .setProperty("hibernate.connection.password", PASSWORD)
                             .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                             .addAnnotatedClass(User.class);
                     ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                             .applySettings(configuration.getProperties()).build();
                     sessionFactory = configuration.buildSessionFactory(serviceRegistry);
              } catch (HibernateException e) {
                     e.printStackTrace();
              }
              return sessionFactory;


       }
       public static void closeConnection() {
              if (sessionFactory != null)
                     sessionFactory.close();
       }
}
