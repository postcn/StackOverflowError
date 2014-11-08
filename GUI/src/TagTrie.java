import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TagTrie {
	private Node root;
	private LinkedList<String> suggestions;
	
	public TagTrie() {
		this.root = new Node("");
		this.suggestions = new LinkedList<String>();
		List<String> tags = Backend.getAllTagNames();
		Iterator<String> iter = tags.iterator();
		while(iter.hasNext()) {
			String line = iter.next().toLowerCase();
			Node node = this.root, n;
			String c;
			int i = 0;

			while (i < line.length()) {
				n = null;
				c = line.substring(i, ++i);
				for (Node child: node.children) {
					if (child.equals(c)) {
						n = child;
						break;
					}
				}

				if (n != null) {
					node = n;
				} else {
					Node t = new Node(c);
					node.children.add(t);
					node = t;
				}
			}
			node.word = line;
		}
	}

	public LinkedList<String> getSuggestions(String prefix) {
		this.suggestions.clear();
		prefix = prefix.trim().toLowerCase();
		Node prefixRoot = getPrefixNode(prefix);
		if (prefixRoot == null) {
			return new LinkedList<String>();
		}
		else {
			generateSuggestions(prefixRoot);
		}
		return this.suggestions;
	}

	private void generateSuggestions(Node n) {
		if (n.word != null) {
			this.suggestions.add(n.word);
		}
		for (Node c: n.children) {
			generateSuggestions(c);
		}
	}

	private Node getPrefixNode(String prefix) {
		Node node = this.root, n;
		String c;
		int i = 0;
		while (i < prefix.length()) {
			n = null;
			c = prefix.substring(i, ++i);
			for (Node child: node.children) {
				if (child.equals(c)) {
					n = child;
					break;
				}
			}
			if (n != null) {
				node = n;
			} else {
				return null;
			}
		}
		return node;
	}

	// Test function to check that all words made it. Used for verifying Kyle's
	// questionable work
	public void containsAllWords() {
		try (BufferedReader reader = Files.newBufferedReader(
				Paths.get("tagnames.txt"), Charset.forName("UTF-8"))) {
			String line;
			Node n;
			while ((line = reader.readLine()) != null) {
				line = line.trim().toLowerCase();
				n  = getPrefixNode(line);
				if (n == null) {
					System.out.println(line + " is not a prefix node in the trie.");
				}
				if (n.word == null) {
					System.out.println(line + " has no word at its prefix node");
				}
				else if (!line.equals(n.word)) {
					System.out.println(line + " is not listed the listed word at its prefix node.");
				}
			}
		}
		catch (IOException e) {
			System.out.println("Kyle has failed us all. He can't even open the file.");
		}
	}

	class Node {
		public String value; // The character leading to this node
		public String word; // The end word for this node, if it is one
		public ArrayList<Node> children; // The Node's children

		public Node(String value) {
			this.value = value;
			this.word = null;
			this.children = new ArrayList<Node>();
		}

		// Allow us to see if the node represents a character
		@Override
		public boolean equals(Object n) {
			return this.value.equals((String) n);
		}
	}

	public static void main(String args[]) {
		TagTrie t = new TagTrie();
		t.containsAllWords();
		System.out.println(t.getSuggestions("perl").size());
	}
}
