package foundation.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import foundation.dto.ErrorResponse;
import foundation.entities.AppUser;
import foundation.exception.DataSourceException;
import foundation.exception.InvalidCredentialsException;
import foundation.exception.InvalidRequestException;
import foundation.services.AuthService;
import foundation.util.Logger;
import foundation.util.LoggerLevels;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

public class AuthServlet extends HttpServlet {

    private final ObjectMapper mapper;
    private final AuthService userService;

    public AuthServlet(ObjectMapper mapper, AuthService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        String log;
        try {
            HashMap<String, Object> credentials = mapper.readValue(req.getInputStream(), HashMap.class);
            String providedUsername = (String) credentials.get("username");
            String providedPassword = (String) credentials.get("password");
            AppUser dbUser = userService.AuthService(providedUsername,providedPassword);
            System.out.println("Match!");
            HttpSession session = req.getSession();
            session.setAttribute("auth-user", dbUser);

            String respPayload = mapper.writeValueAsString(dbUser);
            resp.setContentType("application/json");
            resp.getWriter().write(respPayload);
            resp.setStatus(200);

        } catch (InvalidCredentialsException e) {
            resp.setStatus(400);
            resp.getWriter().write(mapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));
        } catch (InvalidRequestException e) {
            resp.setStatus(400);
            resp.getWriter().write(mapper.writeValueAsString(new ErrorResponse(400,e.getMessage())));
        } catch (DataSourceException e) {
            resp.setStatus(500);
            System.out.println(e.getMessage());
            resp.getWriter().write(mapper.writeValueAsString(new ErrorResponse(500, "An internal error has occurred.")));
        }

    }
}
