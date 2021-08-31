package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.AllRequestsServlet;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new AllRequestsServlet()), "/*");

        Server server = new Server(9999);
        server.setHandler(context);

        try {
            server.start();
            server.join();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
