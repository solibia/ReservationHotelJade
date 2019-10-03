package reservationhotel.ontology;

import java.util.List;

import javax.persistence.Query;

public class Entitie {

	public boolean save() {
        try {
            DbManager.getEntityManager().persist(this);
            DbManager.commit();
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
	
	public boolean update() {
        try {
            DbManager.getEntityManager().merge(this);
            DbManager.commit();
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public boolean delete() {
        try {
            DbManager.getEntityManager().remove(this);
            DbManager.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    
     @SuppressWarnings("unchecked")
	public Object find(int id, Class oclass) {
        try {
            return DbManager.getEntityManager().find(oclass, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param T of object's class to return
     * @return list of object
     */
    public List findAll(Class T) {
        Query q = DbManager.getEntityManager().createNamedQuery(T.getSimpleName() + ".findAll");
        return q.getResultList();
    }
}
