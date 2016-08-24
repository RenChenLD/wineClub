

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

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

@Path("/admin")
public class Adm {
	private String parseIncomingData(String line)
	{
		if(line.contains("="))
		{
			line.replaceAll("=", ": ");
			line.replaceAll("+", " ");
		}
		int i;
		i = line.indexOf(" ");
		String s0 = line.substring(i+1, line.length());
		return s0;	
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addAdmin(InputStream incomingData)
	{
		StringBuilder crunchifyBuilder = new StringBuilder();
		String json;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = in.readLine();
            System.out.println(line);
            System.out.println(parseIncomingData(line));
            Admin a = new Admin(parseIncomingData(line));
        	Admin origin = new Admin(000);
      		Gson gson = new Gson();
       		json = gson.toJson(a.getId());
       		a.setCreate_date(YearMonth.now().toString());
       		a.setPid(000);
       		origin.saveAdmin(a);            
        } catch (Exception e) {
            System.out.println("Error Parsing: - ");
            e.printStackTrace();
            return Response.status(404).entity("Not Found").build();
        }
        System.out.println("Data Received: " + crunchifyBuilder.toString());
 
        // return HTTP response 200 in case of success
        return Response.status(200).entity(json).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllAdmin()
	{
		Gson gson = new Gson();
		String json = gson.toJson(Admin.getAllAdmin());
		if(json == null)
			return Response.status(404).entity("Not Found").build();
		else
			return Response.status(200).entity(json).build();
	}
	
	@PUT
	@Path("/{aid}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateAdmin(@PathParam("aid") int aid, @QueryParam("name") String name)
	{
		System.out.println(name);
		try{
			Admin.updateAdmin(aid, name);
		}catch(Exception e)
		{
			return Response.status(404).entity("Not Found").build();
		}
		return Response.status(200).entity("ok").build();
	}
	
	@GET
	@Path("/{aid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAdmin(@PathParam("aid") int aid)
	{
		Gson gson = new Gson();
		String json = gson.toJson(Admin.getAdmin(aid));
		if(json == null)
			return Response.status(404).entity("Not Found").build();
		else
			return Response.status(200).entity(json).build();
	}
	
//	@GET
//	@Path("/monthly_selection")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getMonthlySelection()
//	{
//		
//	}
	@POST
	@Path("/monthly_selection")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addMonthlySelection(JSONObject incomingData)
	{
		Gson gson = new Gson();
		MonthlySelection ms;
		String type = (String)incomingData.get("type");
		switch(type){
		case "AR":
			ms = new AR();
			break;
		case "AW":
			ms = new AW();
			break;
			default:
				ms = new RW();
			break;		
		}
		ms.setSelectionMonth(incomingData.getString("selection_month"));
		ms.setWines((List<Wine>)incomingData.get("wines"));
		System.out.println(ms.getWines().size());
		
		return Response.status(200).entity("ok").build();
	}
}
