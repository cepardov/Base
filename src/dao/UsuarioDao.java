/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conexion.DataBaseInstance;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import entidad.Usuario;

/**
 *
 * @author cepardov
 */
public class UsuarioDao {
    protected Connection getConnection() {
        return DataBaseInstance.getInstanceConnection();
    }

    public Object [][] getUsuario(){
        int posid = 0;
        try{
            PreparedStatement pstm = getConnection().prepareStatement("SELECT count(1) as total FROM usuario");
            try (ResultSet res = pstm.executeQuery()) {
                res.next();
                posid = res.getInt("total");
            }
            this.closeConnection();
            }catch(SQLException se){
                JOptionPane.showMessageDialog(null, se);
        }
        Object[][] data = new String[posid][8];
        try{
//idUsuario, rut, nombre, apellido, usuario, clave, privilegio, enabled
            PreparedStatement pstm = getConnection().prepareStatement("SELECT "
                    + "idUsuario,"
                    + "rut,"
                    + "nombre,"
                    + "apellido,"
                    + "usuario,"
                    + "clave,"
                    + "privilegio,"
                    + "enabled"
                    + " FROM usuario ORDER BY id");
            try (ResultSet res = pstm.executeQuery()) {
                int increment = 0;
                while(res.next()){
                    
                    String estIdUsuario = res.getString("idUsuario");
                    String estRut = res.getString("rut");
                    String estNombre= res.getString("nombre");
                    String estApellido = res.getString("apellido");
                    String estUsuario = res.getString("usuario");
                    String estClave = res.getString("clave");
                    String estPrivilegio= res.getString("privilegio");
                    String estEnabled = res.getString("enabled");

                    data[increment][0] = estIdUsuario;
                    data[increment][1] = estRut;
                    data[increment][2] = estNombre;
                    data[increment][3] = estApellido;
                    data[increment][4] = estUsuario;
                    data[increment][5] = estClave;
                    data[increment][7] = estPrivilegio;
                    data[increment][6] = estEnabled;

                    increment++;
                }
            }
            this.closeConnection();
            }catch(SQLException se){
                JOptionPane.showMessageDialog(null, se);
            }
        return data;
    }
    
    public boolean findById(Usuario usuario) {
        PreparedStatement getUsuario;
        ResultSet result = null;
        try {
            getUsuario = getConnection().prepareStatement("SELECT * FROM usuario WHERE idUsuario = ?");
            getUsuario.setString(1, usuario.getIdUsuario());
            result = getUsuario.executeQuery();
            if (result.next()) {
//idUsuario, rut, nombre, apellido, usuario, clave, privilegio, enabled
                usuario.setIdUsuario(result.getString("idUsuario"));
                usuario.setRut(result.getString("rut"));
                usuario.setNombre(result.getString("nombre"));
                usuario.setApellido(result.getString("apellido"));
                usuario.setUsuario(result.getString("usuario"));
                usuario.setClave(result.getString("clave"));
                usuario.setPrivilegio(result.getString("privilegio"));
                usuario.setEnabled(result.getString("enabled"));
                result.close();
            } else {
                return false;
            }
            closeConnection();
            return true;
        } catch (SQLException se) {
            System.err.println("Se ha producido un error de BD.");
            System.err.println(se.getMessage());
            return false;
        }
    }
    
    public Usuario loginUsuario(Usuario usuario){
        PreparedStatement getUsuario;
        ResultSet result = null;
        
        Usuario u=null;
        try{
            getUsuario = getConnection().prepareStatement("SELECT * FROM usuario WHERE usuario=? AND clave=?");
            getUsuario.setString(1, usuario.getUsuario());
            getUsuario.setString(2, usuario.getClave());
            result = getUsuario.executeQuery();
            while(result.next()){
                u=new Usuario();
                
                u.setIdUsuario(result.getString("idUsuario"));
                u.setRut(result.getString("rut"));
                u.setNombre(result.getString("nombre"));
                u.setApellido(result.getString("apellido"));
                u.setUsuario(result.getString("usuario"));
                u.setClave(result.getString("clave"));
                u.setPrivilegio(result.getString("privilegio"));
                u.setEnabled(result.getString("enabled"));
                
                break;
            }
            this.closeConnection();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
            u=null;
        }
        return u;
    }
    
    public boolean save(Usuario usuario) {
        PreparedStatement saveUsuario;
        try {
            saveUsuario = getConnection().prepareStatement(
                    "INSERT INTO usuario ("
                    + "idUsuario,"
                    + "rut,"
                    + "nombre,"
                    + "apellido,"
                    + "usuario,"
                    + "clave,"
                    + "privilegio,"
                    + "enabled) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            saveUsuario.setString(1, usuario.getIdUsuario());
            saveUsuario.setString(2, usuario.getRut());
            saveUsuario.setString(3, usuario.getNombre());
            saveUsuario.setString(4, usuario.getApellido());
            saveUsuario.setString(5, usuario.getUsuario());
            saveUsuario.setString(6, usuario.getClave());
            saveUsuario.setString(7, usuario.getPrivilegio());
            saveUsuario.setString(8, usuario.getEnabled());
            saveUsuario.executeUpdate();

            this.closeConnection();
            return true;
        } catch (SQLException se) {
            int errorcod=se.getErrorCode();
            System.err.println("Debug: ("+errorcod+") Error ejecutando saveUsuario(): "+se.getMessage());
            usuario.setError(""+errorcod);
            return false;
        }
    }
    
    public boolean update(Usuario usuario) {
        PreparedStatement saveUsuario;
        try {
            saveUsuario = getConnection().prepareStatement("UPDATE usuario SET "
                    + "idUsuario=?,"
                    + "rut=?,"
                    + "nombre=?,"
                    + "apellido=?,"
                    + "usuario=?,"
                    + "clave=?,"
                    + "privilegio=?,"
                    + "enabled=?"
                    + " WHERE idUsuario=?");
            saveUsuario.setString(1, usuario.getIdUsuario());
            saveUsuario.setString(2, usuario.getRut());
            saveUsuario.setString(3, usuario.getNombre());
            saveUsuario.setString(4, usuario.getApellido());
            saveUsuario.setString(5, usuario.getUsuario());
            saveUsuario.setString(6, usuario.getClave());
            saveUsuario.setString(7, usuario.getPrivilegio());
            saveUsuario.setString(8, usuario.getEnabled());
            saveUsuario.setString(9, usuario.getIdUsuario());
            saveUsuario.executeUpdate();
            
            this.closeConnection();
            return true;
        } catch (SQLException se) {
            int errorcod=se.getErrorCode();
            System.err.println("Debug: ("+errorcod+") Error ejecutando updateUsuario(): "+se.getMessage());
            usuario.setError(""+errorcod);
            return false;
        }
    }

    public boolean delete(Usuario usuario) {
        PreparedStatement delUsuario;
        try {

            if (usuario.getIdUsuario() != null) {
                delUsuario = getConnection().prepareStatement(
                        "DELETE FROM usuario WHERE id_usuario=?");
                delUsuario.setString(1, usuario.getIdUsuario());
                delUsuario.executeUpdate();
            }
            this.closeConnection();
            return true;
        } catch (SQLException se) {
            int errorcod=se.getErrorCode();
            System.err.println("Debug: ("+errorcod+") Error ejecutando deleteUsuario(): "+se.getMessage());
            usuario.setError(""+errorcod);
            return false;
        }
    }

    public void closeConnection() {
        DataBaseInstance.closeConnection();
    }
    
}
