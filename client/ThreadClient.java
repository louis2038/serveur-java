
import java.awt.Desktop;
import java.net.*;
import java.io.*;
import java.net.URI;

public class ThreadClient extends Thread{
	private Socket sock;
	private String str;
	private String id;
	
	public ThreadClient( String ip, int port) throws UnknownHostException, IOException {
		
		Socket sock = new Socket(ip,port);
	    this.sock = sock;
	    
	}
	
	
	public void run(){
		String[] listemsg;
		
		try {		
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();}
		
		try {
			while(true) {
			InputStreamReader in = new InputStreamReader(this.sock.getInputStream());
			BufferedReader br = new BufferedReader(in);

		
			
			this.str = br.readLine();		
			listemsg = str.split("&");
			
			
			if(listemsg[0].contains("client")) {
				
				if(listemsg[1].contains("newclientID")) {			
				this.id = listemsg[2];
				System.out.println("attribution de l'id" + this.id);}
					
				if(listemsg[1].contains("afficher")) {
					System.out.println(listemsg[2]);}
				
				if(listemsg[1].contains("listeclientname")) {
					for(int i = 2;i<(listemsg.length);i++) {
						System.out.println(i-2);
						System.out.println(listemsg[i]);
					}}
				if(listemsg[1].contains("error")){
						if(listemsg[2].contains("newclientError")){
							System.out.println("newclient error, le user est deja pris");
						}

						if(listemsg[2].contains("connectedError")){
							if(listemsg[3].contains("passnotvalid")){
								System.out.println("le mot de passe est invalide");
							}
							if(listemsg[3].contains("usernotfound")){
								System.out.println("le user est introuvable");
							}
						}}

						if(listemsg[2].contains("notAccessToken")){
							System.out.println("vous n'avez pas l'autorisation !");
						}

						if(listemsg[2].contains("argumentError")){
							System.out.println("erreur d'argument");
						}

				if(listemsg[1].contains("connected")){
					this.id = listemsg[2];
					System.out.println("attribution de l'id" + this.id);}
				
				if(listemsg[1].contains("explorefile")){
					for(int i = 3;i<listemsg.length; i++){
						System.out.println(listemsg[i]);
					}
				}

				if(listemsg[1].contains("openurl")){
					System.out.println(listemsg[2]);
					try{
					Desktop d = Desktop.getDesktop();
					d.browse(new URI(listemsg[2]));
					}catch (Exception e){
						System.out.println(e);
					}
				}

				if(listemsg[1].contains("reportmega")){
					for(int i = 2;i<listemsg.length; i++){
						System.out.println(listemsg[i]);
					}
				}

		}
			
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		}
	
	
	public String getmsg() {
		return this.str;	}
	
	
	
	
	public void sendmsg(String msg) throws UnknownHostException, IOException {
		PrintWriter pr = new PrintWriter(this.sock.getOutputStream());	
		pr.println(msg);
		pr.flush();	}
	
	

	}
	

	
	

