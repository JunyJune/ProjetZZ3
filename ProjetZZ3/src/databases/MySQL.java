package databases;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQL {
	
	public String filePath;

	private String url = "jdbc:mysql://localhost/projetzz3?useSSL=false";
	private String login = "root";
	private String password = "isima";
	private Connection connection = null;
	
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
	
	/******************************************************************************************/
	/****************************************Fonctions*****************************************/
	/******************************************************************************************/
	
	public String toString(){
		String name = "MySQL";
		return name;
	}

	/******************************************************************************************/
	/****************************************Connexion*****************************************/
	/******************************************************************************************/
	
	public void Connect(){
		System.out.println("Connexion à la base de données MySQL\n");
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, login, password);
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		//Creation du fichier de resultat
		wb = new HSSFWorkbook();
		sheet = wb.createSheet("FeuilleMySQL");
		nbRow = 0;
	}
	
	/******************************************************************************************/
	/*************************************Création tables**************************************/
	/******************************************************************************************/
	
	public void CreateTables(){
		System.out.println("Création de table dans la base de données MySQL\n");
		
		String query1 = "create table if not exists Client ("
				+ "id_client int primary key,"
				+ "ville_client varchar(255),"
				+ "prenom_client varchar(255),"
				+ "nom_client varchar(255),"
			 	+ "email_client varchar(255),"
				+ "gender_client varchar(255),"
				+ "telephone_client varchar(255),"
				+ "iban_client varchar(255),"
				+ "abonnement_client varchar(255)"
				+ ");"
				;
		
		String query2 = "create table if not exists Fournisseur ("
				+ "id_fournisseur int primary key,"
				+ "ville_fournisseur varchar(255),"
				+ "nom_fournisseur varchar(255),"
				+ "slogan_fournisseur varchar(255),"
			 	+ "devise_fournisseur varchar(255),"
				+ "email_fournisseur varchar(255),"
				+ "iban_fournisseur varchar(255),"
				+ "telephone_fournisseur varchar(255)"
				+ ");"
				;
		
		String query3 = "create table if not exists Produit ("
				+ "id_produit int primary key,"
				+ "id_fournisseur int,"
				+ "foreign key (id_fournisseur) references Fournisseur(id_fournisseur),"
				+ "couleur_produit varchar(255),"
				+ "prix_produit varchar(255),"
			 	+ "label_produit varchar(255)"
				+ ");"
				;
		
		String query4 = "create table if not exists Commande ("
				+ "id_commande int primary key,"
				+ "id_produit int,"
				+ "foreign key (id_produit) references Produit(id_produit),"
				+ "id_client int,"
				+ "foreign key (id_client) references Client(id_client),"
				+ "date_commande varchar(255)"
				+ ");"
				;
		try {
			PreparedStatement preparedStatement1 = (PreparedStatement) connection.prepareStatement(query1);
			preparedStatement1.execute();
			PreparedStatement preparedStatement2 = (PreparedStatement) connection.prepareStatement(query2);
			preparedStatement2.execute();
			PreparedStatement preparedStatement3 = (PreparedStatement) connection.prepareStatement(query3);
			preparedStatement3.execute();
			PreparedStatement preparedStatement4 = (PreparedStatement) connection.prepareStatement(query4);
			preparedStatement4.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/******************************************************************************************/
	/****************************************Insertion*****************************************/
	/******************************************************************************************/
	
	public void Insert(){
		System.out.println("Insertion dans la base de données MySQL\n");
		long startTime = System.currentTimeMillis();
		
		try {
			InputStream inputStream = new FileInputStream(filePath);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			String query = "";
			List<String> client_ids = new ArrayList<String>();
			List<String> fournisseur_ids = new ArrayList<String>();
			List<String> produit_ids = new ArrayList<String>();
			List<String> commande_ids = new ArrayList<String>();
			client_ids.clear();
			fournisseur_ids.clear();
			produit_ids.clear();
			commande_ids.clear();
						
			while ((query = bufferedReader.readLine()) != null) {
				PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query + ";");
				String titlesVSdata[] = query.split(" values ");
				String titles[] = titlesVSdata[0].split(" ");
				String table = titles[2];
				String data[] = titlesVSdata[1].split(",");
				String id = data[0];
				if (!(
						((table.equals("Client"))&&(client_ids.contains(id)))
							||
						((table.equals("Fournisseur"))&&(fournisseur_ids.contains(id)))
							||
						((table.equals("Produit"))&&(produit_ids.contains(id)))
							||
						((table.equals("Commande"))&&(commande_ids.contains(id)))
					 )
				   ) {
					preparedStatement.execute();
					if (table.equals("Client")) {
						client_ids.add(id);						
					}
					else {
						if (table.equals("Fournisseur")) {
							fournisseur_ids.add(id);						
						}
						else{
							if (table.equals("Produit")) {
								produit_ids.add(id);						
							}
							else{
								commande_ids.add(id);						
							}
						}
					}
				}
				bufferedReader.readLine();
				bufferedReader.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Calcul du temps de traitement ici
		long time = System.currentTimeMillis()-startTime;
		
		//CAlcul du nombre de lignes traîtées ici
		int ligneInserees = 0;
		try {
			PreparedStatement select1 = (PreparedStatement) connection.prepareStatement("select count(*) from client;");
			ResultSet clients = select1.executeQuery();
			clients.next();
			ligneInserees += clients.getInt(1);
			PreparedStatement select2 = (PreparedStatement) connection.prepareStatement("select count(*) from fournisseur;");
			ResultSet fournisseurs = select2.executeQuery();
			fournisseurs.next();
			ligneInserees += fournisseurs.getInt(1);
			PreparedStatement select3 = (PreparedStatement) connection.prepareStatement("select count(*) from produit;");
			ResultSet produits = select3.executeQuery();
			produits.next();
			ligneInserees += produits.getInt(1);
			PreparedStatement select4 = (PreparedStatement) connection.prepareStatement("select count(*) from commande;");
			ResultSet commandes = select4.executeQuery();
			commandes.next();
			ligneInserees += commandes.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		 writeResult("Insertion ", ligneInserees , this.toString() , time + " ms"   );

	}
	
	/******************************************************************************************/
	/*****************************************Lecture******************************************/
	/******************************************************************************************/
	
	public void ReadAll(){
		System.out.println("Lecture de la base de données MySQL\n");
		int lignesLues = 0;

		long startTime = System.currentTimeMillis();		
		
		String selectCommande = "select count(*) as commandeRows from projetzz3.Commande;";
		String selectProduit = "select count(*) as produitRows from projetzz3.Produit;";
		String selectFournisseur = "select count(*) as FournisseurRows from projetzz3.Fournisseur;";
		String selectClient = "select count(*) as clientRows from projetzz3.Client;";

		try {
			

			PreparedStatement preparedStatement1 = (PreparedStatement) connection.prepareStatement(selectCommande);
			ResultSet res1 = preparedStatement1.executeQuery();
			res1.next();
			lignesLues += res1.getInt("commandeRows");
			
			PreparedStatement preparedStatement2 = (PreparedStatement) connection.prepareStatement(selectProduit);
			ResultSet res2 = preparedStatement2.executeQuery();
			res2.next();
			lignesLues += res2.getInt("produitRows");
			
			PreparedStatement preparedStatement3 = (PreparedStatement) connection.prepareStatement(selectFournisseur);
			ResultSet res3= preparedStatement3.executeQuery();
			res3.next();
			lignesLues += res3.getInt("FournisseurRows");
			
			PreparedStatement preparedStatement4 = (PreparedStatement) connection.prepareStatement(selectClient);
			ResultSet res4 = preparedStatement4.executeQuery();
			res4.next();
			lignesLues += res4.getInt("clientRows");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Calcul du temps de traitement ici
		long time = System.currentTimeMillis() - startTime;
		
		writeResult("Lecture", lignesLues , this.toString() , time + " ms"   );

	}
	
	public void Read(){
		
		System.out.println("Lecture de la base de données MySQL\n");
		int lignesLues = 0;

		long startTime = System.currentTimeMillis();		
		
		String query1 = "select count(*) as clientRows from projetzz3.Client;";
		String query12 = "select count(*) as clientRows from projetzz3.Client where abonnement_client= \"false\" ;";
		String query2 = "select count(*) as fournisseurRows from Fournisseur;";
		String query22 = "select count(*) as fournisseurRows from Fournisseur where ville_fournisseur=" + " \"Arneiro\" " + ";";
		String query23 = "select count(*) as produitRows from Produit;";
		String query3 = "select count(*) as produitRows from Produit where couleur_produit=" + " \"Mauv\" " + ";";
		String query4 = "select count(*) as commandeRows from Commande;";

		try {
			
//			System.out.println("\n*******************************************************************\n");

			PreparedStatement preparedStatement1 = (PreparedStatement) connection.prepareStatement(query1);
			ResultSet res1 = preparedStatement1.executeQuery();
			res1.next();
			lignesLues += res1.getInt("clientRows");
//			System.out.println("Nombre de ligne dans la table Client : " + res1.getInt("clientRows"));
			
			PreparedStatement preparedStatement12 = (PreparedStatement) connection.prepareStatement(query12);
			ResultSet res12 = preparedStatement12.executeQuery();
			res12.next();
			lignesLues += res12.getInt("clientRows");
//			System.out.println("Nombre de clients sans abonnement : " + res12.getInt("clientRows"));
			
//			System.out.println("\n*******************************************************************\n");
			
			PreparedStatement preparedStatement2 = (PreparedStatement) connection.prepareStatement(query2);
			ResultSet res2 = preparedStatement2.executeQuery();
			res2.next();
			lignesLues += res2.getInt("fournisseurRows");
//			System.out.println("Nombre de ligne dans la table Fournisseur : " + res2.getInt("fournisseurRows"));

			PreparedStatement preparedStatement22 = (PreparedStatement) connection.prepareStatement(query22);
			ResultSet res22 = preparedStatement22.executeQuery();
			res22.next();
			lignesLues += res22.getInt("fournisseurRows");
//			System.out.println("Nombre fournisseurs venant d'Arneiro : " + res22.getInt("fournisseurRows"));

//			System.out.println("\n*******************************************************************\n");

			PreparedStatement preparedStatement23 = (PreparedStatement) connection.prepareStatement(query23);
			ResultSet res23 = preparedStatement23.executeQuery();
			res23.next();
			lignesLues += res23.getInt("produitRows");
//			System.out.println("Nombre de ligne dans la table Produit : " + res23.getInt("produitRows"));
			
			PreparedStatement preparedStatement3 = (PreparedStatement) connection.prepareStatement(query3);
			ResultSet res3 = preparedStatement3.executeQuery();
			res3.next();
			lignesLues += res3.getInt("produitRows");
//			System.out.println("Nombre de produit Mauv : " + res3.getInt("produitRows"));

//			System.out.println("\n*******************************************************************\n");
			
			PreparedStatement preparedStatement4 = (PreparedStatement) connection.prepareStatement(query4);
			ResultSet res4 = preparedStatement4.executeQuery();
			res4.next();
			lignesLues += res4.getInt("commandeRows");
//			System.out.println("Nombre de ligne dans la table Commande : " + res4.getInt("commandeRows"));
			
//			System.out.println("\n*******************************************************************\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Calcul du temps de traitement ici
		long time = System.currentTimeMillis() - startTime;
		
		writeResult("Lecture", lignesLues , this.toString() , time + " ms"   );
	}
	
public void ReadSelectEtoile(String whereClause){
		
		System.out.println("Lecture Select * de la base de données MySQL\n");
		ResultSet res = null;
		int lignesLues = 0;
		String query = "Select * from Client " + whereClause + ";";
		
		//Début du calcul du temps
		long startTime = System.currentTimeMillis();
		
		try {
			PreparedStatement preparedStatement1 = (PreparedStatement) connection.prepareStatement(query);
			res =preparedStatement1.executeQuery();		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Calcul du temps de traitement ici
		long time = System.currentTimeMillis() - startTime;
		
		//Calcul du nombre de lignes traitées ici
		try {
			res.next();
			lignesLues += res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		writeResult("Lecture Select *", lignesLues , this.toString() , time + " ms"   );
	}

	public void ReadGenerique(String query){
		
		System.out.println("Lecture générique de la base de données MySQL\n");
	
		//Début du calcul du temps
		long startTime = System.currentTimeMillis();
		
		try {
			PreparedStatement preparedStatement1 = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement1.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Calcul du temps de traitement ici
		long time = System.currentTimeMillis() - startTime;
		
		//Calcul du nombre de lignes mises à jour
		int lignesLues = 0;
		String selectCount = "";
		
		if (!query.contains("(")) {
			String jenaibesoin = (query.split("where "))[0];
			String table = (jenaibesoin.split("from "))[1];
			String whereClause = "where ";
			for (int i = 1; i < (query.split("where ")).length; i++) {
				whereClause += (query.split("where "))[i];
			}
			selectCount = new String("select count(*) from " + table + whereClause);
		}
		else{
			String attibuts = (query.split("from"))[0];
			selectCount = attibuts;
			for (int i = 1; i < (query.split("from")).length; i++) {
				selectCount += " from " + (query.split("from"))[i];
			}
		}
		
		try {
			PreparedStatement count = (PreparedStatement) connection.prepareStatement(selectCount);
			ResultSet result = count.executeQuery();
			result.next();
			lignesLues = result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		writeResult("Lecture générique", lignesLues , this.toString() , time + " ms"   );
	}

/******************************************************************************************/
/***************************************Mise à jour****************************************/
/******************************************************************************************/

	public void Update(String query){
		System.out.println("Mise à jour dans la base de données MySQL");
	
		//Début du calcul du temps
		long startTime = System.currentTimeMillis();
		
		try {
			PreparedStatement preparedStaement = (PreparedStatement) connection.prepareStatement(query);
			preparedStaement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Calcul du temps de traitement ici
		long time = System.currentTimeMillis()-startTime;
		
		//Calcul du nombre de lignes mises à jour
		int lignesMisesAjour = 0;	
		String jenaibesoin = (query.split("set "))[0];
		String table = (jenaibesoin.split("update "))[1];
		String whereClause = "where " +(query.split("where "))[1];
		String selectCount = new String("SELECT COUNT(*) FROM " + table + whereClause);
		try {
			PreparedStatement count = (PreparedStatement) connection.prepareStatement(selectCount);
			ResultSet result = count.executeQuery();
			result.next();
			lignesMisesAjour = result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		writeResult("Mise à jour ", lignesMisesAjour , this.toString() , time + " ms"   );
	}
	
	/******************************************************************************************/
	/***************************************Suppression****************************************/
	/******************************************************************************************/
	
	public void DeleteAll(){
		System.out.println("Suppression de toutes les données dans la base de données MySQL");
		
		//Calcul du nombre de lignes supprimées
		int lignesSupp = 0;	
		String selectClient = "select * from Client;";
		String selectFournisseur = "select * from Fournisseur;";
		String selectProduit = "select * from Produit;";
		String selectCommande = "select * from Commande;";
		try {
			PreparedStatement countClient = (PreparedStatement) connection.prepareStatement(selectClient);
			ResultSet result1 = countClient.executeQuery();
//			result1.next();
			if(result1.next()){
				lignesSupp += result1.getInt(1);
			}
			
			PreparedStatement countFournisseur = (PreparedStatement) connection.prepareStatement(selectFournisseur);
			ResultSet result2 = countFournisseur.executeQuery();
//			result2.next();
			if (result2.next()) {
				lignesSupp += result2.getInt(1);
			}
			
			PreparedStatement countProduit = (PreparedStatement) connection.prepareStatement(selectProduit);
			ResultSet result3 = countProduit.executeQuery();
//			result3.next();
			if (result3.next()) {
				lignesSupp += result3.getInt(1);
			}
			
			PreparedStatement countCommande = (PreparedStatement) connection.prepareStatement(selectCommande);
			ResultSet result4 = countCommande.executeQuery();
//			result4.next();
			if (result4.next()) {
				lignesSupp += result4.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
					
		long startTime = System.currentTimeMillis();
		
		String query1 = "delete from commande;";
		String query2 = "delete from produit;";
		String query3 = "delete from fournisseur;";
		String query4 = "delete from client;";
		
		try {
			PreparedStatement preparedStatement1 = (PreparedStatement) connection.prepareStatement(query1);
			preparedStatement1.execute();
			PreparedStatement preparedStatement2 = (PreparedStatement) connection.prepareStatement(query2);
			preparedStatement2.execute();
			PreparedStatement preparedStatement3 = (PreparedStatement) connection.prepareStatement(query3);
			preparedStatement3.execute();
			PreparedStatement preparedStatement4 = (PreparedStatement) connection.prepareStatement(query4);
			preparedStatement4.execute();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Calcul du temps de traitement ici
		long time = System.currentTimeMillis()-startTime;
		
		writeResult("Delete", lignesSupp , this.toString() , time + " ms"   );
	}
	
	/******************************************************************************************/
	/***********************************Suppression CLIENT*************************************/
	/******************************************************************************************/
	
	public void DeleteClient(String whereClause){
		System.out.println("Suppression de clients dans la base de données MySQL");
		long startTime = System.currentTimeMillis();		
		String select1 = "select id_client from client " + whereClause + ";";
		ResultSet client_id_client = null;
		
		PreparedStatement preparedStatement1;
		try {
			preparedStatement1 = (PreparedStatement) connection.prepareStatement(select1);
			client_id_client = preparedStatement1.executeQuery();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		//Calcul du nombre de lignes supprimées
		int lignesSupp = 0;
		
		try {
			String selectClient = "select id_client from client " + whereClause + ";";
			while (client_id_client.next()) {
				String selectCommande = "select count(*) from commande where id_client=" + client_id_client.getString(1) + ";";

				PreparedStatement count2 = (PreparedStatement) connection.prepareStatement(selectCommande);
				ResultSet result2 = count2.executeQuery();
				result2.next();
				lignesSupp += result2.getInt(1);
			}
		
			PreparedStatement count = (PreparedStatement) connection.prepareStatement(selectClient);
			ResultSet result = count.executeQuery();
			result.next();
			lignesSupp += result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while (client_id_client.next()) {
				String select2 = "select id_commande from commande where id_client=" + client_id_client.getString(1) + ";";
				PreparedStatement preparedStatement2 = (PreparedStatement) connection.prepareStatement(select2);
				ResultSet commande_id_commande = preparedStatement2.executeQuery();
				while (commande_id_commande.next()) {
					String delete1 = "delete from commande where id_commande=" + commande_id_commande.getString(1) + ";";
					PreparedStatement preparedStatement3 = (PreparedStatement) connection.prepareStatement(delete1);
					preparedStatement3.execute();

					String delete2 = "delete from client where id_client=" + client_id_client.getString(1) + ";";
					PreparedStatement preparedStatement4 = (PreparedStatement) connection.prepareStatement(delete2);
					preparedStatement4.execute();
				}
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//Calcul du temps de traitement ici
		long time = System.currentTimeMillis() - startTime;
		
		
		writeResult("Delete", lignesSupp , this.toString() , time + " ms"   );
	}
	
	/******************************************************************************************/
	/********************************Suppression FOURNISSEUR***********************************/
	/******************************************************************************************/
	
	public void DeleteFournisseur(String whereClause){
		System.out.println("Suppression de fournisseurs dans la base de données MySQL");

		int lignesSupp = 0;
		ResultSet produit_id_produit = null;
		ResultSet fournisseur_id_fournisseur = null;
		PreparedStatement preparedStatement1 = null;
		ResultSet result = null;

		String select1 = "select id_fournisseur from fournisseur " + whereClause + ";";
		
		//Calcul du nombre de lignes supprimées
		try{
			preparedStatement1 = (PreparedStatement) connection.prepareStatement(select1);
			fournisseur_id_fournisseur = preparedStatement1.executeQuery();
			while (fournisseur_id_fournisseur.next()) {
				String select2 = "select id_produit from produit where id_fournisseur=" + fournisseur_id_fournisseur.getString(1) + ";";
				PreparedStatement preparedStatement2 = (PreparedStatement) connection.prepareStatement(select2);
				produit_id_produit = preparedStatement2.executeQuery();
				produit_id_produit.next();

				String selectFournisseur = "select count(*) from fournisseur " + whereClause + ";";
				String selectProduit = "select count(*) from produit where id_fournisseur=" + fournisseur_id_fournisseur.getString(1) + ";";
				String selectCommande = "select count(*) from commande where id_produit=" + produit_id_produit.getString(1) + ";";
			
				PreparedStatement count = (PreparedStatement) connection.prepareStatement(selectFournisseur);
				result = count.executeQuery();
				result.next();

				PreparedStatement count2 = (PreparedStatement) connection.prepareStatement(selectProduit);
				ResultSet result2 = count2.executeQuery();
				result2.next();
				lignesSupp += result2.getInt(1);
				
				PreparedStatement count3 = (PreparedStatement) connection.prepareStatement(selectCommande);
				ResultSet result3 = count3.executeQuery();
				result3.next();
				lignesSupp += result.getInt(1);
				lignesSupp += result3.getInt(1);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		long startTime = System.currentTimeMillis();		

		//Delete
		try {

			preparedStatement1 = (PreparedStatement) connection.prepareStatement(select1);
			fournisseur_id_fournisseur = preparedStatement1.executeQuery();
			while (fournisseur_id_fournisseur.next()) {
				String select2 = "select id_produit from produit where id_fournisseur=" + fournisseur_id_fournisseur.getString(1) + ";";
				PreparedStatement preparedStatement2 = (PreparedStatement) connection.prepareStatement(select2);
				produit_id_produit = preparedStatement2.executeQuery();
				while (produit_id_produit.next()) {
					String delete1 = "delete from commande where id_produit=" + produit_id_produit.getString(1) + ";";
					PreparedStatement preparedStatement3 = (PreparedStatement) connection.prepareStatement(delete1);
					preparedStatement3.execute();
				}
				String delete2 = "delete from produit where id_fournisseur=" + fournisseur_id_fournisseur.getString(1) + ";";
				PreparedStatement preparedStatement4 = (PreparedStatement) connection.prepareStatement(delete2);
				preparedStatement4.execute();
				
				String delete3 = "delete from fournisseur where id_fournisseur=" + fournisseur_id_fournisseur.getString(1) + ";";
				PreparedStatement preparedStatement5 = (PreparedStatement) connection.prepareStatement(delete3);
				preparedStatement5.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Calcul de temps de traitement ici
		long time = System.currentTimeMillis() - startTime;				
		writeResult("Delete", lignesSupp , this.toString() , time + " ms"   );
	}
	
	/******************************************************************************************/
	/***********************************Suppression PRODUIT************************************/
	/******************************************************************************************/
	
	public void DeleteProduit(String whereClause){
		System.out.println("Suppression de produits dans la base de données MySQL");
		ResultSet produit_id_produit = null;
		String query1 = "select id_produit from produit " + whereClause + ";";
		String query2 = "delete from produit " + whereClause + ";";
		
		//Calcul du nombre de lignes supprimées
				int lignesSupp = 0;
				
				try {
					PreparedStatement preparedStatement1 = (PreparedStatement) connection.prepareStatement(query1);
					produit_id_produit = preparedStatement1.executeQuery();
					
					while (produit_id_produit.next()) {
						String query3 = "delete from commande where commande.id_produit=" + produit_id_produit.getString(1) + ";";
						PreparedStatement preparedStatement3 = (PreparedStatement) connection.prepareStatement(query3);
						preparedStatement3.execute();
						
						String selectProduit = "select count(*) from produit " + whereClause + ";";
						String selectCommande = "select count(*) from commande where commande.id_produit=" + produit_id_produit.getString(1) + ";";
	
						PreparedStatement count2 = (PreparedStatement) connection.prepareStatement(selectProduit);
						ResultSet result2 = count2.executeQuery();
						result2.next();
						lignesSupp += result2.getInt(1);
						
						PreparedStatement count3 = (PreparedStatement) connection.prepareStatement(selectCommande);
						ResultSet result3 = count3.executeQuery();
						result3.next();
						lignesSupp += result3.getInt(1);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}		

		long startTime = System.currentTimeMillis();
		try {
			PreparedStatement preparedStatement2 = (PreparedStatement) connection.prepareStatement(query2);
			preparedStatement2.execute();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Calcul du temps de traitement
		long time = System.currentTimeMillis() - startTime;		
		writeResult("Delete", lignesSupp , this.toString() , time + " ms"   );
	}
	
	/******************************************************************************************/
	/***********************************Suppression COMMANDE***********************************/
	/******************************************************************************************/
	
	public void DeleteCommande(String whereClause){
		System.out.println("Suppression de commandes dans la base de données MySQL");
		
		//Calcul du nombre de lignes traitées
		int lignesSupp = 0;
		String selectCommande = "select count(*) from commande " + whereClause + ";";
		try {
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(selectCommande);
			ResultSet res = preparedStatement.executeQuery();
			res.next();
			lignesSupp += res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		long startTime = System.currentTimeMillis();
		String query = "delete from commande " + whereClause + ";";
		try {
			PreparedStatement preparedStatement1 = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement1.execute();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Calcul du temps de traitement
		long time = System.currentTimeMillis() - startTime;		
		writeResult("Delete", lignesSupp , this.toString() , time + " ms"   );
	}
	
	/******************************************************************************************/
	/************************************Suppression Table*************************************/
	/******************************************************************************************/	
	
	public void DropTable(){
		System.out.println("Suppression des tables dans la base de données MySQL");
		
		String query1 = "drop table commande;";
		String query2 = "drop table produit;";
		String query3 = "drop table fournisseur;";
		String query4 = "drop table client;";
		try {
			PreparedStatement preparedStatement1 = (PreparedStatement) connection.prepareStatement(query1);
			preparedStatement1.execute();
			PreparedStatement preparedStatement2 = (PreparedStatement) connection.prepareStatement(query2);
			preparedStatement2.execute();
			PreparedStatement preparedStatement3 = (PreparedStatement) connection.prepareStatement(query3);
			preparedStatement3.execute();
			PreparedStatement preparedStatement4 = (PreparedStatement) connection.prepareStatement(query4);
			preparedStatement4.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/******************************************************************************************/
	/***************************************Déconnexion****************************************/
	/******************************************************************************************/
	
	public void Disconnect(){
		System.out.println("Déconnexion");

		try {
			connection.close();
			
			fileOut = new FileOutputStream("Resultat/ResultatMySQL.xls");
			wb.write(fileOut);
		} catch (SQLException e) {
			e.printStackTrace();
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
			fileOut = new FileOutputStream("Resultat/ResultatMySQL.xls");
			wb.write(fileOut);
		
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
}
