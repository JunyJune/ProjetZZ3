package databases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public class Neo4j {
	private String filePath;
	//private static String DB_PATH = "C:/Users/Aude/Desktop/ZZ3/ProjetZZ3/Databases";
	//private File DB_FILE = new File(DB_PATH);
	
	//private GraphDatabaseService graphDb;
	//private Session session;
	private Driver driver;
	private Session session;
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

	
	/*private static void registerShutdownHook(final GraphDatabaseService graphDb){
		Runtime.getRuntime().addShutdownHook(
				new Thread(){
					@Override
					public void run(){
						graphDb.shutdown();
					}
				}
		);
	}*/
	
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
		 
	}
	
	public void Insert(){
		System.out.println("Insertion dans la base de données Neo4j");
		
		
		//ouverture du fichier destiné à NEO4J
		//String fichier ="DataFiles/data.txt";
		System.out.println("Debut de l'insertion");
		//lecture du fichier texte	
		try{
			InputStream ips=new FileInputStream(getFilePath()); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;	
			String chaine="";
			int inc=0;
			long startTime = System.currentTimeMillis();

			while ((ligne=br.readLine())!=null){
				if(ligne.length() != 0){
					 //on recupère ici les 5 relations de creation des tables et relations
						chaine +=ligne;
						inc ++;
						if(inc%5 == 0){
						session.run( chaine );
						chaine = "";
						}

				}
			}
			long endTime = System.currentTimeMillis();
			br.close();
			System.out.println("Insertion effectuée");
		    System.out.println("Temps total d'executiion de l'insertion :"+ (endTime-startTime) +"ms");
 
			
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
		System.out.println("Temps total d'executiion de la mise à jour :"+ (endTime-startTime) +"ms");
		//Affichage des noeuds modifiés
		while ( result.hasNext() )
		{
			Record record = result.next();
			System.out.println( record.get( "abo" ).asString() );
			
		}
		
	}
	
	public void Read(){
		System.out.println("Lecture de la base de données Neo4j");
		long startTime = System.currentTimeMillis();
		
		StatementResult result = session.run( "MATCH p=(n)-[r]->(m) " +
				"RETURN keys(n),keys(r),keys(m) " );
		//OU//
		/*StatementResult result = session.run( "MATCH p=(n)-[r]->(m) " +
				"RETURN * " );*/
		
		/*StatementResult result = session.run( "MATCH (cl:Client)--(c:Commande)--(p:Produit)--(f:Fournisseur) " +
				"RETURN cl.id_client,c.id_commande,p.id_produit,f.id_fournisseur " );*/
		while ( result.hasNext() )
		{
			Record record = result.next();
			System.out.println( record );
			
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Temps total d'executiion de la lecture :"+ (endTime-startTime) +"ms");
	}
	
	public void Delete(){
		System.out.println("Suppresion dans la base de données Neo4j");
		
		System.out.println("Mise à jour dans la base de données Neo4j");
		long startTime = System.currentTimeMillis();
		
		
		/****************************Cas suppression client -> suppresion relation commande/client et supp commande *****************************/
		/* session.run( "MATCH (a:Client)--(commande:Commande) WHERE a.id_client = '3' " +
				"DETACH DELETE commande, a" );*/
		
		
		/***************************Cas suppresion commande -> suppresion relation commande/produit, commande/client************/
		/*session.run( "MATCH (a:Commande) WHERE a.id_commande = '4' " +
				"DETACH DELETE a" );*/
		
		/*********Cas suppression produit -> suppression relation commande/produit, produit/fournisseur, suppresion fournisseur et commande***********/
		/*session.run( "MATCH (fournisseur:Fournisseur)--(a:Produit)--(commande:Commande) WHERE a.id_produit = '900000005' " +
				"DETACH DELETE commande, fournisseur, a" );*/
		
		
		/*********Cas suppression fournisseur -> suppression produit, commande, et relation fournisseur/produit, produit/commande, commande/client***********/
		/*session.run( "MATCH (a:Fournisseur)--(produit:Produit)--(commande:Commande) WHERE a.id_fournisseur = '4971' " +
				"DETACH DELETE commande, produit, a" );*/
		
		
		long endTime = System.currentTimeMillis();
		System.out.println("Temps total d'executiion de la suppresion :"+ (endTime-startTime) +"ms");
		
		
	}
	
	public void Disconnect(){
		//graphDb.shutdown();
		driver.close();
		session.close();
	}
}
