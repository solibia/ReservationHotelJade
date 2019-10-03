/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationhotel.agents;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import reservationhotel.ontology.Reservation;

/**
 *
 * @author Basile
 */
public class FacilitateurAgent extends Agent {

    private static final String TYPEAGENT = "agent-facilitateur";

    @Override
    public void setup() {
        String str = "Bonjour! Agent - facilitateur " + getAID().getName() + " est pret.";
        // Imprimer un message de bienvenue
        System.out.println(str);
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
                myAgent.addBehaviour(new OfferRequestsServer());

            }
        });
    }

    public class OfferRequestsServer extends Behaviour {

        private MessageTemplate mt; // Le modèle pour recevoir des réponses

        @Override
        public void action() {
            mt = MessageTemplate.MatchConversationId(TYPEAGENT);
            ACLMessage repAgentFacile = myAgent.receive(mt);
            if (repAgentFacile != null) {
                if (repAgentFacile.getPerformative() == ACLMessage.INFORM) {
                    try {
                        Reservation chambrePropose = (Reservation) repAgentFacile.getContentObject();
                        //recuperer le choix de la chambre du client et l'envoyer a l'agent hotel
                        ACLMessage order = new ACLMessage(ACLMessage.INFORM);
                        DFAgentDescription tmp = new DFAgentDescription();
                        ServiceDescription sd = new ServiceDescription();
                        sd.setType("agent-hotel");
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
                        if (chambrePropose == null) {
                            System.err.println(TYPEAGENT + " reservation null");
                        }
                        //
                        try {
                            order.setContentObject(chambrePropose);
                        } catch (IOException ex) {
                            Logger.getLogger(ReservationAgent.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        order.setPerformative(ACLMessage.INFORM);
                        order.setConversationId("agent-hotel");
                        order.setReplyWith("agent-hotel" + System.currentTimeMillis());
                        myAgent.send(order);
                        System.out.println(TYPEAGENT + " Envoi de la réservation de chambre choisit a l'agent hotel");
                    } catch (UnreadableException ex) {
                        Logger.getLogger(FacilitateurAgent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            ACLMessage repAgentHotel = repAgentFacile; //myAgent.receive(mt);
            if (repAgentHotel != null) {
                if (repAgentHotel.getPerformative() == ACLMessage.CONFIRM) {
                    try {
                        Reservation reservationPropose = (Reservation) repAgentHotel.getContentObject();
                        //recuperer le choix de la chambre du client et l'envoyer a l'agent hotel
                        ACLMessage order = new ACLMessage(ACLMessage.CONFIRM);
                        DFAgentDescription tmp = new DFAgentDescription();
                        ServiceDescription sd = new ServiceDescription();
                        sd.setType("agent-basededonne");
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
                            order.setContentObject(reservationPropose);
                        } catch (IOException ex) {
                            Logger.getLogger(ReservationAgent.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        order.setConversationId("agent-basededonne");
                        order.setReplyWith("agent-basededonne" + System.currentTimeMillis());
                        myAgent.send(order);
                        System.out.println(TYPEAGENT + " Envoi de la confirmation a agent-basededonne");
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
                            order.setContentObject(reservationPropose);
                        } catch (IOException ex) {
                            Logger.getLogger(ReservationAgent.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        order.setPerformative(ACLMessage.INFORM);
                        order.setConversationId("agent-reservation");
                        order.setReplyWith("agent-reservation" + System.currentTimeMillis());
                        myAgent.send(order);
                        System.out.println(TYPEAGENT + " Envoi de la confirmation a agent-reservation");

                    } catch (UnreadableException ex) {
                        Logger.getLogger(FacilitateurAgent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        @Override
        public boolean done() {

            return true;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

}
