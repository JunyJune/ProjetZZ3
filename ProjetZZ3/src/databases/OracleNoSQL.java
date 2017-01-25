package databases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.dev.util.StringKey;
import com.sun.xml.internal.ws.api.message.stream.InputStreamMessage;

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
		System.out.println("Connexion � la base de donn�es OracleNoSQL");
		
		String storeName = "kvstore";
		String hostname = "localhost";
		String hostPort = "5000";
		
		KVStoreConfig config = new KVStoreConfig(storeName, hostname + ":" + hostPort);
		kvstore = KVStoreFactory.getStore(config);
	}
	
	public void Insert(){
		System.out.println("Insertion dans la base de donn�es OracleNoSQL");
		
		try {
//		InputStream inputStream = new FileInputStream(filePath);
//		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//		String firstLigne = bufferedReader.readLine();
//		
//		String stringKeys[] = firstLigne.split(",");
//		Key key;
//		Value value;
//		Key keys[] = new Key[stringKeys.length];
//		String fullLigne;
//		String ligne[] = new String[stringKeys.length];
//		
//		byte[] line;
			
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Update(){
		System.out.println("Mise � jour dans la base de donn�es OracleNoSQL");
	}
	
	public void Read(){
		System.out.println("Lecture de la base de donn�es OracleNoSQL");
		
		/*if (keys[1] != null) {
			System.out.println(kvstore.get(keys[0]));
		}
		else{
			System.out.println("Erreur");
		}*/
		
		ArrayList<String> tab_cles = new ArrayList<String>();
		tab_cles.clear();
		tab_cles.add("id_client");
		Key mykey2 = Key.createKey(tab_cles);
		Iterator<KeyValueVersion> i = kvstore.storeIterator(Direction.UNORDERED, 0, mykey2, null, null);
		System.out.println("Liste des gens");
		while(i.hasNext()){
			Key k = i.next().getKey();
			ValueVersion valueVersCherchee = kvstore.get(k);
			Value v = valueVersCherchee.getValue();
			byte[] tab_bytes = v.getValue();
			String val_recup = new String(tab_bytes);
			String ligne = "cle = " + k.toString();
			ligne = ligne + "valeur = "+ val_recup;
			
			System.out.println(ligne+"\n");
		}
	}
	
	public void Delete(){
		System.out.println("Suppresion dans la base de donn�es OracleNoSQL");
	}
	
	public void Disconnect(){
		System.out.println("D�connexion de la base de donn�es OracleNoSQL");
		kvstore.close();
	}
}