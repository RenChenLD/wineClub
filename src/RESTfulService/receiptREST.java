package RESTfulService;

import javax.ws.rs.Consumes;
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

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

@Path("/receipt")
public class receiptREST {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addReceipt(JSONObject jo)
	{
		Receipt r = new Receipt(jo.getString("name"));
		return Response.status(201).entity(r.getId()).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllReceipt()
	{
		Gson gson = new Gson();
		String json = gson.toJson(Receipt.getAllReceipt());
		if(json == null)
			return Response.status(404).entity("Not Found").build();
		else
			return Response.status(200).entity(json).build();
	}
	
	@GET
	@Path("/{rid}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getReceipt(@PathParam("rid") int rid)
	{
		Gson gson = new Gson();
		String json = gson.toJson(Receipt.getReceipt(rid));
		if(json == null)
			return Response.status(404).entity("Not Found").build();
		else
			return Response.status(200).entity(json).build();
	}
		
	
}
