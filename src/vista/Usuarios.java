/*
 * Copyright (C) 2015 cepardov
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package vista;

import beans.UsuarioBeans;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import utilidades.Barra;

/**
 *
 * @author cepardov
 */
public class Usuarios extends javax.swing.JInternalFrame {
    UsuarioBeans usuariobeans=new UsuarioBeans();
    Barra barra = new Barra();
    Object[][] dtPrev;
    int fila;
    /**
     * Creates new form Usuarios
     */
    public Usuarios() {
        initComponents();
        this.inicializa();
    }
    
    public final void inicializa(){
        barra.setBarra("daweda");
        this.cargaTablaUsuario();
    }
    
    private boolean verificaDatos(){
        String idUsuario=this.lblIdUsuario.getText();
        String rut=this.txtRut.getText().replace(" ", "");
        String nombre=this.txtNombre.getText().replace(" ", "");
        String apellido=this.txtApellido.getText().replace(" ", "");
        String usuario=this.txtUsuario.getText().replace(" ", "").toLowerCase();
        String claveReigresado=this.txtClaveReingresado.getText();
        String clave=this.txtClave.getText();
        String privilegio=this.cbPrivilegio.getSelectedItem().toString();
        String enabled=String.valueOf(this.chkEnable.isSelected());
        
        if(rut.isEmpty()){
            JOptionPane.showMessageDialog(null,"Formulario Inclompleto. Debe llenar el campo:\n- RUT", "Verificación de Formularios", JOptionPane.ERROR_MESSAGE);
        } else if(nombre.isEmpty()){
            JOptionPane.showMessageDialog(null,"Formulario Inclompleto. Debe llenar el campo:\n- Nombre", "Verificación de Formularios", JOptionPane.ERROR_MESSAGE);
        } else if(apellido.isEmpty()){
            JOptionPane.showMessageDialog(null,"Formulario Inclompleto. Debe llenar el campo:\n- Apellido", "Verificación de Formularios", JOptionPane.ERROR_MESSAGE);
        } else if(usuario.isEmpty()){
            JOptionPane.showMessageDialog(null,"Formulario Inclompleto. Debe llenar el campo:\n- Nombre de Usuario", "Verificación de Formularios", JOptionPane.ERROR_MESSAGE);
        } else if(clave.isEmpty()){
            JOptionPane.showMessageDialog(null,"Formulario Inclompleto. Debe llenar el campo:\n- Clave", "Verificación de Formularios", JOptionPane.ERROR_MESSAGE);
        } else if(claveReigresado.isEmpty()){
            JOptionPane.showMessageDialog(null,"Formulario Inclompleto. Debe llenar el campo:\n- Reingrese Clave", "Verificación de Formularios", JOptionPane.ERROR_MESSAGE);
        } else if(!claveReigresado.equals(clave)){
            JOptionPane.showMessageDialog(null,"Las claves ingresadas no coinciden", "Verificación de Formularios", JOptionPane.ERROR_MESSAGE);
            this.txtClave.setText("");
            this.txtClaveReingresado.setText("");
        } else if(privilegio.equals("Seleccione")){
            JOptionPane.showMessageDialog(null,"Debe seleccionar un privilegio para este usuario.", "Verificación de Formularios", JOptionPane.ERROR_MESSAGE);
        } else {
            if(idUsuario.equals("No Definido")){
                idUsuario=null;
            }
            usuariobeans.setIdUsuario(idUsuario);
            usuariobeans.setRut(rut);
            usuariobeans.setNombre(nombre);
            usuariobeans.setApellido(apellido);
            usuariobeans.setUsuario(usuario);
            usuariobeans.setClave(clave);
            usuariobeans.setPrivilegio(privilegio);
            usuariobeans.setEnabled(enabled);
            return true;
        }
        return false;
        
    }
    
    private boolean existencia(){
        String rut=this.txtRut.getText().replace(" ", "");
        
        usuariobeans.setRut(rut);
        
        if(usuariobeans.findByRut()!=false){
            JOptionPane.showMessageDialog(null,"El usuario con RUT \""+rut+"\" ya existe", "Verificación de Formularios", JOptionPane.ERROR_MESSAGE);
//            this.clean();
            this.btnGuardar.setEnabled(true);
            this.btnModificar.setEnabled(false);
            this.btnEliminar.setEnabled(false);
        } else {
            return true;
        }        
        return false;
    }
    
    private boolean existenciaNombreUsuario(){
        String usuario=this.txtUsuario.getText().replace(" ", "");
        
        usuariobeans.setUsuario(usuario);
        
        if(usuariobeans.findByUsuario()!=false){
            JOptionPane.showMessageDialog(null,"El usuario  \""+usuario+"\" ya existe", "Verificación de Formularios", JOptionPane.ERROR_MESSAGE);
//            this.clean();
//            this.btnGuardar.setEnabled(true);
//            this.btnModificar.setEnabled(false);
//            this.btnEliminar.setEnabled(false);
        } else {
            return true;
        }        
        return false;
    }
    
