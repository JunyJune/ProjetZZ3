package databases;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.omg.CORBA.portable.UnknownException;

public class MongoDB {
	
	private String filePath;
	
	private MongoClient mongoClient;
	private DB db;
	private DBCollection collection;
	private List<DBCollection> collections;
	private List<BasicDBObject> documents;
	int nbRow;
	HSSFWorkbook wb;
	HSSFSheet sheet;
	FileOutputStream fileOut;
	
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
	@Override
	public String toString(){
		String name = "MongoDB";
		return name;
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
				
		String databaseName = "MongoDB_Database"; //
		try{
			//Connect to MongoDB server
			System.out.println("Connect to server");
			mongoClient = new MongoClient("localhost");
			
			//Connect and/or create database
			System.out.println("Create database");
			 db = new DB(mongoClient, databaseName);
			 
			 System.out.println("Create collection");
				if (collections.contains(collection)) {
					collections.add(collection);
				}
				else {
					collection = db.getCollection("Commandes");
				}
				//Creation du fichier de resultat
				wb = new HSSFWorkbook();
				sheet = wb.createSheet("FeuilleMongoDB");
				nbRow = 0;
		}
		catch(Exception e){
			System.err.println(e.getClass().getName() + " : " + e.getMessage());;
		}
		
		
	}
	
