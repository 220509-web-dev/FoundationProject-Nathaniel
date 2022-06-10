package foundation.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import foundation.dao.UserDAOPostgres;
import foundation.dto.ErrorResponse;
import foundation.entities.AppUser;
import foundation.exception.InvalidRequestException;
import foundation.exception.UsernameNotAvailableException;

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
        try {
            List<AppUser> users = userDAO.getAllUsers();

            String respPayload = mapper.writeValueAsString(users);
            resp.setContentType("application/json");
            resp.getWriter().write(respPayload);
        } catch (InvalidRequestException e) {

        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("[LOG] - Test received at: " + LocalDateTime.now()); //this is temporary
        resp.setContentType("application/json");
        try {
            AppUser reqUser = mapper.readValue(req.getInputStream(), AppUser.class);
            String reqUsername = reqUser.getUsername();
            System.out.println(reqUsername);
            String reqFirstName = reqUser.getFirst_name();
            System.out.println(reqFirstName);
            AppUser dbUser;
            System.out.println("req: " + reqUser);
            if (reqUsername != null) {
                if (reqFirstName != null) {
                    userDAO.createUser(reqUser);

                    String respPayload = mapper.writeValueAsString(reqUser.getId());
                    resp.getWriter().write(respPayload);}

                dbUser = userDAO.getUserByUsername((reqUser.getUsername()));
                System.out.println("resp: " + dbUser); //also temporary

                String respPayload = mapper.writeValueAsString(dbUser);
                resp.getWriter().write(respPayload);


            }else {
                dbUser = userDAO.getUserById((reqUser.getId()));
                System.out.println("resp: " + dbUser); //also temporary

                String respPayload = mapper.writeValueAsString(dbUser);
                resp.setContentType("application/json");
                resp.getWriter().write(respPayload);
            }
            resp.setStatus(200);
        } catch (InvalidRequestException e) {
            resp.setStatus(400);
            resp.getWriter().write(mapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));
        } catch (UsernameNotAvailableException e) {
            resp.setStatus(400);
            resp.getWriter().write(mapper.writeValueAsString(new ErrorResponse(400, "This Username is not available, please try again.")));
        }
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
