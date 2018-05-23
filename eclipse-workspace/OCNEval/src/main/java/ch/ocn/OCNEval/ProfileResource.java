package ch.ocn.OCNEval;

import javax.ws.rs.Path;

import java.sql.SQLException;

import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.ocn.OCNEval.sql.SqlRequest;

@Path("profiles")
public class ProfileResource {
	
	public ProfileResource() {
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfilesJson() throws SQLException {
		return Response.status(Response.Status.OK).entity(SqlRequest.requestProfiles("SELECT * FROM profile WHERE valid='1';")).build();
	}
	
	@GET
	@Path("{profileId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfileJson(@PathParam("profileId") String profileId) throws SQLException {
		return Response.status(Response.Status.OK).entity(SqlRequest.requestProfile("SELECT * FROM profile WHERE id='" + profileId + "';")).build();
	}
	
	@DELETE
	@Path("{profileId}")
	public Response deleteProfile(@PathParam("profileId") String profileId) throws SQLException {
		System.out.println("Test");
		SqlRequest.deleteProfile("DELETE FROM profile WHERE id='" + profileId + "';");
		
		return Response.status(200).build();
	}
}