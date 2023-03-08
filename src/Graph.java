package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Graph {

    private Map< Integer, Set<Troncon>> map;
    // ajouter une file et un autre set qui servira de stocker toutes les stations
    //deja pacrourues pour BFS

    public Graph(File fileLignes, File fileTrancons) throws FileNotFoundException {


        Scanner scLigne = new Scanner(fileLignes);
        Scanner scTrancons = new Scanner(fileTrancons);

        while (scLigne.hasNextLine()){
            String value = scLigne.nextLine();
            String[] values = value.split(",");
            Ligne ligne = new Ligne(Integer.parseInt(values[0]),Integer.parseInt(values[1]),values[2],values[3],values[4],Integer.parseInt(values[5]));

        }

    }
}
