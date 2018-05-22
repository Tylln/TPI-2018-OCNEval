package ch.ocn.OCNEval;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("Staff")
public class StaffResource {

	public StaffResource()  {
		
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveStaff(String person) throws Exception {
		System.out.println(person);
		return Response.status(200).build();
	}
}
