package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
       // реализуйте настройку соеденения с БД
       private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
       private static final String URL = "jdbc:mysql://localhost:3306/db";
       private static final String USER = "root";
       private static final String PASSWORD = "1111";
       public static Connection connection;
       public static Connection getConnection() {
              try {
                     Class.forName(DRIVER);
                     connection = DriverManager.getConnection (URL, USER, PASSWORD);
              } catch (SQLException | ClassNotFoundException e) {
                     e.printStackTrace();
              }
              return connection;
       }
}
