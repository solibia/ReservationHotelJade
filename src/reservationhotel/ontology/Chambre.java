/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationhotel.ontology;

import jade.content.Concept;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Basile
 */
@Entity
@Table(name = "Chambre")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Chambre.findAll", query = "SELECT c FROM Chambre c"),
    @NamedQuery(name = "Chambre.findById", query = "SELECT c FROM Chambre c WHERE c.id = :id"),
    @NamedQuery(name = "Chambre.findByNum", query = "SELECT c FROM Chambre c WHERE c.num = :num"),
    @NamedQuery(name = "Chambre.findByType", query = "SELECT c FROM Chambre c WHERE c.typeChambre = :type"),
    @NamedQuery(name = "Chambre.findByType_Prix", query = "SELECT c FROM Chambre c WHERE c.typeChambre = :type AND c.prix <= :prixMax AND c.prix >= :prixMin AND c.hotel.localite.nom = :localite ORDER BY c.prix"),
    //@NamedQuery(name = "Chambre.findByType_Prix", query = "SELECT c FROM Chambre c WHERE c.typeChambre = :type AND c.prix = :prix AND c.hotel.id = :hotel"),
    @NamedQuery(name = "Chambre.findByHotel", query = "SELECT c FROM Chambre c WHERE c.hotel.id = :hotel"),
    @NamedQuery(name = "Chambre.findAllOrder", query = "SELECT c FROM Chambre c ORDER BY c.num")})

public class Chambre extends Entitie implements Serializable, Concept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idChambre", nullable = false)
    private int id;
    @Column(name = "typeChambre", length = 100, nullable = false)
    private String typeChambre;
    @Column(name = "num", length = 100, nullable = false)
    private String num;
    @Column(name = "prix", nullable = false)
    private double prix;

    @ManyToOne
    @JoinColumn(name = "idHotel")
    private Hotel hotel;

    @OneToMany(mappedBy = "chambre", cascade = CascadeType.REMOVE)
    private List<Reservation> listReservationC; 

    // constructors

    public Chambre() {
    }

    public Chambre(String typeChambre, String num, double prix, Hotel hotel) {
        this.typeChambre = typeChambre;
        this.num = num;
        this.prix = prix;
        this.hotel = hotel;
    }

    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeChambre() {
        return typeChambre;
    }

    public void setTypeChambre(String typeChambre) {
        this.typeChambre = typeChambre;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<Reservation> getListReservationC() {
        return listReservationC;
    }

    public void setListReservationC(List<Reservation> listReservationC) {
        this.listReservationC = listReservationC;
    }
    
    //
    @SuppressWarnings("unchecked")
    public List<Chambre> findAllOrder() {
        Query query = DbManager.getEntityManager().createNamedQuery(
                "Chambre.findAllOrder");
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    //
    @SuppressWarnings("unchecked")
    public List<Chambre> findByHotel(Hotel h) {
        Query query = DbManager.getEntityManager()
                .createNamedQuery("Chambre.findByHotel")
                .setParameter("hotel", hotel.getId());
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    //
    @SuppressWarnings("unchecked")
    public List<Chambre> findByType(String type) {
        Query query = DbManager.getEntityManager()
                .createNamedQuery("Chambre.findByType").setParameter("typeChambre", type);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    //
    public Chambre findByNum(String num) {
        Query query = DbManager.getEntityManager()
                .createNamedQuery("Chambre.findByNum").setParameter("num", num);
        try {
            return (Chambre) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    //
    @SuppressWarnings("unchecked")
    public List<Chambre> findByType_Prix(String type, double prix, Hotel h) {
        Query query = DbManager.getEntityManager()
                .createNamedQuery("Chambre.findByType")
                .setParameter("type", type).setParameter("prix", prix)
                .setParameter("hotel", h.getId());
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Chambre> findByPredicats(String typeChambre, String localite, double prixMin, double prixMax) {
        Query query = DbManager.getEntityManager()
                .createNamedQuery("Chambre.findByType_Prix")
                .setParameter("type", typeChambre)
                .setParameter("localite", localite)
                .setParameter("prixMin", prixMin)
                .setParameter("prixMax", prixMax);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
