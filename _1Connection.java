import java.sql.*;
 
public class _1Connection {
    public static void main(String arg[])
    {
        Connection con = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/college",
                "root", "1998");
 
            
 
            Statement statement;
            statement = con.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery(
                "select * from student");
            int s_no;
            String s_name;
            while (resultSet.next()) {
                s_no = resultSet.getInt("S_no");
                s_name = resultSet.getString("S_name").trim();
                System.out.println("S_no : " + s_no
                                   + " S_name : " + s_name);
            }
            resultSet.close();
            statement.close();
            con.close();
        }
        catch (Exception exception) {
            System.out.println(exception);
        }
    } // function ends
} // class ends