/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.UsuarioDao;
import entidad.Usuario;

/**
 * Usuario
 * @author cepardov
 */
public class UsuarioBeans extends Usuario{
    UsuarioDao usuarioDao=new UsuarioDao();
    
    public boolean save(){
        return usuarioDao.save(this);
    }
    public boolean delete(){
        return usuarioDao.delete(this);
    }
    public boolean update(){
        return usuarioDao.update(this);
    }
    public boolean find(){
        return usuarioDao.findById(this);
    }
    public Object[][] getUsuarios(){
        return usuarioDao.getUsuario();
    }
    public Usuario login(){
        return usuarioDao.loginUsuario(this);
    }
}
