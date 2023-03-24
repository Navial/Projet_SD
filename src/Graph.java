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
import java.util.AbstractMap.SimpleEntry;
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

                        nbreTrancons = 0;
                        dureeTransport = 0;
                        dureeTotal = 0;
                        int i = 0;
                        Troncon tronconPrecedent;
                        for(Troncon t : itineraire){
                            nbreTrancons ++;
                            dureeTransport += t.getDuree();
                            if (i == 0){
                                dureeTotal += (t.getLigne().getTempsAttenteMoyen());
                                i++;
                            }
                            tronconPrecedent = t;
                            if (t.getLigne().getId() != tronconPrecedent.getLigne().getId()){
                                dureeTotal += (t.getLigne().getTempsAttenteMoyen());
                            }
                            System.out.println(t);
                        }
                        dureeTotal += dureeTransport;
                        System.out.println("");
                        System.out.println("nbTroncons=" + nbreTrancons);
                        System.out.println("dureeTransport=" +dureeTransport+ " dureeTotale="+ dureeTotal);
                        return;
                    }
                    if(!fileDesStationsAParcourir.contains(troncon.getArrivee()))
                        fileDesStationsAParcourir.add(troncon.getArrivee());    
                }
            }   
        }
    }

    public void calculerCheminMinimisantTempsTransport(String depart, String arrivee) {
        // etiquettes provisoires => Map<sommet, valeur actuelle>
        Map<String, Integer> etiquetteProvisoire = new HashMap<>();

        // etiquettes définitives => Map<sommet, valeur definitive>
        Map<String, Integer> etiquetteDefinitive = new HashMap<>();

        // Map pour retenir l'arc avec son départ et l'arrivée (comme BFS)
        Map<String, Troncon> mapTrajetLePlusCourt = new HashMap<>();
        
        etiquetteProvisoire.put(depart, 0);
        String stationCourante = depart;

        while(!stationCourante.equals(arrivee)){

            // Recherche du plus petit trajet parmis les etiquettes provisoire
            Entry<String, Integer> plusPetitTrajet = new SimpleEntry<String, Integer>(null, Integer.MAX_VALUE);
            
            for(Entry<String, Integer> entry : etiquetteProvisoire.entrySet()){
                if(entry.getValue()<plusPetitTrajet.getValue() ){
                    plusPetitTrajet = entry;
                }
            }

            // Ajout de la plus petite valeur au etiquettes definitives + supression dans les etiquettes provisoires
            String key = plusPetitTrajet.getKey();
            etiquetteDefinitive.put(key, plusPetitTrajet.getValue());            
            etiquetteProvisoire.remove(key);

            stationCourante = key;
            // Construction de l'itineraire en retrournant la map
            if(stationCourante.equals(arrivee)){
                // Deque pour inverser le chemin de la map
                Deque<Troncon> itineraire = new ArrayDeque<>();

                Troncon tronconItineraire = mapTrajetLePlusCourt.get(stationCourante);

                while(!tronconItineraire.getDepart().equals(depart)){
                    itineraire.addFirst(tronconItineraire);
                    tronconItineraire = mapTrajetLePlusCourt.get(tronconItineraire.getDepart());
                }
                itineraire.addFirst(tronconItineraire);

                int nbrTroncons = 0;
                int dureeTotale = 0;
                int dureeTransport = 0;
                String lignePrecedente = null;

                for(Troncon troncon : itineraire){
                    if(!troncon.getLigne().getNumeroDeLigne().equals(lignePrecedente)){
                        dureeTotale += troncon.getLigne().getTempsAttenteMoyen();
                    }
                    nbrTroncons ++;
                    dureeTransport += troncon.getDuree();

                    System.out.println(troncon);
                    lignePrecedente = troncon.getLigne().getNumeroDeLigne();
                }
                dureeTotale += dureeTransport;
                System.out.println("nbTroncons : " + nbrTroncons);
                System.out.println("dureeTransport : " + dureeTransport + " dureeTotal : " + dureeTotale);
                return;
            }
            // Mise à jour des etiquettes provisoires
            for(Troncon troncon : mapTronconsDepart.get(stationCourante)){
                if(!etiquetteDefinitive.containsKey(troncon.getArrivee())){
                    if(etiquetteProvisoire.containsKey(troncon.getArrivee())){
                        if(etiquetteDefinitive.get(stationCourante) + troncon.getDuree() < etiquetteProvisoire.get(troncon.getArrivee())){
                            etiquetteProvisoire.put(troncon.getArrivee(), etiquetteDefinitive.get(stationCourante) + troncon.getDuree());
                            mapTrajetLePlusCourt.put(troncon.getArrivee(), troncon);
                        }
                    }else{
                        etiquetteProvisoire.put(troncon.getArrivee(), etiquetteDefinitive.get(stationCourante) + troncon.getDuree());
                        mapTrajetLePlusCourt.put(troncon.getArrivee(), troncon);
                    }
                }
            }
        }
    }
}