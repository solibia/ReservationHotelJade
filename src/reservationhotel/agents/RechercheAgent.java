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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import reservationhotel.gui.ClientGui;
import reservationhotel.ontology.BesoinsClient;
import reservationhotel.ontology.Chambre;
import reservationhotel.ontology.Client;
import reservationhotel.ontology.Hotel;
import reservationhotel.ontology.Reservation;

/**
 *
 * @author Basile
 */
public class RechercheAgent extends Agent {

    private static final String TYPEAGENT = "agent-recherche";

    @Override
    protected void setup() {
        String str = "Bonjour! Agent - recherche " + getAID().getName() + " est pret.";
        // Imprimer un message de bienvenue
        System.out.println(str);
        DFAgentDescription askDFD = new DFAgentDescription();
        askDFD.setName(getAID());

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
                myAgent.addBehaviour(new RequestPerformer());
            }
        });
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
            //Voir s'il ya un nouveau message de l'agent client
            mt = MessageTemplate.MatchConversationId("agent-recherche");
            ACLMessage repAgentClient = myAgent.receive(mt);
            if (repAgentClient != null) {
                //une reponse recu
                if (repAgentClient.getPerformative() == ACLMessage.REQUEST) {
                    try {
                        //recuperer la requete de recherche du client et l'envoyer a l'agent base de donnée
                        BesoinsClient hotelDemande = (BesoinsClient) repAgentClient.getContentObject();
                        if (hotelDemande != null) {
                            //renvoyer a l'utlisateur de la meilleur offre retourné
                            ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                            DFAgentDescription tmp = new DFAgentDescription();
                            ServiceDescription sd = new ServiceDescription();
                            sd.setType("agent-basededonne");
                            tmp.addServices(sd);
                            try {
                                DFAgentDescription[] result = DFService.search(myAgent, tmp);
                                if (result.length > 0) {
                                    cfp.addReceiver(result[0].getName());
                                }

                            } catch (FIPAException fe) {
                                // TODO: handle exception
                                fe.printStackTrace();
                            }
                            try {
                                cfp.setContentObject(hotelDemande);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            cfp.setConversationId("agent-basededonne");
                            cfp.setPerformative(ACLMessage.REQUEST);
                            cfp.setReplyWith("cfp" + System.currentTimeMillis()); // Valeur
                            // unique
                            myAgent.send(cfp);
                            System.out.println(TYPEAGENT + " Envoi de la requete de la chambre a agent-basededonne");
                        }
                    } catch (UnreadableException ex) {
                        Logger.getLogger(ClientAgent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            //mt = MessageTemplate.MatchConversationId("agent-recherche");
            ACLMessage repAgentBaseDeDonne = repAgentClient; //myAgent.receive(mt);
            if (repAgentBaseDeDonne != null && repAgentBaseDeDonne.getPerformative() == ACLMessage.PROPOSE) {
                try {
                    //recuperer la proposition de la base de donnée
                    ArrayList <Reservation> hotelDemande = new ArrayList<>();
                    hotelDemande.clear();
                    hotelDemande = (ArrayList <Reservation>) repAgentBaseDeDonne.getContentObject();
                    //hotelDemande.addAll((ArrayList <Reservation>) repAgentBaseDeDonne.getContentObject());
                    System.out.println(TYPEAGENT + " réception de la reponse de agent-basededonne");
                    //System.out.println(TYPEAGENT+"Taille des réservations trouvé : " + hotelDemande.size());
                    //if (hotelDemande != null) {
                    //renvoyer a l'utlisateur de la meilleur offre retourné
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                    DFAgentDescription tmp = new DFAgentDescription();
                    ServiceDescription sd = new ServiceDescription();
                    sd.setType("agent-client");
                    tmp.addServices(sd);
                    try {
                        DFAgentDescription[] result = DFService.search(myAgent, tmp);
                        if (result.length > 0) {
                            cfp.addReceiver(result[0].getName());
                            //System.out.println(TYPEAGENT + " cfp.addReceiver(result[0].getName()) = ");                                                
                        }

                    } catch (FIPAException fe) {
                        // TODO: handle exception
                        fe.printStackTrace();
                    }
                    try {
                        cfp.setContentObject(hotelDemande);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    cfp.setConversationId("agent-client");
                    sd.setType("agent-client");
                    cfp.setPerformative(ACLMessage.PROPOSE);
                    cfp.setReplyWith("cfp" + System.currentTimeMillis()); // Valeur
                    // unique
                    myAgent.send(cfp);
                    System.out.println(TYPEAGENT + " Envoi de la réponse a agent-client");                    
                    //}
                } catch (UnreadableException ex) {
                    Logger.getLogger(ClientAgent.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        @Override
        public boolean done() {
            return true;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    } // End of inner class RequestPerformer

}
