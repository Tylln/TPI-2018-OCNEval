package ch.ocn.OCNEval.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ch.ocn.OCNEval.data.Person;
import ch.ocn.OCNEval.data.Profile;
import ch.ocn.OCNEval.data.Section;

public class SqlRequest {
	
	//Déclaration des informations de connexion
	private static String url = "jdbc:mysql://localhost:3306/ocneval?useSSL=false";
	private static String user = "java";
	private static String password = "123";
	
	//Constructeur par défaut
	public SqlRequest() {
		
	}
	
	//STAFF --------------------------------------------------------------------------------------
	
	//Méthode permettant de récupérer la liste du personnel
	public static List<Person> requestStaff(String request) throws SQLException {
		List<Person> staff = new ArrayList<Person>(); //Création d'une liste d'objets Person
		
		//Chargement du driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
			
		try (
			//Connexion à la base de données
			Connection connection = DriverManager.getConnection(url, user, password);
			//Préparation de la requête passée en paramètre
			PreparedStatement preparedStatement = connection.prepareStatement(request);
			//Exécution de la requête et récupération du résultat dans un ResultSet
			ResultSet resultSet = preparedStatement.executeQuery();
		){
			//Pour chaque entrée du ResultSet, on récupère les informations pour en créer un objet Person et l'ajouter à la liste
			while (resultSet.next()) {
				staff.add(new Person(resultSet.getInt("id"), resultSet.getString("firstname"), resultSet.getString("name"), resultSet.getString("sector"), resultSet.getString("responsable"), resultSet.getString("function_ocn"), resultSet.getString("date_function"), resultSet.getString("language"), resultSet.getString("validity_date")));
			}
		}
			
		return staff; //Retourne la liste du personnel
	}
	
	//Méthode permettant d'ajouter le personnel
	public static void saveStaff(Person staff[]) throws SQLException {
		
		//Création d'une variable d'index
		int i = 0;
		
		//Chargement du driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
		
		try (
				//Connexion à la base de données
				Connection connection = DriverManager.getConnection(url, user, password);
				//Préparation de la requête permettant de supprimer la table (uniquement pour la phase de développement)
				PreparedStatement preparedStatementTruncate = connection.prepareStatement("TRUNCATE TABLE person;");
			){
				//Suppression des données de la table (uniquement pour la phase de développement)
				preparedStatementTruncate.executeUpdate();
				//Pour chaque entrée du tableau staff
				for (i = 0; i < staff.length; i++) {
					if (staff[i].getValidityDate() != null) { //Si la date de validité n'est pas nulle
						staff[i].setValidityDate("'" + staff[i].getValidityDate() + "'"); //On ajoute des guillemets afin d'avoir une requête SQL valide
					}
					//Preparation de la requête SQL
					PreparedStatement preparedStatementSave = connection.prepareStatement("INSERT INTO person VALUES (" + staff[i].getId() + ", '" + staff[i].getFirstname() + "', '" + staff[i].getName() + "', '" + staff[i].getSector() + "', '" + staff[i].getResponsable() + "', '" + staff[i].getFunction() + "', '" + staff[i].getDateFunction() + "', '" + staff[i].getLanguage() + "', " + staff[i].getValidityDate() + ");");
					//Exécution de la requête permettant d'ajouter le personnel
					preparedStatementSave.executeUpdate();
				}
		}
	}
	
	//Méthode permettant de récupérer une personne en particulier
	public static Person requestPerson(String request) throws SQLException {
		//Instanciation d'un objet Person
		Person person = null;
			
		//Chargement du driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
			
		try (
			//Connexion à la base de données
			Connection connection = DriverManager.getConnection(url, user, password);
			//Préparation de la requête
			PreparedStatement preparedStatementProfile = connection.prepareStatement(request);
			//Exécution de la requête et récupération du résultat dans un ResultSet
			ResultSet resultSet = preparedStatementProfile.executeQuery();
		){
			//Permet de créer un objet Person avec les informations récupérées
			if (resultSet.next()) {
				person = new Person(resultSet.getInt("id"), resultSet.getString("firstname"), resultSet.getString("name"), resultSet.getString("sector"), resultSet.getString("responsable"), resultSet.getString("function_ocn"), resultSet.getString("date_function"), resultSet.getString("language"), resultSet.getString("validity_date"));
			}
		}
			
		return person; //Retourne la personne
	}
	
	
	
	
	
	
	//Profils ------------------------------------------------------------------------------------
	
