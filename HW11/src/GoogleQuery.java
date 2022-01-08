import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;

public class GoogleQuery {
	/**
	 * To crawl down web content and identify specific info.
	 */
	public GoogleQuery() {
	}

	public String fetchContent(String url) throws IOException {
		String retVal = "";

		try {
			URL u = new URL(url);

			URLConnection conn = u.openConnection();

			conn.setRequestProperty("User-agent", "Chrome/96.0.4664.110");

			InputStream in = conn.getInputStream();

			InputStreamReader inReader = new InputStreamReader(in, "utf-8");

			BufferedReader bufReader = new BufferedReader(inReader);
			String line = null;

			while ((line = bufReader.readLine()) != null) {
				retVal += line;

			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return retVal;
	}

	public HashMap<String, String> query(String searchKeyword) throws IOException {
		String[] splitStr = searchKeyword.split(" ");
		if (splitStr.length > 1) {
			String comb = "";
			for (int i = 0; i < splitStr.length; i++) {
				comb += splitStr[i] + "+";
			}
			searchKeyword = comb;
		}
		if (!searchKeyword.contains("movie")) {
			searchKeyword += "+movie";
		}
		//if (!searchKeyword.contains("電影")) {
		//	searchKeyword += "+電影";
		//}
		String url = "http://www.google.com/search?q=" + searchKeyword + "&oe=utf8&num=20";
		System.out.println("url: " + url);

		String content = fetchContent(url);
		HashMap<String, String> retVal = new HashMap<String, String>();

		Document doc = Jsoup.parse(content);

		Elements lis = doc.select("div");

		lis = lis.select(".kCrYT");

		for (Element li : lis) {
			try {
				// parse down URL link
				String citeUrl = li.select("a").attr("href");
				System.out.println("origin: " + citeUrl);
				if (citeUrl.startsWith("/url?q=")) {
					citeUrl = citeUrl.replace("/url?q=", "");
				}
				String[] splittedString = citeUrl.split("&sa=");
				if (splittedString.length > 1) {
					citeUrl = splittedString[0];
				}
				// url decoding from UTF-8
				citeUrl = java.net.URLDecoder.decode(citeUrl, StandardCharsets.UTF_8);

				// parse down title
				String title = li.select("a").select(".vvjwJb").text();
				if (title.equals("")) {
					continue;
				}

				System.out.println(title + "," + citeUrl);
				retVal.put(title, citeUrl);

			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}
		return retVal;
	}

}