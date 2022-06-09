package foundation.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import foundation.dao.UserDAOPostgres;
import foundation.entities.AppUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class AppUserServlet extends HttpServlet {

    private final ObjectMapper mapper;
    private final UserDAOPostgres userDAO;
    public AppUserServlet(ObjectMapper mapper, UserDAOPostgres userDAO) {
        this.mapper = mapper;
        this.userDAO = userDAO;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<AppUser> users = userDAO.getAllUsers();

        String respPayload = mapper.writeValueAsString(users);
        resp.setContentType("application/json");
        resp.getWriter().write(respPayload);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("[LOG] - Test received at: " + LocalDateTime.now()); //this is temporary


        AppUser reqUser = mapper.readValue(req.getInputStream(), AppUser.class);
        AppUser dbUser = userDAO.getUserByUsername(reqUser.getUsername());

        System.out.println("req: " + reqUser);
        System.out.println("resp: " + dbUser); //also temporary

        String respPayload = mapper.writeValueAsString(dbUser);
        resp.setContentType("application/json");
        resp.getWriter().write(respPayload);
        resp.setStatus(200);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
