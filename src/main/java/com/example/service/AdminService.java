package com.example.service;

import com.example.DataSourceUtils;
import com.example.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminService {
    public static void addStudents(List<User> users) {
        String checkSql = "select id from user where number=?";
        String sql = "insert into user(number, name, password, clazz, role) values(?,?,?,?,?)";
        for (User u : users) {
            try(Connection conn = DataSourceUtils.getConnection();
                PreparedStatement s = conn.prepareStatement(checkSql);) {
                s.setString(1, u.getNumber());
                try(ResultSet rs = s.executeQuery()) {
                    if(!rs.next()) {
                        PreparedStatement sInsert = conn.prepareStatement(sql);
                        sInsert.setString(1, u.getNumber());
                        sInsert.setString(2, u.getName());
                        sInsert.setString(3, u.getNumber());
                        sInsert.setString(4, u.getClazz());
                        sInsert.setInt(5, u.getRole());
                        sInsert.executeUpdate();
                    }
                }
            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void removeDeploy(String number) {
        String sql = "delete p from project p join user u on u.id=p.user_id where u.number=?";
        try(Connection conn = DataSourceUtils.getConnection();
            PreparedStatement s = conn.prepareStatement(sql)) {
            s.setString(1, number);
            s.executeUpdate();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void updateDeadline(String time, String number) {
        String sql = "update user u set u.clazz=? where u.number=?";
        try(Connection conn = DataSourceUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, time);
            ps.setString(2, number);
            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
