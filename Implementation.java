import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSet;
import java.sql.SQLException;
 class _4crudOperation{
    private static final String url="jdbc:mysql://localhost:3306/TestDB?useSSL=false";
    private static final String user="root";
    private static final String password="1998";


    public static Connection getConnection()throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }
    public static void printSQLException(SQLException ex){
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}


public class Implementation{
   public void SelectEmployee() {
        String sql = "SELECT E_id, name, Email, Country, Salary FROM employees WHERE id = ?";
        try (Connection conn = JDBCUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 1);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("E_id"));
                System.out.println("Name: " + rs.getString("Name"));
                System.out.println("Email: " + rs.getString("Email"));
                System.out.println("Country: " + rs.getString("Country"));
                System.out.println("Salary: " + rs.getDouble("Salary"));
            }
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
   }

   public static void insertEmployee() {
        String sql = "INSERT INTO employee(Name, Email, Country, Salary) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = JDBCUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "John Doe");
            pstmt.setString(2, "john.doe@example.com");
            pstmt.setString(3, "USA");
            pstmt.setDouble(4, 50000.00);
            pstmt.executeUpdate();
            System.out.println("Record created.");
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
   }

   public void updateEmployee (){
        String sql = "UPDATE employee SET Name = ?, Email = ?, Country = ?, Salary = ? WHERE id = ?";
        try {Connection conn = JDBCUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "Jane Doe");
            pstmt.setString(2, "jane.doe@example.com");
            pstmt.setString(3, "Canada");
            pstmt.setDouble(4, 60000.00);
            pstmt.setInt(5, 1);
            pstmt.executeUpdate();
            System.out.println("Record updated.");
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
   }

   public void deleteEmployee()  {
    String sql = "DELETE FROM employee WHERE id = ?";
    try (Connection conn = JDBCUtils.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, 1);
        pstmt.executeUpdate();
        System.out.println("Record deleted.");
    } catch (SQLException e) {
        JDBCUtils.printSQLException(e);
    }
}

public static void main(String[] args) {
int choice;
Implementation obj=new Implementation();

System.out.println("Welcome to ABC Limited \n Please Enter your Choice");
System.out.println(" 1. View Employee \n 2. Add New Employee \n 3. Update Existing Employee \n 4. Delete Exisitng                              Employee \n 5. Exit ");
Scanner sc=new Scanner(System.in);
choice=sc.nextInt();

switch(choice)
{
    case 1: obj.SelectEmployee();break;
    case 2: obj.insertEmployee();break;
    case 3: obj.updateEmployee();break;
    case 4: obj.deleteEmployee();break;
     case 5: System.exit(choice);
}
}
}