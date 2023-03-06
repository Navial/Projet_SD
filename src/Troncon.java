package src;

public class Troncon {
    private int indentifiant;
    private String depart;
    private String arrivee;
    private int duree;


    public Troncon(int indentifiant, String depart, String arrivee, int duree) {
        this.indentifiant = indentifiant;
        this.depart = depart;
        this.arrivee = arrivee;
        this.duree = duree;
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

    @Override
    public String toString() {
        return "{" +
            " indentifiant='" + getIndentifiant() + "'" +
            ", depart='" + getDepart() + "'" +
            ", arrivee='" + getArrivee() + "'" +
            ", duree='" + getDuree() + "'" +
            "}";
    }
}
