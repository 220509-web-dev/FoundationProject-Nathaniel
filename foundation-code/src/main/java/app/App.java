package app;

import dao.UserDAO;
import dao.UserDAOPostgres;
import entities.AppUser;
import java.util.List;

public class App {

    public static void main(String[] args) {

        UserDAO userDAO = new UserDAOPostgres();

        //Create a new user in the table
        //AppUser newUser = new AppUser(0, "Jack", "White", "jwhite", "moredumbpass");
        //userDAO.createUser(newUser);
        //System.out.println("New record has been added to the table:" + newUser);

        //Get a user by their ID
        AppUser user1 = userDAO.getUserById(2);
        System.out.println("Here is user with the Id of 2 " + user1);

        //Get all users in table
        List<AppUser> allUsers = userDAO.getAllUsers();
        System.out.println("Here are all the users in the table: " + allUsers);

        //Update a user in the table by their ID
        //AppUser user2 = userDAO.getUserById(2);
        //user2.setPassword("thisisanupdatedpassword");
        //userDAO.updateUser(user2);

        //Remove a user in the table by their ID
        //userDAO.deleteUserById(2);







    }
}
