package com.example;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.logging.Logger;


@WebListener
public class DataSourceUtils implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(DataSourceUtils.class.getName());
    @Resource(name = "jdbc/MySQL")
    private static DataSource dSource;

    public static Connection getConnection() throws SQLException {
        return dSource.getConnection();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LocalDateTime dl = null;
        String dlStr = null;
        LOGGER.info("contextInitialized");
        executeInitSqlFile();
        if (!checkUserTable()) {
             dl = LocalDateTime.now().plusMonths(4);
             dlStr = dl.toString().replace("T", " ").substring(0, 16);
            sce.getServletContext().setAttribute("deadlineT", dl);
            sce.getServletContext().setAttribute("deadline", dlStr);
            insertAdmin(dlStr);
        } else {
            String sql = "select u.clazz from user u where u.number=?";
            try (Connection conn = dSource.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "2046204601");
                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        dlStr = rs.getString("clazz");
                        dl = LocalDateTime.parse(dlStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        sce.getServletContext().setAttribute("deadlineT", dl);
        sce.getServletContext().setAttribute("deadline", dlStr);
    }

    /**
     * 读取初始化脚本文件，执行数据库/数据表的初始化
     */
    private void executeInitSqlFile() {
        try (InputStream io = DataSourceUtils.class.getClassLoader().getResourceAsStream("init.sql")) {
            byte[] buffer = new byte[1024];
            StringBuilder builder = new StringBuilder();
            int length;
            while ((length = io.read(buffer)) != -1) {
                String s = new String(Arrays.copyOf(buffer, length));
                builder.append(s);
            }
            String reader = builder.toString();
            String[] strings = reader.split("###");
            for (String sql : strings) {
                LOGGER.info(sql);
                try (Connection conn = dSource.getConnection();
                     PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查user表是否为空。
     * @return
     */
    private boolean checkUserTable() {
        String sqlCount = "select u.id from user u limit 1";
        try (Connection conn = dSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlCount);
             ResultSet rs = ps.executeQuery()) {
            boolean r = rs.next();
            LOGGER.info("checkUserTable: " + r);
            return r;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 初始化admin数据
     */
    private void insertAdmin(String dlStr) {
        String sqlInsert = "insert into user(name, number ,password, role, clazz, photo) values(?,?,?,?,?,?) ";
        try (Connection conn = dSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
            ps.setString(1, "BO");
            ps.setString(2, "2046204601");
            ps.setString(3, "2046204601");
            ps.setInt(4, 1);
            ps.setString(5, dlStr);
            ps.setString(6, "");
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
