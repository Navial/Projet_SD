package src;

public class Troncon {
    private int indentifiant;
    private String depart;
    private String arrivee;
    private int duree;
    private Ligne ligne;


    public Troncon(int indentifiant, String depart, String arrivee, int duree, Ligne ligne) {
        this.indentifiant = indentifiant;
        this.depart = depart;
        this.arrivee = arrivee;
        this.duree = duree;
        this.ligne= ligne;
    }    

    public int getIndentifiant() {
        return this.indentifiant;
    }

    public String getDepart() {
        return this.depart;
    }

    public String getArrivee() {
        return this.arrivee;
    }

    public int getDuree() {
        return this.duree;
    }

    public Ligne getLigne() {
        return ligne;
    }

    @Override
    public String toString() {
        return "Troncon [ " +
            " indentifiant='" + getIndentifiant() + "'" +
            ", depart='" + getDepart() + "'" +
            ", arrivee='" + getArrivee() + "'" +
            ", duree='" + getDuree() + " , ligne = "  + ligne.toString()+
            " ] ";
    }
}
