package dao;

import entities.AppUser;
import util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOPostgres implements UserDAO {

    //Overrides createUser from the UserDAO interface
    @Override
    public AppUser createUser(AppUser user) {

        try (Connection connect = ConnectionUtil.getConnection()) {
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
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Overrides getUserById from the UserDAO interface
    @Override
    public AppUser getUserById(int id) {

        try (Connection connect = ConnectionUtil.getConnection()){
            String sql = "select * from foundations_project.app_users where id = ?";
            PreparedStatement ps = connect.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            rs.next();

            AppUser user = new AppUser();
            user.setId(id);
            user.setFirst_name(rs.getString("first_name"));
            user.setLast_name(rs.getString("last_name"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Overrides getAllUsers from the UserDAO interface
    @Override
    public List<AppUser> getAllUsers() {

        try (Connection connect = ConnectionUtil.getConnection()) {
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
            }
            return users;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    //Overrides updateUser from the UserDAO interface
    @Override
    public AppUser updateUser(AppUser user) {

        try (Connection connect = ConnectionUtil.getConnection()) {
            String sql = "update foundations_project.app_users set first_name = ?, last_name = ?, username = ?, password = ? where id = ?";
            PreparedStatement ps = connect.prepareStatement(sql);
            ps.setString(1, user.getFirst_name());
            ps.setString(2, user.getLast_name());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getId());

            ps.execute();
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    //Overrides deleteUserById from the UserDAO interface
    @Override
    public void deleteUserById(int id) {

        try (Connection connect = ConnectionUtil.getConnection()) {
            String sql = "delete from foundations_project.app_users where id = ?";
            PreparedStatement ps = connect.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





}
