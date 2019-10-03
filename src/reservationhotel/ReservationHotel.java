/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationhotel;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import reservationhotel.agents.BaseDeDonneeAgent;
import reservationhotel.ontology.DbManager;

/**
 *
 * @author Basile
 */
public class ReservationHotel {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DbManager db = new DbManager();
        
        try {
            BaseDeDonneeAgent.enregistrerDonnee();
            jade.core.Runtime rt = jade.core.Runtime.instance();
            Properties p = new ExtendedProperties();
            p.setProperty(Profile.GUI, "true");
            ProfileImpl proImpl = new ProfileImpl(p);
            AgentContainer agentContainer = rt.createMainContainer(proImpl);
            AgentController agentController = agentContainer.createNewAgent("CLIENT", reservationhotel.agents.ClientAgent.class.getName(), new Object[]{});
            agentController.start();
            agentController = agentContainer.createNewAgent("RECHERCHE", reservationhotel.agents.RechercheAgent.class.getName(), new Object[]{});
            agentController.start();
            agentController = agentContainer.createNewAgent("BASEDEDONNE", reservationhotel.agents.BaseDeDonneeAgent.class.getName(), new Object[]{});
            agentController.start();
            agentController = agentContainer.createNewAgent("HOTEL", reservationhotel.agents.HotelAgent.class.getName(), new Object[]{});
            agentController.start();
            agentController = agentContainer.createNewAgent("RESERVATION", reservationhotel.agents.ReservationAgent.class.getName(), new Object[]{});
            agentController.start();
            agentController = agentContainer.createNewAgent("FACILITATEUR", reservationhotel.agents.FacilitateurAgent.class.getName(), new Object[]{});
            agentController.start();

        } catch (ControllerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
