package paperstable;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {

	

		public static Connection doConnect()
		{
			Connection con=null;
			try{
				Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost/dbnewspaper","root","");
			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			return con;
			
		}
		public static void main(String args[])
		{
			Connection con=doConnect();
			if(con==null)
				System.out.println("Not Coonected");
			else
				System.out.println("Connected");;
		}

	}

