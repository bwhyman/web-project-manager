package com.example.service;

import com.example.DataSourceUtils;
import com.example.ProjectsCache;
import com.example.entity.Project;
import com.example.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectService {
    public static void updateProject(int pid, String index) {
        String sql = "update project p set p.`index`=?, p.selfaddress=null, p.updatetime=? where p.id=?";
        try (Connection conn = DataSourceUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, index);
            ps.setTimestamp(2, new Timestamp(new Date().getTime()));
            ps.setInt(3, pid);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void updateProjectSelf(int pid, String self) {
        String sql = "update project p set p.selfaddress=?, p.index=null where p.id=?";
        try (Connection conn = DataSourceUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, self);
            ps.setInt(2, pid);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static int addProject(int uid) {
        String sql = "insert into project(user_id) values(?)";
        int id = 0;
        try (Connection conn = DataSourceUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, uid);
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()) {
                while (rs.next()) {
                    id = rs.getInt(1);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }

    public static List<Project> listProjects() {
        List<Project> list = new ArrayList<>();
        String sql = "select user_id, length(photo)!=0 as showphoto, `index`, name, number, clazz, repositoryurl, " +
                "updatetime, selfaddress " +
                "from project p left join user u on p.user_id=u.id order by p.updatetime desc";
        try (Connection conn = DataSourceUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Project p = new Project();
                    User user = new User();
                    user.setId(rs.getInt("user_id"));
                    user.setName(rs.getString("name"));
                    user.setNumber(rs.getString("number"));
                    user.setClazz(rs.getNString("clazz"));
                    user.setShowPhoto(rs.getInt("showphoto"));
                    user.setRepositoryUrl(rs.getString("repositoryurl"));
                    p.setIndex(rs.getString("index"));
                    p.setUpdateTime(rs.getTimestamp("updatetime"));
                    p.setSelfAddress(rs.getString("selfaddress"));
                    p.setUser(user);
                    list.add(p);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public static Project getProject(int uid) {
        Project project = null;
        String sql = "select * from project p where p.user_id=?";
        try(Connection conn = DataSourceUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, uid);
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    project = new Project();
                    project.setId(rs.getInt("id"));
                    project.setIndex(rs.getString("index"));
                    project.setSelfAddress(rs.getString("selfaddress"));
                    project.setUpdateTime(rs.getTimestamp("updatetime"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return project;
    }
}
