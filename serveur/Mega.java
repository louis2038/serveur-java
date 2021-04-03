import java.io.*;
import java.lang.*;
import java.util.*;
import java.net.*;


public class Mega{

	public static String putFile(String myFile, String whereToGo) throws IOException{
		String contentInput = "";
		String contentError = "";



		// myfile ====> DATA/nom du fichier (xxx_xxx_yy.odt)
		myFile = "DATA/" + myFile;
		myFile = "\"" + myFile + "\"";
	   	Runtime runtime = Runtime.getRuntime();
		String[] args = {"C:/Users/Louis/AppData/Local/MEGAcmd/mega-get.bat",myFile,whereToGo};
		final Process process = runtime.exec(args);
		
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            try {
                while((line = reader.readLine()) != null) {
                    // Traitement du flux de sortie de l'application si besoin est
                    System.out.println(line);
                    contentInput = contentInput + line + "&";
                }
            } finally {
                reader.close();
            }
        } catch(Exception e) {	
        	System.out.println(e);
        }
    

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = "";
            try {
                while((line = reader.readLine()) != null) {
                    // Traitement du flux d'erreur de l'application si besoin est
                    System.out.println(line);
                    contentInput = contentInput + line + "&";
                }
            } finally {
                reader.close();
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    	
    	String content = "report input : &" + contentInput + "&" + "report erreur : &" + contentError;
        return content;

	}

	public static String uploadFile(String file) throws IOException{
		String contentInput = "";
		String contentError = "";
		// file ====>  D:\FICHIER\Louis\caca.txt

		Runtime runtime = Runtime.getRuntime();
		String[] args = {"C:/Users/Louis/AppData/Local/MEGAcmd/mega-put.bat",file};
		final Process process = runtime.exec(args);
	
		
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            try {
                while((line = reader.readLine()) != null) {
                    // Traitement du flux de sortie de l'application si besoin est
                    System.out.println(line);
                    contentInput = contentInput + line + "&";
                }
            } finally {
                reader.close();
            }
        } catch(Exception e) {	
        	System.out.println(e);
        }
    

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = "";
            try {
                while((line = reader.readLine()) != null) {
                    // Traitement du flux d'erreur de l'application si besoin est
                    System.out.println(line);
                    contentInput = contentInput + line + "&";
                }
            } finally {
                reader.close();
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        String content = "report input : &" + contentInput + "&" + "report erreur : &" + contentError;
        return content;

	}

	public static String getLinkFile(String file) throws IOException{
		String contentInput = "";
		String contentError = "";

		file = "DATA/" + file;

		Runtime runtime = Runtime.getRuntime();
		String[] args = {"C:/Users/Louis/AppData/Local/MEGAcmd/mega-export.bat","-a",file};
		final Process process = runtime.exec(args);
	
		
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            try {
                while((line = reader.readLine()) != null) {
                    // Traitement du flux de sortie de l'application si besoin est
                    System.out.println(line);
                    contentInput = contentInput + line + "&";
                }
            } finally {
                reader.close();
            }
        } catch(Exception e) {	
        	System.out.println(e);
        }
    
        return contentInput;

	}

	



	



}