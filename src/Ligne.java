package src;

public class Ligne {

    private int id;
    private int numeroDeLigne;
    private String premiereStation;
    private String terminus;
    private Type vehicule;
    private int tempsAttenteMoyen;

    public enum Type{
        METRO, TRAM, BUS 
    }

    public Ligne(int id, int numeroDeLigne, String premiereStation, String terminus, Type vehicule, int tempsAttenteMoyen) {
        this.id = id;
        this.numeroDeLigne = numeroDeLigne;
        this.premiereStation = premiereStation;
        this.terminus = terminus;
        this.vehicule = vehicule;
        this.tempsAttenteMoyen = tempsAttenteMoyen;
    }

    public int getId() {
        return this.id;
    }

    public int getNumeroDeLigne() {
        return this.numeroDeLigne;
    }

    public String getPremiereStation() {
        return this.premiereStation;
    }

    public String getTerminus() {
        return this.terminus;
    }

    public Type getVehicule() {
        return this.vehicule;
    }

    public int getTempsAttenteMoyen() {
        return this.tempsAttenteMoyen;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", numeroDeLigne='" + getNumeroDeLigne() + "'" +
            ", premiereStation='" + getPremiereStation() + "'" +
            ", terminus='" + getTerminus() + "'" +
            ", vehicule='" + getVehicule() + "'" +
            ", tempsAttenteMoyen='" + getTempsAttenteMoyen() + "'" +
            "}";
    }

}