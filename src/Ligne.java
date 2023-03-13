package src;

public class Ligne {

    private int id;
    private String numeroDeLigne;
    private String premiereStation;
    private String terminus;
    private String typeVehicule;
    private int tempsAttenteMoyen;



    public Ligne(int id , String numeroDeLigne, String premiereStation, String terminus, String typeVehicule, int tempsAttenteMoyen) {
        this.id = id;
        this.numeroDeLigne = numeroDeLigne;
        this.premiereStation = premiereStation;
        this.terminus = terminus;
        this.typeVehicule = typeVehicule;
        this.tempsAttenteMoyen = tempsAttenteMoyen;
    }

    public int getId() {
        return this.id;
    }

    public String getNumeroDeLigne() {
        return this.numeroDeLigne;
    }

    public String getPremiereStation() {
        return this.premiereStation;
    }

    public String getTerminus() {
        return this.terminus;
    }

    public String getVehicule() {
        return this.typeVehicule;
    }

    public int getTempsAttenteMoyen() {
        return this.tempsAttenteMoyen;
    }



    @Override
    public String toString() {
        return " Ligne = [ " +
            " id='" + getId() + "'" +
            ", numeroDeLigne='" + getNumeroDeLigne() + "'" +
            ", premiereStation='" + getPremiereStation() + "'" +
            ", terminus='" + getTerminus() + "'" +
            ", vehicule='" + getVehicule() + "'" +
            ", tempsAttenteMoyen='" + getTempsAttenteMoyen() + "'" +
            "] ";
    }

}