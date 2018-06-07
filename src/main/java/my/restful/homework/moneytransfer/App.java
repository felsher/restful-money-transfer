package my.restful.homework.moneytransfer;

import com.fasterxml.jackson.core.JsonProcessingException;
import my.restful.homework.moneytransfer.controller.AccountController;
import my.restful.homework.moneytransfer.dao.AccountDAO;
import my.restful.homework.moneytransfer.entity.Account;
import my.restful.homework.moneytransfer.entity.User;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.math.BigDecimal;
import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(5555);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        String transferControllerName = AccountController.class.getCanonicalName();
        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", transferControllerName);

        prepareTestAccounts();

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }

    private static void prepareTestAccounts() throws SQLException, ClassNotFoundException {
        AccountDAO accountDAO = new AccountDAO();

        User userFirst = new User("Umputun", "U");
        User userSecond = new User("Bobuk", "B");
        Account accountFirst = new Account(userFirst, new BigDecimal(3000.0000));
        Account accountSecond = new Account(userSecond, new BigDecimal(1000.000));

        accountDAO.insertAccount(accountFirst);
        accountDAO.insertAccount(accountSecond);
    }

}
