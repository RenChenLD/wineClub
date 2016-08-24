import java.text.ParseException;
import java.time.Year;
import java.time.YearMonth;
import org.junit.Test;

import com.google.gson.Gson;

public class GeneralTest {

	Subscriber s = new Subscriber("rchen00@gamil.com", "John Washon", "1231321223", new Address());
	Subscriber s1 = new Subscriber("rchen00@gamil.com", "John Washon", "1231321223", new Address());
	Subscriber s2 = new Subscriber("rchen00@gamil.com", "John Washon", "1231321223", new Address());
	Subscriber s0 = new Subscriber(0);
	SubInfo a = new SubInfo("rchen99@gamil.com", "Sherlock Holmes", "9999999999");
	Shipment sh = new Shipment();
	Gson gson = new Gson();
	String json;
	@Test
	public void testAddress()
	{
		Address addr = new Address();
		Address addr2 = new Address("3203 S State St", "Chicago", "IL", "60616");
		System.out.println(addr.Street());
		System.out.println(addr.City());
		System.out.println(addr.State());
		System.out.println(addr.Zip());
		System.out.println(addr2.toString());
	}
	
	@Test
	public void testAdmin()
	{
		Admin ad1 = new Admin("Ben");
		Admin ad2 = new Admin(0);
		ad1.setCreate_date("2015-01");
		ad1.setPid(0);
		System.out.println(ad2.getId());
		ad2.setName("Chris");
		System.out.println(ad2.getName());
		System.out.println(ad1.getCreate_date());
		System.out.println(ad1.getPid());
	}
	
	@Test
	public void testAddAdmin()
	{
		Admin ad1 = new Admin("Ben");
		ad1.setPid(0);
		Admin ad2 = new Admin(0);
		ad2.saveAdmin(ad1);
	}
	
	@Test
	public void testSearchAdmin()
	{
		json = gson.toJson(Admin.getAdmin(0));
		System.out.println(json);
	}
	
	@Test
	public void testGetAllAdmin()
	{
		json = gson.toJson(Admin.getAllAdmin());
		System.out.print(json);
	}
	
	@Test
	public void testUpdateAdmin()
	{
		Admin.updateAdmin(0, "Jack");
		json = gson.toJson(Admin.getAdmin(0));
		System.out.print(json);
	}

	@Test
	public void testGetAllSubscriber()
	{
		json = gson.toJson(Subscriber.getAllSubscriber());
		System.out.println(json);
	}
	
	@Test
	public void testNote() throws ParseException
	{
		Note n1 = new Note("Hello N1", YearMonth.now());
		Note n2 = new Note("123","Hello N2");
		Note n3 = new Note(9);
		n3.setId(8);
		n2.setDate(YearMonth.now().toString());
		System.out.println(n2.getDate());
		n3.setContent("Hello N3");
		n3.setDate(YearMonth.now().toString());
		System.out.println(n1.getContent());
	}
	
	@Test
	public void testReceipt() throws ParseException
	{
		Receipt r1 = new Receipt();
		Receipt r2 = new Receipt("Ann");
		System.out.println(r2.getId() + " " + r2.getDate() +  " " + r2.getName() + " " + r2.getSubID());
		r1.setId(0);
		r1.setDate(YearMonth.now().toString());
		r1.setName("John Washon");
		r1.setSubID(Integer.toString(Subscriber.searchSubscriber(r1.getName()).getId()));
	}
	
	@Test
	public void testSaveAndLoadReceipt()
	{
		Receipt r = new Receipt("Ann");
		Receipt.saveReceipt(r);
		json = gson.toJson(Receipt.getReceipt(r.getId()));
		System.out.println(json);
		json = gson.toJson(Receipt.getAllReceipt());
		System.out.println(json);
	}
	
	@Test
	public void testShipment()
	{
		Shipment sh = new Shipment(1000);
		Shipment sh1 = new Shipment(new AR(),new Address());
		Shipment sh2 = new Shipment(new AW(),new Address());
		Shipment sh3 = new Shipment(new RW(),new Address());
		sh.setAddress(new Address());
		sh.setMST(MonthlySelectionType.AR);
		sh.setMST(MonthlySelectionType.AW);
		sh.setMST(MonthlySelectionType.RW);
		sh.setStatus("Delivered");
		sh.setSM("2015-01");
		System.out.println(sh1.getId());
		System.out.println(sh2.getStatus());
		System.out.println(sh3.getSM());
		System.out.println(sh.getAddress());		
	}
	
	@Test
	public void testSaveAndLoadShipment()
	{
		Shipment sh = new Shipment(new AR(),new Address());
		sh.saveShipment();
		Shipment.searchShipment(Integer.toString(sh.getId()));
		Note n = new Note("Hello N1", YearMonth.now());
		sh.addNote(n);
		sh.searchNote();
		sh.searchNote(n.getId());
		n.setContent("Hello N4");
		sh.updateShipmentNote(n);
		sh.deleteNote(n.getId());
		
	}
	
