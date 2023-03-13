package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.server.SocketSecurityException;
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

    // ajouter une file et un autre set qui servira de stocker toutes les stations
    //deja pacrourues pour BFS

    public Graph(File fileLignes, File fileTrancons) throws FileNotFoundException {

        this.mapTronconsDepart = new HashMap<>();
        Scanner scLigne = new Scanner(fileLignes);
        Scanner scTroncons = new Scanner(fileTrancons);

        while (scTroncons.hasNextLine()){
            String value = scTroncons.nextLine();
            String[] values = value.split(",");
            Troncon troncon = new Troncon(Integer.parseInt(values[0]),values[1],values[2],Integer.parseInt(values[3]));
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
