package main;

import java.io.IOException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

import databases.MongoDB;
import databases.MySQL;
import databases.Neo4j;
//import databases.Neo4j;
import databases.OracleNoSQL;
import files.CSVtoJSON;
import files.CSVtoNEO;
import files.CSVtoSQL;
//
public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println("*********************Projet ZZ3**************************");
		
		String data_filePath_Root = "DataFiles/data.";
		String data_filePath_Source = "DataFiles/data.csv";
		String data_filePath_Json = "DataFiles/data.json";
		String data_filePath_Sql = "DataFiles/data.sql";
		String data_filePath_Neo = "DataFiles/data.txt";
		
		CSVtoJSON jsonConverter = new CSVtoJSON();
		CSVtoSQL sqlConverter = new CSVtoSQL();
//		CSVtoNEO neoConverter = new CSVtoNEO();
		
		try {
//			jsonConverter.convertCSVtoJSON(data_filePath_Source);
			sqlConverter.execute(data_filePath_Source);
			//neoConverter.execute(data_filePath_Source);
		} catch (Exception e) {
			e.printStackTrace();
		}
/*********************************MongoDB*************************************/				
//		MongoDB mongoDB = new MongoDB();
//		mongoDB.setFilePath(data_filePath_Json);
//		mongoDB.InitialisationListes();
//		mongoDB.Connect();
//		mongoDB.Insert();
//		mongoDB.ReadAll();
//		mongoDB.UpdateWithParameter("abonnement_client", "false", "abonnement_client", "true");
//		mongoDB.DeleteBetween("id_fournisseur", "2000", "4000");
//		
//		mongoDB.ReadOne("abonnement_client", "true");
//		mongoDB.Update();
//		mongoDB.DeleteOne("id_client", "10");
//		mongoDB.DeleteAll();
//		mongoDB.ReadOne("abonnement_client", "=", "true", true, "id_client", "<", "3000" );
//		mongoDB.Disconnect();
		
/*********************************MySQL*************************************/			
		MySQL mySQL = new MySQL();
		mySQL.setFilePath(data_filePath_Sql);
		mySQL.Connect();
		
//		mySQL.CreateTables();	//Gère les clés étrangères
//		mySQL.Insert();
		
		mySQL.ReadAll();
//		mySQL.Read();	//Requête en dur dans le code
//		mySQL.ReadSelectEtoile("where abonnement_client=\"false\"");		//Uniquement pour un select*
//		
//		mySQL.ReadGenerique("Select id_produit from commande where date_commande=\"5/3/2013\" or id_client=\"2\";");
//		mySQL.ReadGenerique("Select count(*) from Client where abonnement_client=\"true\" AND id_client < \"3000\";");
//		
//		mySQL.Update("update Client set ville_client=\"UnVilleAssezLonguePourQueJeLaVoisBienDansLaBase\" where abonnement_client=\"true\"; ");
//		
//		mySQL.DeleteClient("where iban_client like \"F%\" ");
//		mySQL.DeleteFournisseur("where email_fournisseur =\"\" and telephone_fournisseur = \"\" ");		
//		mySQL.DeleteProduit("where couleur_produit = \"Mauv\"");
//		mySQL.DeleteCommande("where date_commande like \"%/3/%\" ");
//		
//		mySQL.DropTable();
		
		mySQL.Disconnect();

		
/***********************************NEO4J***************************************/			
//		Neo4j neo4j = new Neo4j();
//		neo4j.setFilePath(data_filePath_Neo);
//		neo4j.Connect();
//		neo4j.Insert();
//		neo4j.ReadAll();
//		neo4j.UpdateWithParameter("Client","abonnement_client","false", "abonnement_client", "true");
//		neo4j.DeleteBetween("Fournisseur", "id_fournisseur", "2000", "4000");
//		neo4j.ReadOne("Client","abonnement_client", "=", "true", true,"Client", "id_client", "<", "3000");
//		neo4j.Update();
//		neo4j.Delete();
//		neo4j.DeleteOne("Client", "id_client", "10");
//		neo4j.DeleteAll();
//		neo4j.Disconnect();

/*********************************OracleNoSQL*************************************/			
//		OracleNoSQL oracleNoSQL = new OracleNoSQL();
//		oracleNoSQL.setFilePath(data_filePath_Source);
//		oracleNoSQL.Connect();
//		oracleNoSQL.CreateTable();
//		oracleNoSQL.Insert();
//		oracleNoSQL.UpdateWithParameter("abonnement_client","false", "abonnement_client", "true");
//		oracleNoSQL.ReadOne("abonnement_client", "=", "true", true, "id_client", "<", "3000" );
//		oracleNoSQL.DeleteOne("id_client", "10");
//		oracleNoSQL.DeleteBetween( "id_fournisseur", "2000", "4000");
//		oracleNoSQL.DeleteAll();		
//		oracleNoSQL.Disconnect();		
	}

}
