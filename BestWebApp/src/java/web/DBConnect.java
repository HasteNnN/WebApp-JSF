package web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

    public static Connection getConnection() {
        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/proiect3",
                    "root", "parola123");
        } catch (ClassNotFoundException cnfe) {
            System.err.println("ClassNotFoundException: Nu s-a gasit driverul bazei de date.");
        } catch (SQLException cnfe) {
            System.err.println("SQLException: Nu se poate conecta la baza de date.");
        } catch (Exception e) {
            System.err.println("Exception: A aparut o exceptie neprevazuta in timp ce se stabilea legatura la baza de date: " + e.getMessage());
        }
        
        return conn;
    }
}
