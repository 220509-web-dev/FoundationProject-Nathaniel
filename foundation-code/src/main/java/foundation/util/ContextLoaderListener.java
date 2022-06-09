package foundation.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import foundation.dao.UserDAOPostgres;
import foundation.services.UserService;
import foundation.servlets.AuthServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ObjectMapper mapper = new ObjectMapper();
        UserDAOPostgres userDAO = new UserDAOPostgres();
        UserService userService = new UserService(userDAO);
        AuthServlet authServlet = new AuthServlet(mapper, userService);

        ServletContext context = sce.getServletContext();
        context.addServlet("AuthServlet", authServlet).addMapping("/login/*");
    }
}
