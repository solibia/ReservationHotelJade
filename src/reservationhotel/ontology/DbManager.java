/**
 *
 * @author Basile
 */
package reservationhotel.ontology;

import javax.persistence.*;

public class DbManager {

    private static EntityManagerFactory emf;
    private static EntityTransaction tx;
    private static EntityManager em;
    private static final DbManager instance = new DbManager();
    
    //constructor
    public DbManager(){
    	
    }
    
    public static EntityManager getEntityManager() {
        if (emf == null || !emf.isOpen()) {
            emf = Persistence.createEntityManagerFactory("reservationhotelPU");
            
        }
        if (em == null || !em.isOpen()) {
            em = emf.createEntityManager();
        }
        tx = em.getTransaction();
        if(!tx.isActive()){
            tx.begin();
        }
        return em;
    }

    public static void commit() {
        tx.commit();
    }

    public static void end() {
        em.close();
        emf.close();
    }

    public static EntityManager getEm() {
        return em;
    }

    
    public static DbManager getInstance() {
        return instance;
    }

    public static EntityTransaction getTx() {
        return tx;
    }
    
}
