import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Test {

	public static void main(String[] args) {
		Connection c = Backend.getDatabaseConnection();
		Statement s;
		try {
			s = c.createStatement();
			ResultSet r = s.executeQuery("SHOW DATABASES");
			while(r.next()) {
				System.out.println(r.getString(0));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
