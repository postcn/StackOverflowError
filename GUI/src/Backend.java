import java.util.List;


public class Backend {
	
	private static Backend instance;
	
	private Backend() {
		//Initialize and create the trie so that we can look up into it.
	}
	
	public Backend getInstance() {
		if (instance == null) {
			instance = new Backend();
		}
		return instance;
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
		return null;
	}

}
