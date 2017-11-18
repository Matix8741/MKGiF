package com.server.servlet.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;




/**
 * Servlet implementation class ConnectionServlet
 */
@WebServlet("/ConnectionServlet")
public class ConnectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnectionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //createNewDatabase("test3.db");
		ArrayList<String> filmy= selectAll();
        
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String message;
		JSONObject json = new JSONObject();
		/*json.put("name", "student");
		JSONArray array = new JSONArray();
		JSONObject item = new JSONObject();
			item.put("information", "test");
			item.put("id", 3);
			item.put("name", "course1");
			json.put("course", array);
		array.add(item);
		//JSONArray tablica = new JSONArray();
		//JSONObject item = new JSONObject();
		 * */
		for (int i = 0 ; i < filmy.size() ; i++)
		{
			json.put(i, filmy.get(i));
		}
		message = json.toString();

		out.print(json);
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private Connection connect() {
        // SQLite connection string
		Connection conn = null;
		try {
		Class.forName("org.sqlite.JDBC");
		 String url = "jdbc:sqlite:\\Baza.db";
        
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
    public static void createNewDatabase(String fileName) {
    	try {

    		Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:" + fileName;
 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	} catch (ClassNotFoundException ex) {
        ex.printStackTrace();
}
    }

}
