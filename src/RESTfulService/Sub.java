package RESTfulService;

import java.io.BufferedReader;
import java.io.IOException;
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

import net.sf.json.*;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

@Path("/Sub")
public class Sub {

	private static final Logger logger = Logger.getLogger(Sub.class);
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addSub(InputStream data)
	{
		StringBuilder crunchifyBuilder = new StringBuilder();
        try {
        	 List<String> slist = new ArrayList<String>();
        	 String line = null;
        	 BufferedReader in = new BufferedReader(new InputStreamReader(data));
             while ((line = in.readLine()) != null) {
                 slist.add(parseIncomingData(line));
             }
            Address addr = new Address(slist.get(3),slist.get(4),slist.get(5),slist.get(6));
            Subscriber s = new Subscriber(slist.get(0),slist.get(1),slist.get(2), addr);
            s.setDefaultMS();
            s.addShipment();
            s.saveSubInfo();
            crunchifyBuilder.append(s.getId());
        } catch (Exception e) {
            System.out.println("Error Parsing: - ");
            e.printStackTrace();
        }
        System.out.println("Data Received: " + crunchifyBuilder.toString());
 
        // return HTTP response 200 in case of success
        return Response.status(200).entity(crunchifyBuilder.toString()).build();
	}
	private String parseIncomingData(String line)
	{
		if(line.contains("="))
			{line.replaceAll("=", ": ");
		line.replaceAll("+", " ");}
		int i;
		i = line.indexOf(" ");
		String s0 = line.substring(i+1, line.length());
		return s0;	
	}
	
	@Path("/{uid}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSub(@PathParam("uid") int uid, InputStream incomingData)
	{
		StringBuilder crunchifyBuilder = new StringBuilder();
        try {
        	 List<String> slist = new ArrayList<String>();
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                
                System.out.println(line);
                slist.add(parseIncomingData(line));	
            }
            Address addr = new Address(slist.get(3),slist.get(4),slist.get(5),slist.get(6));
            Subscriber s = new Subscriber(uid);
            SubInfo si = new SubInfo(slist.get(0),slist.get(1),slist.get(2));
            s.setAccount(si);
            s.setAddress(addr);
            s.updateAccount();
            crunchifyBuilder.append("id: ");
            crunchifyBuilder.append(Integer.toString(s.getId()));
        } catch (Exception e) {
            System.out.println("Error Parsing: - ");
        }
        System.out.println(crunchifyBuilder.toString());
        // return HTTP response 200 in case of success
        return Response.status(200).entity("Error:").build();
	}
	
