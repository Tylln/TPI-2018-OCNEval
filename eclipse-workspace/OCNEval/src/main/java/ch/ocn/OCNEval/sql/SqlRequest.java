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
	
	private static String url = "jdbc:mysql://localhost:3306/ocneval?useSSL=false";
	private static String user = "java";
	private static String password = "123";
	
	public SqlRequest() {
		
	}
	
	public static void saveStaff(Person staff[]) throws SQLException {
		
		int i = 0;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
		
		try (
				Connection connection = DriverManager.getConnection(url, user, password);
				PreparedStatement preparedStatementTruncate = connection.prepareStatement("TRUNCATE TABLE person;");
			){
				preparedStatementTruncate.executeUpdate();
				for (i = 0; i < staff.length; i++) {
					PreparedStatement preparedStatementSave = connection.prepareStatement("INSERT INTO person VALUES (" + staff[i].getId() + ", '" + staff[i].getFirstname() + "', '" + staff[i].getName() + "', '" + staff[i].getSector() + "', '" + staff[i].getResponsable() + "', '" + staff[i].getFunction() + "', '" + staff[i].getDateFunction() + "', '" + staff[i].getLanguage() + "', " + staff[i].getValidityDate() + ");");
					preparedStatementSave.executeUpdate();
				}
		}
	}
	
	//Code récupéré en partie sur https://stackoverflow.com/questions/14853508/returning-a-resultset
	public static List<Profile> requestProfiles(String request) throws SQLException {
		List<Profile> profiles = new ArrayList<Profile>();
		List<Section> sections = new ArrayList<Section>();
			
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
			
		try (	
			Connection connection = DriverManager.getConnection(url, user, password);
			PreparedStatement preparedStatement = connection.prepareStatement(request);
			ResultSet resultSet = preparedStatement.executeQuery();
		){
			while (resultSet.next()) {
				profiles.add(new Profile(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description"), resultSet.getString("validity_date"), sections));
			}
		}
			
		return profiles;
	}
		
	public static Profile requestProfile(String requestProfile, String requestSections) throws SQLException {
		Profile profile = null;
		List<Section> sections = new ArrayList<Section>();
			
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
			
		try (
			Connection connection = DriverManager.getConnection(url, user, password);
			PreparedStatement preparedStatementProfile = connection.prepareStatement(requestProfile);
			ResultSet resultSetProfile = preparedStatementProfile.executeQuery();
			PreparedStatement preparedStatementSections = connection.prepareStatement(requestSections);
			ResultSet resultSetSections = preparedStatementSections.executeQuery();
		){
			while (resultSetSections.next()) {
				sections.add(new Section(resultSetSections.getInt("id"), resultSetSections.getString("name"), resultSetSections.getString("description"), resultSetSections.getString("path"), resultSetSections.getString("validity_date")));
			}
			
			if (resultSetProfile.next()) {
				profile = new Profile(1, resultSetProfile.getString("name"), resultSetProfile.getString("description"), resultSetProfile.getString("validity_date"), sections);
			}
		}
			
		return profile;
	}
	
	public static void modifiyProfile(String request) throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
		
		try (
			Connection connection = DriverManager.getConnection(url, user, password);
			PreparedStatement preparedStatement = connection.prepareStatement(request);	
		){
			preparedStatement.executeUpdate();
		}
	}
	
	public static void deleteProfile(String request) throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
			
		try (
			Connection connection = DriverManager.getConnection(url, user, password);
			PreparedStatement preparedStatement = connection.prepareStatement(request);
		){
			preparedStatement.executeUpdate();
		}
	}
	
	public static List<Section> requestSections(String request) throws SQLException {
		List<Section> sections = new ArrayList<Section>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {}
			
		try (	
			Connection connection = DriverManager.getConnection(url, user, password);
			PreparedStatement preparedStatement = connection.prepareStatement(request);
			ResultSet resultSet = preparedStatement.executeQuery();
		){
			while (resultSet.next()) {
				sections.add(new Section(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("description"), resultSet.getString("path"), resultSet.getString("validity_date")));
			}
		}
			
		return sections;
	}
}
