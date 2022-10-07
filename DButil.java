package ATM;
import java.sql.*;
public class DButil
{
   public static Connection conn;
   public static Connection createConnection()
   {
	   try
	   {
		   Class.forName("com.mysql.cj.jdbc.Driver");
	   }
	   catch(ClassNotFoundException e)
	   {
		   e.printStackTrace();
	   }
	   try
	   {
		  conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/atm data","root","haris15");
	   }
	   catch(SQLException e)
	   {
		   e.printStackTrace();
	   }
	   return conn;
   }
}
