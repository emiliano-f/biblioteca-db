/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DAO {
    
    protected Connection conn;
    protected Statement stm;
    protected ResultSet rs;
    
    protected void connectDB(){
        
        // Environment vars
        String database = System.getenv("DB_NAME");
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");
        String host = System.getenv("DB_HOST");
        String port = System.getenv("DB_PORT");

        if (database == null 
            || username == null 
            || password == null
            || port == null
            || host == null) {
            System.err.println("No se encontraron las variables de entorno requeridas.");
            return;
        }

        // Connect
        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + 
                                                port + "/" + database, 
                                                username, 
                                                password);
            
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
    
    protected void disconnectDB() throws SQLException{
        try{
            if(rs != null) rs.close();
            if(stm != null) stm.close();
            if(conn != null) conn.close();
        }
        catch(SQLException e){
            throw e;
        }
    }        
    
    protected void updateDB(String sql) throws Exception{
        try{
            stm = conn.createStatement();
            stm.executeUpdate(sql);
        }catch(SQLException e){
            throw e;
        }
    }
    
    protected void requestDB(String sql) throws Exception{
        try{
            this.connectDB();
            stm = conn.createStatement();
            rs = stm.executeQuery(sql);
        }catch(SQLException e){
            throw e;
        }
    }
}
