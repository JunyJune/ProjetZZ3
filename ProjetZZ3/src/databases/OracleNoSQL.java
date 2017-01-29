package databases;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import jline.internal.InputStreamReader;
import oracle.kv.KVStore;
import oracle.kv.KVStoreConfig;
import oracle.kv.KVStoreFactory;
import oracle.kv.Key;
import oracle.kv.StatementResult;
import oracle.kv.table.PrimaryKey;
import oracle.kv.table.RecordValue;
import oracle.kv.table.Row;
import oracle.kv.table.Table;
import oracle.kv.table.TableAPI;

public class OracleNoSQL {
	
	private String filePath;
	private KVStore kvstore;
	String firstLigne;
	
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
	
	@Override
	public String toString(){
		String name = "Oracle NOSQL";
		return name;
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
		 
		//Creation du fichier de resultat
			wb = new HSSFWorkbook();
			sheet = wb.createSheet("FeuilleMongoDB");
			nbRow = 0;
	}
	
	
	public void CreateTable(){
		 kvstore.executeSync("CREATE TABLE IF NOT EXISTS Commandes_ ("
			+	"id_client STRING, "
			+	"ville_client STRING, "
			+	"prenom_client STRING, "
			+	"nom_client STRING, "
			+	"email_client STRING, "
			+	"gender_client STRING, "
			+	"telephone_client STRING, "
			+	"iban_client STRING, "
			+	"abonnement_client STRING, "
			+	"id_fournisseur STRING, "
			+	"ville_fournisseur STRING, "
			+	"nom_fournisseur STRING, "
			+	"slogan_fournisseur STRING, "
			+	"devise_fournisseur STRING, "
			+	"email_fournisseur STRING, "
			+	"iban_fournisseur STRING, "
			+	"telephone_fournisseur STRING, "
			+	"id_produit STRING, "
			+	"couleur_produit STRING, "
			+	"prix_produit STRING, "
			+	"label_produit STRING, "
			+	"id_commande  STRING, "
			+	"date_commande  STRING, "
			+	"PRIMARY KEY (id_commande))");
		 
		 //TODO
		//Faire une creation d'index pour rapidité executions CRUD
		
	}
	public void Insert(){
		System.out.println("\nInsertion dans la base de données OracleNoSQL");
		int nb_ligne_insere = 0;
		long startTime = System.currentTimeMillis();
		
		try {
			
			InputStream inputStream = new FileInputStream(filePath);
			
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			 BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			 String firstLigne = bufferedReader.readLine();
			
			String stringKeys[] = firstLigne.split(",");

			Key keys[] = new Key[stringKeys.length];
			String fullLigne;
			String ligne[] = new String[stringKeys.length];
			
			String line;
			TableAPI tableH = kvstore.getTableAPI();
			Table myTable = tableH.getTable("Commandes_");
			Row row;
			
			
			while((fullLigne = bufferedReader.readLine()) != null) {
				ligne = fullLigne.split(",");
				row = myTable.createRow();
				for (int i = 0; i < keys.length; i++) {
					line = ligne[i];
					row.put(stringKeys[i],  line);
				}
				tableH.put(row, null, null); 
				nb_ligne_insere++;
			}
			
			bufferedReader.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		 long endTime = System.currentTimeMillis();
		 long time = endTime-startTime;
		 System.out.println("Temps total d'execution de l'insertion :"+ time +"ms\n");
		 writeResult("Insertion", nb_ligne_insere , this.toString() , time + " ms"   );
	}
	
	public void Update(){
		System.out.println("\nMise à jour dans la base de données OracleNoSQL");
		int nb =0;
		TableAPI tableH = kvstore.getTableAPI();
		Table myTable = tableH.getTable("Commandes_");
		Row row;
		StatementResult result = kvstore.executeSync("SELECT * FROM Commandes_ WHERE abonnement_client='false' ");
		long startTime = System.currentTimeMillis();
		for( RecordValue record : result ) {
		    // Update a field
			 row = myTable.createRow(record);			
		    row.put("abonnement_client", "true" );
		   tableH.put(row, null, null);
			nb++;
		}
		long endTime = System.currentTimeMillis();
		long time = endTime-startTime;
		System.out.println("Temps total d'execution de la mise à jour :"+ time +"ms\n");
		writeResult("Update ", nb , this.toString() , time + " ms"   );
	}
	
	public void UpdateWithParameter(String param_de_recherche, String valeur_recherche, String param_de_modif, String valeur_modif){
		
		System.out.println("\nMise à jour dans la base de données OracleNoSQL");
		int nb =0;
		TableAPI tableH = kvstore.getTableAPI();
		Table myTable = tableH.getTable("Commandes_");
		Row row;
		StatementResult result = kvstore.executeSync("SELECT * FROM Commandes_ WHERE "+ param_de_recherche+ " = '"+ valeur_recherche+"' ");
		long startTime = System.currentTimeMillis();
		for( RecordValue record : result ) {
		    // Update field
			 row = myTable.createRow(record);			
		    row.put(param_de_modif, valeur_modif );
		   tableH.put(row, null, null);
			nb++;
		}
		long endTime = System.currentTimeMillis();
		long time = endTime-startTime;
		System.out.println("Temps total d'execution de la mise à jour :"+ time +"ms\n");
		writeResult("Update ", nb , this.toString() , time + " ms"   );
	}
	
	//Si bool=true alors il y a une deuxième condition pour la selection
	public void ReadOne(String param_de_recherche1,String operator1, String valeur_recherche1, Boolean bool, String param_de_recherche2,String operator2, String valeur_recherche2){
		System.out.println("\nLecture dans la base de données OracleNoSQL");
		int nb =0;
		String _query;
		if(bool){
			_query= ("SELECT id_client FROM Commandes_ WHERE "+ param_de_recherche1+ " "+ operator1 +" '"+ valeur_recherche1+"' AND "+ param_de_recherche2 + " "+ operator2 +" '"+ valeur_recherche2+ "' ");
		}else{
			_query = "SELECT id_client FROM Commandes_ WHERE "+ param_de_recherche1+ " "+ operator1 +" '"+ valeur_recherche1+"' ";
		}
		
		long startTime = System.currentTimeMillis();
		StatementResult result = kvstore.executeSync(_query);
		long endTime = System.currentTimeMillis();
		
		for( RecordValue record : result ) {
			nb++;
		}
		long time = endTime-startTime;
		System.out.println("Temps total d'execution de la lecture :"+ time +"ms\n");
		writeResult("Lecture ", nb , this.toString() , time + " ms"   );
	}
	
	public void readAll(){
		System.out.println("\nLecture dans la base de données OracleNoSQL");
		int nb =0;
		long startTime = System.currentTimeMillis();
		StatementResult result = kvstore.executeSync("SELECT id_commande FROM Commandes_ "); //chercher une facon plus rapide; genre count(*)
		long endTime = System.currentTimeMillis();
		for( RecordValue record : result ) {
			nb++;
		}
		long time = endTime-startTime;
		System.out.println("Temps total d'execution de la lecture :"+ time +"ms\n");
		writeResult("Lecture", nb , this.toString() , time + " ms"   );
		
	}
	
	public void DeleteOne(String param_de_recherche, String valeur_recherche){
		System.out.println("\nSuppresion dans la base de données OracleNoSQL");
		int nb =0;
		TableAPI tableH = kvstore.getTableAPI();
		Table myTable = tableH.getTable("Commandes_");
		StatementResult result = kvstore.executeSync("SELECT id_commande FROM Commandes_ WHERE "+ param_de_recherche+ " = '"+ valeur_recherche+"' ");
		long startTime = System.currentTimeMillis();
		for( RecordValue record : result ) {
			// Create the key
			PrimaryKey primaryKey = myTable.createPrimaryKey();
			primaryKey.put("id_commande", record.get("id_commande"));
		   tableH.delete( primaryKey, null, null);
			nb++;
		}
		long endTime = System.currentTimeMillis();
		long time = endTime-startTime;
		System.out.println("Temps total d'execution de la lecture :"+ time +"ms\n");
		writeResult("Suppression", nb , this.toString() , time + " ms"   );		
	}
	
	public void DeleteAll(){
		System.out.println("\nSuppresion dans la base de données OracleNoSQL");
		int nb =0;
		TableAPI tableH = kvstore.getTableAPI();
		Table myTable = tableH.getTable("Commandes_");
		StatementResult result = kvstore.executeSync("SELECT id_commande FROM Commandes_");
		long startTime = System.currentTimeMillis();
		for( RecordValue record : result ) {
			// Create the key
			PrimaryKey primaryKey = myTable.createPrimaryKey();
			primaryKey.put("id_commande", record.get("id_commande"));
		   tableH.delete( primaryKey, null, null);
			nb++;
		}
		long endTime = System.currentTimeMillis();
		long time = endTime-startTime;
		System.out.println("Temps total d'execution de la lecture :"+ time +"ms\n");
		writeResult("Suppression", nb , this.toString() , time + " ms"   );		
	}
	
	public void DeleteBetween(String param_recherche, String petit, String grand){
		System.out.println("\nSuppresion dans la base de données OracleNoSQL");
		int nb =0;
		TableAPI tableH = kvstore.getTableAPI();
		Table myTable = tableH.getTable("Commandes_");
		StatementResult result = kvstore.executeSync("SELECT id_commande FROM Commandes_ WHERE "+ param_recherche+ " > '"+petit+"' AND " + param_recherche+ " < '"+grand+"'");
		long startTime = System.currentTimeMillis();
		for( RecordValue record : result ) {
			// Create the key
			PrimaryKey primaryKey = myTable.createPrimaryKey();
			primaryKey.put("id_commande", record.get("id_commande"));
		   tableH.delete( primaryKey, null, null);
			nb++;
		}
		long endTime = System.currentTimeMillis();
		long time = endTime-startTime;
		System.out.println("Temps total d'execution de la lecture :"+ time +"ms\n");
		writeResult("Suppression", nb , this.toString() , time + " ms"   );		
		
		
	}
	
	public void Disconnect(){
		System.out.println("\nDéconnexion de la base de données OracleNoSQL");	
		kvstore.close();
		
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
			fileOut = new FileOutputStream("Resultat/ResultatOracleNOSQL.xls");
		wb.write(fileOut);
		
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
}
