/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationhotel.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import reservationhotel.gui.ClientGui;
import reservationhotel.gui.ClientGuiReponse;
import reservationhotel.gui.ResultJFrame;
import reservationhotel.ontology.BesoinsClient;

/**
 *
 * @author Basile
 */
import reservationhotel.ontology.Reservation;

public class ClientAgent extends Agent {

    private ClientGui myGui;
    //private ClientGuiReponse myGuiReponse;
    private ResultJFrame myResultJFrame;
    private static final String TYPEAGENT = "agent-client";
    private BesoinsClient userRequest;
    private Reservation userConfirmReservation;

    // Mettez initialisations d'agent
    @Override
    protected void setup() {

        myGui = new ClientGui(this);
        myGui.setVisible(true);

        String str = "Bonjour! Agent - client " + getAID().getName() + " est pret.";
        System.out.println(str);
        BesoinsClient besoinClient = new BesoinsClient();
        besoinClient.setLocalite("Saigon");
        besoinClient.setTypeChambre("VIP");
        besoinClient.setDatArrive(new Date());
        besoinClient.setDatDepart(new Date());
        besoinClient.setPrixMax(9000000);
        besoinClient.setPrixMin(50000);
        setUserRequest(besoinClient);
        DFAgentDescription askDFD = new DFAgentDescription();

        ServiceDescription askSD = new ServiceDescription();
        askSD.setType(TYPEAGENT);
        askSD.setName("Agent" + TYPEAGENT);
        askDFD.addServices(askSD);
        try {
            DFService.register(this, askDFD);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        addBehaviour(new TickerBehaviour(this, 1500) {

            @Override
            protected void onTick() {
                // TODO Auto-generated method stub
                if (userRequest == null) {
                    System.out.println("Agent Client: aucune demande");
                }
                myAgent.addBehaviour(new RequestPerformer());

            }
        });
    }

    public void requestHotel(BesoinsClient u) {
        userRequest = u;
    }

    // Mettez les operations de nettoyage agents
    @Override
    protected void takeDown() {
        // Printout a dismissal message
        System.out.println(TYPEAGENT + getAID().getName() + " termine.");
    }

    /**
     * Inner class RequestPerformer. Ce comportement est utilise par les agents
     * utilisateurs de demander.
     */
    private class RequestPerformer extends Behaviour {

        private MessageTemplate mt; // Le modèle pour recevoir des réponses

        @Override
        public void action() {

            if (userRequest != null) {
                //recupérer la demande et envoyer a l'agent recherche
                //envoyer l'hotel de reservation a l'agent de reservation
                ACLMessage requestHotel = new ACLMessage(ACLMessage.REQUEST);
                //
                //MessageTemplate tpl = MessageTemplate.MatchReceiver(rechercheAgent)              
                DFAgentDescription tmp = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("agent-recherche");
                tmp.addServices(sd);
                try {
                    DFAgentDescription[] result = DFService.search(myAgent, tmp);
                    if (result.length > 0) {
                        requestHotel.addReceiver(result[0].getName());
                        try {
                            requestHotel.setContentObject(userRequest);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        requestHotel.setConversationId("agent-recherche");
                        requestHotel.setReplyWith("agent-recherche" + System.currentTimeMillis());
                        myAgent.send(requestHotel);
                        System.out.println(TYPEAGENT + " Envoi de la recherche de chambre a l'agent recherche");

                        //A la fin mettre le userRequest hotel a null
                        setUserRequest(null);
                    }
                } catch (FIPAException fe) {
                    // TODO: handle exception
                    fe.printStackTrace();
                }
                //
            }
            //System.out.println(TYPEAGENT + " Fin premier if");
            if (userConfirmReservation != null) {
                //envoyer l'hotel de reservation a l'agent de reservation
                ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                //
                DFAgentDescription tmp = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("agent-reservation");
                tmp.addServices(sd);
                try {
                    DFAgentDescription[] result = DFService.search(myAgent, tmp);
                    if (result.length > 0) {
                        order.addReceiver(result[0].getName());
                    }

                } catch (FIPAException fe) {
                    // TODO: handle exception
                    fe.printStackTrace();
                }
                //
                try {
                    order.setContentObject(userConfirmReservation);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                order.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                order.setConversationId("agent-reservation");
                order.setReplyWith("agent-reservation" + System.currentTimeMillis());
                myAgent.send(order);
                System.out.println(TYPEAGENT + " Envoi de la confirmation de reservation a l'agent de reservation");
                //A la fin mettre le userConfirm hotel a null
                setUserConfirmReservation(null);
            }
            //System.out.println(TYPEAGENT + " Fin deuxieme if");
            //Voir s'il ya un nouveau message de l'agent de recherche
            MessageTemplate mtmt = MessageTemplate.MatchConversationId(TYPEAGENT);
            ACLMessage repAgentRecherche = myAgent.receive(mtmt);
            if (repAgentRecherche != null) {
                //une reponse recu
                System.out.println(TYPEAGENT + " Réception de la réponse de l'agent-recherche");
                if (repAgentRecherche.getPerformative() == ACLMessage.PROPOSE) {
                    try {
                        ArrayList<Reservation> reservationPropose = new ArrayList<>();
                        reservationPropose.clear();
                        reservationPropose = (ArrayList<Reservation>) repAgentRecherche.getContentObject();
                        if (reservationPropose!=null) {
                            //Envoyer la chambre proposé au client
                            myResultJFrame = new ResultJFrame((ClientAgent) myAgent, reservationPropose);
                            myResultJFrame.setVisible(true);
                            myGui.setVisible(false);
                            System.out.println(TYPEAGENT + " Réponse non vide");
                            //renvoyer a l'utlisateur de la meilleur offre retourné
                        } else {
                            System.out.println(TYPEAGENT + " Réponse vide");
                            myGui.infoBox("Aucune offre ne correspond à votre demande!");
                            myGui.clearZoneEdit();
                            //renvoyer a l'utilsateur de l'indisponibilité de la demande
                        }
                    } catch (UnreadableException ex) {
                        //Logger.getLogger(ClientAgent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
            ACLMessage repAgentReservation = repAgentRecherche; //myAgent.receive(mt);
            if (repAgentReservation != null) {
                //une reponse recu
                if (repAgentReservation.getPerformative() == ACLMessage.CONFIRM) {
                    try {
                        //recuperer l'hotel proposé pour l'envoyer au client
                        Reservation reservationPropose = (Reservation) repAgentReservation.getContentObject();
                        if (reservationPropose != null) {
                            sendEmail(reservationPropose);
                            System.out.println(TYPEAGENT + "Votre réservation a été bien pris en compte!");
                            JOptionPane.showMessageDialog(null, "Votre réservation a été bien pris en compte! ", "Succes",  JOptionPane.INFORMATION_MESSAGE);
                            myResultJFrame.activeWindow(false);
                        } else {
                            JOptionPane.showMessageDialog(null,"Désolé, une erreur s'est  produite lors de la réservation", "Erreur",  JOptionPane.INFORMATION_MESSAGE);
                            System.out.println(TYPEAGENT + " Désolé, une erreur s'est  produite lors de la réservation");

                        }
                        myGui.setVisible(true);
                        myGui.clearZoneEdit();
                    } catch (UnreadableException ex) {
                        Logger.getLogger(ClientAgent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

        }

        @Override
        public boolean done() {
            return true;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    } // End of inner class RequestPerformer

    public BesoinsClient getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(BesoinsClient userRequest) {
        this.userRequest = userRequest;
    }

    public Reservation getUserConfirmReservation() {
        return userConfirmReservation;
    }

    public void setUserConfirmReservation(Reservation userConfirmReservation) {
        this.userConfirmReservation = userConfirmReservation;
    }

    public void sendEmail(Reservation reservation) {
        String emailFrom = "bsolibia@gmail.com";
        String password = " basile2019";
        String emailTo = reservation.getClient().getEmail();
        String sujet = "Reservation d'hotel";
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("\nHotel : " + reservation.getChambre().getHotel().getNom());
        stringBuilder.append("\nType : " + reservation.getChambre().getTypeChambre());
        stringBuilder.append("\nPrix : " + Double.toString(reservation.getChambre().getPrix()));
        stringBuilder.append("\nNom : " + reservation.getClient().getNom());
        stringBuilder.append("\nPrénom : " + reservation.getClient().getPrenom());
        stringBuilder.append("\nNom de l'hotel : " + reservation.getChambre().getHotel().getNom());
        stringBuilder.append("\nType de chambre : " + reservation.getChambre().getTypeChambre());
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String formatDate = simpleDateFormat.format(reservation.getDatArrive());
        stringBuilder.append("\nDate d'arrivée: " + formatDate);
        formatDate = simpleDateFormat.format(reservation.getDatDepart());
        stringBuilder.append("\nDate de départ: " + formatDate);
        String message = stringBuilder.toString();

        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.debug", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.ssl.checkserveridentity", "false");
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.socketFactory.fallback", "false");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailFrom, password);
            }

        });

        try {
            Message mailMessage = new MimeMessage(session);
            mailMessage.setFrom(new InternetAddress(emailFrom));
            mailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));

            mailMessage.setSubject(sujet);
            mailMessage.setText(message);
            Transport.send(mailMessage);
            System.out.println("Email envoye avec succes");
        } catch (MessagingException e) {
            System.out.println("Unable to send mail" + e.getMessage());
            //throw new RuntimeException(e);
        }
    }

}
