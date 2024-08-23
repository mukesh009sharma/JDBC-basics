import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class _2InsertData {

	public static void main(String[] args) {
		
		String url = "jdbc:MySQL://localhost:3306/college";         
		String user ="root";                                                  
		String password = "1998";

		try {

			//1. Create a connection
			Connection myConn = DriverManager.getConnection(url,user,password);
			
			//2. Create a statement
			Statement myStmt = myConn.createStatement();
			
			//3. Execute Query
			String sql = "insert into student " + "(S_name)" + " value ('baka')";
			myStmt.executeUpdate(sql);
			System.out.println("Insert complete.");

            //4. Close connection
			myConn.close();
		}

		catch(Exception e){
			e.printStackTrace();
		}
	}
}