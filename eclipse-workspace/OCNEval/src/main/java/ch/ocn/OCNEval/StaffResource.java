package ch.ocn.OCNEval;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import ch.ocn.OCNEval.data.Person;
import ch.ocn.OCNEval.sql.SqlRequest;

//Si l'URL est webapi/staff, cette classe sera utilisée
@Path("staff")
public class StaffResource {

	//Constructeur par défaut
	public StaffResource()  {
		
	}
	
	/*
	 * Méthode permettant de récupérer la liste du personnel depuis la BDD
	 * Cette méthode sera exécutée en cas de méthode GET
	 * Lorsque l'URL est webapi/staff
	 * Produit un JSON
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStaff() throws SQLException {
		String request = "SELECT * FROM person"; //Création de la requête
		
		//Envoie une réponse HTTP contenant la liste du personnel
		return Response.status(Response.Status.OK).entity(SqlRequest.requestStaff(request)).build();
	}
	
	/*
	 * Méthode permettant d'ajouter la liste du personnel dans la BDD
	 * Cette méthode sera exécutée en cas de méthode POST
	 * Lorsque l'URL est webapi/staff
	 * Consomme un JSON
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveStaff(String staffJson) throws Exception {
		//https://stackoverflow.com/questions/9598707/gson-throwing-expected-begin-object-but-was-begin-array/9598988#9598988
		Gson gson = new Gson(); //Instancie un objet Gson
		Person[] staff = gson.fromJson(staffJson, Person[].class); //Permet de créer une liste d'objet Person avec le JSON
		SqlRequest.saveStaff(staff); //Transmet la requête à la classe JDBC
		
		//Envoie une réponse HTTP
		return Response.status(200).build();
	}
}
