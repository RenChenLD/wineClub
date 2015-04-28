package RESTfulService;

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
import org.apache.log4j.Logger;

@Path("/service")
public class SimpleRestService {

	private static final Logger logger = Logger.getLogger(SimpleRestService.class);
	
	@GET
	@Path("/searchSub")
	@Produces(MediaType.TEXT_PLAIN)
	public String getSub(@QueryParam("uid") int uid)
	{
		String result=null;
		try{
		Subscriber s = Subscriber.searchSubscriber(uid);
		result = "Email: " + s.getAccount().Email() + "\nName: " + s.getAccount().Name();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
		
	}
	@GET
	@Path("/getsomething")
    @Produces(MediaType.TEXT_PLAIN)
	public String getSomething(@QueryParam("request") String request ,
			 @DefaultValue("1") @QueryParam("version") int version) {

		if (logger.isDebugEnabled()) {
			logger.debug("Start getSomething");
			logger.debug("data: '" + request + "'");
			logger.debug("version: '" + version + "'");
		}

		String response = null;

        try{			
            switch(version){
	            case 1:
	                if(logger.isDebugEnabled()) logger.debug("in version 1");

	                response = "Response from Jersey Restful Webservice : " + request;
                    break;
                default: throw new Exception("Unsupported version: " + version);
            }
        }
        catch(Exception e){
        	response = e.getMessage().toString();
        }
        
        if(logger.isDebugEnabled()){
            logger.debug("result: '"+response+"'");
            logger.debug("End getSomething");
        }
        return response;	
	}

	@POST
	@Path("/sub")
    @Produces(MediaType.TEXT_PLAIN)
	public String sub(@FormParam("Email") String Email, @DefaultValue("Andy Black") @FormParam("Name") String Name, @DefaultValue("1234123")@FormParam("Phone") String Phone, @DefaultValue("123 St")@FormParam("Street") String street, @DefaultValue("sz")@FormParam("City") String city, @DefaultValue("js")@FormParam("State") String state, @DefaultValue("215000")@FormParam("Zip") String zip)
	{
		String result = null;
		try{
		Address a = new Address(street, city, state, zip);
		Subscriber s = new Subscriber(Email, Name, Phone, a);
//		result = Integer.toString(s.getId());
//		s.saveSubInfo();
		result = s.getAccount().Email();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		result = Email;
		return result;
	}
	
	@POST
	@Path("/postsomething")
    @Produces(MediaType.TEXT_PLAIN)
	public String postSomething(@FormParam("request") String request ,  @DefaultValue("1") @FormParam("version") int version) {

		if (logger.isDebugEnabled()) {
			logger.debug("Start postSomething");
			logger.debug("data: '" + request + "'");
			logger.debug("version: '" + version + "'");
		}

		String response = null;

        try{			
            switch(version){
	            case 1:
	                if(logger.isDebugEnabled()) logger.debug("in version 1");

	                response = "Response from Jersey Restful Webservice : " + request;
                    break;
                default: throw new Exception("Unsupported version: " + version);
            }
        }
        catch(Exception e){
        	response = e.getMessage().toString();
        }
        
        if(logger.isDebugEnabled()){
            logger.debug("result: '"+response+"'");
            logger.debug("End postSomething");
        }
        return response;	
	}

	@PUT
	@Path("/<add method name here>")
    @Produces(MediaType.TEXT_PLAIN)
	public String putSomething(@FormParam("request") String request ,  @DefaultValue("1") @FormParam("version") int version) {
		if (logger.isDebugEnabled()) {
			logger.debug("Start putSomething");
			logger.debug("data: '" + request + "'");
			logger.debug("version: '" + version + "'");
		}

		String response = null;

        try{			
            switch(version){
	            case 1:
	                if(logger.isDebugEnabled()) logger.debug("in version 1");

	                response = "Response from Jersey Restful Webservice : " + request;
                    break;
                default: throw new Exception("Unsupported version: " + version);
            }
        }
        catch(Exception e){
        	response = e.getMessage().toString();
        }
        
        if(logger.isDebugEnabled()){
            logger.debug("result: '"+response+"'");
            logger.debug("End putSomething");
        }
        return response;	
	}

	@DELETE
	@Path("/<add method name here>")
	public void deleteSomething(@FormParam("request") String request ,  @DefaultValue("1") @FormParam("version") int version) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Start deleteSomething");
			logger.debug("data: '" + request + "'");
			logger.debug("version: '" + version + "'");
		}


        try{			
            switch(version){
	            case 1:
	                if(logger.isDebugEnabled()) logger.debug("in version 1");

                    break;
                default: throw new Exception("Unsupported version: " + version);
            }
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        if(logger.isDebugEnabled()){
            logger.debug("End deleteSomething");
        }
	}
}
