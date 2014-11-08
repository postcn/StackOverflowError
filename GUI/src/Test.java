import java.util.ArrayList;
import java.util.List;


public class Test {

	public static void main(String[] args) {
		Backend b = Backend.getInstance();
		ArrayList<String> tags = new ArrayList<String>();
		tags.add(".net");
		tags.add("c");
		tags.add("b");
		tags.add("Ed Goldthorpe");
		List<Tag> res = b.getTags(tags);
		for (Tag t: res) {
			System.out.println(t.toString());
		}
	}
}