	@GET
	@Path("/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	public String searchSub(@PathParam("uid") int uid)
	{
		System.out.println(uid);
		Subscriber s = new Subscriber(uid);
		try{
			s.setAccount(Subscriber.searchSubscriber(uid).getAccount());
			s.setAddress(Subscriber.searchSubscriber(uid).getAddress());
			System.out.println(s.getId() + " " + Subscriber.searchSubscriber(uid).getAccount().Email());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		Gson gson = new Gson();
		String json = gson.toJson(s);
		return json;
	}
	
	@GET
	@Path("/{uid}/search")
	@Produces(MediaType.APPLICATION_JSON)
	public String search(@PathParam("uid") int uid, @DefaultValue("ALL") @QueryParam("q") String q)
	{
		Subscriber s = Subscriber.searchSubscriber(uid);
		Gson gson = new Gson();
		String json;
		StringBuffer sb = new StringBuffer();
		List<Note> ns = new ArrayList<Note>();
//		if(q.equals("ALL"))
//		{
			s.setDefaultMS();
			json = gson.toJson(s.getWines());
			sb.append(json);
			if(s.getShipments()!=null)
			{	
				json = gson.toJson(s.getShipments());
				sb.append(json);
				for(int i = 0; i<s.getShipments().size(); i++)
					for(int j = 0; j<s.getShipments().get(i).getNotes().size(); j++)
						if(s.getWines()[i].getNote()!=null)
							ns.add(s.getShipments().get(i).getNotes().get(j));
			}
			for(int i = 0; i<s.getWines().length; i++)
				for(int j = 0; j<s.getWines()[i].getNote().size(); j++)
					if(s.getWines()[i].getNote()!=null)
						ns.add(s.getWines()[i].getNote().get(j));
			
			json = gson.toJson(ns);
			sb.append(json);
			return sb.toString();
//		}
		
	}
	
	@GET
	@Path("/{uid}/shipments")
	@Produces(MediaType.APPLICATION_JSON)
	public Response shipments(@PathParam("uid") int uid)
	{
		Subscriber s = Subscriber.searchSubscriber(uid);
		s.getShipments();
		Gson gson = new Gson();
		String json = gson.toJson(s.getShipments());
        return Response.status(200).entity(json).build();
	}
	@GET
	@Path("/{uid}/shipments/{sid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response shipmentDetail(@PathParam("uid") int uid, @PathParam("sid") int sid)
	{
		Gson gson = new Gson();
		String json;
		json = gson.toJson(Shipment.searchShipment(Integer.toString(sid)));
		return Response.status(200).entity(json).build();
	}
	@GET
	@Path("/{uid}/shipments/{sid}/notes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response notes(@PathParam("uid") int uid, @PathParam("sid") int sid)
	{
		Gson gson = new Gson();
		String json;
		Shipment sh = Shipment.searchShipment(Integer.toString(sid));
		sh.searchNote();
		json = gson.toJson(sh.getNotes());
		return Response.status(200).entity(json).build();
	}
	@POST
	@Path("/{uid}/shipments/{sid}/notes")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addShipmentNote(@PathParam("uid") int uid, @PathParam("sid") int sid, InputStream incomingData)
	{
		Shipment sh = Shipment.searchShipment(Integer.toString(sid));
        try {
        	 List<String> slist = new ArrayList<String>();
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                slist.add(parseIncomingData(line));
            }
            Note n = new Note(slist.get(0), YearMonth.now());
            sh.addNote(n);
        }catch(Exception e)
        {
        	e.printStackTrace();
        }
		return Response.status(201).entity("created").build();
	}
	@GET
	@Path("/{uid}/shipments/{sid}/notes/{nid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response shipmentNoteDetail(@PathParam("uid") int uid, @PathParam("sid") int sid, @PathParam("nid") int nid)
	{
		Gson gson = new Gson();
		String json;
		Shipment sh = Shipment.searchShipment(Integer.toString(sid));
		json =gson.toJson(sh.searchNote(nid));
		return Response.status(200).entity(json).build();		
	}
	
	@PUT
	@Path("/{uid}/shipments/{sid}/notes/{nid}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateShipmentNote(@PathParam("uid") int uid, @PathParam("sid") int sid, @PathParam("nid") int nid, InputStream incomingData)
	{
		Shipment sh = Shipment.searchShipment(Integer.toString(sid));
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(parseIncomingData(line));                
            }
            Note n = new Note(nid);
            n.setContent(parseIncomingData(line));
            sh.updateShipmentNote(n);
        }catch(Exception e)
        {
        	return Response.status(204).entity("No Content").build();
        }
        return Response.status(200).entity("OK").build();
	}
	
	@DELETE
	@Path("/{uid}/shipments/{sid}/notes/{nid}")
	public Response deleteShipmentNote(@PathParam("uid") int uid, @PathParam("sid") int sid, @PathParam("nid") int nid)
	{
		Shipment sh = Shipment.searchShipment(Integer.toString(sid));
		if(sh.searchNote(nid) == null)
			return Response.status(404).entity("Not found").build();
		else
		{	
			sh.deleteNote(nid);
			return Response.status(200).entity("OK").build();
		}
	}

	@GET
	@Path("/{uid}/wines")
	@Produces(MediaType.APPLICATION_JSON)
	public Response wines(@PathParam("uid") int uid)
	{
		Gson gson = new Gson();
		Subscriber s = Subscriber.searchSubscriber(uid);
		s.setDefaultMS();
		String json = gson.toJson(s.getWines());
		if(json == null)
			return Response.status(404).entity("Not found").build();
		else
			return Response.status(200).entity(json).build();
	}
	
	@GET
	@Path("/{uid}/wines/{wid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response wineDetail(@PathParam("uid") int uid,@PathParam("wid") int wid)
	{
		Wine w = Wine.getWineDetail(wid);
		Gson gson = new Gson();
		String json = gson.toJson(w);
		if(json == null)
			return Response.status(404).entity("Not found").build();
		else
			return Response.status(200).entity(json).build();
	}
	
	@GET
	@Path("/{uid}/wines/{wid}/notes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response wineNotes(@PathParam("uid") int uid,@PathParam("wid") int wid)
	{
		Gson gson = new Gson();
		String json;
		try{
			Wine w = Wine.getWineDetail(wid);
			json = gson.toJson(w.getNote());
		}catch(Exception e)
		{
			return Response.status(404).entity("Not found").build();
		}
		return Response.status(200).entity(json).build();
	}
	
	@POST
	@Path("/{uid}/wines/{wid}/notes")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addWineNote(@PathParam("uid") int uid,@PathParam("wid") int wid, InputStream incomingData)
	{
		Wine w = Wine.getWineDetail(wid);
        try {
        	 List<String> slist = new ArrayList<String>();
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                
                System.out.println(parseIncomingData(line));
                slist.add(parseIncomingData(line));
            }
            Note n = new Note(slist.get(0),YearMonth.now());
            w.addWineNote(n);
        }catch(Exception e)
        {
        	e.printStackTrace();
        }
        
		return Response.status(201).entity("created").build();
	}
	@GET
	@Path("/{uid}/wines/{wid}/notes/{nid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response wineNoteDetail(@PathParam("uid") int uid, @PathParam("wid") int wid, @PathParam("nid") int nid)
	{
		Gson gson = new Gson();
		String json;
		Wine w = Wine.getWineDetail(wid);
		json =gson.toJson(w.getNote(nid));
		return Response.status(200).entity(json).build();		
	}
	
	@PUT
	@Path("/{uid}/wines/{wid}/notes/{nid}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateWineNote(@PathParam("uid") int uid, @PathParam("wid") int wid, @PathParam("nid") int nid, InputStream incomingData)
	{
		
		Wine w = Wine.getWineDetail(wid);
		Note n = new Note(nid);
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = in.readLine();
//            while ((line = in.readLine()) != null) {
                System.out.println(parseIncomingData(line));
//            }
            n.setContent(parseIncomingData(line));
            n.setDate(YearMonth.now().toString());
            w.updateWineNote(n);
        }catch(Exception e)
        {
        	e.printStackTrace();
        }        

        if(n.getContent()==null)
        	return Response.status(204).entity("No content").build();
        else
        	return Response.status(200).entity("OK").build();
	}
	
	@DELETE
	@Path("/{uid}/wines/{wid}/notes/{nid}")
	public Response deleteWineNote(@PathParam("uid") int uid, @PathParam("wid") int wid, @PathParam("nid") int nid)
	{
		Wine w = Wine.getWineDetail(wid);
		w.deleteWineNote(nid);
		if(w.getNote(nid) == null)
			return Response.status(404).entity("Not found").build();
		else
		{	
			w.deleteWineNote(nid);
			return Response.status(200).entity("OK").build();
		}
	}
	
	@GET
	@Path("/{uid}/wines/{wid}/rating")
	@Produces(MediaType.APPLICATION_JSON)
	public Response wineRating(@PathParam("uid") int uid, @PathParam("wid") int wid)
	{
		Wine w;
		Gson gson = new Gson();
		String json;
		try{
			w = Wine.getWineDetail(wid);
			json = gson.toJson(w.getRating());
		}catch(Exception s)
		{
			return Response.status(404).entity("Not found").build();
		}
		return Response.status(200).entity(json).build();
	}
	
	@POST
	@Path("/{uid}/wines/{wid}/rating")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response wineRate(@PathParam("uid") int uid, @PathParam("wid") int wid, InputStream incomingData)
	{
		Wine w = Wine.getWineDetail(wid);
		BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
        String line = null;
        try {
			while ((line = in.readLine()) != null) {
			    System.out.println(parseIncomingData(line));
			    w.addRating(Integer.parseInt(parseIncomingData(line)));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return Response.status(404).entity("Not Found").build();
		}
        Gson gson = new Gson();
        String json = gson.toJson(w.getRating());
        return Response.status(200).entity(json).build();
        
	}
}
