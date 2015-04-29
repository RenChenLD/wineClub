package RESTfulService;

import java.io.File;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
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

public class Receipt {
	private String name;
	private YearMonth date;
	private int SubID, rid;
	private static File f = new File("/home/Receipts.xml");
	public Receipt()
	{
		
	}
	public Receipt(String name)
	{
		this.name = name;
		this.date = YearMonth.now();
		this.rid = IdGenerator.newID();
		this.SubID = getSubscriberID(name);
	}
	public int getId()
	{
		return this.rid;
	}
	public void setId(int id)
	{
		this.rid = id;
	}
	public String getName()
	{
		return this.name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getDate()
	{
		return this.date.toString();
	}
	public void setDate(String d) throws ParseException
	{
		
		this.date = YearMonth.parse(d); 
	}
	public int getSubID()
	{
		return this.SubID;
	}
	public void setSubID(String s)
	{
		this.SubID = Integer.parseInt(s);
	}
	public static Receipt getReceipt(int rid)
	{
		Receipt r = new Receipt();
		r.setId(rid);
		try{
			DocumentBuilderFactory  builderFactory =  DocumentBuilderFactory.newInstance();  
			DocumentBuilder   builder = builderFactory.newDocumentBuilder();  	             
			Document  doc = builder.parse(f);  
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("receipt");
			for(int i = 0; i<nList.getLength(); i++)
			{
				Node node = nList.item(i);
				Element ele = (Element) node;
				if(node.getNodeType() == Element.ELEMENT_NODE)
				{
					if(ele.getAttribute("id").equals(Integer.toString(rid)))
					{
						r.setName(ele.getElementsByTagName("name").item(0).getTextContent());
						r.setSubID(ele.getElementsByTagName("subscriber").item(0).getTextContent());
						r.setDate(ele.getElementsByTagName("date").item(0).getTextContent());						
					}
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return r;
	}
	
	private static Node getPendingElement(Document doc, Element e, String name, String value)
	{
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
	
	private static Node	getReceipt(Document doc, int rid, String name, String date, int Subid)
	{
		Element receipt = doc.createElement("receipt");
		receipt.setAttribute("id", Integer.toString(rid));
		receipt.appendChild(getPendingElement(doc, receipt, "name", name));
		receipt.appendChild(getPendingElement(doc, receipt, "date", date));
		receipt.appendChild(getPendingElement(doc, receipt, "subscriber", Integer.toString(Subid)));
		return receipt;
	}
	public static void saveReceipt(Receipt r)
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
						mainRootElement = doc.createElementNS(f.toURI().toString(), "receipts");
						doc.appendChild(mainRootElement);
					}else
					{
						doc = builder.parse(f);
						mainRootElement = doc.getElementsByTagName("receipts").item(0);
					}
					mainRootElement.appendChild(getReceipt(doc, r.getId(), r.getName(), r.getDate(), r.getSubID()));
					
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
	private static int getSubscriberID(String name)
	{
		return Subscriber.searchSubscriber(name).getId();
	}
	public static List<Receipt> getAllReceipt()
	{
		List<Receipt> rlist = new ArrayList<Receipt>();
		try{
			DocumentBuilderFactory  builderFactory =  DocumentBuilderFactory.newInstance();  
			DocumentBuilder   builder = builderFactory.newDocumentBuilder();  	             
			Document  doc = builder.parse(f);  
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("receipt");
			for(int i = 0; i<nList.getLength(); i++)
			{
				Node node = nList.item(i);
				Element ele = (Element) node;
				if(node.getNodeType() == Element.ELEMENT_NODE)
				{
					Receipt r = new Receipt();
					r.setName(ele.getElementsByTagName("name").item(0).getTextContent());
					r.setSubID(ele.getElementsByTagName("subscriber").item(0).getTextContent());
					r.setDate(ele.getElementsByTagName("date").item(0).getTextContent());						
					rlist.add(r);	
				}
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return rlist;
	}
}
