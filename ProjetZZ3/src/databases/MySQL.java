package databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class MySQL {
	
	public String filePath;

	private String url = "jdbc:mysql://localhost/projetzz3?useSSL=false";
	private String port = "33060";
	private String login = "root";
	private String password = "isima";
	private Connection connection = null;
	private Statement statement = null;
	
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
	}
	
	public void CreateTables(){
		System.out.println("Création de table dans la base de données MySQL");
		
		String query1 = "create table if not exists Client ("
				+ "id_client int primary key auto_increment,"
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
				+ "id_fournisseur int primary key auto_increment,"
				+ "ville_fournisseur varchar(255),"
				+ "nom_fournisseur varchar(255),"
				+ "slogan_fournisseur varchar(255),"
			 	+ "devise_fournisseur varchar(255),"
				+ "email_fournisseur varchar(255),"
				+ "iban_fournisseur varchar(255),"
				+ "telephone_fournisseur varchar(255)"
				+ ");"
				;
		
		String query3 = "create table if not exists Commande ("
				+ "id_produit int primary key auto_increment,"
				+ "id_fournisseur varchar(255),"
				+ "couleur_produit varchar(255),"
				+ "prix_produit varchar(255),"
			 	+ "label_produit varchar(255)"
				+ ");"
				;
		try {
			PreparedStatement preparedStatement1 = (PreparedStatement) connection.prepareStatement(query1);
			preparedStatement1.execute();
			PreparedStatement preparedStatement2 = (PreparedStatement) connection.prepareStatement(query2);
			preparedStatement2.execute();
			PreparedStatement preparedStatement3 = (PreparedStatement) connection.prepareStatement(query3);
			preparedStatement3.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Insert(){
		System.out.println("Insertion dans la base de données MySQL");
	}
	
	public void Update(){
		System.out.println("Mise à jour dans la base de données MySQL");
	}
	
	public void Read(){
		System.out.println("Lecture de la base de données MySQL");
	}
	
	public void Delete(){
		System.out.println("Suppresion dans la base de données MySQL");
	}
	
	public void Disconnect(){
		System.out.println("Déconnexion");
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
