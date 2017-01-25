package databases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.dev.util.StringKey;

import jline.internal.InputStreamReader;
import oracle.kv.Direction;
import oracle.kv.KVStore;
import oracle.kv.KVStoreConfig;
import oracle.kv.KVStoreFactory;
import oracle.kv.Key;
import oracle.kv.KeyValueVersion;
import oracle.kv.Value;
import oracle.kv.ValueVersion;

public class OracleNoSQL {
	
	private String filePath;
	private KVStore kvstore;
//	private String keyString;
	InputStream inputStream;
	InputStreamReader inputStreamReader;
	BufferedReader bufferedReader;
	String firstLigne;
	
	String stringKeys[];
	Key key;
	Value value;
	Key keys[];
	String fullLigne;
	String ligne[];
	
	byte[] line;
	
	
	/******************************************************************************************/
	/***************************************Accesseurs*****************************************/
	/******************************************************************************************/
	
	public void setFilePath(String filePath){
		this.filePath = filePath;
	}
	
	public String getFilePath(){
		return filePath;
	}
	
	/******************************************************************************************/
	/****************************************Fonctions*****************************************/
	/******************************************************************************************/	
	
	public void Connect(){
		System.out.println("\nConnexion à la base de données OracleNoSQL");
		
		long startTime = System.currentTimeMillis();
		
		String storeName = "kvstore";
		String hostname = "localhost";
		String hostPort = "5000";
		
		KVStoreConfig config = new KVStoreConfig(storeName, hostname + ":" + hostPort);
		kvstore = KVStoreFactory.getStore(config);
		
		 long endTime = System.currentTimeMillis();
		 System.out.println("Temps total d'execution de la connexion :"+ (endTime-startTime) +"ms\n");
	}
	
