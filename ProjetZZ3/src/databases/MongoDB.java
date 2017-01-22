package databases;

import com.google.gwt.validation.client.constraints.FutureValidatorForDate;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

import oracle.kv.impl.admin.plan.task.StartAddTextIndex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.portable.UnknownException;

public class MongoDB {
	
	private String filePath;
	
	private MongoClient mongoClient;
	private DB db;
	private DBCollection collection;
	private List<DBCollection> collections;
	private BasicDBObject document;
	private List<BasicDBObject> documents;
	
	/******************************************************************************************/
	/***************************************Accesseurs*****************************************/
	/******************************************************************************************/
	
	public void setFilePath(String filePath){
		this.filePath = filePath;
	}
	
	public String getFilePath(){
		return filePath;
	}
	
	public DB getDb(){
		return db;
	}
	
	public List<DBCollection> getCollections(){
		return collections;
	}
	
	public List<BasicDBObject> getDocuments(){
		return documents;
	}
	
	/******************************************************************************************/
	/****************************************Fonctions*****************************************/
	/******************************************************************************************/	
		
	public void InitialisationListes(){
		collections = new ArrayList<DBCollection>();
		documents = new ArrayList<BasicDBObject>();
	}
	
	public void Connect(){
		System.out.println("Connexion à la base de données MongoDB");
				
		String databaseName = "MongoDB_Database";
		try{
			//Connect to MongoDB server
			System.out.println("Connect to server");
			mongoClient = new MongoClient("localhost");
			
			//Connect and/or create database
			System.out.println("Create database");
			 db = new DB(mongoClient, databaseName);

		}
		catch(Exception e){
			System.err.println(e.getClass().getName() + " : " + e.getMessage());;
		}
	}
	
	public void InsertFile() throws IOException{
		InputStream inputStream = new FileInputStream(filePath);
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferReader = new BufferedReader(inputStreamReader);
		String futurDocument ="";
		String ligne;
		String[] attributs;
		long startTime = System.currentTimeMillis();
		
		try {
			
//			for (int i = 0; i < 27; i++) {
//				ligne.concat(bufferReader.readLine());
//			}
			while ((ligne = bufferReader.readLine()) != null) {
				while ((ligne = bufferReader.readLine()) != null && bufferReader.readLine() != "{") {
					futurDocument = ligne.concat(ligne);
					
//					System.out.println(ligne);
//					if(ligne=="{"){
//						document = new BasicDBObject();
//					}
//					else{
//						for (int i = 0; i < ligne.split(":").length; i++) {
//							attributs[] = ligne.split(":");						
//						}
//					}
					
//					document.append(key, val)
//					collection.insert(document);
				}		
			}
		}
		
		catch (UnknownException e) {
			e.printStackTrace();
		}
		catch (MongoException e) {
			e.printStackTrace();
		}
		
		finally{
			bufferReader.close();
		}
		
		System.out.println(startTime);
	}
	
	public void Insert(String collectionName, String documentName){
		System.out.println("Insertion dans la base de données MongoDB");
		
		//Get and/or create Collection
		System.out.println("Create collection");
		if (collections.contains(collection)) {
			collections.add(collection);
		}
		else {
			collection = db.getCollection(collectionName);
		}
						
		//Create a document
		System.out.println("Create document");
		document = new BasicDBObject(documentName, "Premier")
				.append("chose", "database")
//				.append("truc", 1)
//				.append("machins", new BasicDBObject("a", 203).append("b", 102))
		;
		
		//Add document to collection
		System.out.println("Insert document into a collection");
		collection.insert(document);
		documents.add(document);
	}
	
	public void Update(){
		System.out.println("Mise à jour dans la base de données MongoDB");
	}
	
//	public void ReadOne(){
//		System.out.println("Lecture de la base de données MongoDB");
//		
//		//Find a document in a collection
//		System.out.println("Look for a document");
//		DBObject foundDocument = collection.findOne();
//		System.out.println(foundDocument);
//	}

	public void ReadAFullDocument(DBCollection collection){
//		System.out.println("Lecture de la base de données MongoDB");
		
		//Find a document in a collection
//		System.out.println("Look for a document");
		DBCursor document = collection.find();
		for (DBObject info : document) {
			System.out.println("Mais enfin est-ce que ça marche ??");
			System.out.println(document.getCollection().findOne());
		}
	}
	
	public void ReadAllDocuments(){
		for (BasicDBObject document : documents) {
			System.out.println(document);
			ReadAFullDocument(collection);
		}
	}
	
	public void ReadAllDatabase(){
		for (DBCollection collection : collections) {
			System.out.println(collection);
			ReadAllDocuments();
		}
	}
	
	public void DeleteOne(){
		System.out.println("Suppresion dans la base de données MongoDB");
		
	}
	
	public void DeleteAll(){
		System.out.println("Suppresion dans la base de données MongoDB");
		
	}
	
	public void Disconnect(){
		//Close the server connection
		System.out.println("Close connection");
		mongoClient.close();
	}
}

