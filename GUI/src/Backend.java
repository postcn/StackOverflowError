import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Backend {

	private static Backend instance;
	private static Connection connection;
	private static String TAG_QUERY = "SELECT tag FROM StackOverflowError.Analysis;";
	private static String GET_TAGS_QUERY = "SELECT * FROM StackOverflowError.Analysis WHERE tag IN (%s)";
	private static TagTrie trie;

	private Backend() {
		// Initialize and create the trie so that we can look up into it.
		trie = new TagTrie();
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
		} else {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager
						.getConnection("jdbc:mysql://hadoop04.csse.rose-hulman.edu?user=selector");
				return connection;
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Returns a list of suggestions back to the caller with entries that match
	 * the passed in prefix string. Does not make a call to the database.
	 */
	public List<String> getSuggestions(String entry) {
		return trie.getSuggestions(entry);
	}

	public static List<String> getAllTagNames() {
		LinkedList<String> tags = new LinkedList<String>();
		Connection c = getDatabaseConnection();
		try {
			ResultSet tagsResult = c.createStatement().executeQuery(TAG_QUERY);
			while (tagsResult.next()) {
				tags.add(tagsResult.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tags;
	}

	/**
	 * Returns the list of Tag objects which match the asked for string tag
	 * names. Makes a call back to the database to do so.
	 */
	public List<Tag> getTags(List<String> requestedTags) {
		LinkedList<Tag> tags = new LinkedList<Tag>();
		Connection c = getDatabaseConnection();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < requestedTags.size() - 1; i++) {
			builder.append("'" + requestedTags.get(i) + "', ");
		}
		builder.append("'" + requestedTags.get(requestedTags.size() - 1) + "'");
		try {
			ResultSet results = c.createStatement().executeQuery(
					String.format(GET_TAGS_QUERY, builder.toString()));
			while (results.next()) {
				Tag t = new Tag(results.getString(1), results.getInt(2),
						results.getInt(3), results.getInt(4),
						results.getInt(5), results.getInt(6),
						results.getInt(7), results.getInt(8),
						results.getInt(9), results.getInt(10),
						results.getInt(11), results.getInt(12),
						results.getInt(13), results.getInt(14), results.getInt(15));
				tags.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tags;
	}

	public List<Tag> getSampleTags() {
		Tag t1 = new Tag("John Cena", 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0);
		Tag t2 = new Tag("The Undertaker", 5, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 0,
				0, 46);
		Tag t3 = new Tag("Triple H", 1, 1, 1, 1, 1, 1, 5, 1, 1, 1, 1, 0, 0, 0);
		Tag t4 = new Tag("Rey Mysterio", 55, 1, 1, 7, 1, 1, 1, 1, 1, 1, 1, 0,
				0, 0);
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
