

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Subscriber {
	private SubInfo ac;
	private Address add;
	private MonthlySelection ms;
	private int id;
	private static Wine[] ws;
	private static List<Note> ns = new ArrayList<Note>();
	private static List<Shipment> shs = new ArrayList<Shipment>();
	private Shipment sht;
	private static File f = new File("/home/subscribers.xml");
	public Subscriber()
	{
		
	}
	public Subscriber(int id)
	{
		this.id = id;
	}
	public Subscriber(String email, String name, String phone, Address ad)
	{
		this.id = IdGenerator.newID();
		this.ac = new SubInfo(email, name, phone.replaceAll("[\\s\\-()]", ""));
		this.add = ad;
	}
	public void setDefaultMS()
	{
		this.ms = new RW();
	}
	public void setMS(MonthlySelection m)
	{
		this.ms = m;
	}
	public MonthlySelection getMS()
	{
		return this.ms;
	}
	public SubInfo getAccount()
	{
		return this.ac;
	}
	public void setAccount(SubInfo a)
	{
		this.ac = a;
	}
	public int getId()
	{
		return this.id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public void addShipment()
	{
		this.sht = new Shipment(this.ms, this.add);
		this.addShipmentHistory();
		this.sht.saveShipment();
		
	}
	public void setAddress(Address a)
	{
		this.add = a;
	}
	public Address getAddress()
	{
		return this.add;
	}
	public Wine[] getWines()
	{
		ws = this.ms.Wines();
		return ws;
	}
	public List<Note> getNotes()
	{
		for(int i = 0; i<ws.length; i++)
		{
			for(int j = 0; j<ws[i].getNote().size(); j++)
				ns.add(ws[i].getNote().get(j));
		}
		return ns;
	}
	private static Node getPendingElement(Document doc, Element e, String name, String value)
	{
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
	
	private static Node	getSubscriber(Document doc, String id, SubInfo a, Address ad)
	{
		Element sub = doc.createElement("subscriber");
		sub.setAttribute("id", id);
		sub.appendChild(getPendingElement(doc, sub, "email", a.Email()));
		sub.appendChild(getPendingElement(doc, sub, "name", a.Name()));
		sub.appendChild(getPendingElement(doc, sub, "phone", a.Phone()));
		Element add = doc.createElement("address");
		add.appendChild(getPendingElement(doc, add, "street", ad.Street()));
		add.appendChild(getPendingElement(doc, add, "city", ad.City()));
		add.appendChild(getPendingElement(doc, add, "state", ad.State()));
		add.appendChild(getPendingElement(doc, add, "zip", ad.Zip()));
		sub.appendChild(add);
		if(a.Twitter()!=null)
		sub.appendChild(getPendingElement(doc, sub, "twitter", a.Twitter()));
		else
			sub.appendChild(getPendingElement(doc, sub, "twitter", " "));
		if(a.Facebook()!=null)
		sub.appendChild(getPendingElement(doc, sub, "facebook", a.Facebook()));
		else
			sub.appendChild(getPendingElement(doc, sub, "facebook", " "));
		return sub;
	}
	
	public void saveSubInfo()
	{
//		MonthlySelection.saveMonthlySelection(ms, ws);
		try{
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = builderFactory.newDocumentBuilder();
				Document doc;
				
				if(!f.exists())
				{
					f.createNewFile();
					doc = builder.newDocument();
					Element mainRootElement = doc.createElementNS(f.toURI().toString(), "subscribers");
					doc.appendChild(mainRootElement);
					mainRootElement.appendChild(getSubscriber(doc, Integer.toString(this.id), this.ac, this.add));
				}else
				{
					doc = builder.parse(f);
					Node mainRootElement = doc.getElementsByTagName("subscribers").item(0);
//					if(searchSubscriber(this.id)==null)
					mainRootElement.appendChild(getSubscriber(doc, Integer.toString(this.id), this.ac, this.add));
				}
				
				saveToFile(doc);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	
	public void addShipmentHistory()
	{
		try{
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.parse(f);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("subscriber");
			for(int i = 0; i<nList.getLength(); i++)
	        {
	        	Node node = nList.item(i);
	        	Element ele = (Element) node;
	        	if(node.getNodeType() == Element.ELEMENT_NODE)
	        	{
	        		if(ele.getAttribute("id").equals(Integer.toString(this.id)))
	        			{
	        				
	        				if(!(doc.getElementsByTagName("shipments") == null))
	        				{
	        					Element shipments = doc.createElement("shipments");
	        					Element e = doc.createElement("shipment");
	        					e.setAttribute("id", Integer.toString(this.sht.getId()));
	        					e.appendChild(getPendingElement(doc, e, "selection_month", this.sht.getSM().toString()));
	        					e.appendChild(getPendingElement(doc, e, "status", this.sht.getStatus()));
	        					shipments.appendChild(e);
	        					ele.appendChild(shipments);
	        				}else{
	        					Node shipments = ele.getElementsByTagName("shipments").item(0);
	        					Element e = doc.createElement("shipment");
	        					e.setAttribute("id", Integer.toString(this.sht.getId()));
	        					e.appendChild(getPendingElement(doc, e, "selection_month", this.sht.getSM().toString()));
	        					e.appendChild(getPendingElement(doc, e, "status", this.sht.getStatus()));
	        					shipments.appendChild(e);
	        					ele.appendChild(shipments);
	        				}
	        			}
	        	}
	        }
			saveToFile(doc);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public List<Shipment> getShipments()
	{
		shs.clear();
		try{
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.parse(f);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("subscriber");
			for(int i = 0; i<nList.getLength(); i++)
	        {
	        	Node node = nList.item(i);
	        	Element ele = (Element) node;
	        	if(node.getNodeType() == Element.ELEMENT_NODE)
	        	{
	        		if(ele.getAttribute("id").equals(Integer.toString(this.id))&&ele.getElementsByTagName("shipment")!=null);
	        		{
	        			NodeList sList = ele.getElementsByTagName("shipment");
	        			for(int j = 0; j<sList.getLength(); j++)
	        			{
	        				Node node2 = sList.item(j);
	        				Element ele2 = (Element) node2;
	        				Shipment sht = new Shipment(Integer.parseInt(ele2.getAttribute("id")));
	        				sht.setSM(ele2.getElementsByTagName("selection_month").item(0).getTextContent());
	        				shs.add(sht);
	        			}
	        		}
	        	}
	        }
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return shs;
	}
	public void updateAccount()
	{
		try{
			DocumentBuilderFactory  builderFactory =  DocumentBuilderFactory.newInstance();  
	        DocumentBuilder   builder = builderFactory.newDocumentBuilder();  	             
	        Document  doc = builder.parse(f);
	        NodeList nList = doc.getElementsByTagName("subscriber");
	        for(int i = 0; i<nList.getLength(); i++)
	        {
	        	Node node = nList.item(i);
	        	Element ele = (Element) node;
	        	if(node.getNodeType() == Element.ELEMENT_NODE)
	        	{
	        		if(ele.getAttribute("id").equals(Integer.toString(this.id)))
	        		{
        				ele.getElementsByTagName("email").item(0).setTextContent(this.ac.Email());
        				ele.getElementsByTagName("name").item(0).setTextContent(this.ac.Name());
        				if(this.ac.Twitter()!=null )
        					ele.getElementsByTagName("twitter").item(0).setTextContent(this.ac.Twitter());
        				if(this.ac.Facebook()!=null)	
        					ele.getElementsByTagName("facebook").item(0).setTextContent(this.ac.Facebook());
        				ele.getElementsByTagName("phone").item(0).setTextContent(this.ac.Phone());
        				ele.getElementsByTagName("street").item(0).setTextContent(this.add.Street());
        				ele.getElementsByTagName("city").item(0).setTextContent(this.add.City());
        				ele.getElementsByTagName("state").item(0).setTextContent(this.add.State());
        				ele.getElementsByTagName("zip").item(0).setTextContent(this.add.Zip());
	        		}
	        	}
	        }
	       saveToFile(doc);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static Subscriber searchSubscriber(int id)
	{
		Subscriber s = new Subscriber(id);
		SubInfo a = new SubInfo();
		Address ad;
		try{
			DocumentBuilderFactory  builderFactory =  DocumentBuilderFactory.newInstance();  
			DocumentBuilder   builder = builderFactory.newDocumentBuilder(); 
			if(f.exists()){
			Document  doc = builder.parse(f);  
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("subscriber");
			for(int i = 0; i<nList.getLength(); i++)
			{
				Node node = nList.item(i);
				Element ele = (Element) node;
				if(node.getNodeType() == Element.ELEMENT_NODE)
				{
					if(ele.getAttribute("id").equals(Integer.toString(id)))
					{
						a.setEmail(ele.getElementsByTagName("email").item(0).getTextContent());
						a.setName(ele.getElementsByTagName("name").item(0).getTextContent());
						if(ele.getElementsByTagName("twitter").item(0).getTextContent()!=null)
						a.setTwitter(ele.getElementsByTagName("twitter").item(0).getTextContent());
						if(ele.getElementsByTagName("facebook").item(0).getTextContent()!=null)
						a.setFacebook(ele.getElementsByTagName("facebook").item(0).getTextContent());
						a.setPhone(ele.getElementsByTagName("phone").item(0).getTextContent());
						ad = new Address(ele.getElementsByTagName("street").item(0).getTextContent(), ele.getElementsByTagName("city").item(0).getTextContent(), ele.getElementsByTagName("state").item(0).getTextContent(), ele.getElementsByTagName("zip").item(0).getTextContent());
						s.setAccount(a);
						s.setAddress(ad);
					}
				}
			}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return s;
	}
	public static Subscriber searchSubscriber(String name)
	{
		Subscriber s = new Subscriber();
		SubInfo a = new SubInfo();
		Address ad;
		try{
			DocumentBuilderFactory  builderFactory =  DocumentBuilderFactory.newInstance();  
			DocumentBuilder   builder = builderFactory.newDocumentBuilder();  	             
			Document  doc = builder.parse(f);  
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("subscriber");
			for(int i = 0; i<nList.getLength(); i++)
			{
				Node node = nList.item(i);
				Element ele = (Element) node;
				if(node.getNodeType() == Element.ELEMENT_NODE)
				{
					if(ele.getElementsByTagName("name").item(0).getTextContent().equals(name))
					{
						s.setId(Integer.parseInt(ele.getAttribute("id").toString()));
						a.setEmail(ele.getElementsByTagName("email").item(0).getTextContent());
						a.setName(ele.getElementsByTagName("name").item(0).getTextContent());
						if(ele.getElementsByTagName("twitter")!=null)
						a.setTwitter(ele.getElementsByTagName("twitter").item(0).getTextContent());
						if(ele.getElementsByTagName("facebook")!=null)
						a.setFacebook(ele.getElementsByTagName("facebook").item(0).getTextContent());
						a.setPhone(ele.getElementsByTagName("phone").item(0).getTextContent());
						ad = new Address(ele.getElementsByTagName("street").item(0).getTextContent(), ele.getElementsByTagName("city").item(0).getTextContent(), ele.getElementsByTagName("state").item(0).getTextContent(), ele.getElementsByTagName("zip").item(0).getTextContent());
						s.setAccount(a);
						s.setAddress(ad);
					}
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return s;
	}
	public static List<Subscriber> getAllSubscriber()
	{
		List<Subscriber> slist = new ArrayList<Subscriber>();
		try{
			DocumentBuilderFactory  builderFactory =  DocumentBuilderFactory.newInstance();  
			DocumentBuilder   builder = builderFactory.newDocumentBuilder();  	             
			Document  doc = builder.parse(f);  
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("subscriber");
			for(int i = 0; i<nList.getLength(); i++)
			{
				Node node = nList.item(i);
				Element ele = (Element) node;
				if(node.getNodeType() == Element.ELEMENT_NODE)
				{
					Subscriber s = new Subscriber();
					SubInfo a = new SubInfo();
					Address ad;
					s.setId(Integer.parseInt(ele.getAttribute("id").toString()));
					a.setEmail(ele.getElementsByTagName("email").item(0).getTextContent());
					a.setName(ele.getElementsByTagName("name").item(0).getTextContent());
					a.setTwitter(ele.getElementsByTagName("twitter").item(0).getTextContent());
					a.setFacebook(ele.getElementsByTagName("facebook").item(0).getTextContent());
					a.setPhone(ele.getElementsByTagName("phone").item(0).getTextContent());						ad = new Address(ele.getElementsByTagName("street").item(0).getTextContent(), ele.getElementsByTagName("city").item(0).getTextContent(), ele.getElementsByTagName("state").item(0).getTextContent(), ele.getElementsByTagName("zip").item(0).getTextContent());
					s.setAccount(a);
					s.setAddress(ad);
					slist.add(s);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return slist;
	}
	public static void clearSubscriberList()
	{
		try {
		    Files.delete(f.toPath());
		} catch (NoSuchFileException x) {
		    System.err.format("%s: no such" + " file or directory%n", f.toPath());
		} catch (DirectoryNotEmptyException x) {
		    System.err.format("%s not empty%n", f.toPath());
		} catch (IOException x) {
		    // File permission problems are caught here.
		    System.err.println(x);
		}
	}
	
	private static void saveToFile(Document doc)
	{
		try{
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); 
	        DOMSource source = new DOMSource(doc);
	        StreamResult console = new StreamResult(f);
	        transformer.transform(source, console);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
//	private boolean isMatchName(String s)
//	{
//		String regex = "(?i).*" + s + ".*";
//    	return this.ac.Name().matches(regex);
//	}
//	private boolean isMatchEmail(String s)
//	{
//		String regex = "(?i).*" + s + ".*";
//		return this.ac.Email().matches(regex);
//	}
//	private boolean isMatchPhone(String s) {
//    	String s2 = s.replaceAll("[\\s\\-()]", ""); // drop all non-digit characters from search string
//    	String regex = "(?i).*" + s2 + ".*";
//    	return this.ac.Phone().matches(regex);
//    }
//    public boolean isMatch(String kw) {
//    	if (isMatchName(kw) || isMatchEmail(kw) || isMatchPhone(kw)) {
//    		return true;
//    	} else return false;
//    }
    
    
}
