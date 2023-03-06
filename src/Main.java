package src;
import java.io.File;

public class Main {
	public static void main(String[] args) {
		try {
			File lignes = new File("lignes.txt");
			File troncons = new File("troncons.txt");
			Graph g = new Graph(lignes,troncons);
			g.calculerCheminMinimisantNombreTroncons("BOILEAU", "ALMA");
			System.out.println("------------------------------");
			g.calculerCheminMinimisantTempsTransport("BOILEAU", "ALMA");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
