package com.example.service;

import com.example.DataSourceUtils;
import com.example.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AdminService {
    public static void addStudents(List<User> users) {
        /*String sql2 = "delete from user where user.number!=2046204601";
        try(Connection conn = DataSourceUtils.getConnection();
            PreparedStatement s = conn.prepareStatement(sql2)) {
            s.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
        String sql = "insert into user(number, name, password, clazz, role) values(?,?,?,?,?)";
        try(Connection conn = DataSourceUtils.getConnection();
            PreparedStatement s = conn.prepareStatement(sql)) {
            for (User u : users) {
                s.setString(1, u.getNumber());
                s.setString(2, u.getName());
                s.setString(3, u.getNumber());
                s.setString(4, u.getClazz());
                s.setInt(5, u.getRole());
                s.addBatch();
            }
            s.executeBatch();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
