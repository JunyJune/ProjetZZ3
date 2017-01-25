//package databases;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//
//import org.neo4j.driver.v1.AuthTokens;
//import org.neo4j.driver.v1.Config;
//import org.neo4j.driver.v1.Driver;
//import org.neo4j.driver.v1.GraphDatabase;
//import org.neo4j.driver.v1.Session;
//import org.neo4j.graphdb.GraphDatabaseService;
//import org.neo4j.graphdb.factory.GraphDatabaseFactory;
//
//public class Neo4j {
//	private String filePath;
//	private static String DB_PATH = "C:/Users/Aude/Desktop/ZZ3/ProjetZZ3/Databases";
//	private File DB_FILE = new File(DB_PATH);
//	
//	private GraphDatabaseService graphDb;
//	//private Session session;
//	private Driver driver;
//	/******************************************************************************************/
//	/***************************************Accesseurs*****************************************/
//	/******************************************************************************************/
//	
//	public void setFilePath(String filePath){
//		this.filePath = filePath;
//	}
//	
//	public String getFilePath(){
//		return filePath;
//	}
//	
//	/******************************************************************************************/
//	/****************************************Fonctions*****************************************/
//	/******************************************************************************************/	
//
//	
//	/*private static void registerShutdownHook(final GraphDatabaseService graphDb){
//		Runtime.getRuntime().addShutdownHook(
//				new Thread(){
//					@Override
//					public void run(){
//						graphDb.shutdown();
//					}
//				}
//		);
//	}*/
//	
//	public void Connect(){
//		System.out.println("Connexion à la base de données Neo4j");
//		
//		/*GraphDatabaseFactory generateur = new GraphDatabaseFactory();
//		graphDb = generateur.newEmbeddedDatabase(DB_FILE);
//		registerShutdownHook(graphDb);*/
//		driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic("neo4j", "46wjxxpc"), Config.build()
//		        .withEncryptionLevel( Config.EncryptionLevel.REQUIRED )
//		        .withTrustStrategy( Config.TrustStrategy.trustOnFirstUse( new File( "/path/to/neo4j_known_hosts" ) ) )
//		        .toConfig() );
//		 
//	}
//	
//	public void Insert(){
//		System.out.println("Insertion dans la base de données Neo4j");
//		
//		Session session = driver.session();
//		//ouverture du fichier destiné à NEO4J
//		//String fichier ="DataFiles/data.txt";
//		System.out.println("Debut de l'insertion");
//		//lecture du fichier texte	
//		try{
//			InputStream ips=new FileInputStream(getFilePath()); 
//			InputStreamReader ipsr=new InputStreamReader(ips);
//			BufferedReader br=new BufferedReader(ipsr);
//			String ligne;	
//			String chaine="";
//			int inc=0;
//			long startTime = System.currentTimeMillis();
//
//			while ((ligne=br.readLine())!=null){
//				if(ligne.length() != 0){
//					 //on recupère ici les 5 relations de creation des tables et relations
//						chaine +=ligne;
//						inc ++;
//						if(inc%5 == 0){
//						session.run( chaine );
//						chaine = "";
//						}
//
//				}
//			}
//			long endTime = System.currentTimeMillis();
//			br.close();
//			System.out.println("Insertion effectuée");
//		    System.out.println("Temps total d'executiion de l'insertion :"+ (endTime-startTime) +"ms");
// 
//			
//		}		
//		catch (Exception e){
//			System.out.println(e.toString());
//		}
//		session.close();
//	}
//
//	
//	public void Update(){
//		System.out.println("Mise à jour dans la base de données Neo4j");
//	}
//	
//	public void Read(){
//		System.out.println("Lecture de la base de données Neo4j");
//	}
//	
//	public void Delete(){
//		System.out.println("Suppresion dans la base de données Neo4j");
//	}
//	
//	public void Disconnect(){
//		//graphDb.shutdown();
//		driver.close();
//	}
//}
