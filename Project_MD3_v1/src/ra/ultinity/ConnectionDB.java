package ra.ultinity;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static ra.config.DataSource.*;

public class ConnectionDB {
    public static Connection openConnection() {
        Connection con = null;
        try {
            Class.forName(DRIVER);
            try {
                con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void closeConnection(Connection con, CallableStatement callSt) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (callSt != null) {
            try {
                callSt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Connection con = ConnectionDB.openConnection();
        if(con!=null){
            System.out.println("Connect successfully!");
        } else {
            System.out.println("Connect failed!");
        }
    }
}


