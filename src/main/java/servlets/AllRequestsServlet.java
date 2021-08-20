package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import json.Item;
import templater.PageGenerator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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

        String tag  = request.getParameter("tag");

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
        pageVariables.put("result",request.getSession());
        return pageVariables;
    }
}
