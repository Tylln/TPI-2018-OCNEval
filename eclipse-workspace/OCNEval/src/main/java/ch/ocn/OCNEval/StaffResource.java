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

@Path("staff")
public class StaffResource {

	public StaffResource()  {
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStaff() throws SQLException {
		String request = "SELECT * FROM person";
		
		return Response.status(Response.Status.OK).entity(SqlRequest.requestStaff(request)).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveStaff(String staffJson) throws Exception {
		//https://stackoverflow.com/questions/9598707/gson-throwing-expected-begin-object-but-was-begin-array/9598988#9598988
		Gson gson = new Gson();
		Person[] staff = gson.fromJson(staffJson, Person[].class);
		SqlRequest.saveStaff(staff);
		
		return Response.status(200).build();
	}
}
