
import java.io.File;
import java.time.Year;
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

public class Wine {
	private WineType wt;
	private WineVariety wv;
	private String labelName;
	private String grape;	// e.g. Merlot, Chardonnay, Riesling, etc.
	private String region;	// e.g. Napa, Russian Valley, etc.
	private String country; // e.g. France, USA, Australia, Chile
	private String maker;	// the wine maker, e.g. Sterling, Krupp Brother, etc.
	private Year year;		// Vintage year
	private static int numberOfRatings=0;
	private static float rating = 0;
	private int ID;
	private static List<Note> n;
	private static File file = new File("/home/wineList.xml");
	
	Wine(int id)
	{
		this.ID = id;
	}
	Wine()//default information
	{
		this.wt = WineType.RED;
		this.wv = WineVariety.SPARKING;
		this.labelName = "Lambrusco";
		this.grape = "Rhubarb";
		this.region = "Romagna";
		this.country = "Italy";
		this.maker = "Emila";
		this.year = Year.parse("2010");
		this.ID = IdGenerator.newID();
	}
	Wine(MonthlySelectionType mst)
	{
		switch(mst){
		case AR:
			this.wt = WineType.RED;
			this.wv = WineVariety.SPARKING;
			this.labelName = "Lambrusco";
			this.grape = "Rhubarb";
			this.region = "Romagna";
			this.country = "Italy";
			this.maker = "Emila";
			this.year = Year.parse("2010");
			this.ID = IdGenerator.newID();
			break;
		case AW:
			this.wt = WineType.WHITE;
			this.wv = WineVariety.SPARKING;
			this.labelName = "Lambrusco1";
			this.grape = "Rhubarb";
			this.region = "Romagna";
			this.country = "Italy";
			this.maker = "Emila";
			this.year = Year.parse("2010");
			this.ID = IdGenerator.newID();
			break;
		case RW:
			this.wt = WineType.RED;
			this.wv = WineVariety.SPARKING;
			this.labelName = "Lambrusco2";
			this.grape = "Rhubarb";
			this.region = "Romagna";
			this.country = "Italy";
			this.maker = "Emila";
			this.year = Year.parse("2010");
			this.ID = IdGenerator.newID();
			break;
		}
	}
	Wine(WineType t, WineVariety v, String ln, String g, String r, String c, String m, Year y)
	{
		this.wv = v;
		this.wt = t;
		this.labelName = ln;
		this.grape = g;
		this.region = r;
		this.country = c;
		this.maker = m;
		this.year = y;
		this.ID = IdGenerator.newID();
	}
	
