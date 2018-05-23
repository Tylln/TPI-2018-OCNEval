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
	
	//Code récupéré en partie sur https://stackoverflow.com/questions/14853508/returning-a-resultset
	public static List<Profile> requestProfiles(String request) throws SQLException {
		List<Profile> profiles = new ArrayList<Profile>();
		List<Section> sections = new ArrayList<Section>();
		sections.add(new Section(1, "section 1", "description 1", "Path 1", "10.10.1000"));
			
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
				
		}
			
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
		
	public static Profile requestProfile(String request) throws SQLException {
		Profile profile = null;
		List<Section> sections = new ArrayList<Section>();
		sections.add(new Section(1, "section 1", "description 1", "Path 1", "10.10.1000"));
		sections.add(new Section(2, "section 2", "description 2", "Path 2", "19.19.1999"));
			
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
				
		}
			
		try (
			Connection connection = DriverManager.getConnection(url, user, password);
			PreparedStatement preparedStatement = connection.prepareStatement(request);
			ResultSet resultSet = preparedStatement.executeQuery();
		){
			if (resultSet.next()) {
				profile = new Profile(1, resultSet.getString("name"), resultSet.getString("description"), resultSet.getString("validity_date"), sections);
			}
		}
			
		return profile;
	}
	
	public static void saveStaff(Person staff[]) throws SQLException {
		
		int i = 0;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			
		}
		
		try (
				Connection connection = DriverManager.getConnection(url, user, password);
				PreparedStatement preparedStatementTruncate = connection.prepareStatement("TRUNCATE TABLE person;");
			){
				preparedStatementTruncate.executeUpdate();
				for (i = 0; i < staff.length; i++) {
					System.out.println(staff[i].getFirstname());
					System.out.println(staff[i].getDateFunction());
					PreparedStatement preparedStatementSave = connection.prepareStatement("INSERT INTO person VALUES (" + staff[i].getId() + ", '" + staff[i].getFirstname() + "', '" + staff[i].getName() + "', '" + staff[i].getSector() + "', '" + staff[i].getResponsable() + "', '" + staff[i].getFunction() + "', '" + staff[i].getDateFunction() + "', '" + staff[i].getLanguage() + "', " + staff[i].getValidityDate() + ");");
					preparedStatementSave.executeUpdate();
				}
		}
	}
	
	public static void deleteProfile(String request) throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
				
		}
			
		try (
			Connection connection = DriverManager.getConnection(url, user, password);
			PreparedStatement preparedStatement = connection.prepareStatement(request);
		){
			System.out.println(request);
			preparedStatement.executeUpdate();
		}
	}
}
