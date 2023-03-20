package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

public class Graph {

    private Map<String, Set<Troncon>> mapTronconsDepart;
    private Map<Integer, Ligne> mapLigne;
    // ajouter une file et un autre set qui servira de stocker toutes les stations
    //deja pacrourues pour BFS
    public Graph(File fileLignes, File fileTrancons) throws FileNotFoundException {
        this.mapTronconsDepart = new HashMap<>();

        this.mapLigne = new HashMap<>();


        Scanner scLigne = new Scanner(fileLignes);
        Scanner scTrancons = new Scanner(fileTrancons);

        while (scLigne.hasNextLine()){
            String value = scLigne.nextLine();
            String[] values = value.split(",");
            Ligne ligne = new Ligne(Integer.parseInt(values[0]),(values[1]),values[2],values[3],values[4],Integer.parseInt(values[5]));
            mapLigne.put(Integer.parseInt(values[0]),ligne);
        }

        while (scTrancons.hasNextLine()){
            String value = scTrancons.nextLine();
            String[] values = value.split(",");
            Ligne ligne = mapLigne.get(Integer.parseInt(values[0]));
            Troncon troncon = new Troncon(Integer.parseInt(values[0]),values[1],values[2],Integer.parseInt(values[3]),ligne);
            if (!mapTronconsDepart.containsKey(troncon.getDepart())){
                mapTronconsDepart.put(troncon.getDepart(), new HashSet<>());
                mapTronconsDepart.get(troncon.getDepart()).add(troncon);
            } else {
                mapTronconsDepart.get(troncon.getDepart()).add(troncon);
            }
        }
    }

    public void calculerCheminMinimisantNombreTroncons(String depart, String arrivee) {
        // Map de tous les chemins les plus court entre le départ et toutes les autres stations 
        Map<String, Troncon> mapBFS = new HashMap<>();
        // Retiens toutes les stations a parcourir dans l'ordre 
        Deque<String> fileDesStationsAParcourir = new ArrayDeque<>();
        // Retiens toutes les stations parcoures pour ne pas tourner en boucle
        Set<String> stationsParcourues = new HashSet<>();
        // Contient l'itinéraire dans le bon ordre
        Deque<Troncon> itineraire = new ArrayDeque<>();

        int nbreTrancons;
        int dureeTransport;
        int dureeTotal;

        mapBFS.put(depart, null);
        fileDesStationsAParcourir.add(depart);

        while(!fileDesStationsAParcourir.isEmpty()){
            String stationCourante = fileDesStationsAParcourir.poll();
            for (Troncon troncon : mapTronconsDepart.get(stationCourante))  {
                if(!stationsParcourues.contains(troncon.getArrivee())){

                    mapBFS.put(troncon.getArrivee(), troncon);
                    stationsParcourues.add(troncon.getDepart());
                    if(troncon.getArrivee().equals(arrivee)){
                        fileDesStationsAParcourir.clear();
                        Troncon tronconItineraire = troncon;
                        while(!tronconItineraire.getDepart().equals(depart)){
                            itineraire.addFirst(tronconItineraire);
                            tronconItineraire = mapBFS.get(tronconItineraire.getDepart());
                        }
                        itineraire.addFirst(tronconItineraire);
                        break;
                    }
                    if(!fileDesStationsAParcourir.contains(troncon.getArrivee()))
                        fileDesStationsAParcourir.add(troncon.getArrivee());    
                }
            }   
        }

        nbreTrancons = 0;
        dureeTransport = 0;
        dureeTotal = 0;
        int i = 0;
        Troncon tronconPrecedent;
        for(Troncon troncon : itineraire){
            nbreTrancons ++;
            dureeTransport += troncon.getDuree();
            if (i == 0){
                dureeTotal += (troncon.getLigne().getTempsAttenteMoyen());
                i++;
            }
            tronconPrecedent = troncon;
            if (troncon.getLigne().getId() != tronconPrecedent.getLigne().getId()){
                dureeTotal += (troncon.getLigne().getTempsAttenteMoyen());
            }
            System.out.println(troncon);
        }
        dureeTotal += dureeTransport;
        System.out.println("");
        System.out.println("nbTroncons=" + nbreTrancons);
        System.out.println("dureeTransport=" +dureeTransport+ " dureeTotale="+ dureeTotal);
    }

