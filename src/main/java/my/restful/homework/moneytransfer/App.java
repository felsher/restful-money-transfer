package my.restful.homework.moneytransfer;

import my.restful.homework.moneytransfer.controller.AccountController;
import my.restful.homework.moneytransfer.controller.TransactionController;
import my.restful.homework.moneytransfer.entity.Account;
import my.restful.homework.moneytransfer.entity.User;
import my.restful.homework.moneytransfer.error.H2DbException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.math.BigDecimal;

import static my.restful.homework.moneytransfer.dao.AccountDAO.getAccountDao;

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
            prepareTestAccounts();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }

    private static void prepareTestAccounts() throws H2DbException {
        User userFirst = new User(1L);
        User userSecond = new User(2L);
        Account accountFirst = new Account(userFirst, new BigDecimal(3000.0000));
        Account accountSecond = new Account(userSecond, new BigDecimal(1000.000));

        getAccountDao().insertAccount(accountFirst);
        getAccountDao().insertAccount(accountSecond);
    }

}
