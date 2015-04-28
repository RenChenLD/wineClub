package RESTfulService;

import java.io.File;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Date;

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

public class Note {
	private int id;
	private String content;
	private YearMonth date;
	private static File f = new File("Notes.xml");
	
	public Note(String c, YearMonth yearMonth)
	{
		this.content = c;
		this.date = yearMonth;
		this.id = IdGenerator.newID();
	}
	public Note(String id, String content)
	{
		this.id = Integer.parseInt(id);
		this.content = content;
	}
	public Note(int i)
	{
		this.id = i;
	}
	public int getId()
	{
		return this.id;
	}
	public void setId(int i)
	{
		this.id = i;
	}
	public String getDate()
	{
		return this.date.toString();
	}
	public void setDate(String d) throws ParseException
	{
		this.date =YearMonth.parse(d);
	}
	public String getContent()
	{
		return this.content;
	}
	public void setContent(String s)
	{
		this.content = s;
	}
	
	private static Node getPendingElement(Document doc, Element e, String name, String value)
	{
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
	private static Node getNote(Document doc, String id, String content, YearMonth date)
	{
		Element note = doc.createElement("note");
		note.setAttribute("id", id);
		note.appendChild(getPendingElement(doc, note, "date", date.toString()));
		note.appendChild(getPendingElement(doc, note, "content", content));
		
		return note;
	}
	
	
	public static void deleteNote(int nid)
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
}
