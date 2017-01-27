package databases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.omg.CORBA.portable.UnknownException;

public class Neo4j {
	private String filePath;
	
	private Driver driver;
	private Session session;
	
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
		String name = "NEO4J";
		return name;
	}
	
	/******************************************************************************************/
	/****************************************Fonctions*****************************************/
	/******************************************************************************************/		
	public void Connect(){
		System.out.println("Connexion à la base de données Neo4j");
		
		/*GraphDatabaseFactory generateur = new GraphDatabaseFactory();
		graphDb = generateur.newEmbeddedDatabase(DB_FILE);
		registerShutdownHook(graphDb);*/
		driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic("neo4j", "46wjxxpc"), Config.build()
		        .withEncryptionLevel( Config.EncryptionLevel.REQUIRED )
		        .withTrustStrategy( Config.TrustStrategy.trustOnFirstUse( new File( "/path/to/neo4j_known_hosts" ) ) )
		        .toConfig() );
		 session = driver.session();
		 
		//Creation du fichier de resultat
			wb = new HSSFWorkbook();
			sheet = wb.createSheet("FeuilleMongoDB");
			nbRow = 0;
		 
	}
	
	public void Insert(){
		System.out.println("Insertion dans la base de données Neo4j");

		//ouverture du fichier destiné à NEO4J
		System.out.println("Debut de l'insertion");
		//lecture du fichier texte	
		try{
			InputStream ips=new FileInputStream(getFilePath()); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;	
			String chaine="";
			int inc=0 , nb_ligne_insere=0;
			long startTime = System.currentTimeMillis();

			while ((ligne=br.readLine())!=null){
				if(ligne.length() != 0){
					 //on recupère ici les 5 relations de creation des tables et relations
						chaine +=ligne;
						inc ++;
						nb_ligne_insere ++;
						if(inc%5 == 0){
						session.run( chaine );
						chaine = "";
						}

				}
			}
			long endTime = System.currentTimeMillis();
			br.close();
			System.out.println("Insertion effectuée");
		    long time = endTime-startTime;
			 System.out.println("Temps total d'execution de l'insertion :"+ (time) +"ms");
			 writeResult("Insertion ", nb_ligne_insere , this.toString() , time + " ms"   );
 
			
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		
	}

	
	public void Update(){
		System.out.println("Mise à jour dans la base de données Neo4j");
		long startTime = System.currentTimeMillis();
		StatementResult result = session.run( "MATCH (a:Client) WHERE a.abonnement_client = 'false' " +
				"SET a.abonnement_client = 'true' " +
                "RETURN a.id_client AS abo" );
		long endTime = System.currentTimeMillis();
		long time = endTime-startTime;
		System.out.println("Temps total d'execution de la mise à jour :"+ time +"ms");
		int nb = 0;
		//Affichage des noeuds modifiés
		while ( result.hasNext() )
		{
			result.next();
			nb++;
			//System.out.println( record.get( "abo" ).asString() );
			
		}
		//result.
		writeResult("Update ", nb , this.toString() , time + " ms"   );
		
	}
	
	public void UpdateWithParameter(String table, String param_de_recherche, String valeur_recherche, String param_de_modif, String valeur_modif){
		System.out.println("Mise à jour dans la base de données Neo4j");
		String _match = "MATCH (a:"+table+") WHERE a." +param_de_recherche+ "= '" + valeur_recherche +"'";
		String _set = "SET a."+ param_de_modif + "= '"+ valeur_modif +"' ";
		String _return ="RETURN a AS abo";
		long startTime = System.currentTimeMillis();
		StatementResult result = session.run( _match + _set +_return );
		long endTime = System.currentTimeMillis();
		long time = endTime-startTime;
		System.out.println("Temps total d'execution de la mise à jour :"+ time +"ms");
		int nb = 0;
		//Affichage des noeuds modifiés
		while ( result.hasNext() )
		{
			result.next();
			nb++;
			//System.out.println( record.get( "abo" ).asString() );
			
		}
		//result.
		writeResult("Update ", nb , this.toString() , time + " ms"   );
		
	}
	
	
	public void ReadOne(String table, String param_de_recherche, String valeur_recherche){
		System.out.println("Lecture dans la base de données Neo4j");
		long startTime = System.currentTimeMillis();
		StatementResult result =  session.run( "MATCH (a:"+table+") WHERE a."+ param_de_recherche+" = '"+valeur_recherche+"' " + 
			"RETURN a" );
		long endTime = System.currentTimeMillis();
		long time = endTime-startTime;
		System.out.println("Temps total d'executiion de la lecture :"+ time +"ms");
		int nb = 0;
		while ( result.hasNext() )
		{
			 result.next();
			 nb++;
			//System.out.println( record );
			
		}
		writeResult("Lecture", nb , this.toString() , time + " ms"   );
		
	}
	
	
	public void ReadAll(){
		System.out.println("Lecture de la base de données Neo4j");
		long startTime = System.currentTimeMillis();
		
		StatementResult result = session.run( "MATCH p=(n)-[r]->(m) " +
				"RETURN keys(n),keys(r),keys(m) " );
		//OU//
		/*StatementResult result = session.run( "MATCH p=(n)-[r]->(m) " +
				"RETURN * " );*/
		
		/*StatementResult result = session.run( "MATCH (cl:Client)--(c:Commande)--(p:Produit)--(f:Fournisseur) " +
				"RETURN cl.id_client,c.id_commande,p.id_produit,f.id_fournisseur " );*/
		int nb = 0;
		while ( result.hasNext() )
		{
			 result.next();
			 nb++;
			//System.out.println( record );
			
		}
		long endTime = System.currentTimeMillis();
		long time = endTime-startTime;
		System.out.println("Temps total d'executiion de la lecture :"+ time +"ms");
		writeResult("Lecture", nb , this.toString() , time + " ms"   );
	}
	
	public void DeleteOne(String table,String param_de_recherche, String valeur_recherche){
		System.out.println("Suppresion dans la base de données Neo4j");
		String _match;
		StatementResult result = null;
		long endTime = 0 ;
		long startTime = 0;
		
		
		/****************************Cas suppression client -> suppresion relation commande/client et supp commande *****************************/
		if(table == "Client"){
			//execution requete pour recuperer le nombre de noeud supprime car impossible de recuperer avec delete
			_match = "MATCH (a:Client)--(commande:Commande) WHERE a."+ param_de_recherche+" = '"+valeur_recherche+"' ";
			result = session.run( _match + " RETURN a" );
			//suppression
			startTime = System.currentTimeMillis();
			 session.run( _match + 
				"DETACH DELETE commande, a" );
			endTime = System.currentTimeMillis();
		}else
		
		/***************************Cas suppresion commande -> suppresion relation commande/produit, commande/client************/
		if(table == "Commande"){
			_match = "MATCH (a:Commande) WHERE a."+ param_de_recherche+" = '"+valeur_recherche+"' ";
			result = session.run( _match + " RETURN a" );
			startTime = System.currentTimeMillis();
			 session.run( _match +
				"DETACH DELETE a" );
			 endTime = System.currentTimeMillis();
		}else
		
		/*********Cas suppression produit -> suppression relation commande/produit, produit/fournisseur, suppresion fournisseur et commande***********/
		if(table == "Produit"){
			_match =  "MATCH (fournisseur:Fournisseur)--(a:Produit)--(commande:Commande) WHERE a."+ param_de_recherche+" = '";
			result = session.run( _match + " RETURN a" );
			startTime = System.currentTimeMillis();
			 session.run( _match +
				"DETACH DELETE commande, fournisseur, a" );
			 endTime = System.currentTimeMillis();
		}else
		
		
		/*********Cas suppression fournisseur -> suppression produit, commande, et relation fournisseur/produit, produit/commande, commande/client***********/
		if(table == "Fournisseur"){
			_match = "MATCH (a:Fournisseur)--(produit:Produit)--(commande:Commande) WHERE a."+ param_de_recherche+" = '"+valeur_recherche+"' ";
			result = session.run( _match + " RETURN a" );		
			startTime = System.currentTimeMillis();
			 session.run( _match +
				"DETACH DELETE commande, produit, a" );
			 endTime = System.currentTimeMillis();
		}

		long time = endTime-startTime;
		System.out.println("Temps total d'executiion de la suppresion :"+ time +"ms");
		int nb = 0;
		while ( result.hasNext() )
		{
			 result.next();
			 nb++;
			//System.out.println( record );
			
		}
		writeResult("Delete", nb , this.toString() , time + " ms"   );
		
		
	}
	
	public void DeleteAll(){
		System.out.println("Lecture de la base de données Neo4j");
		//recherche du nombre de lignes supprimees
		StatementResult result = session.run( "MATCH (n) " + "RETURN n " );
		
		long startTime = System.currentTimeMillis();
		session.run( "MATCH (a) " + 
				"DETACH DELETE a ");
		int nb = 0;
		while ( result.hasNext() )
		{
			 result.next();
			 nb++;
			//System.out.println( record );
			
		}
		long endTime = System.currentTimeMillis();
		long time = endTime-startTime;
		System.out.println("Temps total d'executiion de la lecture :"+ time +"ms");
		writeResult("Delete", nb , this.toString() , time + " ms"   );
	}
	
	public void DeleteBetween(String table, String param_de_recherche, String petit, String grand){
		System.out.println("Suppresion dans la base de données Neo4j");
		
		System.out.println("Mise à jour dans la base de données Neo4j");
		String _match;
		StatementResult result = null;
		long endTime = 0 ;
		long startTime = 0;
		
		
		/****************************Cas suppression client -> suppresion relation commande/client et supp commande *****************************/
		if(table == "Client"){
			//execution requete pour recuperer le nombre de noeud supprime car impossible de recuperer avec delete
			_match = "MATCH (a:Client)--(commande:Commande) WHERE a."+ param_de_recherche+" > '"+petit+"' AND a."+ param_de_recherche+" < '"+grand+"'";
			result = session.run( _match + " RETURN a" );
			//suppression
			startTime = System.currentTimeMillis();
			 session.run( _match + 
				"DETACH DELETE commande, a" );
			endTime = System.currentTimeMillis();
		}else
		
		/***************************Cas suppresion commande -> suppresion relation commande/produit, commande/client************/
		if(table == "Commande"){
			_match = "MATCH (a:Commande) WHERE WHERE a."+ param_de_recherche+" > '"+petit+"' AND a."+ param_de_recherche+" < '"+grand+"'";
			result = session.run( _match + " RETURN a" );
			startTime = System.currentTimeMillis();
			 session.run( _match +
				"DETACH DELETE a" );
			 endTime = System.currentTimeMillis();
		}else
		
		/*********Cas suppression produit -> suppression relation commande/produit, produit/fournisseur, suppresion fournisseur et commande***********/
		if(table == "Produit"){
			_match =  "MATCH (fournisseur:Fournisseur)--(a:Produit)--(commande:Commande) WHERE a."+ param_de_recherche+" > '"+petit+"' AND a."+ param_de_recherche+" < '"+grand+"'";
			result = session.run( _match + " RETURN a" );
			startTime = System.currentTimeMillis();
			 session.run( _match +
				"DETACH DELETE commande, fournisseur, a" );
			 endTime = System.currentTimeMillis();
		}else
		
		
		/*********Cas suppression fournisseur -> suppression produit, commande, et relation fournisseur/produit, produit/commande, commande/client***********/
		if(table == "Fournisseur"){
			_match = "MATCH (a:Fournisseur)--(produit:Produit)--(commande:Commande) WHERE a."+ param_de_recherche+" > '"+petit+"' AND a."+ param_de_recherche+" < '"+grand+"'";
			result = session.run( _match + " RETURN a" );		
			startTime = System.currentTimeMillis();
			 session.run( _match +
				"DETACH DELETE commande, produit, a" );
			 endTime = System.currentTimeMillis();
		}

		long time = endTime-startTime;
		System.out.println("Temps total d'executiion de la suppresion :"+ time +"ms");
		int nb = 0;
		while ( result.hasNext() )
		{
			 result.next();
			 nb++;
			//System.out.println( record );
			
		}
		writeResult("Delete", nb , this.toString() , time + " ms"   );
		
		
	}
	
	public void Disconnect(){
		//graphDb.shutdown();
		System.out.println("Close connection");
		driver.close();
		session.close();
		
		try {
			fileOut = new FileOutputStream("Resultat/ResultatNEO4J.xls");
		wb.write(fileOut);
		
		} catch (FileNotFoundException e) {
		e.printStackTrace();
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
			fileOut = new FileOutputStream("Resultat/ResultatNEO4J.xls");
		wb.write(fileOut);
		
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
}
