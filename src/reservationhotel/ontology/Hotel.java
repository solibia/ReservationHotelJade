package reservationhotel.ontology;

import jade.content.Concept;
import jade.core.AID;
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
@Table(name = "Hotel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hotel.findAll", query = "SELECT h FROM Hotel h"),
    @NamedQuery(name = "Hotel.findById", query = "SELECT h FROM Hotel h WHERE h.id = :id"),
    @NamedQuery(name = "Hotel.findByLocalite", query = "SELECT h FROM Hotel h WHERE h.localite.id = :localite"),
    @NamedQuery(name = "Hotel.findAllOrder", query = "SELECT h FROM Hotel h ORDER BY h.nom")})

public class Hotel extends Entitie implements Serializable, Concept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idHotel", nullable = false)
    private int id;
    @Column(name = "nom", nullable = false)
    private String nom;
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @ManyToOne
    @JoinColumn(name = "idLocalite")
    private Localite localite;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.REMOVE)
    private List<Chambre> listChambre; 

    //constructors
    public Hotel() {
    }

    public Hotel(String nom, Localite localite, String telephone) {
        super();
        this.nom = nom;
        this.localite = localite;
        this.telephone = telephone;
    }

    //getters and setters
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

    public String getPhone() {
        return telephone;
    }

    public void setPhone(String telephone) {
        this.telephone = telephone;
    }

    public List<Chambre> getListChambre() {
        return listChambre;
    }

    public void setListChambre(List<Chambre> listChambre) {
        this.listChambre = listChambre;
    }

    public Localite getLocalite() {
        return localite;
    }

    public void setLocalite(Localite localite) {
        this.localite = localite;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    //
    @SuppressWarnings("unchecked")
    public List<Hotel> findByLocalite(Localite localite) {
        Query query = DbManager.getEntityManager()
                .createNamedQuery("Hotel.findByLocalite")
                .setParameter("localite", localite.getId());
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    //
    @SuppressWarnings("unchecked")
    public List<Hotel> findAllOrder() {
        Query query = DbManager.getEntityManager()
                .createNamedQuery("Hotel.findAllOrder");
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
