
import java.net.*;
import java.io.*;
import java.util.Scanner;



class Server extends ThreadServerPlay{
	

	public Server(int Slot,int port) {
		super(Slot,port);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] arg) throws IOException, InterruptedException {
		System.out.println("cote Serveur");
		int solt_ = 6;
		int port_ = 48650;
		System.out.print("il y a ");
		System.out.print(solt_);
		System.out.println(" solt");
		System.out.print("serveur demarré sur le port ");
		System.out.println(port_);

		Scanner scan = new Scanner(System.in);
		
		ThreadServerPlay thservPlay = new ThreadServerPlay(solt_,port_);	// attrubue 6 slot    + 2 argument 
		thservPlay.start();										// instance de thPlay   .sendmsg    No instance .redirection
		
		while(true) {
			
			System.out.println("entrer thread");
			String straqui = scan.nextLine();	
			System.out.println(straqui);
			System.out.println("entrer message");
			String strmsg = scan.nextLine();
			thservPlay.sendmsg(Integer.parseInt(straqui), strmsg);
			
			
		}
		
	}}