    private boolean buscar(){
        String rut=this.txtRut.getText();
        
        usuariobeans.setRut(rut);
        
        if(usuariobeans.findByRut()!=false){
            this.lblIdUsuario.setText(usuariobeans.getIdUsuario());
            this.txtRut.setText(usuariobeans.getRut());
            this.txtNombre.setText(usuariobeans.getNombre());
            this.txtApellido.setText(usuariobeans.getApellido());
            this.txtUsuario.setText(usuariobeans.getUsuario());
            this.txtClave.setText(usuariobeans.getClave());
            this.txtClaveReingresado.setText(usuariobeans.getClave());
            this.cbPrivilegio.setSelectedItem(usuariobeans.getPrivilegio());
            this.chkEnable.setSelected(Boolean.valueOf(usuariobeans.getEnabled()));
        } else {
            return true;
        }
        return false;
    }
    
    public final void cargaTablaUsuario(){
        String[] columNames = {"","","","","","","",""};  
        dtPrev = usuariobeans.getUsuarios();
        DefaultTableModel datos = new DefaultTableModel(dtPrev,columNames);                        
        tabla.setModel(datos);
        //Autoredimensionar Columnas
        try{
            String col0=String.valueOf(tabla.getValueAt(0, 0));
            int width0=35;//col0.length()*160;
            System.out.println("Debug: Redimensión col0="+col0);
            tabla.getColumnModel().getColumn(0).setMaxWidth(width0);
            tabla.getColumnModel().getColumn(0).setMinWidth(width0);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(width0);
            
//            String col1=String.valueOf(tabla.getValueAt(0, 0));
//            int width=col0.length()*160;
//            System.out.println("Debug: Redimensión col0="+col0);
//            tabla.getColumnModel().getColumn(0).setMaxWidth(width);
//            tabla.getColumnModel().getColumn(0).setMinWidth(width);
//            tabla.getColumnModel().getColumn(0).setPreferredWidth(width);
        }
        catch (Exception se){
            System.out.println("Debug: Error redimensionar col0: "+se.getMessage());
        }
        //Esconder Comunas
        tabla.getColumnModel().getColumn(5).setMaxWidth(0);
        tabla.getColumnModel().getColumn(5).setMinWidth(0);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(0);
//        
        tabla.getColumnModel().getColumn(0).setMaxWidth(0);
        tabla.getColumnModel().getColumn(0).setMinWidth(0);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblIdUsuario = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtRut = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        txtUsuario = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtClave = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        txtClaveReingresado = new javax.swing.JPasswordField();
        jLabel9 = new javax.swing.JLabel();
        chkEnable = new javax.swing.JCheckBox();
        cbPrivilegio = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Usuario"));

        jLabel1.setText("ID Usuario:");

        lblIdUsuario.setText("No Definido");

        jLabel3.setText("RUT");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel4.setText("Nombre");

        jLabel5.setText("Apellido");

        jLabel6.setText("Nombre Usuario");

        jLabel7.setText("Clave");

        jLabel8.setText("Reingrese Clave");

        jLabel9.setText("Privilegio");

        chkEnable.setText("Marcar para habilitar");

        cbPrivilegio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione", "Administrador", "Vendedor" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblIdUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtClave, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                        .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtClaveReingresado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                        .addComponent(txtApellido, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel6)
                                    .addComponent(txtUsuario)
                                    .addComponent(cbPrivilegio, 0, 150, Short.MAX_VALUE)))
                            .addComponent(chkEnable))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblIdUsuario)
                    .addComponent(txtRut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(btnBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtClaveReingresado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbPrivilegio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkEnable))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de Usuarios"));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnNuevo.setText("Nuevo/Borrar Formulario");

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCerrar)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnNuevo)
                    .addComponent(btnCerrar)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if(this.verificaDatos()==true&&this.existencia()&&this.existenciaNombreUsuario()){
            System.out.println("Guardando usuario...");
            if(usuariobeans.save()==false){
                JOptionPane.showMessageDialog(null,"Error: "+usuariobeans.getError(), "¡ups! Algo inesperado ha pasado", JOptionPane.ERROR_MESSAGE);
            } else {
                this.cargaTablaUsuario();
                this.btnGuardar.setEnabled(false);
                this.btnModificar.setEnabled(true);
                this.btnEliminar.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
        if(this.verificaDatos()==true){
            if(usuariobeans.update()==false){
                JOptionPane.showMessageDialog(null,"Error: "+usuariobeans.getError(), "¡ups! Algo inesperado ha pasado", JOptionPane.ERROR_MESSAGE);
            } else {
                this.cargaTablaUsuario();
                this.btnGuardar.setEnabled(false);
                this.btnModificar.setEnabled(true);
                this.btnEliminar.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        if(this.verificaDatos()==true){
            if(usuariobeans.delete()==false){
                JOptionPane.showMessageDialog(null,"Error: "+usuariobeans.getError(), "¡ups! Algo inesperado ha pasado", JOptionPane.ERROR_MESSAGE);
            } else {
                this.cargaTablaUsuario();
                this.btnGuardar.setEnabled(true);
                this.btnModificar.setEnabled(false);
                this.btnEliminar.setEnabled(false);
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        this.buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
        // TODO add your handling code here:
        fila = tabla.rowAtPoint(evt.getPoint());
        if (fila > -1){
            this.txtRut.setText(String.valueOf(tabla.getValueAt(fila, 1)));
            this.buscar();
        }
    }//GEN-LAST:event_tablaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox cbPrivilegio;
    private javax.swing.JCheckBox chkEnable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblIdUsuario;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JPasswordField txtClave;
    private javax.swing.JPasswordField txtClaveReingresado;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRut;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}