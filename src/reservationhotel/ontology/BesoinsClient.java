/**
 *
 * @author Basile
 */

package reservationhotel.ontology;

import jade.content.Predicate;
import java.util.Date;

public class BesoinsClient implements Predicate {

    private String localite;
    private String typeChambre;
    private Date datDepart;
    private Date datArrive;
    private double prixMax;
    private double prixMin;

    public double getPrixMax() {
        return prixMax;
    }

    public double getPrixMin() {
        return prixMin;
    }


    public void setPrixMax(double prixMax) {
        this.prixMax = prixMax;
    }

    public void setPrixMin(double prixMin) {
        this.prixMin = prixMin;
    }

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

    public String getLocalite() {
        return localite;
    }

    public void setLocalite(String localite) {
        this.localite = localite;
    }

    public String getTypeChambre() {
        return typeChambre;
    }

    public void setTypeChambre(String typeChambre) {
        this.typeChambre = typeChambre;
    }

}
