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
//		
////		String data_filePath_Root = "DataFiles/data.";
////		String data_filePath_Source = "DataFiles/data.csv";
////		String data_filePath_Json = "DataFiles/data.json";
//		String data_filePath_Sql = "DataFiles/data.sql";
//		String data_filePath_Neo = "DataFiles/data.txt";
//		
////		CSVtoJSON jsonConverter = new CSVtoJSON();
//		CSVtoSQL sqlConverter = new CSVtoSQL();
//		CSVtoNEO neoConverter = new CSVtoNEO();
//		
//		try {
////			jsonConverter.convertCSVtoJSON(data_filePath_Source);
////			sqlConverter.execute(data_filePath_Source);
////			neoConverter.execute(data_filePath_Source);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		
/*********************************MySQL*************************************/			
//		MySQL mySQL = new MySQL();
//		mySQL.setFilePath(data_filePath_Sql);
//		mySQL.Connect();
//		
//		mySQL.CreateTables();	//Gère les clés étrangères
//		mySQL.Insert();
//		
//		mySQL.ReadAll();
//		mySQL.Update("update Client set abonnement_client=\"true\" where abonnement_client=\"false\"; ");
//		mySQL.DeleteFournisseur("where id_fournisseur >\"2000\" and id_fournisseur < \"4000\" ");	
//		mySQL.ReadGenerique("Select count(*) from Client where abonnement_client=\"true\" AND id_client<\"3000\";");
//		
//		mySQL.Read();	//Requête en dur dans le code
////		mySQL.ReadSelectEtoile("where abonnement_client=\"false\"");		//Uniquement pour un select*
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
//		mySQL.DeleteAll();
//		mySQL.DropTable();
//		mySQL.Disconnect();

		
/********************************Plan de Test************************************/
/*********************************MongoDB*************************************/				
		/*int i = 1;
		
		MongoDB mongoDB = new MongoDB();
		mongoDB.InitialisationListes();
		mongoDB.Connect();
		CSVtoJSON jsonConverter = new CSVtoJSON();
		String data_filePath_Source = "DataFiles/data_"+i+".csv";
		try {
			jsonConverter.convertCSVtoJSON(data_filePath_Source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String data_filePath_Json = "DataFiles/data_"+i+".json";
		i++;
		mongoDB.setFilePath(data_filePath_Json);
		mongoDB.Insert(); //1 000
		mongoDB.ReadOne("abonnement_client", "=", "false", false, null, null, null);
		mongoDB.ReadOne("abonnement_client", "=", "false", true, "couleur_produit", "=", "Mauv" );
		jsonConverter = new CSVtoJSON();
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		try {
			jsonConverter.convertCSVtoJSON(data_filePath_Source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		data_filePath_Json = "DataFiles/data_"+i+".json";
		i++;
		mongoDB.setFilePath(data_filePath_Json);
		mongoDB.Insert(); //10 000
		mongoDB.UpdateWithParameter("devise_fournisseur", "EUR", "telephone_fournisseur", "+33");
		mongoDB.DeleteOne("gender_client", "male");
		jsonConverter = new CSVtoJSON();
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		try {
			jsonConverter.convertCSVtoJSON(data_filePath_Source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		data_filePath_Json = "DataFiles/data_"+i+".json";
		i++;
		mongoDB.setFilePath(data_filePath_Json);
		mongoDB.Insert(); //50 000
		mongoDB.ReadAll();
		jsonConverter = new CSVtoJSON();
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		try {
			jsonConverter.convertCSVtoJSON(data_filePath_Source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		data_filePath_Json = "DataFiles/data_"+i+".json";
		i++;
		mongoDB.setFilePath(data_filePath_Json);
		mongoDB.Insert(); //20 000
		mongoDB.UpdateWithParameter("abonnement_client", "false", "abonnement_client", "true");
		mongoDB.ReadOne("devise_fournisseur", "=", "DOLL", true, "prix_produit", ">", "1000" );
		mongoDB.ReadOne("couleur_produit", "=", "Blue", false, null, null, null );
		mongoDB.DeleteBetween("id_fournisseur", "2000", "4000");
		mongoDB.ReadAll();
		jsonConverter = new CSVtoJSON();
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		try {
			jsonConverter.convertCSVtoJSON(data_filePath_Source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		data_filePath_Json = "DataFiles/data_"+i+".json";
		i++;
		mongoDB.setFilePath(data_filePath_Json);
		mongoDB.Insert(); //1 000
		jsonConverter = new CSVtoJSON();
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		try {
			jsonConverter.convertCSVtoJSON(data_filePath_Source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		data_filePath_Json = "DataFiles/data_"+i+".json";
		i++;
		mongoDB.setFilePath(data_filePath_Json);
		mongoDB.Insert(); //30 000
		mongoDB.ReadAll();
		mongoDB.UpdateWithParameter("ville_client", "Cahors", "ville_client", "Aubiere");
		jsonConverter = new CSVtoJSON();
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		try {
			jsonConverter.convertCSVtoJSON(data_filePath_Source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		data_filePath_Json = "DataFiles/data_"+i+".json";
		i++;
		mongoDB.setFilePath(data_filePath_Json);
		mongoDB.Insert(); //1 000
		mongoDB.ReadOne("id_fournisseur", "=", "10", false, null, null, null );
		jsonConverter = new CSVtoJSON();
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		try {
			jsonConverter.convertCSVtoJSON(data_filePath_Source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		data_filePath_Json = "DataFiles/data_"+i+".json";
		i++;
		mongoDB.setFilePath(data_filePath_Json);
		mongoDB.Insert(); //30 000
		mongoDB.ReadAll();
		mongoDB.DeleteBetween("id_fournisseur", "1", "1000");
		mongoDB.UpdateWithParameter("couleur_produit", "Yellow", "couleur_produit", "Beige");
		jsonConverter = new CSVtoJSON();
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		try {
			jsonConverter.convertCSVtoJSON(data_filePath_Source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		data_filePath_Json = "DataFiles/data_"+i+".json";
		i++;
		mongoDB.setFilePath(data_filePath_Json);
		mongoDB.Insert(); //20 000
		jsonConverter = new CSVtoJSON();
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		try {
			jsonConverter.convertCSVtoJSON(data_filePath_Source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		data_filePath_Json = "DataFiles/data_"+i+".json";
		i++;
		mongoDB.setFilePath(data_filePath_Json);
		mongoDB.Insert(); //1 000
		mongoDB.ReadOne("abonnement_client", "=", "true", true, "prix_produit", ">", "4000" );
		mongoDB.ReadAll();
		jsonConverter = new CSVtoJSON();
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		try {
			jsonConverter.convertCSVtoJSON(data_filePath_Source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		data_filePath_Json = "DataFiles/data_"+i+".json";
		i++;
		mongoDB.setFilePath(data_filePath_Json);
		mongoDB.Insert(); //30 000
		mongoDB.ReadOne("gender_client", "=", "female", false, null, null, null );
		mongoDB.DeleteBetween("prix_produit", "300", "1000");
		jsonConverter = new CSVtoJSON();
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		try {
			jsonConverter.convertCSVtoJSON(data_filePath_Source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		data_filePath_Json = "DataFiles/data_"+i+".json";
		i++;
		mongoDB.setFilePath(data_filePath_Json);
		mongoDB.Insert(); //50 000
		jsonConverter = new CSVtoJSON();
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		try {
			jsonConverter.convertCSVtoJSON(data_filePath_Source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		data_filePath_Json = "DataFiles/data_"+i+".json";
		i++;
		mongoDB.setFilePath(data_filePath_Json);
		mongoDB.Insert(); //20 000
		mongoDB.ReadAll();
		mongoDB.DeleteAll();
		
		mongoDB.Disconnect(); */
		
