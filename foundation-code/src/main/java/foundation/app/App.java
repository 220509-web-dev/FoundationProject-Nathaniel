package foundation.app;

import foundation.dao.UserDAO;
import foundation.dao.UserDAOPostgres;
import foundation.entities.AppUser;

public class App {

    public static void main(String[] args) {
//
        UserDAO userDAO = new UserDAOPostgres();
//
//        //Create a new user in the table
//        AppUser newUser = new AppUser(0, "Max", "Maxerton", "MMaxerton", "testestest");
//        userDAO.createUser(newUser);
//
//
//        //Get a user by their ID
//        AppUser user1 = userDAO.getUserById(5);

          //Get user by their Username
          AppUser user3 = userDAO.getUserByUsername("MMaxerton");
//
//
//        Get all users in table
//          List<AppUser> allUsers = userDAO.getAllUsers();
//
//
//        //Update a user in the table by their ID
//        AppUser user2 = userDAO.getUserById(2);
//        user2.setPassword("thisisanupdatedpassword");
//        userDAO.updateUser(user2);
//
//        //Remove a user in the table by their ID
//        userDAO.deleteUserById(2);







    }
}
