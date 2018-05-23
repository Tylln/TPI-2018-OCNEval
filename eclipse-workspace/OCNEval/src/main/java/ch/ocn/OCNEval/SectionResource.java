package ch.ocn.OCNEval;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	
	@GET
	@Path("{sectionId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfileJson(@PathParam("sectionId") String sectionId) throws SQLException {
		String request = "SELECT * FROM section WHERE id='" + sectionId + "';";
		
		return Response.status(Response.Status.OK).entity(SqlRequest.requestSection(request)).build();
	}
	
	@PUT
	@Path("{sectionId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifySection(@PathParam("sectionId") String sectionId, String sectionJson) throws SQLException {
		return Response.status(200).build();
	}
}
