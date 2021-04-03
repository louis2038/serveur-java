import java.net.*;
import java.io.*;
import java.util.Scanner; // import the Scanner class 


public class Main{
		
		public static void main(String[] arg) throws UnknownHostException, IOException {
			System.out.println("cote Client");
			
			String str;
			ThreadClient th = new ThreadClient("192.168.0.46", 48650);
			th.start();
			
				
			
			while(true) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String command = th.getmsg();
				
				Scanner scan = new Scanner(System.in);
				System.out.println("entrer msg"); 
				str = scan.nextLine();

				th.sendmsg(str);
			}
			
			
			
		
			
		}}
		