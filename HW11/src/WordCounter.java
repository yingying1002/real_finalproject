import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class WordCounter {
	/**
	 * To count how many times a Keyword appears.
	 */
	private HashMap<String, String> query;
	private GoogleQuery google;
	private ArrayList<Keyword> keywords;

	public WordCounter(HashMap<String, String> query) {
		this.query = query;
		google = new GoogleQuery();

		KeywordList lst = new KeywordList();
		keywords = lst.getKeywordList();
	}

	private int countKeyword(String content, String keyword) throws IOException {
		// To do a case-insensitive search, we turn the whole content and keyword into
		// upper-case:
		content = content.toUpperCase();
		keyword = keyword.toUpperCase();

		int count = 0;
		int fromIdx = 0;
		int found = -1;

		while ((found = content.indexOf(keyword, fromIdx)) != -1) {
			count++;
			fromIdx = found + keyword.length();
		}

		return count;
	}

	public HashMap<Integer, ArrayList<String>> calcScore() throws IOException {
		HashMap<Integer, ArrayList<String>> retVal = new HashMap<Integer, ArrayList<String>>();

		// get every result link and calculate its score
		for (String title : query.keySet()) {
			// fetch the HTML content of each page
			String content = google.fetchContent(query.get(title));

			int score = 0;

			for (Keyword k : keywords) {
				score += k.weight * countKeyword(content, k.name);
			}

			ArrayList<String> t = new ArrayList<String>();
			// store the title by its score
			if (retVal.containsKey(Integer.valueOf(score))) {
				t = retVal.get(Integer.valueOf(score));
			}
			t.add(title);
			retVal.put(score, t);
		}
		System.out.println("decoded: " + retVal);

		return retVal;
	}

	public List<Integer> sort(HashMap<Integer, ArrayList<String>> score) throws IOException {
		List<Integer> retVal = new ArrayList<Integer>();

		// sort by a descend order of score
		for (Integer sc : score.keySet()) {
			retVal.add(sc);
		}
		Collections.sort(retVal, Collections.reverseOrder());
		return retVal;
	}

}