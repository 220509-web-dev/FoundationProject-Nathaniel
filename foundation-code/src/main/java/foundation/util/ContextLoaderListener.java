package foundation.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import foundation.dao.UserDAOPostgres;
import foundation.services.AuthService;
import foundation.servlets.AppUserServlet;
import foundation.servlets.AuthServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ObjectMapper mapper = new ObjectMapper();
        UserDAOPostgres userDAO = new UserDAOPostgres();
        AuthService userService = new AuthService(userDAO);
        AuthServlet authServlet = new AuthServlet(mapper, userService);
        AppUserServlet appUserServlet = new AppUserServlet(mapper, userDAO);

        ServletContext context = sce.getServletContext();
        context.addServlet("AuthServlet", authServlet).addMapping("/login/*");
        context.addServlet("AppUserServlet", appUserServlet).addMapping("/user/*");
    }
}