	public String getId()
	{
		return Integer.toString(this.ID);
	}
	public void setId(String wid)
	{
		this.ID = Integer.parseInt(wid);
	}
	public WineVariety getWineVariety()
	{
		return this.wv;
	}
	public void setWineVariety(String s)
	{
		if(s.equals("TABLE"))
		this.wv =  WineVariety.TABLE;
		else if(s.equals("SWEET"))
			this.wv = WineVariety.SWEET;
		else
			this.wv = WineVariety.SPARKING;
	}
	public WineType getWineType()
	{
		return this.wt;
	}
	public void setWineType(String s)
	{
		if(s.equals("RED"))
			this.wt = WineType.RED;
		else if(s.equals("WHITE"))
			this.wt = WineType.WHITE;
		else
			this.wt = WineType.ROSE;
	}
	public String getLabelName()
	{
		return this.labelName;
	}
	public void setLabelName(String n)
	{
		this.labelName = n;
	}
	public String getGrape()
	{
		return this.grape;
	}
	public void setGrape(String s)
	{
		this.grape = s;
	}
	public String getRegion()
	{
		return this.region;
	}
	public void setRegion(String s)
	{
		this.region = s;
	}
	public String getContry()
	{
		return this.country;
	}
	public void setCountry(String s)
	{
		this.country = s;
	}
	public String getMaker()
	{
		return this.maker;
	}
	public void setMaker(String s)
	{
		this.maker = s;
	}
	public String getYear()
	{
		return this.year.toString();
	}
	public void setYear(String s)
	{
		try{
			this.year = Year.parse(s);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public int getNumberOfRatings() {
		return this.numberOfRatings;
	}
	public void setNumberOfRatings(String s)
	{
		this.numberOfRatings = Integer.parseInt(s);
	}
	public float getRating() {
		return this.rating;
	}
	public void setRating(String s)
	{
		this.rating = Float.parseFloat(s);
	}
	public List<Note> getNote()
	{
		return n;
	}
	public void setNote(Note note)
	{
		n.add(note);
	}
	public void addRating(int r)
	{
		numberOfRatings = numberOfRatings + 1;
		rating = rating*((float)(numberOfRatings - 1)/numberOfRatings) + (float)r/numberOfRatings;
	}
//	public boolean isMatch(String kw) {
//        if (isMatchVariety(kw) || isMatchType(kw) || isMatchLabel(kw) || isMatchGrape(kw) || isMatchRegion(kw) || isMatchCountry(kw) || isMatchMaker(kw) || isMatchYear(kw)) {
//                return true;
//        } else return false;
//	}
	    
//    private boolean isMatchVariety(String kw) {
//    	String regex = "(?i).*" + kw + ".*";
//        return this.wv.name().matches(regex);
//    }
//
//    private boolean isMatchType(String kw) {
//    	String regex = "(?i).*" + kw + ".*";
//        return this.wt.name().matches(regex);
//    }
//    
//    private boolean isMatchLabel(String kw) {
//        String regex = "(?i).*" + kw + ".*";
//        return this.labelName.matches(regex);
//    }
//    
//    private boolean isMatchGrape(String kw) {
//    	String regex = "(?i).*" + kw + ".*";
//        return this.grape.matches(regex);
//    }
//    
//    private boolean isMatchRegion(String kw) {
//    	String regex = "(?i).*" + kw + ".*";
//        return this.region.matches(regex);    }
//
//    private boolean isMatchCountry(String kw) {
//    	String regex = "(?i).*" + kw + ".*";
//        return this.country.matches(regex);
//    }
//
//    private boolean isMatchMaker(String kw) {
//    	String regex = "(?i).*" + kw + ".*";
//        return this.maker.matches(regex);
//    }
//
//    private boolean isMatchYear(String kw) {
//    	String regex = "(?i).*" + kw + ".*";
//        return this.year.toString().matches(regex);
//    }
    
    public static Wine getWineDetail(int wid)
    {
        Wine w = new Wine();
    	try{
    		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder builder = builderFactory.newDocumentBuilder();
    		Document doc = builder.parse(file);
    		doc.getDocumentElement().normalize();
	        NodeList nList = doc.getElementsByTagName("wine");
	        for(int i = 0; i<nList.getLength(); i++)
	        {
	        	Node node = nList.item(i);
	        	Element ele = (Element) node;
	        	if(node.getNodeType() == Element.ELEMENT_NODE)
	        	{
	        		if(ele.getAttribute("id").equals(Integer.toString(wid)))
    				{
	        			w.setLabelName(ele.getElementsByTagName("LabelName").item(0).getTextContent());
	        			w.setGrape(ele.getElementsByTagName("Grape").item(0).getTextContent());
	    				w.setRegion(ele.getElementsByTagName("Region").item(0).getTextContent());
	    				w.setCountry(ele.getElementsByTagName("Country").item(0).getTextContent());
	    				w.setMaker(ele.getElementsByTagName("Maker").item(0).getTextContent());
	    				w.setYear(ele.getElementsByTagName("Year").item(0).getTextContent());
	    				w.setId( ele.getAttribute("id"));
	    				w.setNumberOfRatings(ele.getElementsByTagName("NumberOfRatings").item(0).getTextContent());
	    				w.setRating(ele.getElementsByTagName("Ratings").item(0).getTextContent());
	    				w.setWineType(ele.getElementsByTagName("WineType").item(0).getTextContent());
	    				w.setWineVariety(ele.getElementsByTagName("WineVariety").item(0).getTextContent());
//	    				NodeList notes = ele.getElementsByTagName("notes");
//	    				if(notes != null)
//	    				for(int j = 0; j<notes.getLength(); j++)
//	    				{
//	    					Node node2 = notes.item(j);
//	    					Element ele2 = (Element) node2;
//	    					Note no = new Note(ele2.getAttribute("id"),ele2.getElementsByTagName("content").item(0).getTextContent());
//	    					no.setDate(ele.getElementsByTagName("date").item(0).getTextContent());
//	    					w.setNote(no);
//	    				}
    				}
	        	}
	        }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return w;
    }
    
    private static Node getWineElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
    private static Node getWine(Document doc, int id, String ln, String gra, String reg, String coun, String ma, Year y, int num, float ra, WineType t, WineVariety v, List<Note> n)
	{
		Element wine = doc.createElement("wine");
		wine.setAttribute("id", Integer.toString(id));
		wine.appendChild(getWineElements(doc, wine, "LabelName", ln));
		wine.appendChild(getWineElements(doc, wine, "Grape", gra));
		wine.appendChild(getWineElements(doc, wine, "Region", reg));
		wine.appendChild(getWineElements(doc, wine, "Country", coun));
		wine.appendChild(getWineElements(doc, wine, "Maker", ma));
		wine.appendChild(getWineElements(doc, wine, "Year", y.toString()));
		wine.appendChild(getWineElements(doc, wine, "NumberOfRatings", Integer.toString(num)));
		wine.appendChild(getWineElements(doc, wine, "Ratings", Float.toString(ra)));
		wine.appendChild(getWineElements(doc, wine, "WineType", t.name().toString()));
		wine.appendChild(getWineElements(doc, wine, "WineVariety", v.name().toString()));
		if(n != null)
		{
			Element notes = doc.createElement("notes");
			Element note = doc.createElement("note");
			for(int i = 0; i<n.size(); i++)
			{
				note.setAttribute("id", Integer.toString(n.get(i).getId()));
				note.appendChild(getWineElements(doc, note, "content",n.get(i).getContent()));
				notes.appendChild(note);
			}
			wine.appendChild(notes);
		}
		return wine;
	}
    public void saveWineList()
    {
    	try {
    	DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc;
		Node mainRootElement;
		
		if(file.exists())
		{
            builder = builderFactory.newDocumentBuilder();
            doc = builder.parse(file);
            mainRootElement = doc.getElementsByTagName("wines").item(0);
		}else
		{
			file.createNewFile();
			doc = builder.newDocument();
			mainRootElement = doc.createElementNS(file.toURI().toString(), "wines");
			doc.appendChild(mainRootElement);
		}
            // append child elements to root element
            mainRootElement.appendChild(getWine(doc, this.ID, this.labelName, this.grape, this.region, this.country, this.maker, this.year, this.numberOfRatings, this.rating, this.wt, this.wv, n));
            
            // output DOM XML to console 
            
            	saveToFile(doc);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addWineNote(Note n)
    {
    	try{
    		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder builder = builderFactory.newDocumentBuilder();
    		Document doc = builder.parse(file);
    		doc.getDocumentElement().normalize();
	        NodeList nList = doc.getElementsByTagName("wine");
	        for(int i = 0; i<nList.getLength(); i++)
	        {
	        	Node node = nList.item(i);
	        	Element ele = (Element) node;
	        	if(node.getNodeType() == Element.ELEMENT_NODE)
	        	{
	        		if(ele.getAttribute("id").equals(Integer.toString(this.ID)))
    				{
	        			if(ele.getElementsByTagName("notes") == null)
        				{
        					Element shipments = doc.createElement("notes");
        					Element e = doc.createElement("note");
        					e.setAttribute("id", Integer.toString(n.getId()));
        					e.appendChild(getWineElements(doc, e, "content", n.getContent()));
        					e.appendChild(getWineElements(doc, e, "date", n.getDate()));
        					shipments.appendChild(e);
        					ele.appendChild(shipments);
        				}else{
        					Node no = ele.getElementsByTagName("notes").item(0);
        					Node shipments = (Element)no;
        					Element e = doc.createElement("note");
    						e.setAttribute("id", Integer.toString(n.getId()));
    						e.appendChild(getWineElements(doc, e, "content", n.getContent()));
    						e.appendChild(getWineElements(doc, e, "date", n.getDate()));
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
//    public void saveWineNote(Note n)
//    {
//    	try{
//    		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
//    		DocumentBuilder builder = builderFactory.newDocumentBuilder();
//    		Document doc = builder.parse(file);
//    		doc.getDocumentElement().normalize();
//	        NodeList nList = doc.getElementsByTagName("notes");
//	        for(int i = 0; i<nList.getLength(); i++)
//	        {
//	        	Node node = nList.item(i);
//	        	Element ele = (Element) node;
//	        	if(node.getNodeType() == Element.ELEMENT_NODE)
//	        	{
//	        		if(ele.getAttribute("id").equals(Integer.toString(this.ID)))
//    				{
//	        			Element newNote = doc.createElement("note");
//	        			newNote.setAttribute("id", Integer.toString(n.getId()));
//	        			newNote.appendChild(getWineElements(doc, newNote, "content", n.getContent()));
//	        			newNote.appendChild(getWineElements(doc, newNote, "date", n.getDate()));
//	        			ele.appendChild(newNote);
//    				}
//	        	}
//	        }
//	        saveToFile(doc);
//    	}catch(Exception e)
//    	{
//    		e.printStackTrace();
//    	}
//    }
    public Note getNote(int nid)
    {
    	Note n = new Note(nid);
    	try{
    		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder builder = builderFactory.newDocumentBuilder();
    		Document doc = builder.parse(file);
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
	        			n.setContent(ele.getElementsByTagName("content").item(0).getTextContent());
	        			n.setDate(ele.getElementsByTagName("date").item(0).getTextContent());
    				}
	        	}
	        }
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return n;
    }
    public void updateWineNote(Note n)
    {
    	try{
    		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder builder = builderFactory.newDocumentBuilder();
    		Document doc = builder.parse(file);
    		doc.getDocumentElement().normalize();
	        NodeList nList = doc.getElementsByTagName("note");
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
    public void deleteWineNote(int nid)
    {
    	try{
    		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder builder = builderFactory.newDocumentBuilder();
    		Document doc = builder.parse(file);
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
        	transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); 
        	transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        	DOMSource source = new DOMSource(doc);
        	StreamResult console = new StreamResult(file);
        	
        	transformer.transform(source, console);
        	
        	System.out.println("\nXML DOM Created Successfully..");
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
//    public void printWineList()
//    {
//    	ArrayList list = this.getWineList();
//    	for(int i = 0; i <= list.size(); i++)
//    	{
//    		wine w = (wine) list.get(i);
//    		System.out.println("wine (" + w.ID +")");
//    		System.out.println("-----------------------------------------");
//    		System.out.println("LabelName: " + w.labelName);
//    		System.out.println("WineVariety: " + w.wv);
//    		System.out.println("WineType: " + w.wt);
//    		System.out.println("Details:");
//    		System.out.println("Grape: " + w.grape);
//    		System.out.println("Region: " + w.region);
//    		System.out.println("Country: " + w.country);
//    		System.out.println("Maker: " + w.maker);
//    		System.out.println("Year: " + w.year.toString());
//    		System.out.println("Rating: " + w.rating);
//    		System.out.println();
//    	}
//    }
}
