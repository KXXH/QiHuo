package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by zjm97 on 2019/5/15.
 */
public class dbOpener {
    public static Connection getDB() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException classNotFoundException) {
            exceptionManager.logException(classNotFoundException,Thread.currentThread().getStackTrace()[1].getClassName());
            classNotFoundException.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/xm10-qihuo?user=xm10&password=qihuoqihuoqihuo&useUnicode=true&characterEncoding=UTF-8");
    }
}
