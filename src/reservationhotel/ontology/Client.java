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
@Table(name = "Client")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
    @NamedQuery(name = "Client.findById", query = "SELECT c FROM Client c WHERE c.id = :id"),
    @NamedQuery(name = "Client.findAllOrder", query = "SELECT c FROM Client c ORDER BY c.nom,c.prenom")})

public class Client extends Entitie implements Serializable, Concept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idClient", nullable = false)
    private int id;
    @Column(name = "nom", length = 100, nullable = false)
    private String nom;
    @Column(name = "prenom", length = 100, nullable = false)
    private String prenom;

    @Column(name = "email", length = 100, nullable = false)
    private String email;
    @Column(name = "telephone", length = 100, nullable = false)
    private String telephone;

    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
    private List<Reservation> listReservationCl; // = new LinkedList<Reservation>();

    // constructors
    public Client() {
    }

    public Client(String nom, String prenom, String email, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<Reservation> getListReservationCl() {
        return listReservationCl;
    }

    public void setListReservationCl(List<Reservation> listReservationCl) {
        this.listReservationCl = listReservationCl;
    }

    //
    @SuppressWarnings("unchecked")
    public List<Client> findAllOrder() {
        Query query = DbManager.getEntityManager().createNamedQuery(
                "Client.findAllOrder");
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
