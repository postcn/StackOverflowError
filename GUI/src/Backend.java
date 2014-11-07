import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class Backend {
	
	private static Backend instance;
	private static Connection connection;
	
	private Backend() {
		//Initialize and create the trie so that we can look up into it.
	}
	
	public static Backend getInstance() {
		if (instance == null) {
			instance = new Backend();
		}
		return instance;
	}
	
	public static Connection getDatabaseConnection() {
		if (connection != null) {
			return connection;
		}
		else {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://hadoop04.csse.rose-hulman.edu?user=root");
				return connection;
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Returns a list of suggestions back to the caller with entries that match the passed in prefix string.
	 * Does not make a call to the database.
	 */
	public List<String> getSuggestions(String entry) {
		return null;
	}
	
	/**
	 * Returns the list of Tag objects which match the asked for string tag names.
	 * Makes a call back to the database to do so.
	 */
	public List<Tag> getTags(List<String> requestedTags) {
		return getSampleTags();
	}
	
	public List<Tag> getSampleTags() {
		Tag t1 = new Tag("John Cena", 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0);
		Tag t2 = new Tag("The Undertaker", 5, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 0, 0, 46);
		Tag t3 = new Tag("Triple H", 1, 1, 1, 1, 1, 1, 5, 1, 1, 1, 1, 0, 0, 0);
		Tag t4 = new Tag("Rey Mysterio", 55, 1, 1, 7, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0);
		Tag t5 = new Tag("The Rock", 1, 1, 1, 18, 1, 1, 1, 1, 5, 1, 1, 0, 0, 0);
		LinkedList<Tag> l = new LinkedList<Tag>();
		l.add(t1);
		l.add(t2);
		l.add(t3);
		l.add(t4);
		l.add(t5);
		return l;
	}

}
