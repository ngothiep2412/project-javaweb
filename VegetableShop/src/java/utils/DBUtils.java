package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Thiep Ngo
 */
public class DBUtils {
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;dataBaseName=VegetableShop";
        conn = DriverManager.getConnection(url, "sa", "thieppro123");
        return conn;
    }
}
