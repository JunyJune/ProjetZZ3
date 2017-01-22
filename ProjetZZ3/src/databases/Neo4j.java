package databases;

import java.io.File;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Neo4j {
	private String filePath;
	private static String DB_PATH = "C:/Users/Aude/Desktop/ZZ3/ProjetZZ3/Databases";
	private File DB_FILE = new File(DB_PATH);
	
	private GraphDatabaseService graphDb;
	
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

	
	private static void registerShutdownHook(final GraphDatabaseService graphDb){
		Runtime.getRuntime().addShutdownHook(
				new Thread(){
					@Override
					public void run(){
						graphDb.shutdown();
					}
				}
		);
	}
	
	public void Connect(){
		System.out.println("Connexion à la base de données Neo4j");
		
		GraphDatabaseFactory generateur = new GraphDatabaseFactory();
		graphDb = generateur.newEmbeddedDatabase(DB_FILE);
		registerShutdownHook(graphDb);
	}
	
	public void Insert(){
		System.out.println("Insertion dans la base de données Neo4j");
	}
	
	public void Update(){
		System.out.println("Mise à jour dans la base de données Neo4j");
	}
	
	public void Read(){
		System.out.println("Lecture de la base de données Neo4j");
	}
	
	public void Delete(){
		System.out.println("Suppresion dans la base de données Neo4j");
	}
	
	public void Disconnect(){
		graphDb.shutdown();
	}
}
