package ch.ocn.OCNEval;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.ocn.OCNEval.sql.SqlRequest;

@Path("sections")
public class SectionResource {
	
	public SectionResource() {
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSectionsJson() throws SQLException {
		String request = "SELECT * FROM section WHERE valid='1';";
		
		return Response.status(Response.Status.OK).entity(SqlRequest.requestSections(request)).build(); 
	}
}
