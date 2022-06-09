package foundation.dao;

import foundation.entities.AppUser;
import foundation.exception.InvalidCredentialsException;
import foundation.exception.UsernameNotAvailableException;
import foundation.util.ConnectionUtil;
import foundation.util.Logger;
import foundation.util.LoggerLevels;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class UserDAOPostgres implements UserDAO {

    private String log;

    //Overrides createUser from the UserDAO interface
    @Override
    public AppUser createUser(AppUser user) {

        try (Connection connect = ConnectionUtil.getInstance().getConnection()) {

            String sql = "insert into foundations_project.app_users values (default, ?, ?, ?, ?)";
            PreparedStatement ps = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,user.getFirst_name());
            ps.setString(2,user.getLast_name());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int generatedID = rs.getInt("id");
            user.setId(generatedID);

            System.out.println("New record has been added to the table: " + user.getUsername());
            log = "New user created with id of: " + user.getId();
            Logger.log(log, LoggerLevels.INFO);
            return user;

        } catch (SQLException e) {
            log = "User with username of: " + user.getUsername() + " already exists.";
            Logger.log(log, LoggerLevels.ERROR);
            throw new UsernameNotAvailableException(log);

        }
    }

    //Overrides getUserById from the UserDAO interface
    @Override
    public AppUser getUserById(int id) {

        try (Connection connect = ConnectionUtil.getInstance().getConnection()) {
            String sql = "select * from foundations_project.app_users where id = ?";
            PreparedStatement ps = connect.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            rs.next();

            AppUser user = new AppUser();
            user.setId(id);
            user.setFirst_name(rs.getString("first_name"));
            user.setLast_name(rs.getString("last_name"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));

            System.out.println("Here is your user information: " +  user);

            log = "User with id of: " + user.getId() + " looked up";
            Logger.log(log, LoggerLevels.INFO);
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //Overrides getUserByUsername from the UserDAO interface
    @Override
    public AppUser getUserByUsername(String username) {

        AppUser user = new AppUser();
        try (Connection connect = ConnectionUtil.getInstance().getConnection()) {
            String sql = "select * from foundations_project.app_users where username = ?";
            PreparedStatement ps = connect.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            rs.next();

            user.setId(rs.getInt("id"));
            user.setFirst_name(rs.getString("first_name"));
            user.setLast_name(rs.getString("last_name"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));

            //System.out.println("Here is your user information: " +  user);

            log = user.getUsername() + " looked up";
            Logger.log(log, LoggerLevels.INFO);

        } catch (SQLException e) {
            log = "User with username of: " + username + " cannot be found.";
            Logger.log(log, LoggerLevels.ERROR);
        }
        return user;
    }

    //Overrides getAllUsers from the UserDAO interface
    @Override
    public List<AppUser> getAllUsers() {

        try (Connection connect = ConnectionUtil.getInstance().getConnection()) {
            System.out.println("Here are all the users in the database: \n");
            String sql = "select * from foundations_project.app_users";
            PreparedStatement ps = connect.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<AppUser> users = new ArrayList<>();

            while (rs.next()) {
                AppUser user = new AppUser();
                user.setId(rs.getInt("id"));
                user.setFirst_name(rs.getString("first_name"));
                user.setLast_name(rs.getString("last_name"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                users.add(user);
                System.out.println(user + "\n");
            }

            log = "All users looked up";
            Logger.log(log, LoggerLevels.INFO);

            return users;

        } catch (SQLException e) {
            log = "Error accessing information from database";
            Logger.log(log, LoggerLevels.ERROR);
            e.printStackTrace();
        }
        return null;

    }

    //Overrides updateUser from the UserDAO interface
    @Override
    public AppUser updateUser(AppUser user) {

        try (Connection connect = ConnectionUtil.getInstance().getConnection()) {
            String sql = "update foundations_project.app_users set first_name = ?, last_name = ?, username = ?, password = ? where id = ?";
            PreparedStatement ps = connect.prepareStatement(sql);
            ps.setString(1, user.getFirst_name());
            ps.setString(2, user.getLast_name());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getId());

            ps.execute();

            log = "User with id of: " + user.getId() + " Updated";
            Logger.log(log, LoggerLevels.WARNING);
            System.out.println(log);
            return user;

        } catch (SQLException e) {
            log = "Error updating user" + user;
            Logger.log(log, LoggerLevels.ERROR);
            e.printStackTrace();
        }

        return null;
    }

    //Overrides deleteUserById from the UserDAO interface
    @Override
    public void deleteUserById(int id) {

        try (Connection connect = ConnectionUtil.getInstance().getConnection()) {
            String sql = "delete from foundations_project.app_users where id = ?";
            PreparedStatement ps = connect.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();

            log = "User with id " + id + " deleted from database";
            Logger.log(log, LoggerLevels.WARNING);
            System.out.println(log);

        } catch (SQLException e) {
            log = "Error deleting user with id" + id;
            Logger.log(log, LoggerLevels.ERROR);
            e.printStackTrace();
        }
    }





}