	//Code récupéré en partie sur https://stackoverflow.com/questions/14853508/returning-a-resultset
	//Méthode permettant de récupérer la liste des profils 
	public static List<Profile> requestProfiles(String request) throws SQLException {
		//Création d'une liste de profils
		List<Profile> profiles = new ArrayList<Profile>();
		//Création d'une liste des sections
		List<Section> sections = new ArrayList<Section>();
		
		//Chargement du driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
			
		try (
			//Connexion à la base de données
			Connection connection = DriverManager.getConnection(url, user, password);
			//Préparation de la requête
			PreparedStatement preparedStatement = connection.prepareStatement(request);
			//Exécution de la requête et récupération du résultat dans un ResultSet
			ResultSet resultSet = preparedStatement.executeQuery();
		){
			//Pour chaque entrée du ResultSet, on récupère les informations pour en créer un objet Profile et l'ajouter à la liste
			while (resultSet.next()) {
				profiles.add(new Profile(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description"), resultSet.getString("validity_date"), sections));
			}
		}
			
		return profiles; //Retourne la liste des profils
	}
	
	//Méthode permettant d'ajouter un profil
	public static void addProfile(String request) throws SQLException {
		//Chargement du driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
		
		try (
			//Connexion à la base de données
			Connection connection = DriverManager.getConnection(url, user, password);
			//Préparation de la requête
			PreparedStatement preparedStatement = connection.prepareStatement(request);	
		){
			//Exécution de la requête
			preparedStatement.executeUpdate();
		}
	}
	
	//Méthode permettant de récupérer un profil en particulier
	public static Profile requestProfile(String requestProfile, String requestSections) throws SQLException {
		Profile profile = null; //Création d'un objet Profile
		List<Section> sections = new ArrayList<Section>(); //Création d'une liste d'objets Section
			
		//Chargement du driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
			
		try (
			//Connexion à la base de données
			Connection connection = DriverManager.getConnection(url, user, password);
			//Préparation de la requête permettant de récupérer le profil
			PreparedStatement preparedStatementProfile = connection.prepareStatement(requestProfile);
			//Exécution de la requête et récupération du résultat dans un ResultSet
			ResultSet resultSetProfile = preparedStatementProfile.executeQuery();
			//Préparation de la requête permettant de récupérer les sections liées au profil
			PreparedStatement preparedStatementSections = connection.prepareStatement(requestSections);
			//Exécution de la requête et récupération du résultat dans un ResultSet
			ResultSet resultSetSections = preparedStatementSections.executeQuery();
		){
			//Pour chaque entrée du ResultSet, on récupère les informations pour en créer un objet Section et l'ajouter à la liste
			while (resultSetSections.next()) {
				sections.add(new Section(resultSetSections.getInt("id"), resultSetSections.getString("name"), resultSetSections.getString("description"), resultSetSections.getString("path"), resultSetSections.getString("validity_date")));
			}
			
			//Permet de créer un objet Profile avec les informations récupérées
			if (resultSetProfile.next()) {
				profile = new Profile(resultSetProfile.getInt("id"), resultSetProfile.getString("name"), resultSetProfile.getString("description"), resultSetProfile.getString("validity_date"), sections);
			}
		}
			
		return profile; //Retourne le profil
	}
	
	//Méthode permettant de modifier un profil
	public static void modifyProfile(String request) throws SQLException {
		
		//Chargement du driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
		
		try (
			//Connexion à la base de données
			Connection connection = DriverManager.getConnection(url, user, password);
			//Préparation de la requête
			PreparedStatement preparedStatement = connection.prepareStatement(request);	
		){
			//Exécution de la requête
			preparedStatement.executeUpdate();
		}
	}
	
	//Méthode permettant de supprimer un profil
	public static void deleteProfile(String request) throws SQLException {
		
		//Chargement du driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
			
		try (
			//Connexion à la base de données
			Connection connection = DriverManager.getConnection(url, user, password);
			//Préparation de la requête
			PreparedStatement preparedStatement = connection.prepareStatement(request);
		){
			//Exécution de la requête
			preparedStatement.executeUpdate();
		}
	}
	
	//Méthode permettant de récupérer l'id d'un profil
	public static int requestIdProfile(String request) throws SQLException {
		int id = 0; //Création d'une variable stockant l'id
		
		//Chargement du driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
		
		try (	
			//Connexion à la base de données
			Connection connection = DriverManager.getConnection(url, user, password);
			//Préparation de la requête
			PreparedStatement preparedStatement = connection.prepareStatement(request);
			//Exécution de la requête et récupération du résultat dans un ResultSet
			ResultSet resultSet = preparedStatement.executeQuery();
		){
			//Permet de récupérer la valeur de l'id et de la stocker dans une variable
			if (resultSet.next()) {
			id = resultSet.getInt("id");
			}
		}
		
		return id; //Retourne l'id du profil
	}
	
	
	
	
	
	////Sections -----------------------------------------------------------------------------------
	
	//Méthode permettant de récupérer la liste des sections
	public static List<Section> requestSections(String request) throws SQLException {
		List<Section> sections = new ArrayList<Section>(); //Création d'une liste d'objets Section
		
		//Chargement du driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
			
		try (	
			//Connexion à la base de données
			Connection connection = DriverManager.getConnection(url, user, password);
			//Préparation de la requête
			PreparedStatement preparedStatement = connection.prepareStatement(request);
			//Exécution de la requête et récupération du résultat dans un ResultSet
			ResultSet resultSet = preparedStatement.executeQuery();
		){
			//Pour chaque entrée du ResultSet, on récupère les informations pour en créer un objet Section et l'ajouter à la liste
			while (resultSet.next()) {
				sections.add(new Section(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description"), resultSet.getString("path"), resultSet.getString("validity_date")));
			}
		}
			
		return sections; //Retourne la liste des sections
	}
	
	//Méthode permettant de récupérer une section en particulier
	public static Section requestSection(String request) throws SQLException {
		Section section = null; //Création d'un objet Section
		
		//Chargement du driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
		
		try (	
			//Connexion à la base de données
			Connection connection = DriverManager.getConnection(url, user, password);
			//Préparation de la requête
			PreparedStatement preparedStatement = connection.prepareStatement(request);
			//Exécution de la requête et récupération du résultat dans un ResultSet
			ResultSet resultSet = preparedStatement.executeQuery();
			){
			//Permet de créer un objet Section avec les informations récupérées
			if (resultSet.next()) {
				section = new Section(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description"), resultSet.getString("path"), resultSet.getString("validity_date"));
			}
		}
		
		return section;
	}
	
	//Méthode permettant d'ajouter une section
	public static void addSection(String request) throws SQLException {
		
		//Chargement du driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
		
		try (
			//Connexion à la base de données
			Connection connection = DriverManager.getConnection(url, user, password);
			//Préparation de la requête
			PreparedStatement preparedStatement = connection.prepareStatement(request);	
		){
			//Exécution de la requête
			preparedStatement.executeUpdate();
		}
	}
	
	//méthode permettant de modifier une section
	public static void modifySection(String request) throws SQLException {
		
		//Chargement du driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
		
		try (
			//Connexion à la base de données
			Connection connection = DriverManager.getConnection(url, user, password);
			//Préparation de la requête
			PreparedStatement preparedStatement = connection.prepareStatement(request);	
		){
			//Exécution de la requête
			preparedStatement.executeUpdate();
		}
	}
	
	//Méthode permettant de supprimer une section
	public static void deleteSection(String request) throws SQLException {
		
		//Chargement du driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
			
		try (
			//Connexion à la base de données
			Connection connection = DriverManager.getConnection(url, user, password);
			//Préparation de la requête
			PreparedStatement preparedStatement = connection.prepareStatement(request);
		){
			//Exécution de la requête
			preparedStatement.executeUpdate();
		}
	}
	
	//Méthode permettant de lier une section à un profil
	public static void bindSectionToProfile(String request) throws SQLException {
		
		//Chargement du driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
			
		try (
			//Connexion à la base de données
			Connection connection = DriverManager.getConnection(url, user, password);
			//Préparation de la requête
			PreparedStatement preparedStatement = connection.prepareStatement(request);
		){
			//Exécution de la requête
			preparedStatement.executeUpdate();
		}
	}
	
	//Méthode permettant d'enlever une section à un profil
	public static void unbindSectionFromProfile(String request) throws SQLException {
		
		//Chargement du driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
			
		try (
			//Connexion à la base de données
			Connection connection = DriverManager.getConnection(url, user, password);
			//Préparation de la requête
			PreparedStatement preparedStatement = connection.prepareStatement(request);
		){
			//Exécution de la requête
			preparedStatement.executeUpdate();
		}
	}
}