	public void Insert(){
		System.out.println("\nInsertion dans la base de données OracleNoSQL");

		long startTime = System.currentTimeMillis();
		
		try {
			
			inputStream = new FileInputStream(filePath);
			inputStreamReader = new InputStreamReader(inputStream);
			bufferedReader = new BufferedReader(inputStreamReader);
			firstLigne = bufferedReader.readLine();
			
			String stringKeys[] = firstLigne.split(",");
			Key key;
			Value value;
			Key keys[] = new Key[stringKeys.length];
			String fullLigne;
			String ligne[] = new String[stringKeys.length];
			
			byte[] line;
			
			for (int i = 0; i < stringKeys.length; i++) {
				key = Key.createKey(stringKeys[i]);
				keys[i] = key;
			}
			
			while ((fullLigne = bufferedReader.readLine()) != null) {
				ligne = fullLigne.split(",");
				for (int i = 0; i < keys.length; i++) {
					key = keys[i];
					line = ligne[i].getBytes();
					value = Value.createValue(line);
					kvstore.put(key, value);
				}
			}
			
			bufferedReader.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		 long endTime = System.currentTimeMillis();
		 System.out.println("Temps total d'execution de l'insertion :"+ (endTime-startTime) +"ms\n");
	}
	
	public void Update(){
		System.out.println("\nMise à jour dans la base de données OracleNoSQL");
		long startTime = System.currentTimeMillis();
		
		

		long endTime = System.currentTimeMillis();
		System.out.println("Temps total d'execution de la mise à jour :"+ (endTime-startTime) +"ms\n");
	}
	
	public void Read(){
		System.out.println("\nLecture de la base de données OracleNoSQL");
		long startTime = System.currentTimeMillis();
				
//		for (int i = 0; i < stringKeys.length; i++) {
//			Key key_lecture = Key.createKey(stringKeys[i]);
//			ValueVersion version = kvstore.get(key_lecture);
//			Value valeur_lue = version.getValue();
//			byte[] tableau = valeur_lue.getValue();
//			String chaine_recuperee = new String(tableau);
//			System.out.println(stringKeys[i] + " : " + chaine_recuperee);
//		}

//		for (int i = 0; i < keys.length; i++) {
//			while (keys[i].toString() != null) {
//				cle_lecture = Key.createKey(keys[i].toString());
//				version = kvstore.get(cle_lecture);				
//			}
//		}
//		
//		if (version != null) {
//			Value valeur_lue = version.getValue();
//			byte[] tableau = valeur_lue.getValue();
//			String chaine_recuperee = new String(tableau);
//			
//			System.out.println("id_client : " + chaine_recuperee);			
//		}
//		else{
//			System.out.println("Pas de chaine récupérable");
//		}
		
		
//		Value valeur_lue = version.getValue();
//		byte[] tableau = valeur_lue.getValue();
//		String chaine_recuperee = new String(tableau);
//		System.out.println("id_client : " + chaine_recuperee);
		
//		if (keys[1] != null) {
//			System.out.println(kvstore.get(keys[0]));
//		}
//		else{
//			System.out.println("Erreur");
//		}

		
//		 long endTime = System.currentTimeMillis();
//		 System.out.println("Temps total d'execution de la lecture :"+ (endTime-startTime) +"ms");
		
		ArrayList<String> tab_cles = new ArrayList<String>();
		tab_cles.clear();
		
		try{
		
			inputStream = new FileInputStream(filePath);
			inputStreamReader = new InputStreamReader(inputStream);
			bufferedReader = new BufferedReader(inputStreamReader);
			firstLigne = bufferedReader.readLine();
			
			ligne = firstLigne.split(",");
			
			for (int i = 0; i < ligne.length; i++) {
				tab_cles.add(ligne[i]);
//				System.out.println("TAB_CLES : " + tab_cles + "\n");
//				key = Key.createKey(tab_cles.get(i));
//				System.out.println("KEY : " + key + "\n");
			}
			
				System.out.println(tab_cles);
				
//				Iterator<KeyValueVersion> iterator = kvstore.storeIterator(Direction.UNORDERED, 0, key, null, null);
//				while(iterator.hasNext()){
//					key = iterator.next().getKey();
//					System.out.println("ITERATOR : " + iterator + "\n");
//					
//					ValueVersion valueVersCherchee = kvstore.get(key);
////					System.out.println("KVSTORE.GET(KEY) : " + kvstore.get(key) + "\n");
//					
//					Value v = valueVersCherchee.getValue();
////					System.out.println("VALUEVERSCHERCHEE.GETVALUE() : " + v + "\n");
//					
//					byte[] tab_bytes = v.getValue();
////					System.out.println("TAB_BYTES : " + tab_bytes + "\n");
//					
//					String val_recup = new String(tab_bytes);
//					System.out.println("VAL_RECUP : " + val_recup + "\n");
//					
//					String ligne = "cle = " + key.toString();					
//					ligne = ligne + "	valeur = "+ val_recup;
//					
//					System.out.println(ligne);
//				}
//			}
				
//			tab_cles.add("id_client");			
//			Key mykey2 = Key.createKey(tab_cles);
//			Iterator<KeyValueVersion> i = kvstore.storeIterator(Direction.UNORDERED, 0, mykey2, null, null);
//			System.out.println("Liste des gens");
//			while(i.hasNext()){
//				Key k = i.next().getKey();
//				ValueVersion valueVersCherchee = kvstore.get(k);
//				Value v = valueVersCherchee.getValue();
//				byte[] tab_bytes = v.getValue();
//				String val_recup = new String(tab_bytes);
//				String ligne = "cle = " + k.toString();
//				ligne = ligne + "	valeur = "+ val_recup;
//				
//				System.out.println(ligne+"\n");
//			}
			
			bufferedReader.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
	    System.out.println("Temps total d'executiion de la lecture :"+ (endTime-startTime) +"ms\n");
	}
	
	public void Delete(){
		System.out.println("\nSuppresion dans la base de données OracleNoSQL");
		long startTime = System.currentTimeMillis();


		long endTime = System.currentTimeMillis();
		System.out.println("Temps total d'execution de la suppression :"+ (endTime-startTime) +"ms\n");
	}
	
	public void Disconnect(){
		System.out.println("\nDéconnexion de la base de données OracleNoSQL");
		long startTime = System.currentTimeMillis();
		
		kvstore.close();
		
		long endTime = System.currentTimeMillis();
		System.out.println("Temps total d'execution de la déconnexion :"+ (endTime-startTime) +"ms\n");
	}
}
