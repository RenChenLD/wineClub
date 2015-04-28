package RESTfulService;

public class SubInfo {
	private String email, name, twitter, facebook, phone, address;
	
	public SubInfo() 
	{
		
	}
	public SubInfo(String email)
	{
		this.email = email;
	}
	public SubInfo(String email, String name, String phone)
	{
		this.email = email;
		this.name = name;
		this.phone = phone;
	}
	public SubInfo(String email, String name, String social, String phone, int i)
	{
		this.email = email;
		this.name = name;
		if(i==0)
		{
			this.twitter = "(EMPTY)";
			this.facebook = social;
		}else
		{
			this.twitter = social;
			this.facebook = "(EMPTY)";
		}
		this.phone = phone;
	}
	public SubInfo(String email,String name, String twitter, String facebook, String phone)
	{
		this.email = email;
		this.name = name;
		this.twitter = twitter;
		this.facebook = facebook;
		this.phone = phone;
	}

//	private static Node getAccountElements(Document doc, Element element, String name, String value) {
//        Element node = doc.createElement(name);
//        node.appendChild(doc.createTextNode(value));
//        return node;
//    }
//	
//	private static Node getAccount(Document doc, int id, String email, String name, String twitter, String facebook, String phone, String address)
//	{
//		Element account = doc.createElement("account");
//		account.setAttribute("id", Integer.toString(id));
//		account.appendChild(getAccountElements(doc, account, "email", email));
//		account.appendChild(getAccountElements(doc, account, "name", name));
//		account.appendChild(getAccountElements(doc, account, "twitter", twitter));
//		account.appendChild(getAccountElements(doc, account, "facebook", facebook));
//		account.appendChild(getAccountElements(doc, account, "phone", phone));
//		account.appendChild(getAccountElements(doc, account, "address", address));
//		return account;
//	}
	
	public boolean checkBeforeStore()
	{
		if(this.email == null || this.name == null || this.twitter == null || this.facebook == null || this.phone == null)
			return false;
		else 
			return true;
	}
//	public static void addAccount(account a)
//	{
//        try {
//        	DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder icBuilder = icFactory.newDocumentBuilder();
//            Node mainRootElement;
//            Document doc;
//            File f = new File("accounts.xml");
//            if(!f.exists())
//            {
//            	f.createNewFile();
//				doc = icBuilder.newDocument();
//				mainRootElement = doc.createElementNS(f.toURI().toString(), "accounts");
//				doc.appendChild(mainRootElement);
//            }else
//            {
//            	doc = icBuilder.parse(f);
//            	mainRootElement = doc.getElementsByTagName("accounts").item(0);
//            }
//            // append child elements to root element
//            mainRootElement.appendChild(getAccount(doc, a.id, a.email, a.name, a.twitter, a.facebook, a.phone, a.address));
//            
//            // output DOM XML to console 
//            
//            	Transformer transformer = TransformerFactory.newInstance().newTransformer();
//		        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
//            	transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); 
//            	DOMSource source = new DOMSource(doc);
//            	StreamResult console = new StreamResult(f);
//            	
//            	transformer.transform(source, console);
//            	
//            	System.out.println("\nXML DOM Created Successfully..");
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//	}
//	
//	public static account searchAccount(String id)
//	{
//		account a = new account();
//		File f = new File("accounts.xml");
//		try{
//			DocumentBuilderFactory  builderFactory =  DocumentBuilderFactory.newInstance();  
//	        DocumentBuilder   builder = builderFactory.newDocumentBuilder();  	             
//	        Document  doc = builder.parse(f);  
//	        doc.getDocumentElement().normalize();
//	        NodeList nList = doc.getElementsByTagName("account");
//	        for(int i = 0; i<nList.getLength(); i++)
//	        {
//	        	Node node = nList.item(i);
//	        	Element ele = (Element) node;
//	        	if(node.getNodeType() == Element.ELEMENT_NODE)
//	        	{
//	        		if(ele.getAttribute("id").equals(id))
//	        			{
//	        				a.email=ele.getElementsByTagName("email").item(0).getTextContent();
//	        				a.name=ele.getElementsByTagName("name").item(0).getTextContent();
//	        				a.twitter=ele.getElementsByTagName("twitter").item(0).getTextContent();
//	        				a.facebook=ele.getElementsByTagName("facebook").item(0).getTextContent();
//	        				a.phone=ele.getElementsByTagName("phone").item(0).getTextContent();
//	        				a.address = ele.getElementsByTagName("address").item(0).getTextContent();
//	        			}
//	        	}
//	        }
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		return a;
//	}
//	
//	public static void updateAccount(String ID, account a)
//	{
//		try{
//			DocumentBuilderFactory  builderFactory =  DocumentBuilderFactory.newInstance();  
//	        DocumentBuilder   builder = builderFactory.newDocumentBuilder();  	             
//	        Document  doc = builder.parse("accounts.xml");
//	        NodeList nList = doc.getElementsByTagName("account");
//	        for(int i = 0; i<nList.getLength(); i++)
//	        {
//	        	Node node = nList.item(i);
//	        	Element ele = (Element) node;
//	        	if(node.getNodeType() == Element.ELEMENT_NODE)
//	        	{
//	        		if(ele.getAttribute("id").equals(ID))
//	        		{
//        				ele.getElementsByTagName("email").item(0).setTextContent(a.email);
//        				ele.getElementsByTagName("name").item(0).setTextContent(a.name);
//        				ele.getElementsByTagName("twitter").item(0).setTextContent(a.twitter);
//        				ele.getElementsByTagName("facebook").item(0).setTextContent(a.facebook);
//        				ele.getElementsByTagName("phone").item(0).setTextContent(a.phone);
//        				ele.getElementsByTagName("address").item(0).setTextContent(a.address);	        				
//	        		}
//	        	}
//	        }
//	        Transformer transformer = TransformerFactory.newInstance().newTransformer();
//        	transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); 
//        	DOMSource source = new DOMSource(doc);
//        	StreamResult console = new StreamResult("accounts.xml");
//        	
//        	transformer.transform(source, console);
//        	
//        	System.out.println("\nXML DOM Created Successfully..");
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
	
	public String Email()
	{
		return this.email;
	}
	public void setEmail(String s)
	{
		this.email = s;
	}
	
	public String Name()
	{
		return this.name;
	}
	public void setName(String s)
	{
		this.name = s;
	}
	
	public String Twitter()
	{
		return this.twitter;
	}
	public void setTwitter(String s)
	{
		this.twitter = s;
	}
	
	public String Facebook()
	{
		return this.facebook;
	}
	public void setFacebook(String s)
	{
		this.facebook = s;
	}
	
	public String Phone()
	{
		return this.phone;
	}
	public void setPhone(String s)
	{
		this.phone = s;
	}
	
	public Address Address()
	{
		String[] s = this.address.split(",");
		return new Address(s[0], s[1], s[2], s[3]);
	}
	public void setAddress(String add)
	{
		this.address = add;
	}
	
	public void PrintToString()
	{
		System.out.println("Email: "+this.email);
		System.out.println("Name: "+this.name);
		System.out.println("Twitter: "+this.twitter);
		System.out.println("Facebook: "+this.facebook);
		System.out.println("Phone: "+this.phone);
		System.out.println("Address: "+this.address);
		
	}
}
