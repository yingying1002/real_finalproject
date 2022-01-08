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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestProject
 */
@WebServlet("/TestProject")
public class TestProject extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestProject() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		if (request.getParameter("keyword") == null) {
			String requestUri = request.getRequestURI();
			request.setAttribute("requestUri", requestUri);
			request.getRequestDispatcher("Search.jsp").forward(request, response);
			return;
		}
		System.out.println(request.getParameter("keyword"));

		GoogleQuery google = new GoogleQuery();
		HashMap<String, String> query = google.query(request.getParameter("keyword"));

		String[][] s = new String[query.size()][2];
		request.setAttribute("query", s);

		WordCounter calc = new WordCounter(query);
		HashMap<Integer, ArrayList<String>> score = calc.calcScore();
		List<Integer> order = calc.sort(score);

		// to parse HashMap into String[][] in order
		int num = 0;
		for (Integer i : order) {
			if (score.get(i).size() > 1) {
				for (int j = 0; j < score.get(i).size(); j++) {
					String key = score.get(i).get(j);
					String value = query.get(key);
					s[num][0] = key;
					s[num][1] = value;
					num++;
				}
			} else {
				String key = score.get(i).get(0);
				String value = query.get(key);
				s[num][0] = key;
				s[num][1] = value;
				num++;
			}

		}

		request.getRequestDispatcher("googleitem.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}