/*********************************MySQL*************************************/			
	//	MySQL mySQL = new MySQL();
	//	mySQL.setFilePath(data_filePath_Sql);
	//	mySQL.Connect();
	//	
	//	mySQL.CreateTables();	//Gère les clés étrangères
	//	mySQL.Insert();
	//	
	//	mySQL.ReadAll();
	//	mySQL.Update("update Client set abonnement_client=\"true\" where abonnement_client=\"false\"; ");
	//	mySQL.DeleteFournisseur("where id_fournisseur >\"2000\" and id_fournisseur < \"4000\" ");	
	//	mySQL.ReadGenerique("Select count(*) from Client where abonnement_client=\"true\" AND id_client<\"3000\";");
	//	
	//	mySQL.Read();	//Requête en dur dans le code
	////	mySQL.ReadSelectEtoile("where abonnement_client=\"false\"");		//Uniquement pour un select*
	//	
	//	mySQL.ReadGenerique("Select id_produit from commande where date_commande=\"5/3/2013\" or id_client=\"2\";");
	//	mySQL.ReadGenerique("Select count(*) from Client where abonnement_client=\"true\" AND id_client < \"3000\";");
	//	
	//	mySQL.Update("update Client set ville_client=\"UnVilleAssezLonguePourQueJeLaVoisBienDansLaBase\" where abonnement_client=\"true\"; ");
	//	
	//	mySQL.DeleteClient("where iban_client like \"F%\" ");
	//	mySQL.DeleteFournisseur("where email_fournisseur =\"\" and telephone_fournisseur = \"\" ");		
	//	mySQL.DeleteProduit("where couleur_produit = \"Mauv\"");
	//	mySQL.DeleteCommande("where date_commande like \"%/3/%\" ");
	//	
	//	mySQL.DeleteAll();
	//	mySQL.DropTable();
	//	mySQL.Disconnect();
	
		
