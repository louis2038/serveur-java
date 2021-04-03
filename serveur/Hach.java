
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.*;
import java.security.MessageDigest;



public class Hach {

public static void main(String[] arg){
	System.out.println("rien");
}	

public static void setClientToken(String name,String pass,String tokenaccess){
	
	try{
		FileWriter fileWriter = new FileWriter("TokenAccess.txt",true);
		
			fileWriter.append(name);
			fileWriter.append(",");
			fileWriter.append(hachPasseword(pass));		// hachage du mdp
			fileWriter.append(",");
			fileWriter.append(tokenaccess);						
		
		fileWriter.append(System.lineSeparator());
		fileWriter.flush();
		fileWriter.close();

		}catch (Exception e){
			System.out.println(e);
		}
}

public static boolean checkPasseword(String user,String pass){

	try{

	boolean goodPass = false;

	FileReader fileReader = new FileReader("TokenAccess.txt");
	BufferedReader bufferReader = new BufferedReader(fileReader);

	

    String line;
    String[] array;
	while((line = bufferReader.readLine()) != null){
        	array = line.split(",");
        	if(array[0].equals(user)){
        		if(array[1].equals(pass)){
        			goodPass = true;
        		}
        	}
        }
       return goodPass;
	}catch (Exception e){
		System.out.println(e);
		return false;
	}
}

public static boolean checkTokenAccess(String user, String tokenaccess){
	try{

	boolean goodtoken = false;

	FileReader fileReader = new FileReader("TokenAccess.txt");
	BufferedReader bufferReader = new BufferedReader(fileReader);

    String line;
    String[] array;
	while((line = bufferReader.readLine()) != null){
        	array = line.split(",");
        	if(array[0].equals(user)){
        		if(array[2].equals(tokenaccess)){
        			goodtoken = true;
        		}
        	}
        }
       return goodtoken;
	}catch (Exception e){
		System.out.println(e);
		return false;
	}
}

public static String hachPasseword(String pass){
	try{
	MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(pass.getBytes());

        byte byteData[] = md.digest();

        //convertir le tableau de bits en une format hexadécimal - méthode 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();

    }catch (Exception e){
    	System.out.println("erreur du hachage");
    	return "erreur";
    }
}

public static boolean isSameUser(String user){
	try{
	boolean sameUser = false;
	System.out.println("user:" + user);
	FileReader fileReader = new FileReader("TokenAccess.txt");
	BufferedReader bufferReader = new BufferedReader(fileReader);

	
    String line;
    String[] array;
	while((line = bufferReader.readLine()) != null){
        	array = line.split(",");
        	if(array[0].equals(user)){
        		sameUser = true;
        	}
        }
       return sameUser;
	}catch (Exception e){
		System.out.println(e);
		return true;
	}
}

public static void deleteUser(String user){
	try{
	List<String> data = new ArrayList<String>();

	FileReader fileReader = new FileReader("TokenAccess.txt");
	BufferedReader bufferReader = new BufferedReader(fileReader);

	

    String line;
    String[] array;
    int indexSupp = -1;
    int compteurindex = 0;

	while((line = bufferReader.readLine()) != null){
        	data.add(line);
        	array = line.split(",");
        	if(array[0].equals(user)){
        		indexSupp = compteurindex;
        	}
        	compteurindex++;
        }
    if(indexSupp == -1){
    	System.out.println("don't found the user");
    }else{
    	    System.out.println(data.get(indexSupp));
    }
      
    FileWriter fileWriter = new FileWriter("TokenAccess.txt",false);

    for(int i = 0; i<data.size();i++){
    	if(i != indexSupp){
    		fileWriter.append(data.get(i));
    		fileWriter.append(System.lineSeparator());
    	}
    }
    fileWriter.flush();
    fileWriter.close();





	}catch (Exception e){
		System.out.println(e);
	}
}

public static void modifyToken(String user, String tokenaccess){
	try{
	List<String> data = new ArrayList<String>();

	FileReader fileReader = new FileReader("TokenAccess.txt");
	BufferedReader bufferReader = new BufferedReader(fileReader);

	
	String nametampon = "";
	String passtampon = "";
    String line;
    String[] array;
    int indexSupp = -1;
    int compteurindex = 0;

	while((line = bufferReader.readLine()) != null){
        	data.add(line);
        	array = line.split(",");
        	if(array[0].equals(user)){
        		indexSupp = compteurindex;
        		nametampon = array[0];
        		passtampon = array[1];
        	}
        	compteurindex++;
        }
    if(indexSupp == -1){
    	System.out.println("don't found the user");
    }
      
    FileWriter fileWriter = new FileWriter("TokenAccess.txt",false);

    for(int i = 0; i<data.size();i++){
    	if(i != indexSupp){
    		fileWriter.append(data.get(i));
    		fileWriter.append(System.lineSeparator());
    	}else{
    		fileWriter.append(nametampon);
    		fileWriter.append(",");
    		fileWriter.append(passtampon);
    		fileWriter.append(",");
    		fileWriter.append(tokenaccess);
    		fileWriter.append(System.lineSeparator());
    	}
    }
    fileWriter.flush();
    fileWriter.close();





	}catch (Exception e){
		System.out.println(e);
	}
}


}


