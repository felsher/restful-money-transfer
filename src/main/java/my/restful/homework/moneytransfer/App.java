package my.restful.homework.moneytransfer;

import my.restful.homework.moneytransfer.controller.AccountController;
import my.restful.homework.moneytransfer.controller.TransactionController;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class App {

    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(5555);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        String accountControllerName = AccountController.class.getCanonicalName();
        String transferControllerName = TransactionController.class.getCanonicalName();

        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                accountControllerName + ";" + transferControllerName
        );

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }

}
