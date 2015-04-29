package RESTfulService;

import java.io.File;
import java.time.YearMonth;
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

public class Shipment {
	private int id;
	private String status;
	private static List<Note> notes = new ArrayList<Note>();
	private YearMonth selection_month;
	private YearMonth date;
	private MonthlySelection ms;
	private Wine[] ws;
	private Address add;
	private static File f = new File("/home/shipments.xml");
	public Shipment(int id)
	{
		this.id = id;
	}
	public Shipment()
	{
		this.id = IdGenerator.newID();
		this.selection_month = YearMonth.now();
		this.date = YearMonth.now();
		this.status = "Cancelled";
		this.ms = new RW();
		this.ws = this.ms.Wines();
		
	}
	public Shipment(MonthlySelection w, Address add)
	{
		switch(w.mst)
		{
		case RW:
			this.ms = new RW();
			this.ws = this.ms.Wines();
			break;
		case AR:
			this.ms = new AR();
			break;
		case AW:
			this.ms = new AW();
			break;
		}
		
		selection_month = YearMonth.now();
		this.date = YearMonth.now();
		this.status = "Not shiped";
		this.id = IdGenerator.newID();
		this.add = add;
	}
	
	public int getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id = Integer.parseInt(id);
	}
	public YearMonth getSM()
	{
		return this.selection_month;
	}
	public void setSM(String sm)
	{
		this.selection_month = YearMonth.parse(sm);
	}
	public String getStatus()
	{
		return this.status;
	}
	public void setStatus(String s)
	{
		this.status = s;
	}
	public void setAddress(Address add)
	{
		this.add = add;
	}
	public Address getAddress()
	{
		return this.add;
	}
	public void setMST(MonthlySelectionType mst)
	{
		switch(mst)
		{
		case RW:
			this.ms = new RW();
			this.ws = this.ms.Wines();
			break;
		case AR:
			this.ms = new AR();
			this.ws = this.ms.Wines();
			break;
		case AW:
			this.ms = new AW();
			this.ws = this.ms.Wines();
			break;
		}
	}
	public Wine[] getWines()
	{
		return this.ws;
	}
	public void addNote(Note n)
	{
		notes.add(n);
		this.saveShipmentNote(n);
	}
	public List<Note> getNotes()
	{
		return notes;
	}
	public void deleteNote(int nid)
	{
		for(int i = 0; i<notes.size(); i++)
		{
			if(notes.get(i).getId()==nid)
			{
				notes.remove(i);
			}
		}
		this.deleteShipmentNote(nid);
	}
	
	public void saveShipment()
	{
		try{
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc;
			Node mainRootElement;
			if(!f.exists())
			{
				f.createNewFile();
				doc = builder.newDocument();
				mainRootElement = doc.createElementNS(f.toURI().toString(), "shipments");
				doc.appendChild(mainRootElement);
			}else
			{
				doc = builder.parse(f);
				mainRootElement = doc.getElementsByTagName("shipments").item(0);
			}
			mainRootElement.appendChild(getShipment(doc, Integer.toString(this.id), this.ws, this.selection_month, this.status, this.date, this.ms, this.add));
			
			saveToFile(doc);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private static Node getPendingElement(Document doc, Element e, String name, String value)
	{
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
	private static Node getShipment(Document doc, String id, Wine[] ws, YearMonth sm, String status, YearMonth d, MonthlySelection ms, Address add)
	{
		Element shipment = doc.createElement("shipment");
		shipment.setAttribute("id", id);
		shipment.appendChild(getPendingElement(doc, shipment, "selection_month", sm.toString()));
		shipment.appendChild(getPendingElement(doc, shipment, "status", status));
		shipment.appendChild(getPendingElement(doc, shipment, "date", d.toString()));
		shipment.appendChild(getPendingElement(doc, shipment, "type", ms.mst.toString()));
		if(ws != null){
			Element wines = doc.createElement("wines");
			for(int i = 0; i<ws.length; i++)
			{
				Element w = doc.createElement("wine");
				w.setAttribute("id", ws[i].getId());
				w.appendChild(getPendingElement(doc, w, "label_name", ws[i].getLabelName()));
				wines.appendChild(w);
			}
			shipment.appendChild(wines);
		}
		Element address = doc.createElement("address");
		address.appendChild(getPendingElement(doc, address, "street", add.Street()));
		address.appendChild(getPendingElement(doc, address, "city", add.City()));
		address.appendChild(getPendingElement(doc, address, "state", add.State()));
		address.appendChild(getPendingElement(doc, address, "zip", add.Zip()));
		shipment.appendChild(address);
		return shipment;
	}
	public static Shipment searchShipment(String sid)
	{
		Shipment sh = new Shipment();
		try{
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(f.getAbsolutePath());  
	        doc.getDocumentElement().normalize();
	        NodeList nList = doc.getElementsByTagName("shipment");
	        for(int i = 0; i<nList.getLength(); i++)
	        {
	        	Node node = nList.item(i);
	        	Element ele = (Element) node;
	        	if(node.getNodeType() == Element.ELEMENT_NODE)
	        	{
	        		if(ele.getAttribute("id").equals(sid))
	        			{
	        				sh.setId(sid);
	        				sh.setSM(ele.getElementsByTagName("selection_month").item(0).getTextContent());
	        				sh.setStatus(ele.getElementsByTagName("status").item(0).getTextContent());
	        				NodeList notelist = ele.getElementsByTagName("note");
	        				if(notelist != null)
	        				{
	        					for(int j = 0; j<notelist.getLength(); j++)
	        					{
	        						Node nnode = notelist.item(j);
	        						Element e = (Element) nnode;
	        						Note n = new Note(Integer.parseInt(e.getAttribute("id")));
	        						n.setContent((e.getElementsByTagName("content").item(0).getTextContent()));
	        						sh.addNote(n);
	        					}
	        				}
	        			}
	        	}
	        }
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return sh;
	}
	public void saveShipmentNote(Note n)
	{
		
		try{
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.parse(f);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("shipment");
			for(int i = 0; i<nList.getLength(); i++)
	        {
	        	Node node = nList.item(i);
	        	Element ele = (Element) node;
	        	if(node.getNodeType() == Element.ELEMENT_NODE)
	        	{
	        		if(ele.getAttribute("id").equals(Integer.toString(id)))
	        			{
	        				
	        				if(doc.getElementsByTagName("notes") == null)
	        				{
	        					Element shipments = doc.createElement("notes");
	        					Element e = doc.createElement("note");
	        					e.setAttribute("id", Integer.toString(n.getId()));
	        					e.appendChild(getPendingElement(doc, e, "content", n.getContent()));
	        					e.appendChild(getPendingElement(doc, e, "date", n.getDate()));
	        					shipments.appendChild(e);
	        					ele.appendChild(shipments);
	        				}else{
	        					Node no = ele.getElementsByTagName("notes").item(0);
	        					Node shipments = (Element)no;
	        					Element e = doc.createElement("note");
	    						e.setAttribute("id", Integer.toString(n.getId()));
	    						e.appendChild(getPendingElement(doc, e, "content", n.getContent()));
	    						e.appendChild(getPendingElement(doc, e, "date", n.getDate()));
	    						System.out.println(n.getContent() +n.getDate());
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
	public void deleteShipmentNote(int nid)
	{
		try{
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.parse(f.getAbsolutePath());  
		    doc.getDocumentElement().normalize();
		    NodeList nList = doc.getElementsByTagName("note");
		    for(int i = 0; i<nList.getLength(); i++)
	        {
	        	Node node = nList.item(i);
	        	Element ele = (Element) node;
	        	if(node.getNodeType() == Element.ELEMENT_NODE)
	        	{
	        		if(ele.getAttribute("id").equals(Integer.toString(nid)))
	        		{
	        			ele.getParentNode().removeChild(node);
	        		}
	        	}
	        }
		    saveToFile(doc);
		}catch(Exception e)
		{
			e.printStackTrace();
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
        }
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public Note searchNote(int nid)
	{
		Note n = new Note(nid);
		try{
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.parse(f.getAbsolutePath());  
		        doc.getDocumentElement().normalize();
		        NodeList nList = doc.getElementsByTagName("note");
    			notes.clear();
		        for(int i = 0; i<nList.getLength(); i++)
		        {
		        	Node node = nList.item(i);
		        	Element ele = (Element) node;
		        	if(node.getNodeType() == Element.ELEMENT_NODE)
		        	{
		        		if(ele.getAttribute("id").equals(Integer.toString(nid)))
		        		{
		        			n=new Note(ele.getAttribute("id"),ele.getElementsByTagName("content").item(0).getTextContent());
		        			n.setDate(ele.getElementsByTagName("date").item(0).getTextContent());
		        			notes.add(n);
		        		}
		        	}
		        }
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		return n;
	}
	public void searchNote()
	{
		try{
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.parse(f.getAbsolutePath());  
		        doc.getDocumentElement().normalize();
		        NodeList nList = doc.getElementsByTagName("note");
		        notes.clear();
		        for(int i = 0; i<nList.getLength(); i++)
		        {
		        	Node node = nList.item(i);
		        	Element ele = (Element) node;
		        	if(node.getNodeType() == Element.ELEMENT_NODE)
		        	{
		        		Note n;
		        		n=new Note(ele.getAttribute("id"),ele.getElementsByTagName("content").item(0).getTextContent());
		        		n.setDate(ele.getElementsByTagName("date").item(0).getTextContent());
		        		
		        		notes.add(n);
		        	}
		        }
			}catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	public void updateShipmentNote(Note n)
	{
		try{
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.parse(f.getAbsolutePath());  
		        doc.getDocumentElement().normalize();
		        NodeList nList = doc.getElementsByTagName("note");
		        notes.clear();
		        for(int i = 0; i<nList.getLength(); i++)
		        {
		        	Node node = nList.item(i);
		        	Element ele = (Element) node;
		        	if(node.getNodeType() == Element.ELEMENT_NODE)
		        	{
		        		if(ele.getAttribute("id").equals(Integer.toString(n.getId())))
		        		{
		        			ele.getElementsByTagName("content").item(0).setTextContent(n.getContent());
		        			ele.getElementsByTagName("date").item(0).setTextContent(n.getDate());
		        		}
		        	}
		        }
		        saveToFile(doc);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
	}
}
