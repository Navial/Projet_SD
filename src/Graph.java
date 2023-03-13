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
            System.out.println();
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
        Map<String, Troncon> mapBFS = new HashMap<>();
        Deque<String> fileDesStationsAParcourir = new ArrayDeque<>();
        Set<String> stationsParcourues = new HashSet<>();

        mapBFS.put(depart, null);
        fileDesStationsAParcourir.add(depart);

        while(!fileDesStationsAParcourir.isEmpty()){
            for (Troncon troncon : mapTronconsDepart.get(fileDesStationsAParcourir.poll())) {
                mapBFS.put(troncon.getArrivee(), troncon);
                stationsParcourues.add(troncon.getDepart());
                if(troncon.getArrivee().equals(arrivee)){
                    
                }
                fileDesStationsAParcourir.add(troncon.getArrivee());
            }   

        } 


    }

    public void calculerCheminMinimisantTempsTransport(String string, String string2) {
    }
}