/***********************************NEO4J***************************************/			
	/*int i = 1;
	Neo4j neo4j = new Neo4j();
	neo4j.Connect();
	String data_filePath_Source = "DataFiles/data_"+i+".csv";
	CSVtoNEO neoConverter = new CSVtoNEO();
	try {
		neoConverter.execute(data_filePath_Source);
	} catch (Exception e) {
		e.printStackTrace();
	}
	String data_filePath_Neo = "DataFiles/data_"+i+".txt";
	neo4j.setFilePath(data_filePath_Neo);
	i++;
	neo4j.Insert(); //1000
	neo4j.ReadOne("Client","abonnement_client", "=", "false", false,null, null,null, null);
	neo4j.ReadOne("Client","abonnement_client", "=", "false", true,"Produit", "couleur_produit", "=", "Mauv");
	data_filePath_Source = "DataFiles/data_"+i+".csv";
	neoConverter = new CSVtoNEO();
	try {
		neoConverter.execute(data_filePath_Source);
	} catch (Exception e) {
		e.printStackTrace();
	}
	data_filePath_Neo = "DataFiles/data_"+i+".txt";
	neo4j.setFilePath(data_filePath_Neo);
	i++;
	neo4j.Insert(); //10000
	neo4j.UpdateWithParameter("Fournisseur","devise_fournisseur","EUR", "telephone_fournisseur", "+33");
	neo4j.DeleteOne("Client", "gender_client", "male");
	data_filePath_Source = "DataFiles/data_"+i+".csv";
	neoConverter = new CSVtoNEO();
	try {
		neoConverter.execute(data_filePath_Source);
	} catch (Exception e) {
		e.printStackTrace();
	}
	data_filePath_Neo = "DataFiles/data_"+i+".txt";
	neo4j.setFilePath(data_filePath_Neo);
	i++;
	neo4j.Insert(); //50000
	neo4j.ReadAll();
	data_filePath_Source = "DataFiles/data_"+i+".csv";
	neoConverter = new CSVtoNEO();
	try {
		neoConverter.execute(data_filePath_Source);
	} catch (Exception e) {
		e.printStackTrace();
	}
	data_filePath_Neo = "DataFiles/data_"+i+".txt";
	neo4j.setFilePath(data_filePath_Neo);
	i++;
	neo4j.Insert(); //20000
	neo4j.UpdateWithParameter("Client","abonnement_client","true", "abonnement_client", "false");
	neo4j.ReadOne("Fournisseur","devise_fournisseur", "=", "DOLL", true,"Produit", "prix_produit", ">", "1000");
	neo4j.ReadOne("Produit","couleur_produit", "=", "Blue", false,null, null, null, null);
	neo4j.DeleteBetween("Fournisseur", "id_fournisseur", "2000", "4000");
	neo4j.ReadAll();
	data_filePath_Source = "DataFiles/data_"+i+".csv";
	neoConverter = new CSVtoNEO();
	try {
		neoConverter.execute(data_filePath_Source);
	} catch (Exception e) {
		e.printStackTrace();
	}
	data_filePath_Neo = "DataFiles/data_"+i+".txt";
	neo4j.setFilePath(data_filePath_Neo);
	i++;
	neo4j.Insert(); //1000
	data_filePath_Source = "DataFiles/data_"+i+".csv";
	neoConverter = new CSVtoNEO();
	try {
		neoConverter.execute(data_filePath_Source);
	} catch (Exception e) {
		e.printStackTrace();
	}
	data_filePath_Neo = "DataFiles/data_"+i+".txt";
	neo4j.setFilePath(data_filePath_Neo);
	i++;
	neo4j.Insert(); //30000
	neo4j.ReadAll();
	neo4j.UpdateWithParameter("Client","ville_client","Cahors", "ville_client", "Aubiere");
	data_filePath_Source = "DataFiles/data_"+i+".csv";
	neoConverter = new CSVtoNEO();
	try {
		neoConverter.execute(data_filePath_Source);
	} catch (Exception e) {
		e.printStackTrace();
	}
	data_filePath_Neo = "DataFiles/data_"+i+".txt";
	neo4j.setFilePath(data_filePath_Neo);
	i++;
	neo4j.Insert(); //1000
	neo4j.ReadOne("Fournisseur","id_fournisseur", "=", "10", false,null, null, null, null);
	data_filePath_Source = "DataFiles/data_"+i+".csv";
	neoConverter = new CSVtoNEO();
	try {
		neoConverter.execute(data_filePath_Source);
	} catch (Exception e) {
		e.printStackTrace();
	}
	data_filePath_Neo = "DataFiles/data_"+i+".txt";
	neo4j.setFilePath(data_filePath_Neo);
	i++;
	neo4j.Insert(); //30000
	neo4j.ReadAll();
	neo4j.DeleteBetween("Fournisseur", "id_fournisseur", "1", "1000");
	neo4j.UpdateWithParameter("Produit","couleur_produit","Yellow", "couleur_produit", "Beige");
	data_filePath_Source = "DataFiles/data_"+i+".csv";
	neoConverter = new CSVtoNEO();
	try {
		neoConverter.execute(data_filePath_Source);
	} catch (Exception e) {
		e.printStackTrace();
	}
	data_filePath_Neo = "DataFiles/data_"+i+".txt";
	neo4j.setFilePath(data_filePath_Neo);
	i++;
	neo4j.Insert(); //20000
	data_filePath_Source = "DataFiles/data_"+i+".csv";
	neoConverter = new CSVtoNEO();
	try {
		neoConverter.execute(data_filePath_Source);
	} catch (Exception e) {
		e.printStackTrace();
	}
	data_filePath_Neo = "DataFiles/data_"+i+".txt";
	neo4j.setFilePath(data_filePath_Neo);
	i++;
	neo4j.Insert(); //1000
	neo4j.ReadOne("Client","abonnement_client", "=", "true", true,"Produit", "prix_produit", ">", "4000");
	neo4j.ReadAll();
	data_filePath_Source = "DataFiles/data_"+i+".csv";
	neoConverter = new CSVtoNEO();
	try {
		neoConverter.execute(data_filePath_Source);
	} catch (Exception e) {
		e.printStackTrace();
	}
	data_filePath_Neo = "DataFiles/data_"+i+".txt";
	neo4j.setFilePath(data_filePath_Neo);
	i++;
	neo4j.Insert(); //30000
	neo4j.ReadOne("Client","gender_client", "=", "female", false,null, null, null, null);
	neo4j.DeleteBetween("Produit", "prix_produit", "300", "1000");
	data_filePath_Source = "DataFiles/data_"+i+".csv";
	neoConverter = new CSVtoNEO();
	try {
		neoConverter.execute(data_filePath_Source);
	} catch (Exception e) {
		e.printStackTrace();
	}
	data_filePath_Neo = "DataFiles/data_"+i+".txt";
	neo4j.setFilePath(data_filePath_Neo);
	i++;
	neo4j.Insert(); //50000
	data_filePath_Source = "DataFiles/data_"+i+".csv";
	neoConverter = new CSVtoNEO();
	try {
		neoConverter.execute(data_filePath_Source);
	} catch (Exception e) {
		e.printStackTrace();
	}
	data_filePath_Neo = "DataFiles/data_"+i+".txt";
	neo4j.setFilePath(data_filePath_Neo);
	i++;
	neo4j.Insert(); //20000
	neo4j.ReadAll();
	neo4j.DeleteAll();
	neo4j.Disconnect();*/
	
