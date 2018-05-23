package ch.ocn.OCNEval;

import javax.ws.rs.Path;

import java.sql.SQLException;

import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import ch.ocn.OCNEval.data.Profile;
import ch.ocn.OCNEval.sql.SqlRequest;

@Path("profiles")
public class ProfileResource {
	
	public ProfileResource() {
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfilesJson() throws SQLException {
		String request = "SELECT * FROM profile WHERE valid='1';";
		
		return Response.status(Response.Status.OK).entity(SqlRequest.requestProfiles(request)).build();
	}
	
	@GET
	@Path("{profileId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfileJson(@PathParam("profileId") String profileId) throws SQLException {
		String requestProfile = "SELECT * FROM profile WHERE id='" + profileId + "';";
		String requestSections = "SELECT section.* FROM section, profile_section_junction WHERE profile_section_junction.profile_id='" + profileId + "' AND section.id = profile_section_junction.section_id AND section.valid = '1';";
		
		return Response.status(Response.Status.OK).entity(SqlRequest.requestProfile(requestProfile, requestSections)).build();
	}
	
	@PUT
	@Path("{profileId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyProfile(@PathParam("profileId") String profileId, String profileJson) throws SQLException {
		Gson gson = new Gson();
		Profile profile = gson.fromJson(profileJson, Profile.class);
		String request = "UPDATE profile SET name = '" + profile.getName() + "', description = '" + profile.getDescription() + "', validity_date = '" + profile.getValidityDate() + "' WHERE id = '" + profileId + "';";
		SqlRequest.modifiyProfile(request);
		
		return Response.status(200).build();
	}
	
	@DELETE
	@Path("{profileId}")
	public Response deleteProfile(@PathParam("profileId") String profileId) throws SQLException {
		String request = "UPDATE profile SET valid = '0' WHERE id='" + profileId + "';";
		SqlRequest.deleteProfile(request);
		
		return Response.status(200).build();
	}
}