

import java.io.File;
import java.io.FileNotFoundException;
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

public class Admin {
	private String name, create_date;
	private int aid, pid;
	private MonthlySelection ms;
	private static File f = new File("/home/admins.xml");
	public Admin(String name)
	{
		this.name = name;
		this.create_date = YearMonth.now().toString();
		this.aid = IdGenerator.newID();
	}
	
	public Admin(int id)
	{
		this.aid = id;
	}
	
	public void setName(String n)
	{
		this.name = n;
	}
	public String getName()
	{
		return this.name;
	}
	public void setCreate_date(String d)
	{
		this.create_date = d;
	}
	public String getCreate_date()
	{
		return this.create_date;
	}
	public void setPid(int id)
	{
		this.pid = id;
	}
	public int getPid()
	{
		return this.pid;
	}
	public int getId()
	{
		return this.aid;
	}
	public static Admin getAdmin(int aid) 
	{
		Admin a = new Admin(aid);
		if(f.exists())
		{
			try{
				DocumentBuilderFactory  builderFactory =  DocumentBuilderFactory.newInstance();  
				DocumentBuilder   builder = builderFactory.newDocumentBuilder();  	             
				Document  doc = builder.parse(f);  
				doc.getDocumentElement().normalize();
				NodeList nList = doc.getElementsByTagName("admin");
				for(int i = 0; i<nList.getLength(); i++)
				{
					Node node = nList.item(i);
					Element ele = (Element) node;
					if(node.getNodeType() == Element.ELEMENT_NODE)
					{
						if(ele.getAttribute("id").equals(Integer.toString(aid)))
						{
							
								a.setName(ele.getElementsByTagName("name").item(0).getTextContent());
								a.setCreate_date(ele.getElementsByTagName("create_date").item(0).getTextContent());
								a.setPid(Integer.parseInt(ele.getElementsByTagName("create_by").item(0).getTextContent()));
							}
						}
					}
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
		}else{
			FileNotFoundException e = new FileNotFoundException();
			e.printStackTrace();
		}
		return a;
	}
	public static void updateAdmin(int aid, String name)
	{
		try{
			DocumentBuilderFactory  builderFactory =  DocumentBuilderFactory.newInstance();  
			DocumentBuilder   builder = builderFactory.newDocumentBuilder();  	             
			Document  doc = builder.parse(f);  
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("admin");
			for(int i = 0; i<nList.getLength(); i++)
			{
				Node node = nList.item(i);
				Element ele = (Element) node;
				if(node.getNodeType() == Element.ELEMENT_NODE)
				{
					if(ele.getAttribute("id").equals(Integer.toString(aid)))
					{
						
							ele.getElementsByTagName("name").item(0).setTextContent(name);
						}
					}
				}
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
	public static ArrayList<Admin> getAllAdmin()
	{
		ArrayList<Admin> alist = new ArrayList<Admin>();
		try{
			DocumentBuilderFactory  builderFactory =  DocumentBuilderFactory.newInstance();  
			DocumentBuilder   builder = builderFactory.newDocumentBuilder();  	             
			Document  doc = builder.parse(f);  
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("admin");
			for(int i = 0; i<nList.getLength(); i++)
			{
				Node node = nList.item(i);
				Element ele = (Element) node;
				if(node.getNodeType() == Element.ELEMENT_NODE)
				{
						Admin a = new Admin(Integer.parseInt(ele.getAttribute("id")));
						a.setName(ele.getElementsByTagName("name").item(0).getTextContent());
						a.setCreate_date(ele.getElementsByTagName("create_date").item(0).getTextContent());
						a.setPid(Integer.parseInt(ele.getElementsByTagName("create_by").item(0).getTextContent()));
						alist.add(a);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return alist;
	}
	
	public void saveAdmin(Admin a)
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
						mainRootElement = doc.createElementNS(f.toURI().toString(), "admins");
						doc.appendChild(mainRootElement);
						mainRootElement.appendChild(getAdmin(doc, a.getId(), a.getName(), a.getCreate_date(), a.getPid()));

					}else
					{
						doc = builder.parse(f);
						mainRootElement = doc.getElementsByTagName("admins").item(0);
						mainRootElement.appendChild(getAdmin(doc, a.getId(), a.getName(), a.getCreate_date(), a.getPid()));

					}
					
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
	private static Node getPendingElement(Document doc, Element e, String name, String value)
	{
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
	
	private static Node	getAdmin(Document doc, int aid, String name, String create_date, int pid)
	{
		Element admin = doc.createElement("admin");
		admin.setAttribute("id", Integer.toString(aid));
		admin.appendChild(getPendingElement(doc, admin, "name", name));
		admin.appendChild(getPendingElement(doc, admin, "create_date", create_date));
		admin.appendChild(getPendingElement(doc, admin, "create_by", Integer.toString(pid)));
		return admin;
	}
	
}
