/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationhotel.gui;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import reservationhotel.agents.ClientAgent;
import reservationhotel.ontology.Chambre;
import reservationhotel.ontology.Client;
import reservationhotel.ontology.Reservation;

/**
 *
 * @author Basile
 */
public class ClientGuiReponse extends javax.swing.JFrame {

    /**
     * Creates new form ClientGuiReponse
     */
    ClientAgent clientAgent;
    Reservation curentReservation;

    public ClientGuiReponse(ClientAgent clientAgent, Reservation reservationPropose) {
        this.clientAgent = clientAgent;
        curentReservation = new Reservation();
        curentReservation.setChambre(reservationPropose.getChambre());
        curentReservation.setDatArrive(reservationPropose.getDatArrive());
        curentReservation.setDatDepart(reservationPropose.getDatDepart());
        initComponents();
        txtFieldTypChambre.setText(reservationPropose.getChambre().getTypeChambre());
        txtFieldLocalite.setText(reservationPropose.getChambre().getHotel().getLocalite().getNom());
        txtFieldNomHotel.setText(reservationPropose.getChambre().getHotel().getNom());
        txtFieldPrix.setText(Integer.toString((int) reservationPropose.getChambre().getPrix()));
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date1 = simpleDateFormat.format(reservationPropose.getDatArrive());
        datDebut.setText(date1);
        String date2 = simpleDateFormat.format(reservationPropose.getDatDepart());
        datFin.setText(date2);
    }

    boolean EstVide2() {
        boolean rep = true;
        if (txtFieldNomClient.toString().isEmpty()
                || txtFieldPrenom.toString().isEmpty()
                || txtFieldEmail.toString().isEmpty()
                || txtFieldTel.toString().isEmpty()) {
            rep = true;
        } else {
            rep = false;
        }
        return rep;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtFieldNomClient = new javax.swing.JTextField();
        txtFieldLocalite = new javax.swing.JTextField();
        btnConfirmer = new javax.swing.JButton();
        btnAnnuler = new javax.swing.JButton();
        datDebut = new javax.swing.JFormattedTextField();
        datFin = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtFieldTypChambre = new javax.swing.JTextField();
        txtFieldPrenom = new javax.swing.JTextField();
        txtFieldTel = new javax.swing.JTextField();
        txtFieldEmail = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtFieldPrix = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtFieldNomHotel = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtFieldNomClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFieldNomClientActionPerformed(evt);
            }
        });

        txtFieldLocalite.setEditable(false);
        txtFieldLocalite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFieldLocaliteActionPerformed(evt);
            }
        });

        btnConfirmer.setText("Confirmer");
        btnConfirmer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmerActionPerformed(evt);
            }
        });

        btnAnnuler.setText("Annuler");
        btnAnnuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnnulerActionPerformed(evt);
            }
        });

        datDebut.setEditable(false);
        datDebut.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));

        datFin.setEditable(false);
        datFin.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        datFin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datFinActionPerformed(evt);
            }
        });

        jLabel1.setText("Nom");

        jLabel2.setText("Localité");

        jLabel4.setText("Type chambre");

        jLabel5.setText("Date début");

        jLabel6.setText("Date fin");

        jLabel7.setText("Prix");

        txtFieldTypChambre.setEditable(false);

        jLabel3.setText("Prénom");

        jLabel8.setText("Tel");

        jLabel9.setText("Email");

        txtFieldPrix.setEditable(false);

        jLabel10.setText("Hotel");

        txtFieldNomHotel.setEditable(false);
        txtFieldNomHotel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFieldNomHotelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnConfirmer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAnnuler))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtFieldTel)
                                    .addComponent(txtFieldPrenom)
                                    .addComponent(txtFieldNomClient)
                                    .addComponent(txtFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(txtFieldPrix, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(datFin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                                            .addComponent(datDebut, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtFieldTypChambre)))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(txtFieldLocalite, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txtFieldNomHotel, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(59, 59, 59))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFieldLocalite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtFieldNomHotel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFieldTypChambre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(datDebut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(datFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtFieldPrix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFieldNomClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFieldPrenom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFieldTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmer)
                    .addComponent(btnAnnuler))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFieldNomClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFieldNomClientActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFieldNomClientActionPerformed

    private void datFinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datFinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_datFinActionPerformed

    private void txtFieldLocaliteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFieldLocaliteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFieldLocaliteActionPerformed

    private void btnConfirmerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmerActionPerformed
        // TODO add your handling code here:
        if (!EstVide2()) {
            btnConfirmer.setEnabled(false);
            Client curentClient = new Client();
            curentClient.setNom(txtFieldNomClient.getText());
            curentClient.setPrenom(txtFieldPrenom.getText());
            curentClient.setEmail(txtFieldEmail.getText());
            curentClient.setTelephone(txtFieldTel.getText());
            curentReservation.setClient(curentClient);
            //System.out.println("Client réponse Date début " + curentReservation.getDatArrive());
            //System.out.println("Client réponse Date fin " + curentReservation.getDatDepart());
            //curentReservation.setDatArrive((Date) datDebut.getValue());
            //curentReservation.setDatDepart((Date) datFin.getValue());
            clientAgent.setUserConfirmReservation(curentReservation);
        } else {
            JOptionPane.showMessageDialog(this, "Erreur", "Saisir tous les champs svp! ", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnConfirmerActionPerformed

    private void btnAnnulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnnulerActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_btnAnnulerActionPerformed

    private void txtFieldNomHotelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFieldNomHotelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFieldNomHotelActionPerformed

    public void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(this, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    public void clearZoneEdit() {
        btnConfirmer.setEnabled(true);
        txtFieldLocalite.setText("");
        datDebut.setText("");
        datFin.setText("");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnnuler;
    private javax.swing.JButton btnConfirmer;
    private javax.swing.JFormattedTextField datDebut;
    private javax.swing.JFormattedTextField datFin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtFieldEmail;
    private javax.swing.JTextField txtFieldLocalite;
    private javax.swing.JTextField txtFieldNomClient;
    private javax.swing.JTextField txtFieldNomHotel;
    private javax.swing.JTextField txtFieldPrenom;
    private javax.swing.JTextField txtFieldPrix;
    private javax.swing.JTextField txtFieldTel;
    private javax.swing.JTextField txtFieldTypChambre;
    // End of variables declaration//GEN-END:variables
}
