package com.server.servlet.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Baza {
	/*public static void main( String args[] ) {
	      Connection c = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:\\test.db");
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("2Opened database successfully");
	   }*/
	/*public static void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:D:\\Baza.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
   
    public static void main(String[] args) {
        connect();
    }*/
	private Connection connect() {
        // SQLite connection string
		Connection conn = null;
		try {
		Class.forName("org.sqlite.JDBC");
		 String url = "jdbc:sqlite:Baza.db";
        
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		} catch (ClassNotFoundException ex) {
	            ex.printStackTrace();
		}
        return conn;
    }
 
    
    /**
     * select all rows in the warehouses table
     */
    public ArrayList<String> selectAll(){
    	ArrayList<String> tablica = new ArrayList<String>();
    	String sql = "SELECT tytul FROM Filmy";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
            	tablica.add(rs.getString("tytul"));
                System.out.println(/*rs.getInt("id") +  "\t" +*/ 
                                   rs.getString("tytul"));// + "\t" +
                                   //rs.getDouble("capacity"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		return tablica;
    }
    
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Baza app = new Baza();
        app.selectAll();
    }
}

