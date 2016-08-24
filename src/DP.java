

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

@Path("/partner")
public class DP {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPartner()
	{
		Gson gson = new Gson();
		String json;
		try{
			json = gson.toJson(Subscriber.getAllSubscriber());
		}catch(Exception e)
		{
			e.printStackTrace();
			return Response.status(404).entity("Not Found").build();
		}
		return Response.status(200).entity(json).build();
	}
}
