package com.parkit.parkingsystem.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DataBaseConfig {

    private static final Logger LOGGER = LogManager.getLogger("DataBaseConfig");

    /**
     * CREATE A CONNECTION WITH DATABASE
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection getConnection()
            throws ClassNotFoundException, SQLException {

        Properties prop = new Properties();
        try (InputStream input = this.getClass()
                .getClassLoader()
                .getResourceAsStream("application.properties")) {
            prop.load(input);
        } catch (IOException ex) {
            LOGGER.error("Cannot load properties file");
        }

        LOGGER.info("Create DB connection");
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                prop.getProperty("db.prod.url"),
                prop.getProperty("db.prod.user"),
                prop.getProperty("db.prod.pass"));
    }

    /**
     * EXIT THE CONNECTION WITH DATABASE
     * @param con
     */
    public void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
                LOGGER.info("Closing DB connection");
            } catch (SQLException e) {
                LOGGER.error("Error while closing connection", e);
            }
        }
    }

    /**
     * CLOSING PREPARED STATEMENT
     * @param ps
     */
    public void closePreparedStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
                LOGGER.info("Closing Prepared Statement");
            } catch (SQLException e) {
                LOGGER.error("Error while closing prepared statement", e);
            }
        }
    }

    /**
     * CLOSING RESULT SET
     * @param rs
     */
    public void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
                LOGGER.info("Closing Result Set");
            } catch (SQLException e) {
                LOGGER.error("Error while closing result set", e);
            }
        }
    }
}
