package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import json.Item;
import templater.PageGenerator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class AllRequestsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        Map<String, Object> pageVariables = createPageVariablesMap(request);
        pageVariables.put("tag", "");
        response.getWriter().println(PageGenerator.instance().getPage("page.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = createPageVariablesMap(request);



        URL url = new URL("https://api.stackexchange.com/2.2/search?pagesize=100&order=desc&sort=creation&tagged=python&site=stackoverflow");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");


        GZIPInputStream gis = new GZIPInputStream(connection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gis, StandardCharsets.UTF_8));

        String inputLine;

        StringBuffer kek = new StringBuffer();

        while ((inputLine = bufferedReader.readLine()) != null) {
            kek.append((inputLine));
        }
        bufferedReader.close();

        String tag  = request.getParameter("tag");

        Gson gson = new Gson();
        Item azaz = gson.fromJson(String.valueOf(kek), Item.class);

        pageVariables.put("result", azaz);

        response.setContentType("text/html;charset=utf-8");

        if (tag == null || tag.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        pageVariables.put("tag", tag == null ? "" : tag);

        response.getWriter().println(PageGenerator.instance().getPage("page.html", pageVariables));
    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        //pageVariables.put("result",request.getSession());
        return pageVariables;
    }
}
