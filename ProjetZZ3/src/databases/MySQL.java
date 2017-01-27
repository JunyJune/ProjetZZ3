package databases;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQL {
	
	public String filePath;

	private String url = "jdbc:mysql://localhost/projetzz3?useSSL=false";
//	private String port = "33060";
	private String login = "root";
	private String password = "isima";
	private Connection connection = null;
//	private Statement statement = null;
	
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

	
	public void Connect(){
		System.out.println("Connexion à la base de données MySQL");
		long startTime = System.currentTimeMillis();
		
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
		long endTime = System.currentTimeMillis();
		System.out.println("Temps total d'execution de la connexion :"+ (endTime-startTime) +"ms\n");
	}
	
	public void CreateTables(){
		System.out.println("Création de table dans la base de données MySQL");
		long startTime = System.currentTimeMillis();
		
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
				+ "couleur_produit varchar(255),"
				+ "prix_produit varchar(255),"
			 	+ "label_produit varchar(255)"
				+ ");"
				;
		
		String query4 = "create table if not exists Commande ("
				+ "id_commande int primary key,"
				+ "id_produit int,"
				+ "id_client int,"
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
		long endTime = System.currentTimeMillis();
		System.out.println("Temps total d'execution de la création des tables :"+ (endTime-startTime) +"ms\n");
	}
	
	public void Insert(){
		System.out.println("Insertion dans la base de données MySQL");
		long startTime = System.currentTimeMillis();

		int ligneRejetees = 0;
		
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
//				System.out.println(query);
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
				else{
//					System.out.println(id);
//					ligneRejetees++;
					
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
		
		System.out.println("Lignes rejetées : " + ligneRejetees);
		
		long endTime = System.currentTimeMillis();
		System.out.println("Temps total d'execution de l'insertion :"+ (endTime-startTime) +"ms\n");
	}
	
	public void Update(){
		System.out.println("Mise à jour dans la base de données MySQL");
		long startTime = System.currentTimeMillis();
		
		long endTime = System.currentTimeMillis();
		System.out.println("Temps total d'execution de la mise à jour :"+ (endTime-startTime) +"ms\n");
	}
	
	public void Read(){
		
		System.out.println("Lecture de la base de données MySQL");
		long startTime = System.currentTimeMillis();		
		
		String query1 = "select count(*) as clientRows from projetzz3.Client;";
		String query12 = "select count(*) as clientRows from projetzz3.Client where abonnement_client= \"false\" ;";
		String query2 = "select count(*) as fournisseurRows from Fournisseur;";
		String query22 = "select count(*) as fournisseurRows from Fournisseur where ville_fournisseur=" + " \"Arneiro\" " + ";";
		String query23 = "select count(*) as produitRows from Produit;";
		String query3 = "select count(*) as produitRows from Produit where couleur_produit=" + " \"Mauv\" " + ";";
		String query4 = "select count(*) as commandeRows from Commande;";
		
		try {
			
			System.out.println("\n*******************************************************************\n");

			PreparedStatement preparedStatement1 = (PreparedStatement) connection.prepareStatement(query1);
			ResultSet res1 = preparedStatement1.executeQuery();
			res1.next();
			System.out.println("Nombre de ligne dans la table Client : " + res1.getInt("clientRows"));
			
			PreparedStatement preparedStatement12 = (PreparedStatement) connection.prepareStatement(query12);
			ResultSet res12 = preparedStatement12.executeQuery();
			res12.next();
			System.out.println("Nombre de clients sans abonnement : " + res12.getInt("clientRows"));
			
			System.out.println("\n*******************************************************************\n");
			
			PreparedStatement preparedStatement2 = (PreparedStatement) connection.prepareStatement(query2);
			ResultSet res2 = preparedStatement2.executeQuery();
			res2.next();
			System.out.println("Nombre de ligne dans la table Fournisseur : " + res2.getInt("fournisseurRows"));

			PreparedStatement preparedStatement22 = (PreparedStatement) connection.prepareStatement(query22);
			ResultSet res22 = preparedStatement22.executeQuery();
			res22.next();
			System.out.println("Nombre fournisseurs venant d'Arneiro : " + res22.getInt("fournisseurRows"));

			System.out.println("\n*******************************************************************\n");

			PreparedStatement preparedStatement23 = (PreparedStatement) connection.prepareStatement(query23);
			ResultSet res23 = preparedStatement23.executeQuery();
			res23.next();
			System.out.println("Nombre de ligne dans la table Produit : " + res23.getInt("produitRows"));
			
			PreparedStatement preparedStatement3 = (PreparedStatement) connection.prepareStatement(query3);
			ResultSet res3 = preparedStatement3.executeQuery();
			res3.next();
			System.out.println("Nombre de produit Mauv : " + res3.getInt("produitRows"));

			System.out.println("\n*******************************************************************\n");
			
			PreparedStatement preparedStatement4 = (PreparedStatement) connection.prepareStatement(query4);
			ResultSet res4 = preparedStatement4.executeQuery();
			res4.next();
			System.out.println("Nombre de ligne dans la table Commande : " + res4.getInt("commandeRows"));
			
			System.out.println("\n*******************************************************************\n");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("Temps total d'execution de la lecture :"+ (endTime-startTime) +"ms\n");
	}
	
	public void Delete(){
		System.out.println("Suppression dans la base de données MySQL");
		long startTime = System.currentTimeMillis();
		
		String query1 = "delete from client;";
		String query2 = "delete from fournisseur;";
		String query3 = "delete from produit;";
		String query4 = "delete from commande;";
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
		long endTime = System.currentTimeMillis();
		System.out.println("Temps total d'execution de la suppression des données :"+ (endTime-startTime) +"ms\n");
	}
	
	
	public void DeleteClient(){
		System.out.println("Suppression dans la base de données MySQL");
		long startTime = System.currentTimeMillis();
		
		
		try {
			
			final String select1 = "select id_client from client where abonnement_client= \"\" ;";
			PreparedStatement preparedStatement1 = (PreparedStatement) connection.prepareStatement(select1);
			ResultSet client_id_client=  preparedStatement1.executeQuery();
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
		long endTime = System.currentTimeMillis();
		System.out.println("Temps total d'execution de la suppression des données :"+ (endTime-startTime) +"ms\n");
	}
	
	public void DeleteFournisseur(){
		System.out.println("Suppression dans la base de données MySQL");
		long startTime = System.currentTimeMillis();
		try {
			
			String select1 = "select id_fournisseur from fournisseur where ville_fournisseur=" + " \"Arneiro\" " + ";";		
			PreparedStatement preparedStatement1 = (PreparedStatement) connection.prepareStatement(select1);
			ResultSet fournisseur_id_fournisseur = preparedStatement1.executeQuery();
			while (fournisseur_id_fournisseur.next()) {
				String select2 = "select id_produit from produit where id_fournisseur=" + fournisseur_id_fournisseur.getString(1) + ";";
				PreparedStatement preparedStatement2 = (PreparedStatement) connection.prepareStatement(select2);
				ResultSet produit_id_produit = preparedStatement2.executeQuery();
				while (produit_id_produit.next()) {
					String delete1 = "delete from commande where id_produit=" + produit_id_produit.getString(1) + ";";
					PreparedStatement preparedStatement3 = (PreparedStatement) connection.prepareStatement(delete1);
					preparedStatement3.execute();

					String delete2 = "delete from produit where id_fournisseur=" + fournisseur_id_fournisseur.getString(1) + ";";
					PreparedStatement preparedStatement4 = (PreparedStatement) connection.prepareStatement(delete2);
					preparedStatement4.execute();
				}
			}

			String delete3 = "delete from fournisseur where ville_fournisseur=" + " \"Arneiro\" " + ";";
			PreparedStatement preparedStatement5 = (PreparedStatement) connection.prepareStatement(delete3);
			preparedStatement5.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Temps total d'execution de la suppression des données :"+ (endTime-startTime) +"ms\n");
	}
	
	public void DeleteProduit(){
		System.out.println("Suppression dans la base de données MySQL");
		long startTime = System.currentTimeMillis();
		String query1 = "select id_produit from produit where couleur_produit=" + " \"Mauv\" " + ";";
		String query2 = "delete from produit where couleur_produit=" + " \"Mauv\" " + ";";
		try {

			PreparedStatement preparedStatement1 = (PreparedStatement) connection.prepareStatement(query1);
			ResultSet res = preparedStatement1.executeQuery();
			
			while (res.next()) {
//				System.out.println(res.getString(1));
				String query3 = "delete from commande where commande.id_produit=" + res.getString(1) + ";";
				PreparedStatement preparedStatement3 = (PreparedStatement) connection.prepareStatement(query3);
				preparedStatement3.execute();
			}
			
			PreparedStatement preparedStatement2 = (PreparedStatement) connection.prepareStatement(query2);
			preparedStatement2.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Temps total d'execution de la suppression des données :"+ (endTime-startTime) +"ms\n");
	}
	
	public void DeleteCommande(){
		System.out.println("Suppression dans la base de données MySQL");
		long startTime = System.currentTimeMillis();
		String query = "delete from commande;";
		try {
			PreparedStatement preparedStatement1 = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement1.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Temps total d'execution de la suppression des données :"+ (endTime-startTime) +"ms\n");
	}
	
	
	public void DropTable(){
		System.out.println("Suppression des tables dans la base de données MySQL");
		long startTime = System.currentTimeMillis();
		
		String query1 = "drop table client;";
		String query2 = "drop table fournisseur;";
		String query3 = "drop table produit;";
		String query4 = "drop table commande;";
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
		long endTime = System.currentTimeMillis();
		System.out.println("Temps total d'execution de la suppression des tables :"+ (endTime-startTime) +"ms\n");
	}
	
	public void Disconnect(){
		System.out.println("Déconnexion");
		long startTime = System.currentTimeMillis();

		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Temps total d'execution de la déconnexion :"+ (endTime-startTime) +"ms\n");
	}
}
