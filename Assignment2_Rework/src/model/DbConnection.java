package model;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;

public class DbConnection {
    Connection conn;


    public boolean openConnection() {
        try {
            //conn = DriverManager.getConnection("jdbc:sqlite:D:\\Varun\\Australia\\RMIT\\Sem 2\\Advanced Programming\\Assignment 2\\Assignment2_Code\\src\\database\\database.db");
            conn= DriverManager.getConnection("jdbc:sqlite:./src/database/database.db");
            System.out.println("Connected to database");

            return true;
        } catch (Exception ex) {
            System.out.println("Connection Error to database");
            ex.getStackTrace();
            return false;
        }
    }

    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();

            }
        } catch (Exception ex) {
            System.out.println("Could not close connection " + ex.getMessage());
        }
    }

    public Connection Connect() {
        try {
            String url = "jdbc:sqlite:D:\\Varun\\Australia\\RMIT\\Sem 2\\Advanced Programming\\Assignment 2\\Assignment2_Code\\database.db";
            Connection conn = DriverManager.getConnection(url);
            return conn;
        } catch (Exception e) {
            e.getMessage();
        }

        return null;
    }
}
