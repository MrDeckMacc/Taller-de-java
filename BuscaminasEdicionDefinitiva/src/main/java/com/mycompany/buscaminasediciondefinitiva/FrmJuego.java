/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.buscaminasediciondefinitiva;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class FrmJuego extends javax.swing.JFrame {
    int numFila=10;
    int numColumna=10;
    int numMinas=10;
    
    JButton[][] botonesTablero;
    
    TableroBuscaminas tableroBuscaminas;
    
    public FrmJuego() {
        initComponents();
        juegoNuevo();
    }
    
    void descargarControles(){
        if(botonesTablero!=null){
            for(int i = 0; i < botonesTablero.length; i++){
                for(int j=0; j<botonesTablero[i].length; j++){
                    if(botonesTablero[i][j]!=null){
                        getContentPane().remove(botonesTablero[i][j]);
                    }
                }
            }
        }
    }
    
    private void juegoNuevo(){
        descargarControles();
        cargarControles();
        crearTableroBuscaminas();
        repaint();
    }
    
    private void crearTableroBuscaminas(){
        tableroBuscaminas=new TableroBuscaminas(numFila,numColumna,numMinas);
        tableroBuscaminas.setEventoPatidaPerdida(new Consumer<List<Casilla>>(){
            @Override
            public void accept(List<Casilla>t){
                for(Casilla casillaConMina: t){
                    botonesTablero[casillaConMina.getPosFila()][casillaConMina.getPosColumna()].setText("*");
                }
            }
        });
        tableroBuscaminas.setEventoPatidaGanada(new Consumer<List<Casilla>>(){
            @Override
            public void accept(List<Casilla> t){
                for(Casilla casillaConMina: t){
                    botonesTablero[casillaConMina.getPosFila()][casillaConMina.getPosColumna()].setText(":)");
                }
            }
        });
        tableroBuscaminas.setEventoCasillaAbierta(new Consumer<Casilla>(){
            @Override
            public void accept(Casilla t){
                botonesTablero[t.getPosFila()][t.getPosColumna()].setEnabled(false);
                botonesTablero[t.getPosFila()][t.getPosColumna()].setText(t.getNumMinasAlrededor()==0?"":t.getNumMinasAlrededor()+"");
            }
        });
        tableroBuscaminas.imprimirTablero();
    }
    
    private void cargarControles(){
        int posXRef=25;
        int posYRef=25;
        int anchoControl=30;
        int altoControl=30;
        botonesTablero=new JButton[numFila][numColumna];
        for(int i = 0; i < botonesTablero.length; i++){
            for(int j = 0; j < botonesTablero.length; j++){
                botonesTablero[i][j] = new JButton();
                botonesTablero[i][j].setName(i+","+j);
                botonesTablero[i][j].setBorder(null);
                if(i==0 && j==0){
                    botonesTablero[i][j].setBounds(posXRef, posYRef, anchoControl, altoControl);
                }else if(i==0 && j!=0){
                    botonesTablero[i][j].setBounds(botonesTablero[i][j-1].getX()+botonesTablero[i][j-1].getWidth(), posYRef,anchoControl,altoControl);
                }else{
                     botonesTablero[i][j].setBounds(botonesTablero[i-1][j].getX(),botonesTablero[i-1][j].getY()+botonesTablero[i-1][j].getHeight(),anchoControl,altoControl);                  
                }
                botonesTablero[i][j].addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        btnClick(e);
                    }

                    
                });
                getContentPane().add(botonesTablero[i][j]);
            } 
        }
        this.setSize(botonesTablero[numFila-1][numColumna-1].getX()+botonesTablero[numFila-1][numColumna-1].getWidth()+40,
                botonesTablero[numFila-1][numColumna-1].getY()+botonesTablero[numFila-1][numColumna-1].getHeight()+80);
    }
    private void btnClick(ActionEvent e) {
        JButton btn=(JButton)e.getSource();
        String[] coordenada= btn.getName().split(",");
        int posFila=Integer.parseInt(coordenada[0]);
        int posColumna=Integer.parseInt(coordenada[1]);
        //JOptionPane.showMessageDialog(rootPane,posFila+","+posColumna);
        tableroBuscaminas.seleccionarCasilla(posFila,posColumna);
    }    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuNuevoJuego = new javax.swing.JMenuItem();
        menuTamaño = new javax.swing.JMenuItem();
        menuNumMinas = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("Juego");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        menuNuevoJuego.setText("Nuevo");
        menuNuevoJuego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNuevoJuegoActionPerformed(evt);
            }
        });
        jMenu1.add(menuNuevoJuego);

        menuTamaño.setText("Tamaño");
        menuTamaño.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTamañoActionPerformed(evt);
            }
        });
        jMenu1.add(menuTamaño);

        menuNumMinas.setText("Numero Minas");
        menuNumMinas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNumMinasActionPerformed(evt);
            }
        });
        jMenu1.add(menuNumMinas);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 278, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuNuevoJuegoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNuevoJuegoActionPerformed
        juegoNuevo();
    }//GEN-LAST:event_menuNuevoJuegoActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void menuTamañoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTamañoActionPerformed
        int num=Integer.parseInt(JOptionPane.showInputDialog("Escribe el tamaño del Tablero, nxn"));
        this.numFila=num;
        this.numColumna=num;
        juegoNuevo();
    }//GEN-LAST:event_menuTamañoActionPerformed

    private void menuNumMinasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNumMinasActionPerformed
        int num=Integer.parseInt(JOptionPane.showInputDialog("Escribe la cantidad de minas"));
        this.numMinas=num;
        juegoNuevo();
    }//GEN-LAST:event_menuNumMinasActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmJuego().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem menuNuevoJuego;
    private javax.swing.JMenuItem menuNumMinas;
    private javax.swing.JMenuItem menuTamaño;
    // End of variables declaration//GEN-END:variables
}