/*********************************OracleNoSQL*************************************/
		/*int i = 1;
		OracleNoSQL oracleNoSQL = new OracleNoSQL();
		oracleNoSQL.Connect();
		oracleNoSQL.CreateTable();
		String data_filePath_Source = "DataFiles/data_"+i+".csv";
		oracleNoSQL.setFilePath(data_filePath_Source);
		i++;
		oracleNoSQL.Insert(); //1000
		oracleNoSQL.ReadOne("abonnement_client", "=", "false", false, null,null, null );
		oracleNoSQL.ReadOne("abonnement_client", "=", "false", true, "couleur_produit", "=", "Mauv" );
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		oracleNoSQL.setFilePath(data_filePath_Source);
		i++;
		oracleNoSQL.Insert(); //10000
		oracleNoSQL.UpdateWithParameter("devise_fournisseur","EUR", "telephone_fournisseur", "+33");
		oracleNoSQL.DeleteOne("gender_client", "male");
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		oracleNoSQL.setFilePath(data_filePath_Source);
		i++;
		oracleNoSQL.Insert(); //50000
		oracleNoSQL.readAll();
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		oracleNoSQL.setFilePath(data_filePath_Source);
		i++;
		oracleNoSQL.Insert(); //20000
		oracleNoSQL.UpdateWithParameter("abonnement_client","true", "abonnement_client", "false");
		oracleNoSQL.ReadOne("devise_fournisseur", "=", "DOLL", true, "prix_produit", ">", "1000" );
		oracleNoSQL.ReadOne("couleur_produit", "=", "Blue", false, null,null, null );
		oracleNoSQL.DeleteBetween("id_fournisseur", "2000", "4000");
		oracleNoSQL.readAll();
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		oracleNoSQL.setFilePath(data_filePath_Source);
		i++;
		oracleNoSQL.Insert(); //1000
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		oracleNoSQL.setFilePath(data_filePath_Source);
		i++;
		oracleNoSQL.Insert(); //30000
		oracleNoSQL.readAll();
		oracleNoSQL.UpdateWithParameter("ville_client","Cahors", "ville_client", "Aubiere");
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		oracleNoSQL.setFilePath(data_filePath_Source);
		i++;
		oracleNoSQL.Insert(); //1000
		oracleNoSQL.ReadOne("id_fournisseur", "=", "10", false, null,null, null );
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		oracleNoSQL.setFilePath(data_filePath_Source);
		i++;
		oracleNoSQL.Insert(); //30000
		oracleNoSQL.readAll();
		oracleNoSQL.DeleteBetween("id_fournisseur", "1", "1000");
		oracleNoSQL.UpdateWithParameter("couleur_produit","Yellow", "couleur_produit", "Beige");
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		oracleNoSQL.setFilePath(data_filePath_Source);
		i++;
		oracleNoSQL.Insert(); //20000
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		oracleNoSQL.setFilePath(data_filePath_Source);
		i++;
		oracleNoSQL.Insert(); //1000
		oracleNoSQL.ReadOne("abonnement_client", "=", "true", true, "prix_produit", ">", "4000" );
		oracleNoSQL.readAll();
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		oracleNoSQL.setFilePath(data_filePath_Source);
		i++;
		oracleNoSQL.Insert(); //30000
		oracleNoSQL.ReadOne("gender_client", "=", "female", false, null,null, null );
		oracleNoSQL.DeleteBetween("prix_produit", "300", "1000");
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		oracleNoSQL.setFilePath(data_filePath_Source);
		i++;
		oracleNoSQL.Insert(); //50000
		data_filePath_Source = "DataFiles/data_"+i+".csv";
		oracleNoSQL.setFilePath(data_filePath_Source);
		i++;
		oracleNoSQL.Insert(); //20000
		oracleNoSQL.readAll();
		oracleNoSQL.DeleteAll();		
		oracleNoSQL.Disconnect();*/
	}	

}
