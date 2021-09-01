package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.AllRequestsServlet;
import servlets.StudentsServlet;

import java.io.IOException;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        ServletContextHandler context = new ServletContextHandler(1);
        context.addServlet(new ServletHolder(new AllRequestsServlet()), "/hello");
        context.addServlet(new ServletHolder(new StudentsServlet()), "/students");
        Server server = new Server(9999);
        server.setHandler(context);

        try {
            server.start();
            server.join();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }
}