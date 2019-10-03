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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import reservationhotel.ontology.BesoinsClient;
import reservationhotel.ontology.Chambre;
import reservationhotel.ontology.Hotel;
import reservationhotel.ontology.Localite;
import reservationhotel.ontology.Reservation;

/**
 *
 * @author Basile
 */
public class BaseDeDonneeAgent extends Agent {

    private static final String TYPEAGENT = "agent-basededonne";
    private Reservation reservationVerifier;

    @Override
    protected void setup() {
        String str = "Bonjour! Agent - basededonne " + getAID().getName() + " est pret.";
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
                myAgent.addBehaviour(new BaseDeDonneeAgent.ReceiveMessageReqFromRecherche());
            }
        });

    }

    private class ReceiveMessageReqFromRecherche extends Behaviour {

        // Le modèle pour recevoir des réponses
        private MessageTemplate mt;

        @Override
        public void action() {

            //Message de l'agent recherche
            mt = MessageTemplate.MatchConversationId("agent-basededonne");
            ACLMessage reqFromAgent = myAgent.receive(mt);
            ArrayList<Reservation> listMeilleurReservation = new ArrayList<>();
            listMeilleurReservation.clear();
            if (reqFromAgent != null) {
                //une reponse recu
                if (reqFromAgent.getPerformative() == ACLMessage.REQUEST) {
                    try {
                        //recuperer la requete de recherche du client et l'envoyer a l'agent base de donnée
                        BesoinsClient hotelPredicat = (BesoinsClient) reqFromAgent.getContentObject();
                        //afficheBesoinClient(hotelPredicat);
                        if (hotelPredicat != null) {
                            Chambre chambre = new Chambre();
                            List<Chambre> listeChambre = chambre.findByPredicats(hotelPredicat.getTypeChambre(), hotelPredicat.getLocalite(), hotelPredicat.getPrixMin(), hotelPredicat.getPrixMax());
                            //System.out.println("Taille des chambres trouvé : " + listeChambre.size());
                            if (listeChambre.isEmpty()) {
                                listeChambre = null;
                                listMeilleurReservation = null;
                            } else {
                                for (Chambre chambre1 : listeChambre) {
                                    listMeilleurReservation.add(new Reservation());
                                    listMeilleurReservation.get(listMeilleurReservation.size() - 1).setChambre(chambre1);
                                    listMeilleurReservation.get(listMeilleurReservation.size() - 1).setDatArrive(hotelPredicat.getDatArrive());
                                    listMeilleurReservation.get(listMeilleurReservation.size() - 1).setDatDepart(hotelPredicat.getDatDepart());
                                }
                            }

                            ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                            DFAgentDescription tmp = new DFAgentDescription();
                            ServiceDescription sd = new ServiceDescription();
                            sd.setType("agent-recherche");
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
                                cfp.setContentObject(listMeilleurReservation);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            cfp.setPerformative(ACLMessage.PROPOSE);
                            cfp.setConversationId("agent-recherche");
                            sd.setType("agent-recherche");
                            cfp.setReplyWith("cfp" + System.currentTimeMillis()); // Valeur
                            // unique
                            myAgent.send(cfp);
                            System.out.println(TYPEAGENT + " envoi de la réponse a l'agent recherche");
                        }
                    } catch (UnreadableException ex) {
                        Logger.getLogger(ClientAgent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            reservationVerifier = new Reservation();
            if (reqFromAgent != null) {
                //une reponse recu
                if (reqFromAgent.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                    try {
                        //recuperer l'objet de l'agent reservation
                        reservationVerifier = (Reservation) reqFromAgent.getContentObject();
                        //afficheReservation(reservationVerifier);
                        if (reservationVerifier != null) {
                            Reservation reserv = new Reservation();
                            List<Reservation> listeReservation = reserv.findByChambre(reservationVerifier.getChambre(), reservationVerifier.getDatArrive());
                            if (listeReservation.isEmpty()) {
                                reservationVerifier = null;
                            }

                            ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                            DFAgentDescription tmp = new DFAgentDescription();
                            ServiceDescription sd = new ServiceDescription();
                            sd.setType("agent-reservation");
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
                                cfp.setContentObject(reservationVerifier);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            cfp.setPerformative(ACLMessage.CONFIRM);
                            cfp.setConversationId("agent-reservation");
                            cfp.setReplyWith("cfp" + System.currentTimeMillis()); // Valeur
                            // unique
                            myAgent.send(cfp);
                            System.out.println(TYPEAGENT+" Envoi de la reponse de confirmation a l'agent reservation"+reservationVerifier);

                        }
                    } catch (UnreadableException ex) {
                        Logger.getLogger(ClientAgent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (reqFromAgent.getPerformative() == ACLMessage.CONFIRM) {
                    try {
                        //recuperer l'objet de l'agent reservation
                        reservationVerifier = (Reservation) reqFromAgent.getContentObject();
                        if (reservationVerifier != null && reservationVerifier.getClient() != null) {
                            reservationVerifier.getClient().save();
                            reservationVerifier.save();
                        }
                    } catch (UnreadableException ex) {
                        Logger.getLogger(ClientAgent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        @Override
        public boolean done() {
            return true;
        }

    }

    @Override
    protected void beforeMove() {
        try {
            System.out.println("Avant de migrer  ..... du container " + this.getContainerController().getContainerName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void afterMove() {
        try {
            System.out.println("apres de migrer  ..... vers le container " + this.getContainerController().getContainerName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void takeDown() {
        System.out.println("avant de mourir .....");
    }

    Chambre meilleurChambre(ArrayList<Chambre> chambres) {
        if (chambres.isEmpty()) {
            return null;
        }
        Chambre mc = chambres.get(0);
        for (Chambre chambre : chambres) {
            if (chambre.getPrix() < mc.getPrix()) {
                mc = chambre;
            }
        }
        return mc;
    }

    void afficheBesoinClient(BesoinsClient bs) {
        System.out.println("Affichage du besoin client");
        //System.out.println("Nom = " + bs.getNom());
        System.out.println("Localité = " + bs.getLocalite());
        System.out.println("Type chambre = " + bs.getTypeChambre());
        System.out.println("Date arrive = " + bs.getDatArrive());
        System.out.println("Date depart = " + bs.getDatDepart());
        System.out.println("Prix min = " + bs.getPrixMin());
        System.out.println("Prix Max = " + bs.getPrixMax());
    }

    void afficheChambre(Chambre bs) {
        System.out.println("Affichage du chambre");
        System.out.println("Num = " + bs.getNum());
        System.out.println("Type chambre = " + bs.getTypeChambre());
        System.out.println("Prix = " + bs.getPrix());
        System.out.println("Nom Hotel = " + bs.getHotel().getNom());
    }

    void afficheReservation(Reservation bs) {
        System.out.println("Affichage de la reservation");
        System.out.println("Nom Hotel = " + bs.getChambre().getHotel().getNom());
        System.out.println("Type chambre = " + bs.getChambre().getTypeChambre());
        System.out.println("Num = " + bs.getChambre().getNum());
        System.out.println("Prix = " + bs.getChambre().getPrix());
        System.out.println("Client = " + bs.getClient().getNom());
        System.out.println("Email Client = " + bs.getClient().getEmail());
        System.out.println("Date arrivé = " + bs.getDatArrive());
        System.out.println("Date départ = " + bs.getDatDepart());

    }

    public static void enregistrerDonnee() {
        Localite l1 = new Localite("Hanoi");
        l1.save();
        Localite l2 = new Localite("Metri");
        l2.save();
        Localite l3 = new Localite("Lome");
        l3.save();
        Localite l4 = new Localite("Aneho");
        l4.save();
        Localite l5 = new Localite("Siou");
        l5.save();
        Hotel h1 = new Hotel("PLAZA Hotel", l1, "99 99 99 99");
        h1.save();
        Hotel h2 = new Hotel("Hotel 2 FEVRIER", l1, "92 35 53 63");
        h2.save();
        Hotel h3 = new Hotel("Hotel ABA", l1, "92 45 66 63");
        h3.save();
        Hotel h4 = new Hotel("PALACE hotel", l2, "91 00 78 63");
        h4.save();
        Hotel h5 = new Hotel("Peace or Violence Hotel", l2, "78 35 93 67");
        h5.save();
        Hotel h6 = new Hotel("PMD", l2, "92 35 03 43");
        h6.save();
        Hotel h7 = new Hotel("NAM HOTEL", l3, "92 09 53 63");
        h7.save();
        Hotel h8 = new Hotel("PAPAOUTE HOTEL", l3, "92 99 53 00");
        h8.save();
        Hotel h9 = new Hotel("STROMAE HOTEL", l4, "92 90 65 00");
        h9.save();
        Hotel h10 = new Hotel("TEQUIERO HOTEL", l4, "94 79 00 00");
        h10.save();
        int somme = 10000;
        int somme1 = 1000000;
        int somme2 = 500000;
        for (int i = 0; i < 10; i++) {
            new Chambre("SIMPLE", "P" + String.valueOf(i), somme, h1).save();
            new Chambre("VIP", "V" + String.valueOf(i), somme1, h1).save();
            new Chambre("DOUBLE", "D" + String.valueOf(i), somme2, h1).save();
            if (i % 2 == 0) {
                somme -= 1000;
                somme1 -= 100000;
                somme2 -= 10000;
            } else {
                somme += 500;
                somme1 += 500000;
                somme2 += 70000;
            }
        }
        somme = 10000;
        somme1 = 1000000;
        somme2 = 500000;
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                somme -= 2000;
                somme1 -= 220000;
                somme2 -= 12000;
            } else {
                somme += 800;
                somme1 += 530000;
                somme2 += 77000;
            }
            new Chambre("SIMPLE", "P" + String.valueOf(i), somme, h2).save();
            new Chambre("VIP", "V" + String.valueOf(i), somme1, h2).save();
            new Chambre("DOUBLE", "D" + String.valueOf(i), somme2, h2).save();

        }
        somme = 5000;
        somme1 = 190000;
        somme2 = 90000;
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                somme -= 20;
                somme1 -= 22000;
                somme2 -= 1200;
            } else {
                somme += 800;
                somme1 += 53000;
                somme2 += 7700;
            }
            new Chambre("SIMPLE", "P" + String.valueOf(i), somme, h3).save();
            new Chambre("VIP", "V" + String.valueOf(i), somme1, h3).save();
            new Chambre("DOUBLE", "D" + String.valueOf(i), somme2, h3).save();

        }

    }
}
