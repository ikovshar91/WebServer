package servlets;

import DB.DB;
import com.google.gson.Gson;
import json.Error;
import json.Student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class StudentsServlet extends HttpServlet {

    private static final DB db = new DB();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Gson gson = new Gson();
        String studentId = req.getParameter("id");
        try {
            if (studentId == null) {
                List<Student> students = db.getAllStudents();
                resp.getWriter().print(gson.toJson(students));
                resp.setStatus(200);
            } else {
                Student student = db.getStudentById(studentId);
                if (student == null) {
                    resp.setStatus(404);
                } else {
                    resp.getWriter().print(gson.toJson(student));
                    resp.setStatus(200);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Error error = new Error(String.format("Kakaya-to huynya proizoshla: %s", e.getMessage()));
            resp.getWriter().print(gson.toJson(error));
            resp.setStatus(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Gson gson = new Gson();
        try {
            String rawBody = req.getReader().lines().collect(Collectors.joining());
            Student student = gson.fromJson(rawBody, Student.class);
            db.updateStudent(student);
            resp.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();
            Error error = new Error(String.format("Kakaya-to huynya proizoshla: %s", e.getMessage()));
            resp.getWriter().print(gson.toJson(error));
            resp.setStatus(500);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Gson gson = new Gson();
        try {
            String rawBody = req.getReader().lines().collect(Collectors.joining());
            Student student = gson.fromJson(rawBody, Student.class);
            db.createStudent(student);
            resp.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();
            Error error = new Error(String.format("Kakaya-to huynya proizoshla: %s", e.getMessage()));
            resp.getWriter().print(gson.toJson(error));
            resp.setStatus(500);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Gson gson = new Gson();
        String studentId = req.getParameter("id");
        if (studentId == null) {
            Error error = new Error("Gde 'id' parameter?");
            resp.getWriter().print(gson.toJson(error));
            resp.setStatus(400);
            return;
        }
        try {
            db.deleteStudentById(studentId);
        } catch (SQLException e) {
            e.printStackTrace();
            Error error = new Error(String.format("Kakaya-to huynya proizoshla: %s", e.getMessage()));
            resp.getWriter().print(gson.toJson(error));
            resp.setStatus(500);
        }
        resp.setStatus(200);
    }
}