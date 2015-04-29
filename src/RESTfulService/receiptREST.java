package RESTfulService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
	public Response addReceipt(InputStream jo) throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(jo));
		String line = in.readLine();
		Receipt r = new Receipt(parseIncomingData(line));
		Gson gson = new Gson();
		String json = gson.toJson(r.getId());
		Receipt.saveReceipt(r);
		return Response.status(201).entity(json).build();
	}
	private String parseIncomingData(String line)
	{
		if(line.contains("=")){
		line.replaceAll("=", ": ");
		line.replaceAll("+", " ");}
		int i;
		i = line.indexOf(" ");
		String s0 = line.substring(i+1, line.length());
		return s0;	
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
	@Produces(MediaType.APPLICATION_JSON)
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
