package com.example;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        executeInitSqlFile();
        if (checkUserTable()) {
            insertAdmin();
        }
    }

    /**
     * 读取初始化脚本文件，执行数据库/数据表的初始化
     */
    private void executeInitSqlFile() {
        try (InputStream io = DataSourceUtils.class.getClassLoader().getResourceAsStream("init.sql")) {
            byte[] buffer = new byte[1024];
            StringBuilder builder = new StringBuilder();
            int length = 0;
            while ((length = io.read(buffer)) != -1) {
                String s = new String(Arrays.copyOf(buffer, length));
                builder.append(s);
            }
            String reader = builder.toString();
            //LOGGER.info(reader)
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
        String sqlCount = "select count(id) from user";
        try (Connection conn = dSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlCount);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 初始化admin数据
     */
    private void insertAdmin() {
        String sqlInsert = "insert into user(name, number ,password, role) values(?,?,?,?) ";
        try (Connection conn = dSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
            ps.setString(1, "BO");
            ps.setString(2, "2046204601");
            ps.setString(2, "2046204601");
            ps.setInt(3, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
