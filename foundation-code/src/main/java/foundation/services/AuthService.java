package foundation.services;


import foundation.dao.UserDAOPostgres;
import foundation.entities.AppUser;
import foundation.exception.DataSourceException;
import foundation.exception.InvalidCredentialsException;

public class AuthService {

    private final UserDAOPostgres userDAO;

    public AuthService(UserDAOPostgres userDAO) {
        this.userDAO=userDAO;
    }

    public AppUser AuthService(String providedUsername, String providedPassword) {
        try {
            AppUser user = userDAO.getUserByUsername(providedUsername);
            System.out.println(user);

            if (user == null || !user.getPassword().equals(providedPassword) || !user.getUsername().equals(providedUsername)) {
                System.out.println("WRONG");
                String log = "Provided credentials were invalid";
                throw new InvalidCredentialsException(log);
            }
            System.out.println("Logging in");
            return user;
        } catch (NullPointerException e) {
            System.out.println("Null");
            throw new DataSourceException();
        }
    }
}
