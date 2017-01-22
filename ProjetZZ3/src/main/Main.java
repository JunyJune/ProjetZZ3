package main;

import java.io.IOException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

import databases.MongoDB;
import databases.MySQL;
import databases.Neo4j;
import databases.OracleNoSQL;
import files.CSVtoJSON;
import files.CSVtoNEO;
import files.CSVtoSQL;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println("*********************Projet ZZ3**************************");
		
		String data_filePath_Root = "C:/Users/Aude/Desktop/ZZ3/ProjetZZ3/DataFiles/data.";
		String data_filePath_Source = "C:/Users/Aude/Desktop/ZZ3/ProjetZZ3/DataFiles/data.csv";
		String data_filePath_Json = "C:/Users/Aude/Desktop/ZZ3/ProjetZZ3/DataFiles/data.json";
		String data_filePath_Sql = "C:/Users/Aude/Desktop/ZZ3/ProjetZZ3/DataFiles/data.sql";
		String data_filePath_Neo = "C:/Users/Aude/Desktop/ZZ3/ProjetZZ3/DataFiles/data.txt";
		
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
		
//		MySQL mySQL = new MySQL();
//		mySQL.setFilePath(data_filePath_Sql);
//		mySQL.Connect();
//		mySQL.CreateTables();
//		mySQL.Disconnect();
		
//		Neo4j neo4j = new Neo4j();
//		neo4j.setFilePath(data_filePath_Neo);
		
		OracleNoSQL oracleNoSQL = new OracleNoSQL();
		oracleNoSQL.setFilePath(data_filePath_Json);
		oracleNoSQL.Connect();
		oracleNoSQL.Insert();
		oracleNoSQL.Read();
		oracleNoSQL.Disconnect();				
	}

}
