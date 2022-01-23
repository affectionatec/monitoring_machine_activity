package uk.ac.cardiff.mma.application;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestUtils {
    public static void createTestRecords() throws SQLException, IOException {
        //Registering the Driver
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        //Getting the connection
        String mysqlUrl = "jdbc:mysql://application.c7qnrcxumgb0.eu-west-2.rds.amazonaws.com:3306/EquipmentBooking?useSSL=false";
        Connection con = DriverManager.getConnection(mysqlUrl, "admin", "cardiff123");
        System.out.println("Connection established......");
        //Initialize the script runner
        ScriptRunner sr = new ScriptRunner(con);
        //Creating a reader object
        Reader reader = new BufferedReader(new InputStreamReader(new ClassPathResource("createTestData.sql").getInputStream()));
        //Running the script
        sr.runScript(reader);
        con.close();
    }

    public static void destroyTestRecords() throws SQLException, IOException {
        //Registering the Driver
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        //Getting the connection
        String mysqlUrl = "jdbc:mysql://application.c7qnrcxumgb0.eu-west-2.rds.amazonaws.com:3306/EquipmentBooking?useSSL=false";
        Connection con = DriverManager.getConnection(mysqlUrl, "admin", "cardiff123");
        System.out.println("Connection established......");
        //Initialize the script runner
        ScriptRunner sr = new ScriptRunner(con);
        //Creating a reader object - reading from resource
        Reader reader = new BufferedReader(new InputStreamReader(new ClassPathResource("destroyTestData.sql").getInputStream()));
        //Running the script
        sr.runScript(reader);
        con.close();
    }
}