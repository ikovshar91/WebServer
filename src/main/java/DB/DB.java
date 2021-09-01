package DB;

import json.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DB {

    private Connection connection;

    public DB() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS student(id TEXT PRIMARY KEY, lastName TEXT NOT NULL, firstName TEXT NOT NULL, middleName TEXT NOT NULL);").execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void createStudent(Student student) throws SQLException {
        update(String.format("INSERT INTO student(id, lastName, firstName, middleName) VALUES (\"%s\", \"%s\", \"%s\", \"%s\")", student.id, student.lastName, student.firstName, student.middleName));
    }

    public void updateStudent(Student student) throws SQLException {
        update(String.format("UPDATE student SET lastName = \"%s\", firstName = \"%s\", middleName = \"%s\" WHERE id = \"%s\"", student.lastName, student.firstName, student.middleName, student.id));
    }

    public void deleteStudentById(String id) throws SQLException {
        update(String.format("DELETE FROM student WHERE id = \"%s\"", id));
    }

    public Student getStudentById(String id) throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery(String.format("SELECT * FROM student WHERE id = \"%s\"", id));
        Student student = null;
        while (resultSet.next()) {
            student = new Student(
                    resultSet.getString("id"),
                    resultSet.getString("lastName"),
                    resultSet.getString("firstName"),
                    resultSet.getString("middleName")
            );
        }
        return student;
    }

    public List<Student> getAllStudents() throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM student");
        ArrayList<Student> students = new ArrayList<>();
        while (resultSet.next()) {
            students.add(new Student(
                    resultSet.getString("id"),
                    resultSet.getString("lastName"),
                    resultSet.getString("firstName"),
                    resultSet.getString("middleName")
            ));
        }
        return students;
    }

    private void update(String sql) throws SQLException {
        connection.createStatement().execute(sql);
    }

}