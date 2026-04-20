/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.monitoringcontrol.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author nosli
 */
public class MySqlConfig {

    public static Connection getConnection() throws Exception {
        
        String mysqlClass = "com.mysql.cj.jdbc.Driver";
        String mysqlUser = "fairtek";
        String mysqlPass = "fairtek2018";
        String mysqUrl = "jdbc:mysql://127.0.0.1:3306/fairtek?serverTimezone=UTC&useSSL=false";
        
        Class.forName(mysqlClass);
        
  
        return DriverManager.getConnection(mysqUrl, mysqlUser, mysqlPass);
    }

    /**
     * Fecha a conexão com o banco de dados
     * @param conn Connection
     * @param stmt Statement
     * @param rs   ResultSet
     * @throws Exception Exceção da conexão com o BD
     */
    public static void closeConnection(Connection conn, Statement stmt, ResultSet rs) throws Exception {
        close(conn, stmt, rs);
    }

    /**
     * Fecha a conexão com o banco de dados
     * @param conn Connection
     * @param stmt Statement
     * @throws Exception Exceção da conexão com o BD
     */
    public static void closeConnection(Connection conn, Statement stmt) throws Exception {
        close(conn, stmt, null);
    }

    /**
     * Fecha a conexão com o banco de dados
     * @param conn Connection
     * @throws Exception Exceção da conexão com o BD
     */
    public static void closeConnection(Connection conn) throws Exception {
        close(conn, null, null);
    }

    /**
     * Fecha a conexão com o banco de dados
     * @param conn Connection
     * @param stmt Statement
     * @param rs ResultSet
     * @throws Exception Exceção da conexão com o BD
     */
    private static void close(Connection conn, Statement stmt, ResultSet rs) throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}
