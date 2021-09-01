package servlets;

import com.google.gson.Gson;
import json.Item;
import json.Root;
import json.Student;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class AllRequestsServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        Student student = new Student("1","Igor","Kovshar","Romanovich");
        Gson gson = new Gson();
        response.setContentType("application/json");
       response.getWriter().print(gson.toJson(student));
    }


    // public void doPost(HttpServletRequest request,
//                       HttpServletResponse response) throws IOException {
//
//
//        String tag  = request.getParameter("tag");
//
//        String name = tag.equals("") ? "1": tag;
//        URL url = new URL("https://api.stackexchange.com/2.2/search?pagesize=100&order=desc&sort=creation&tagged="+
//              name  +"&site=stackoverflow");
//
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//        connection.setRequestMethod("GET");
//        connection.setRequestProperty("Content-Type", "application/json");
//
//
//        GZIPInputStream gis = new GZIPInputStream(connection.getInputStream());
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gis, StandardCharsets.UTF_8));
//
//        String inputLine;
//
//        StringBuilder stringBuilder = new StringBuilder();
//
//
//        while ((inputLine = bufferedReader.readLine()) != null) {
//            stringBuilder.append((inputLine));
//        }
//        bufferedReader.close();
//
//
//        Gson gson = new Gson();
//        Root root = gson.fromJson(stringBuilder.toString(), Root.class);
//
////        for (Item root1 : root.items){
////         //   pageVariables.put("result", gson.toJson(root1));
////        }
//        response.setContentType("text/html;charset=utf-8");
//
//        response.getWriter().println("Hello world");
//    }
}
