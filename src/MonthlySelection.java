
import java.io.File;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

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

public abstract class MonthlySelection {
	protected MonthlySelectionType mst;
	private YearMonth ym;
	private List<Wine> ws = new ArrayList<Wine> ();
	private float price;
	private int id;
	private static File f = new File("/home/monthlySelections.xml");
	abstract void addWine(Wine w);
	abstract Wine[] Wines() ;
	
//	public boolean isMatch(String kw) {
//		Iterator<Wine> it = this.ws.iterator();
//		while (it.hasNext()) {
//			Wine w = it.next();
//			if (w.isMatch(kw)) return true;
//		}
//		return false;
//	}
	
	public String getSelectMonth()
	{
		return this.ym.toString();
	}
	public void setSelectionMonth(String s)
	{
		this.ym = YearMonth.parse(s);
	}
	
	public List<Wine> getWines()
	{
		return this.ws;
	}
	public void setWines(List<Wine> e)
	{
		this.ws = e;
	}
	public void insertWine(Wine e)
	{
		this.ws.add(e);
	}
	public MonthlySelection() {
		this.ym = YearMonth.now().plusMonths(1);	// next month's selection
		this.id = IdGenerator.newID();
	}
	
	public MonthlySelection(String ym) {	// Must be in the yyyy-mm format
		this.ym = YearMonth.parse(ym);
		this.id = IdGenerator.newID();
	}
	
	public static void saveMonthlySelection(MonthlySelection ms, Wine[] ws)
	{
		switch(ms.mst)
		{
			case AR:
				ms = new AR();
				break;
			case AW:
				ms = new AW();
				break;
			case RW:
				ms = new RW();
				break;
			default:
				ms = new RW();
				break;
		}
		try{
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = builderFactory.newDocumentBuilder();					Document doc;
				Node mainRootElement;
				if(!f.exists())
				{
					f.createNewFile();
					doc = builder.newDocument();
					mainRootElement = doc.createElementNS(f.toURI().toString(), "monthlySelections");
					doc.appendChild(mainRootElement);
				}else
				{
					doc = builder.parse(f);
					mainRootElement = doc.getElementsByTagName("monthlySelections").item(0);
				}
				
				mainRootElement.appendChild(getMonthlySelection(doc, Integer.toString(ms.id), ms.getSelectMonth(), ms.mst.toString()));
				
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
	
	private static Node	getMonthlySelection(Document doc, String id, String sm, String type)
	{
		Element ms = doc.createElement("monthlySelection");
		ms.setAttribute("id", id);
		ms.appendChild(getPendingElement(doc, ms, "selection_month", sm));
		ms.appendChild(getPendingElement(doc, ms, "type", type));
		return ms;
	}
	public static List<MonthlySelection> getAllMS()
	{
		ArrayList<MonthlySelection> msList = new ArrayList<MonthlySelection>();
		MonthlySelection ms;
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
					switch(ele.getElementsByTagName("type").item(0).getTextContent())
					{
					case "AR":
						ms = new AR();
						break;
					case "AW":
						ms = new AW();
						break;
					case "RW":
						ms = new RW();
						break;
						default:
							ms = new RW();
							break;
					}
					ms.id = Integer.parseInt(ele.getAttribute("id").toString());
					ms.ym = YearMonth.parse(ele.getElementsByTagName("selectMonth").item(0).getTextContent());
					ms.mst = MonthlySelectionType.valueOf(ele.getElementsByTagName("type").item(0).getTextContent());
					msList.add(ms);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return msList;
	}

	
}