	@Test
	public void testSubInfo()
	{
		SubInfo si = new SubInfo();
		si.setEmail("wersdf@gmail.com");
		si.setName("wersdf");
		si.setPhone("1238769234");
		si.setTwitter("sdfwe");
		si.setFacebook("qazxcf");
		System.out.println(si.Email());
		System.out.println(si.Name());
		System.out.println(si.Phone());
		System.out.println(si.Twitter());
		System.out.println(si.Facebook());
	}
	
	@Test
	public void testWine()
	{
		Wine w = new Wine(14);
		Wine w2 = new Wine();
		Wine w3 = new Wine(WineType.RED,WineVariety.TABLE,"The Mission","Merlot","Napa","USA","Sterling",Year.of(2014));
		w3.setCountry("USA");
		System.out.println(w3.getContry());
		w3.setWineType("WHITE");
		System.out.println(w3.getWineType());
		w3.setWineVariety("SWEET");
		System.out.println(w3.getWineVariety());
		w3.setGrape("Cabernet Sauvignon");
		System.out.println(w3.getGrape());
		w3.setLabelName("Joseph Phelps Cabernet Sauvignon 2012");
		System.out.println(w3.getLabelName());
		w3.setMaker("Joseph Phelps");
		System.out.println(w3.getMaker());
		w3.setRegion("Napa");
		System.out.println(w3.getRegion());
		w3.setYear("2012");
		System.out.println(w3.getYear());
		w3.setNumberOfRatings("20");
		System.out.println(w3.getNumberOfRatings());
		w3.setRating("4");
		System.out.println(w3.getRating());
		w.addRating(5);
		System.out.println(w3.getRating());
		w3.saveWineList();
		w2 = Wine.getWineDetail(Integer.parseInt(w3.getId()));
		Note n = new Note("Hello N1", YearMonth.now());
		w3.addWineNote(n);
		w3.getNote(n.getId());
		n.setContent("Hello N4");
		w3.updateWineNote(n);
		w3.deleteWineNote(n.getId());
	}
	
	@Test
	public void testSaveSubscriber()
	{
		Address addr = new Address();
        Subscriber s = new Subscriber("itche@gmail.com", "itche", "3128764950", addr);
        s.setDefaultMS();
        s.addShipment();
        s.saveSubInfo();
		s1.setMS(new AR());
		System.out.println(s2.getMS());
		System.out.println(s.getAccount());
		System.out.println(s.getAddress());
		System.out.println(s.getWines());
//		System.out.println(s.getNotes());
//		System.out.println(s.getShipments());
	}
	
	@Test
	public void testUpdateSub()
	{
		Address addr = new Address();
     
        Subscriber s = new Subscriber(Subscriber.searchSubscriber("itche").getId());
        SubInfo si = new SubInfo("itche@gmail.com", "itche", "3128764950");
        s.setAccount(si);
        s.setAddress(addr);
        s.updateAccount();
	}
	
	@Test
	public void testGestSub()
	{
		Subscriber.searchSubscriber(0);
	}
	
	@Test
	public void testGetShipments()
	{
		Subscriber s = Subscriber.searchSubscriber(0);
		s.getShipments();
		Gson gson = new Gson();
		String json = gson.toJson(s.getShipments());
		System.out.println(json);
	}
	@Test
	public void testGetShipmet()
	{
		json = gson.toJson(Shipment.searchShipment(Integer.toString(0)));
		System.out.println(json);
	}
	@Test
	public void testRW()
	{
		RW r =new RW();
		r.addWine(new Wine());
		r.addWine(new Wine());
		r.addWine(new Wine());
		r.addWine(new Wine());
		json = gson.toJson(r.Wines());
		System.out.println(r.getSelectMonth());
		r.setSelectionMonth(YearMonth.now().toString());
		System.out.println(json);
	}
	@Test
	public void testAR()
	{
		AR r =new AR();
		json = gson.toJson(r.Wines());
		Wine e = new Wine();
		e.setWineType("RED");
		r.addWine(new Wine());
		System.out.println(json);
		
	}
	@Test
	public void testAW()
	{
		AW r =new AW();
		json = gson.toJson(r.Wines());
		Wine e = new Wine();
		e.setWineType("WHITE");
		r.addWine(e);
		System.out.println(json);
	}
	@Test
	public void  testMonthlySelection()
	{
		RW r =new RW();
		MonthlySelection.saveMonthlySelection(r, r.Wines());
		json = gson.toJson(MonthlySelection.getAllMS());
		System.out.println(json);
		System.out.println(json);
	}
}
