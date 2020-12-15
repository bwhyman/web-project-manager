package com.example.service;

import com.example.DataSourceUtils;
import com.example.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    public static User getUser(String number, String password) {
        User user = null;
        String sql = "select * from user where number=? and password=?";
        try(Connection conn = DataSourceUtils.getConnection();
            PreparedStatement s = conn.prepareStatement(sql)) {
            s.setString(1, number);
            s.setString(2, password);
            try(ResultSet rs = s.executeQuery()) {
                while (rs.next()) {
                    user = new User();
                    user.setRole(rs.getInt("role"));
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setNumber(rs.getString("number"));
                    user.setPhoto(rs.getString("photo"));
                    user.setRepositoryUrl(rs.getString("repositoryurl"));
                    user.setClazz(rs.getString("clazz"));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    public static void updatePhoto(int uid, String fileName) {
        String sql = "update user u set u.photo=? where u.id=?";
        try(Connection conn = DataSourceUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fileName);
            ps.setInt(2, uid);
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void updateRepository(int uid ,String repo) {
        String sql = "update user u set u.repositoryurl=? where u.id=?";
        try (Connection conn = DataSourceUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, repo);
            ps.setInt(2, uid);
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static String getPhoto(int uid) {
        String sql = "select u.photo from user u where u.id=?";
        String rel = "";
        try (Connection conn = DataSourceUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, uid);
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rel = rs.getString("photo");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rel;
    }

    public static void updatePassword(int uid, String pwd) {
        String sql = "update user u set u.password=? where u.id=?";
        try (Connection conn = DataSourceUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pwd);
            ps.setInt(2, uid);
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
