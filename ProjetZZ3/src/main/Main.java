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
		CSVtoNEO neoConverter = new CSVtoNEO();
		
		try {
			//jsonConverter.convertCSVtoJSON(data_filePath_Source);
			//sqlConverter.execute(data_filePath_Source);
			neoConverter.execute(data_filePath_Source);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		MongoDB mongoDB = new MongoDB();
		mongoDB.setFilePath(data_filePath_Json);
		mongoDB.InitialisationListes();
		mongoDB.Connect();
//		mongoDB.Insert();
//		mongoDB.ReadAllDatabase();
//		mongoDB.Update();
//		mongoDB.UpdateWithParameter("id_client", "6", "abonnement_client", "false");
//		mongoDB.DeleteOne("id_client", "10");
//		mongoDB.DeleteAll();
		mongoDB.DeleteBetween("id_fournisseur", "2000", "4000");
		mongoDB.Disconnect();
		
		
//		MySQL mySQL = new MySQL();
//		mySQL.setFilePath(data_filePath_Sql);
//		mySQL.Connect();
//		mySQL.DeleteClient();
//		mySQL.DeleteFournisseur();
//		mySQL.DeleteProduit();
//		mySQL.DeleteCommande();
//		mySQL.Delete();
//		mySQL.DropTable();
//		mySQL.CreateTables();
//		mySQL.Insert();
//		mySQL.Read();
//		mySQL.Disconnect();
		
		Neo4j neo4j = new Neo4j();
		neo4j.setFilePath(data_filePath_Neo);
		neo4j.Connect();
		neo4j.Insert();
		//neo4j.Update();
		//neo4j.Delete();
		//neo4j.Read();
		neo4j.Disconnect();

		
//		OracleNoSQL oracleNoSQL = new OracleNoSQL();
//		oracleNoSQL.setFilePath(data_filePath_Source);
//		oracleNoSQL.Connect();
//		oracleNoSQL.Insert();
//		oracleNoSQL.Read();
//		oracleNoSQL.Disconnect();		
	}

}
