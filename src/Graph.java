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
        // TODO add dans le file
        this.mapLigne = new HashMap<>();
        // TODO add dans le file
        int numeroLigne = 1;

        Scanner scLigne = new Scanner(fileLignes);
        Scanner scTrancons = new Scanner(fileTrancons);

        // TODO add dans le file
        while (scLigne.hasNextLine()){
            String value = scLigne.nextLine();
            String[] values = value.split(",");
            Ligne ligne = new Ligne(Integer.parseInt(values[1]),values[2],values[3],values[4],Integer.parseInt(values[5]));
            mapLigne.put(numeroLigne++,ligne);
        }

        while (scTrancons.hasNextLine()){
            String value = scTrancons.nextLine();
            String[] values = value.split(",");
            Ligne ligne = mapLigne.get(Integer.parseInt(values[0]));
            Troncon trancon = new Troncon(Integer.parseInt(values[0]),values[1],values[2],Integer.parseInt(values[3]),ligne );
            System.out.println(trancon);
            if (!mapTronconsDepart.containsKey(trancon.getDepart())){
                mapTronconsDepart.put(trancon.getDepart(), new HashSet<>());
                mapTronconsDepart.get(trancon.getDepart()).add(trancon);
            } else {
                mapTronconsDepart.get(trancon.getDepart()).add(trancon);
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
