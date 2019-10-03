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
@Table(name = "Localite")
@XmlRootElement
@NamedQueries({
		@NamedQuery(name = "Localite.findAll", query = "SELECT l FROM Localite l"),
		@NamedQuery(name = "Localite.findById", query = "SELECT l FROM Localite l WHERE l.id = :id"),
		@NamedQuery(name = "Localite.findByNom", query = "SELECT l FROM Localite l WHERE l.nom = :nom"),
		@NamedQuery(name = "Localite.findAllOrder", query = "SELECT l FROM Localite l ORDER BY l.id") })

public class Localite extends Entitie implements Serializable, Concept {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLocalite", nullable = false)
    private int id;
    @Column(name = "nom", length = 100, nullable = false)
    private String nom;
   
    @OneToMany(mappedBy = "localite", cascade = CascadeType.REMOVE)
    private List<Hotel> listHotel; // = new LinkedList<Hotel>();
   
    //
    // constructors

    public Localite() {
    }

    public Localite(String nom) {
        this.nom = nom;
    }

    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setListHotel(List<Hotel> listHotel) {
        this.listHotel = listHotel;
    }

    public List<Hotel> getListHotel() {
        return listHotel;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    
    //
    @SuppressWarnings("unchecked")
    public List<Localite> findAllOrder() {
        Query query = DbManager.getEntityManager().createNamedQuery(
                "Localite.findAllOrder");
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    //
    @SuppressWarnings("unchecked")
    public List<Localite> findByNom(String nom) {
        Query query = DbManager.getEntityManager()
                .createNamedQuery("Localite.findByNom").setParameter("nom", nom);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    
}
