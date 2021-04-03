
import java.net.*;
import java.io.*;


public class ThreadServerPlay extends Thread {			// GESTION DES CLIENTS dans l'ensemble
	public static int Slot;
	private int listLenth;
	private static boolean[] listeSlotFree;
	private int port;
	private static ThreadListe[] thlist;
	private static String[] ListeClientidName;

	
	
	
	
	public ThreadServerPlay(int Slotr,int port_) {				// constructeur + recup les arguments de la class Server donc = super(x,y)
		Slot = Slotr;
		thlist = new ThreadListe[Slot];						// set parameter in the DATA slot
		ListeClientidName = new String[Slot];
		port = port_;
	}
	
	
	
	
	public void run() {
		
		System.out.println("cote Serveur");							// creation du server 
		ServerSocket sss = null;
		try {
			sss = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		int tour = -1;
		listeSlotFree = new boolean[Slot];	
		
		for(int i=0;i<Slot;i++) {										// libert tout les slots
			listeSlotFree[i] = true;
		}
		
		
		while(true) {
			
			try {
			
				tour = -1;
				for(int i = 0;i<Slot;i++) {								// choisit un slot libre -> tour
					if(listeSlotFree[i] == true) {
						tour = i;
					}				}
							
				if(tour != (-1)) {											// verifie si un slot est bien libre
					System.out.println("un Slot de libre");
					Socket s = sss.accept();
					System.out.println("socket accepter");
							
					
					thlist[tour] = (ThreadListe) new ThreadServer(s);	
	
					thlist[tour].setID(tour);
			
					((Thread) thlist[tour]).start();
					
					listeSlotFree[tour] = false;							// fermuture du slot en question
					
					System.out.println("mise en place du socket client numero" + tour);
			
				}
			
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("erreur lors de la lecture du socket");				
			}	
		}
	}
	
	public static void outOfClient(int client) {
		listeSlotFree[client] = true;
		ListeClientidName[client] = null;
	}

	
	public ThreadListe[] getliste() {
		return this.thlist;
	}
	public int getlenth() {
		return this.listLenth;
	}
	public void sendmsg(int Aqui,String msg) {
		thlist[Aqui].SendMsg(msg);
	}

	public static void redirection(int aqui, String msg) {	
		thlist[aqui].SendMsg(msg);
	}
	
	public static String[] getlisteName() {
		return ListeClientidName;
	}
	public static void setlisteName(String idName,int id) {
		ListeClientidName[id] = idName;
	}
	
	
	
}
