package br.com.handson.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class H2Connection {
    private final String driver = "org.h2.Driver";
    private final String url = "jdbc:h2:~/handson3";
    private final String usuario = "sa";
    private final String senha = "";
    private Connection con;

    public H2Connection() {
        try {
            Class.forName(driver);            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(H2Connection.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }  
    
    public Connection getConnection(){
        if(con == null){
            try {
                con = DriverManager.getConnection(url, usuario, senha);
                con.setAutoCommit(false);
                return con;
            } catch (SQLException ex) {
                Logger.getLogger(H2Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
           return con; 
        }
        
        return null;
    }
    
    public boolean closeConnection(){
        try {
            con.close();
            con = null;
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(H2Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
}
