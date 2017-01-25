package main;

import java.io.IOException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

import databases.MongoDB;
import databases.MySQL;
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
			jsonConverter.convertCSVtoJSON(data_filePath_Source);
			sqlConverter.execute(data_filePath_Source);
			neoConverter.execute(data_filePath_Source);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
//		MongoDB mongoDB = new MongoDB();
//		mongoDB.setFilePath(data_filePath_Json);
//		mongoDB.InitialisationListes();
//		mongoDB.Connect();
//		mongoDB.Insert("SecondCollection", "FirstDocument");
//		mongoDB.InsertFile();
//		mongoDB.ReadAllDatabase();
//		mongoDB.Disconnect();
		
		MySQL mySQL = new MySQL();
		mySQL.setFilePath(data_filePath_Sql);
		mySQL.Connect();
		mySQL.Delete();
		mySQL.DropTable();
		mySQL.CreateTables();
//		L'insertion de 1000 lignes dure environ 3min
//		Certaines lignes sont rejetées car des fournisseurs différents ont parfois le même ID
		mySQL.Insert();
//		Ne fonctionne pas encore, le resultSet vaut toujours 0
		mySQL.Read();
		mySQL.Disconnect();
		
		/*Neo4j neo4j = new Neo4j();
		neo4j.setFilePath(data_filePath_Neo);
		neo4j.Connect();
		neo4j.Insert();
		neo4j.Disconnect();*/
		
//		OracleNoSQL oracleNoSQL = new OracleNoSQL();
//		oracleNoSQL.setFilePath(data_filePath_Source);
//		oracleNoSQL.Connect();
//		oracleNoSQL.Insert();
//		oracleNoSQL.Read();
//		oracleNoSQL.Disconnect();		
	}

}
