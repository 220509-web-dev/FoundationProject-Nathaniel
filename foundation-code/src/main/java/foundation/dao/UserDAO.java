package foundation.dao;

import foundation.entities.AppUser;

import java.util.List;

public interface UserDAO {

    //Create
    AppUser createUser(AppUser user);

    //Read
    AppUser getUserById(int id);

    AppUser getUserByUsername(String username);

    List<AppUser> getAllUsers();


    //Update
    AppUser updateUser(AppUser user);

    //Delete
    void deleteUserById(int id);


}
