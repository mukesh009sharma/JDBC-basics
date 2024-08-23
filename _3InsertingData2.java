import java.sql.*;
public class _3InsertingData2 {
    public static void main(String[] args) {
      String url="jdbc:mysql://localhost:3306/college";
       String user="root";
       String password="1998";
       try {
        Connection myConnection=DriverManager.getConnection(url, user, password);
        Statement myStatement=myConnection.createStatement();
        String mysql="insert into student (S_no, S_name) values (12, 'akdu')";
        myStatement.executeUpdate(mysql);
        System.out.println("Insert completed............");
        myConnection.close();
        
       }
        catch (Exception e) {
        e.printStackTrace();
       }
    }
}
