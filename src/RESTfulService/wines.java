package RESTfulService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.DELETE;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

@Path("/wines")
public class wines {

	private static final Logger logger = Logger.getLogger(wines.class);

	@GET
	@Path("/{wid}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getSomething(@PathParam("wid") int wid) {
		Gson gson = new Gson();
		String json = gson.toJson(Wine.getWineDetail(wid));
		if(json == null)
			return Response.status(404).entity("Not Found").build();
		else
			return Response.status(200).entity(json).build();
		
	}

	
}
