//package uk.ac.cardiff.mma.application;
//
//import java.sql.*;
//
//public class TestDriver {
//
//    public static void main(String[] args) {
//        try {
//            Class.forName("com.mysql.jdbc.Driver"); //Loading the database driver.
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/EquipmentBooking","admin","secret");
//            System.out.println("Connection established.");
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM GasUse.gas");
//            while(rs.next()) { //Keep pointing to the next record
//                System.out.println(
//                        rs.getInt("id") + ":" +
//                        rs.getString("name") + ":" +
//                        rs.getInt("storage") + ":" +
//                        rs.getString("unit") + ":" +
//                        rs.getString("coshh") + ":" +
//                        rs.getString("location") + ":" +
//                        rs.getString("hazardLevel") + ":" +
//                        rs.getString("comments"));
//            }
//            rs.close();
//            stmt.close();
//            conn.close();
//        } catch(ClassNotFoundException e) {
//            System.out.println("The specified driver class could not be found!");
//        } catch(SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//
//}