	public void Insert() throws IOException{
		InputStream inputStream = new FileInputStream(filePath);
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferReader = new BufferedReader(inputStreamReader);
		String ligne;
		int nb_ligne_insere = 0;
		
		try {
			String fichier ="DataFiles/data.json";
			
    		
			//lecture du fichier texte	
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			 BufferedReader br = new BufferedReader(ipsr);
			 DBObject dbObject;
			 System.out.println("Debut de l'insertion");
			 long startTime = System.currentTimeMillis();

			 try {
				 while ((ligne=br.readLine())!=null){
					 dbObject = (DBObject)JSON.parse(ligne);
					 //insertion des données
					   collection.insert(dbObject);
						nb_ligne_insere ++;
				 }  
	            } finally {
	                br.close();
	            }
			 long endTime = System.currentTimeMillis();
			 System.out.println("Insertion effectuée");

			 long time = endTime-startTime;
			 System.out.println("Temps total d'execution de l'insertion :"+ (time) +"ms");
			 writeResult("Insertion ", nb_ligne_insere , this.toString() , time + " ms"   );
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
	}
	
	
	public void Update(){
		System.out.println("Mise à jour dans la base de données MongoDB");

		//clause WHERE
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.append("$set", new BasicDBObject().append("abonnement_client","true"));
		BasicDBObject searchQuery = new BasicDBObject().append("id_client", "2");
		
		long startTime = System.currentTimeMillis();
		WriteResult wr = collection.update(searchQuery, newDocument);
		 long endTime = System.currentTimeMillis();
		 
		 long time = endTime-startTime;
		 System.out.println("Temps total d'execution de la mise à jour :"+ time +"ms");
		 int nb= wr.getN();
		 writeResult("Update ", nb , this.toString() , time + " ms"   );
	}
	
	
	public void UpdateWithParameter(String param_de_recherche, String valeur_recherche, String param_de_modif, String valeur_modif){
		System.out.println("Mise à jour dans la base de données MongoDB");

		//clause WHERE
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.append("$set", new BasicDBObject().append(param_de_modif,valeur_modif));
		BasicDBObject searchQuery = new BasicDBObject().append(param_de_recherche, valeur_recherche);
		
		long startTime = System.currentTimeMillis();
		WriteResult wr = collection.update(searchQuery, newDocument,false,true);
		long endTime = System.currentTimeMillis();
		
		 long time = endTime-startTime;
		 System.out.println("Temps total d'execution de la mise à jour :"+ (time) +"ms");
		 int nb= wr.getN();
		 writeResult("Update", nb , this.toString() , time + " ms"   );

	}
	//Si bool=true alors il y a une deuxième condition pour la selection
	public void ReadOne(String param_de_recherche1,String operator1, String valeur_recherche1, Boolean bool, String param_de_recherche2,String operator2, String valeur_recherche2){
		System.out.println("Lecture de la base de données MongoDB");
		//clause WHERE
		BasicDBObject newDocument = new BasicDBObject();
		if(operator1== "="){			
			newDocument.append(param_de_recherche1,valeur_recherche1);	
		}else if(operator1== "<"){
			newDocument = new BasicDBObject(param_de_recherche1 ,new BasicDBObject("$lt", valeur_recherche1));
		}else if(operator1== ">"){
			newDocument = new BasicDBObject(param_de_recherche1 ,new BasicDBObject("$gt", valeur_recherche1));
		}
		if(bool){
			if(operator2== "="){			
				newDocument.append(param_de_recherche2,valeur_recherche2);	
			}else if(operator2== "<"){
				newDocument.append(param_de_recherche2 ,new BasicDBObject("$lt", valeur_recherche2));
			}else if(operator2== ">"){
				newDocument.append(param_de_recherche2 ,new BasicDBObject("$gt", valeur_recherche2));
			}
		}
		
		long startTime = System.currentTimeMillis();
		DBCursor cursor= collection.find(newDocument);
		long endTime = System.currentTimeMillis();
		
		long time = endTime-startTime;
		int nb = cursor.size();
		 System.out.println("Temps total d'execution de la suppression :"+ (endTime-startTime) +"ms");
		 writeResult("Lecture", nb , this.toString() , time + " ms"   );
	}

	
	public void ReadAll(){
		int nb = collection.find().count();
		
		long startTime = System.currentTimeMillis();
		long endTime = System.currentTimeMillis();
		 long time = endTime-startTime;
		 
		 System.out.println("Temps total d'execution de la lecture :"+ time +"ms");
		 writeResult("Lecture", nb , this.toString() , time + " ms"   );
	}
	
	
	public void DeleteOne(String param_de_recherche, String valeur_recherche){
		System.out.println("Suppresion dans la base de données MongoDB");
		//clause WHERE
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.append(param_de_recherche,valeur_recherche);
		
		long startTime = System.currentTimeMillis();
		WriteResult wr = collection.remove(newDocument);
		long endTime = System.currentTimeMillis();
		
		long time = endTime-startTime;
		 System.out.println("Temps total d'execution de la suppression :"+ (endTime-startTime) +"ms");
		 int nb= wr.getN();
		 writeResult("Suppresion ", nb , this.toString() , time + " ms"   );
	}
	
	public void DeleteAll(){
		System.out.println("Suppresion dans la base de données MongoDB");
		
		long startTime = System.currentTimeMillis();
		WriteResult wr = collection.remove(new BasicDBObject());
		long endTime = System.currentTimeMillis();
		
		int nb= wr.getN();
		long time = endTime-startTime;
		 System.out.println("Temps total d'execution de la suppression :"+ (time) +"ms");
		 writeResult("Suppression ", nb , this.toString() , time + " ms"   );
		 
	} 
	
	public void DeleteBetween(String param_recherche, String petit, String grand){
		System.out.println("Suppresion dans la base de données MongoDB");
		BasicDBObject newDocument = new BasicDBObject(param_recherche, 
			    new BasicDBObject("$gte", petit).append("$lt",grand));
		
		long startTime = System.currentTimeMillis();
		WriteResult wr = collection.remove(newDocument);
		long endTime = System.currentTimeMillis();
		
		int nb= wr.getN();
		long time = endTime-startTime;
		 System.out.println("Temps total d'execution de la suppression :"+ (time) +"ms");
		 writeResult("Suppression ", nb , this.toString() , time + " ms"   );
		 
	}
	
	public void Disconnect(){
		//Close the server connection
		System.out.println("Close connection");
		mongoClient.close();
		try {
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void writeResult(String typeOp, int nb, String base, String temps){
		
		HSSFRow row = sheet.createRow(nbRow);
		nbRow++;
		row.createCell((short)0).setCellValue(typeOp);
		row.createCell((short)1).setCellValue(nb);
		row.createCell((short)2).setCellValue(base);
		row.createCell((short)3).setCellValue(temps);
		
		try {
			fileOut = new FileOutputStream("Resultat/ResultatMongoDB.xls");
		wb.write(fileOut);
		
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
}

