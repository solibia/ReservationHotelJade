package reservationhotel.ontology;

import jade.content.Concept;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Basile
 */
@Entity
@Table(name = "Reservation")
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r"),
		@NamedQuery(name = "Reservation.findById", query = "SELECT r FROM Reservation r WHERE r.id = :id"),
		@NamedQuery(name = "Reservation.findByClient", query = "SELECT r FROM Reservation r WHERE r.client.id = :client"),
		@NamedQuery(name = "Reservation.findByChambre", query = "SELECT r FROM Reservation r WHERE r.chambre.id = :chambre and r.datDepart< :datArrive"),
		@NamedQuery(name = "Reservation.findAllOrder", query = "SELECT r FROM Reservation r ORDER BY r.id") })

public class Reservation extends Entitie implements Serializable, Concept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReservation", nullable = false)
    private int id;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "dateDepart", nullable = false)
    Date datDepart;
        
    @Column(name = "dateArrivee", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    Date datArrive;
    
    @ManyToOne
    @JoinColumn(name = "idChambre")
    private Chambre chambre;
    @ManyToOne
    @JoinColumn(name = "idClient")
    private Client client;

    // constructors

    public Date getDatArrive() {
        return datArrive;
    }

    public void setDatArrive(Date datArrive) {
        this.datArrive = datArrive;
    }

    public Date getDatDepart() {
        return datDepart;
    }

    public void setDatDepart(Date datDepart) {
        this.datDepart = datDepart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Reservation() {
    }

    public Reservation(Date datDepart, Date datArrive, Chambre chambre) {
        this.datDepart = datDepart;
        this.datArrive = datArrive;
        this.chambre = chambre;
    }

    public Chambre getChambre() {
        return chambre;
    }

    public void setChambre(Chambre chambre) {
        this.chambre = chambre;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    //
    @SuppressWarnings("unchecked")
    public List<Reservation> findAllOrder() {
        Query query = DbManager.getEntityManager().createNamedQuery(
                "Reservation.findAllOrder");
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    //
    @SuppressWarnings("unchecked")
    public List<Reservation> findByChambre(Chambre h) {
        Query query = DbManager.getEntityManager()
                .createNamedQuery("Reservation.findByChambre")
                .setParameter("chambre", chambre.getId()).setParameter("datArrivee", datArrive);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    //
    @SuppressWarnings("unchecked")
    public List<Reservation> findByClient(String type) {
        Query query = DbManager.getEntityManager()
                .createNamedQuery("Reservation.findByClient").setParameter("client",client.getId() );
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    @SuppressWarnings("unchecked")
    public List<Reservation> findByChambre(Chambre h,Date datArrivee) {
        Query query = DbManager.getEntityManager()
                .createNamedQuery("Reservation.findByChambre")
                .setParameter("chambre", h.getId())
                .setParameter("datArrive", datArrivee);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