    public void calculerCheminMinimisantTempsTransport(String depart, String arrivee) {
        // etiquettes provisoires => Map<sommet, valeur actuelle>
        Map<String, Integer> etiquetteProvisoire = new HashMap<>();

        // etiquettes définitives => Map<sommet, valeur definitive>
        Map<String, Integer> etiquetteDefinitive = new HashMap<>();

        // Map pour retenir l'arc avec son départ et l'arrivée (comme BFS)
        Map<String, Troncon> mapTrajetLePlusCourt = new HashMap<>();

        // Deque pour inverser le chemin de la map
        Deque<Troncon> itineraire = new ArrayDeque<>();
        
        // Etiquettes provisoire : remplis un tableau avec, comme valeur, le temps de trajet pour aller d'un 
        // point de départ à tous les autres qui sont accèssible en 1 troncon + le temps necessaire pour
        // aller a ce nouveau point de départ
        etiquetteProvisoire.put(depart, 0);
        etiquetteDefinitive.put(depart, 0);
        String stationCourante = depart;

        for(int i=0 ; i< 5; i++){
                
            for(Troncon troncon : mapTronconsDepart.get(stationCourante)){
                etiquetteProvisoire.put(troncon.getArrivee(), troncon.getDuree());
            }
            System.out.println("etiquetteProvisoire : " + etiquetteProvisoire);

            int valeurTrajetActuel = Integer.MIN_VALUE-1;
            for(Entry<String, Integer> entry : etiquetteProvisoire.entrySet()){
                System.out.println("--- entry : " + entry + " valeurTrajetActuel : " + valeurTrajetActuel);
                if(entry.getValue()<valeurTrajetActuel && !etiquetteDefinitive.containsKey(entry.getKey())){
                    System.out.println("--- Nv minimum : " + entry);
                    stationCourante = entry.getKey();
                    valeurTrajetActuel = entry.getValue();
                }
            }
            
            etiquetteDefinitive.put(stationCourante, valeurTrajetActuel);

            if(stationCourante == null)
                return;
            
            // System.out.println("mapTronconsDepart.get(stationCourante) : "  + mapTronconsDepart.get(stationCourante));
            // System.out.println("etiquetteProvisoire : " + etiquetteProvisoire);
            System.out.println("*** new etiquetteDefinitive : " + etiquetteDefinitive);
            System.out.println("stationCourante :  " + stationCourante);
            System.out.println("valeurTrajetActuel"  + valeurTrajetActuel);
            }
            for(Troncon troncon : mapTronconsDepart.get(stationCourante)){
                etiquetteProvisoire.put(troncon.getArrivee(), troncon.getDuree());
            }
            System.out.println("etiquetteProvisoire : " + etiquetteProvisoire);

            int valeurTrajetActuel = Integer.MIN_VALUE-1;
            for(Entry<String, Integer> entry : etiquetteProvisoire.entrySet()){
                System.out.println("--- entry : " + entry + " valeurTrajetActuel : " + valeurTrajetActuel);
                if(entry.getValue()<valeurTrajetActuel && !etiquetteDefinitive.containsKey(entry.getKey())){
                    System.out.println("--- Nv minimum : " + entry);
                    stationCourante = entry.getKey();
                    valeurTrajetActuel = entry.getValue();
                }
            }
            
        etiquetteDefinitive.put(stationCourante, valeurTrajetActuel);

        if(stationCourante == null)
            return;
        
        // System.out.println("mapTronconsDepart.get(stationCourante) : "  + mapTronconsDepart.get(stationCourante));
        // System.out.println("etiquetteProvisoire : " + etiquetteProvisoire);
        System.out.println("*** new etiquetteDefinitive : " + etiquetteDefinitive);
        System.out.println("stationCourante :  " + stationCourante);
        System.out.println("valeurTrajetActuel"  + valeurTrajetActuel);
        // for (Troncon troncon : mapTronconsDepart.get(stationCourante))  {

        // Etiquettes definitives : A chaque fin de tours, va mettre dans un tableau toutes les valeurs où le 
        // temps est le plus petit

    }
}
