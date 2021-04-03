import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;
import java.net.*;

public class ThreadServer extends Thread implements ThreadListe{		// GESTION DE CHAQUE CLIENT
	private Socket sock;	
	private int id;
	private String idName;
	private boolean accessToken;
	
	public ThreadServer(Socket sock) {
		this.sock = sock;
	}

	public void run() {
		accessToken = false;
		//	pas de token   		-> 0
		//	T = acess_token		-> 1
		//	I = accessFile		-> 2
		//	S = superadmin		-> 3


		String content = null;
		String[] listemsg;
		try {
			while(true) {
			InputStreamReader in = new InputStreamReader(this.sock.getInputStream());
			BufferedReader br = new BufferedReader(in);
			String str = br.readLine();
			System.out.println(str);
	
												// gestion des entrés de comm sur server		
			listemsg = str.split("&");							// separation des commandes avec: &
			int longueur_msg = listemsg.length;
			String msgRecompose = "";
			//
			//			le message est decoupé, ici parti modifiable:
			//
			//			entré de communication du SERVER
			//
			//			
			//
			//
			//
			
			if(listemsg[0].contains("server")) {										// ================================================================
																						// gestion des COMMANDES
					if(listemsg[1].contains("newclient")) {								// NEWCLIENT  attribution d'un identifiant
										boolean sameUser;
										sameUser = Hach.isSameUser(listemsg[2]);
										if(sameUser == false){
										this.idName = listemsg[2];
										Hach.setClientToken(listemsg[2],listemsg[3],"none");	// set the token acces
										String contentsend = "client&newclientID&" + String.valueOf(this.id);	// renvoie des donné de confirmation
										ThreadServerPlay.setlisteName(this.idName,this.id);
										SendMsg(contentsend);
										accessToken = true;
										}else{
											SendMsg("client&error&newclientError");
										}
										}

					if(listemsg[1].contains("connect")){
										boolean sameUser;
										sameUser = Hach.isSameUser(listemsg[2]);
										if(sameUser == true){
										boolean goodPass;
										goodPass = Hach.checkPasseword(listemsg[2],Hach.hachPasseword(listemsg[3]));
										if(goodPass == true){
											this.idName = listemsg[2];
											String contentsend2 = "client&connected&" + String.valueOf(this.id);
											ThreadServerPlay.setlisteName(this.idName,this.id);
											accessToken = true;					// atribution de l'access token
											SendMsg(contentsend2);
										}else{
											SendMsg("client&error&connectedError&passnotvalid");
										}
										}else{
											SendMsg("client&error&connectedError&usernotfound");
										}
					}




					if(listemsg[1].contains("afficher")) {
										if(accessToken == true){
										System.out.println(listemsg[2]);
									}else{
										SendMsg("client&error&notAccessToken");
									}
									}
							
					
					if(listemsg[1].contains("sendatclient")){
									if(accessToken == true){
										for(int i = 3;i<(longueur_msg);i++) {
											if(i == 3) {
												msgRecompose = listemsg[i];
											}else {
												msgRecompose = msgRecompose + "&" + listemsg[i];
											}
										}
										}else{
											SendMsg("client&error&notAccessToken");
										}
						
										ThreadServerPlay.redirection(Integer.parseInt(listemsg[2]), msgRecompose);}
				
					if(listemsg[1].contains("listeclient")){
									if(accessToken == true){
										String contentsend2 = "";
										String[] contentListeClient;
										contentListeClient = ThreadServerPlay.getlisteName();
										for(int i = 0;i<ThreadServerPlay.Slot;i++) {
											if(i == 0) {
												contentsend2 = contentListeClient[i];
											}else {
												contentsend2 = contentsend2 + "&" + contentListeClient[i];
											}
										}
										contentsend2 = "client&listeclientname&" + contentsend2;
										SendMsg(contentsend2);
									}else{
										SendMsg("client&error&notAccessToken");									
									}
									}
					
					if(listemsg[1].contains("sendatallclient")) {
									if(accessToken == true){
										for(int i = 2;i<(longueur_msg);i++) {
												if(i == 2) {
												msgRecompose = listemsg[i];
												}else {
													msgRecompose = msgRecompose + "&" + listemsg[i];
												}
										}
										String[] contentListeClient2;
										contentListeClient2 = ThreadServerPlay.getlisteName();
										
										for(int i = 0;i<ThreadServerPlay.Slot;i++) {
											if((contentListeClient2[i] != null) && (this.id != i)) {
												ThreadServerPlay.redirection(i, msgRecompose);
											}}	
										}else{
										SendMsg("client&error&notAccessToken");									
										}
										}

					if(listemsg[1].contains("settokenaccess")){
							if(accessToken == true){	
									if(Hach.checkPasseword(listemsg[2],Hach.hachPasseword(listemsg[3])) == true){
										if(Hach.checkTokenAccess(listemsg[2],"superadmin") == true){
											Hach.modifyToken(listemsg[4],listemsg[5]);
										}else{
											SendMsg("client&error&notAccessToken");
										}
									}else{
										SendMsg("client&error&notAccessToken");
									}
							}else{
								SendMsg("client&error&notAccessToken");
							}
					}

					if(listemsg[1].contains("explorefile")){
						if(accessToken == true){
							if((Hach.checkTokenAccess(this.idName,"accessfile") == true) || (Hach.checkTokenAccess(this.idName,"superadmin") == true)){
								try{
								String stringatsend = "";

		        				System.out.println("File[] listFiles():");
		 
		       					File dir = new File(listemsg[2]);
		 
		        				File[] children = dir.listFiles();
		 						
		        				for (File file : children) {
		           			 		stringatsend = stringatsend + "&" + file.getAbsolutePath();
		           			 		System.out.println(file.getAbsolutePath());
		        				}
		        				String content2 = "client&explorefile&" + stringatsend;
		        				SendMsg(content2);
		        				}catch (Exception e){
		        					SendMsg("client&error&argumentError");
		        				}
		        			}else{
		        				SendMsg("client&error&notAccessToken");
		        			}

	        			}else{
	        				SendMsg("client&error&notAccessToken");
	        			}
					}

					if(listemsg[1].contains("getdrop")){
						if(accessToken == true){
							if((Hach.checkTokenAccess(this.idName,"accessfile") == true) || (Hach.checkTokenAccess(this.idName,"superadmin") == true)){
								SendMsg("client&openurl&https://mega.nz/megadrop/M5FTSERY9q4");
							}else{
								SendMsg("client&error&notAccessToken");
							}
						}else{
							SendMsg("client&error&notAccessToken");
						}
					}

					if(listemsg[1].contains("putfile")){
						if(accessToken == true){
							if((Hach.checkTokenAccess(this.idName,"accessfile") == true) || (Hach.checkTokenAccess(this.idName,"superadmin") == true)){
								String report;
								report = Mega.putFile(listemsg[2],listemsg[3]);
								report = "client&reportmega&" + report;
								SendMsg(report);
							}else{
								SendMsg("client&error&notAccessToken");
							}
						}else{
							SendMsg("client&error&notAccessToken");
						}
					}

					if(listemsg[1].contains("getfile")){
						if(accessToken == true){
							if((Hach.checkTokenAccess(this.idName,"accessfile") == true) || (Hach.checkTokenAccess(this.idName,"superadmin") == true)){
								String report2;
								report2 = Mega.uploadFile(listemsg[2]);
								report2 = "client&reportmega&" + report2;
								SendMsg(report2);
								report2 = Mega.getLinkFile(listemsg[3]);
								String[] content4 = report2.split(" ");
								System.out.println("url ->" + content4[2]);
								report2 = "client&openurl&" + content4[2];
								System.out.println(report2);
								SendMsg(report2);
							}else{
								SendMsg("client&error&notAccessToken");
							}
						}else{
							SendMsg("client&error&notAccessToken");
						}
					}



					//	
					//		fin de la parti modifiable
					//
					//
					//
					//
					//
					
					
			
	
			}
			

				
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			System.out.println("erreur de communication avec le client: " + this.id);
			ThreadServerPlay.outOfClient(this.id);
			stop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	@Override
	public void SendMsg(String msg) {
		// TODO Auto-generated method stub
		
		try {
			PrintWriter pr = new PrintWriter(this.sock.getOutputStream());
			pr.println(msg);
			pr.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block  z
			e.printStackTrace();
		}
		
	}

	

	@Override
	public void setID(int id) {
		this.id = id;
	}


	
	

	

	

	







}
