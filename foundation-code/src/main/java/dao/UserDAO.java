package dao;

import entities.AppUser;

import java.util.List;

public interface UserDAO {

    //Create
    AppUser createUser(AppUser user);

    //Read
    AppUser getUserById(int id);
    List<AppUser> getAllUsers();


    //Update
    AppUser updateUser(AppUser user);

    //Delete
    void deleteUserById(int id);


}
