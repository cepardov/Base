/*
 * Copyright (C) 2015 cepardov
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package vista;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import beans.UsuarioBeans;
import cl.cepardov.encriptar.Encript;
import entidad.Usuario;
import java.awt.Cursor;
import sistema.Service;

/**
 * @author cepardov <cepardov@gmail.com>
 */
public class Login extends javax.swing.JFrame {
    UsuarioBeans usuariobeans=new UsuarioBeans();
    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Inicio de sesión - NeoMarket");
        this.txtTitulo.setTitulo("Inicio de sesión - NeoMarket");
        this.txtTitulo.hideCloseButton();
    }
    
    private void busy(int estado){
        switch (estado) {
            case 1:
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                break;
            case 2:
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                break;
            default:
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); 
                break;
        }
    }
    
    private boolean isTarjeta(){
        String cadena = this.txtusuario.getText();
        this.txtusuario.setText("");
        try{
            int indexOf = cadena.indexOf("%");
            if(indexOf>-1){
                //verdadero
                this.iniciaSesionTarjeta(cadena);
                return true;
            } else {
                //falso
                this.txtusuario.setText(cadena);
                this.txtclave.requestFocus();
                return false;
            }
        } catch (Exception se){
            System.out.println("Error al detectar terjeta:\n"+se);
        }
        return false;
    }
    
    private void iniciaSesionTarjeta(String cadena){
        String usuario = cadena.substring(0,cadena.indexOf("%"));
        String clave = cadena.substring(cadena.indexOf("%")+1,cadena.length());
        
        this.txtusuario.setText(usuario);
        this.txtclave.setText(clave);
        
        this.iniciarSesion();
    }
    
    int intentos = 3;
    private void iniciarSesion(){
        //Obtención de datos ingresados
        if (intentos==0){
            dispose();
            JOptionPane.showMessageDialog(null, "Inicio de sesión denegado!\nContacte con administrador del sistema...", "Error de Inicio de sesión.", JOptionPane.ERROR_MESSAGE);
            new Service().cerrarApp();
        }else if(this.txtusuario.getText().isEmpty()){
            this.setVisible(false);
            JOptionPane.showMessageDialog(null, "Nombre de usuario no es indicado.\nIngrese su nombre de usuario y contraseña para ingresar al sistema.", "Error de Inicio de sesión.", JOptionPane.ERROR_MESSAGE);
            this.setVisible(true);
            this.txtusuario.requestFocus();
        } else if (this.txtclave.getText().isEmpty()){
            this.setVisible(false);
            JOptionPane.showMessageDialog(null, "Contraseña no es indicado.\nIngrese su contraseña y nombre de usuario para ingresar al sistema.", "Error de Inicio de sesión.", JOptionPane.WARNING_MESSAGE);
            this.setVisible(true);
            this.txtclave.requestFocus();
        } else {
            //Extrae información ingresada-
            String usuario=this.txtusuario.getText();
            String clave=Encript.Encriptar(this.txtclave.getText());

            usuariobeans.setUsuario(usuario);
            usuariobeans.setClave(clave);
            Usuario u=usuariobeans.login();
            
            if(u==null){
                intentos=intentos-1;
                System.out.println("Error inicio de sesión");
                this.setVisible(false);
                JOptionPane.showMessageDialog(null, "El nombre de usuario y/o contraseña no son validos.\nIntentos restantes "+intentos, "Error de Inicio de sesión.", JOptionPane.ERROR_MESSAGE);
                this.setVisible(true);
                this.txtclave.requestFocus();
            }else if(u!=null){
                this.setVisible(false);
                if("false".equals(u.getEnabled())){
                    JOptionPane.showMessageDialog(null,"Los administradores del sistema han desabilitado el acceso a "+u.getNombre()+" "+u.getApellido()+". "
                            + "\nComuniquese con el administrador del sistema.", "Inicio de sesión denegado", JOptionPane.ERROR_MESSAGE);
                    this.limpiar();
                    this.setVisible(true);
                } else {
                    Menu menu = new Menu(u.getIdUsuario(),u.getNombre(),u.getApellido(),u.getUsuario(),u.getClave(),u.getPrivilegio(),u.getEnabled());
                    menu.setTitle("NeoMarket - Usuario ["+u.getNombre()+" "+u.getApellido()+"] - "+u.getPrivilegio());
                    menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    menu.setVisible(true);
                }
            }
        }
    }
    
    private void limpiar(){
        this.txtusuario.setText("");
        this.txtclave.setText("");
        this.txtusuario.requestFocus();
    }
   
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("iconos/login-64px.png"));
        return retValue;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelImage4 = new elaprendiz.gui.panel.PanelImage();
        panelCurves1 = new elaprendiz.gui.panel.PanelCurves();
        txtTitulo = new elaprendiz.gui.varios.TitleBar();
        panelImage5 = new elaprendiz.gui.panel.PanelImage();
        panelImage6 = new elaprendiz.gui.panel.PanelImage();
        panelImage7 = new elaprendiz.gui.panel.PanelImage();
        labelMetric1 = new elaprendiz.gui.label.LabelMetric();
        labelMetric2 = new elaprendiz.gui.label.LabelMetric();
        btnInicioSesion = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        txtclave = new elaprendiz.gui.passwordField.PasswordFieldRoundIcon();
        txtusuario = new elaprendiz.gui.textField.TextFieldRoundIcon();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconImage(getIconImage());
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        panelImage4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/background.png")));

        txtTitulo.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        txtTitulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTituloMouseClicked(evt);
            }
        });
        panelCurves1.add(txtTitulo, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout panelImage5Layout = new javax.swing.GroupLayout(panelImage5);
        panelImage5.setLayout(panelImage5Layout);
        panelImage5Layout.setHorizontalGroup(
            panelImage5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        panelImage5Layout.setVerticalGroup(
            panelImage5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 203, Short.MAX_VALUE)
        );

        panelCurves1.add(panelImage5, java.awt.BorderLayout.LINE_END);

        javax.swing.GroupLayout panelImage6Layout = new javax.swing.GroupLayout(panelImage6);
        panelImage6.setLayout(panelImage6Layout);
        panelImage6Layout.setHorizontalGroup(
            panelImage6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        panelImage6Layout.setVerticalGroup(
            panelImage6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 203, Short.MAX_VALUE)
        );

        panelCurves1.add(panelImage6, java.awt.BorderLayout.LINE_START);

        labelMetric1.setText("Usuario");
        labelMetric1.setDistanciaDeSombra(2);
        labelMetric1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N

        labelMetric2.setText("Contraseña");
        labelMetric2.setDistanciaDeSombra(2);
        labelMetric2.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N

        btnInicioSesion.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btnInicioSesion.setText("Iniciar Sesión");
        btnInicioSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioSesionActionPerformed(evt);
            }
        });

        btnCerrar.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        btnCerrar.setText("Salir");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        txtclave.setText("password");
        txtclave.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtclave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/key.png"))); // NOI18N
        txtclave.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtclaveFocusGained(evt);
            }
        });
        txtclave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtclaveActionPerformed(evt);
            }
        });
        txtclave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtclaveKeyPressed(evt);
            }
        });

        txtusuario.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        txtusuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/user.png"))); // NOI18N
        txtusuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtusuarioFocusGained(evt);
            }
        });
        txtusuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtusuarioKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelImage7Layout = new javax.swing.GroupLayout(panelImage7);
        panelImage7.setLayout(panelImage7Layout);
        panelImage7Layout.setHorizontalGroup(
            panelImage7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelImage7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtclave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelImage7Layout.createSequentialGroup()
                        .addComponent(btnInicioSesion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(btnCerrar))
                    .addGroup(panelImage7Layout.createSequentialGroup()
                        .addGroup(panelImage7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelMetric2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtusuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelImage7Layout.setVerticalGroup(
            panelImage7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelMetric1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelMetric2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(txtclave, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelImage7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInicioSesion)
                    .addComponent(btnCerrar))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        panelCurves1.add(panelImage7, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout panelImage4Layout = new javax.swing.GroupLayout(panelImage4);
        panelImage4.setLayout(panelImage4Layout);
        panelImage4Layout.setHorizontalGroup(
            panelImage4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCurves1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelImage4Layout.setVerticalGroup(
            panelImage4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImage4Layout.createSequentialGroup()
                .addComponent(panelCurves1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelImage4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelImage4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInicioSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioSesionActionPerformed
        // TODO add your handling code here:
        this.busy(1);
        this.iniciarSesion();
        this.busy(0);
        
    }//GEN-LAST:event_btnInicioSesionActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        // TODO add your handling code here:
        new Service().cerrarApp();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void txtTituloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTituloMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTituloMouseClicked

    private void txtclaveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtclaveKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Enter was pressed. Your code goes here.
            this.iniciarSesion();
        }
    }//GEN-LAST:event_txtclaveKeyPressed

    private void txtclaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtclaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtclaveActionPerformed

    private void txtusuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtusuarioKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Enter was pressed. Your code goes here.
            this.isTarjeta();
        }
    }//GEN-LAST:event_txtusuarioKeyPressed

    private void txtusuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtusuarioFocusGained
        // TODO add your handling code here:
        this.txtusuario.setText("");
    }//GEN-LAST:event_txtusuarioFocusGained

    private void txtclaveFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtclaveFocusGained
        // TODO add your handling code here:
        this.txtclave.setText("");
    }//GEN-LAST:event_txtclaveFocusGained

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnInicioSesion;
    private elaprendiz.gui.label.LabelMetric labelMetric1;
    private elaprendiz.gui.label.LabelMetric labelMetric2;
    private elaprendiz.gui.panel.PanelCurves panelCurves1;
    private elaprendiz.gui.panel.PanelImage panelImage4;
    private elaprendiz.gui.panel.PanelImage panelImage5;
    private elaprendiz.gui.panel.PanelImage panelImage6;
    private elaprendiz.gui.panel.PanelImage panelImage7;
    private elaprendiz.gui.varios.TitleBar txtTitulo;
    private elaprendiz.gui.passwordField.PasswordFieldRoundIcon txtclave;
    private elaprendiz.gui.textField.TextFieldRoundIcon txtusuario;
    // End of variables declaration//GEN-END:variables
}
