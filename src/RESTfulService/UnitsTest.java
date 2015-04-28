package RESTfulService;

import static org.junit.Assert.*;

import java.time.YearMonth;
import java.util.List;

import org.junit.Test;

public class UnitsTest {

	Subscriber s = new Subscriber("rchen00@gamil.com", "John Washon", "1231321223", new Address());
	Subscriber s1 = new Subscriber("rchen00@gamil.com", "John Washon", "1231321223", new Address());
	Subscriber s2 = new Subscriber("rchen00@gamil.com", "John Washon", "1231321223", new Address());
	Subscriber s0 = new Subscriber(33);
	SubInfo a = new SubInfo("rchen99@gamil.com", "Sherlock Holmes", "9999999999");
	Shipment sh = new Shipment();

	
	@Test
	public void testAddSubscriber()
	{
		s.saveSubInfo();
		s1.saveSubInfo();
		s2.saveSubInfo();	
	}
	
	@Test
	public void testUpdateSubscriber()
	{
		s0.setAccount(a);
		s0.updateAccount();
	}
	@Test
	public void testAddSubscriberShipment()
	{
		s0.addShipment();
		s0.addShipmentHistory();
	}
	@Test
	public void testSearchSubscriber()
	{
		Subscriber s3;
		s3 = Subscriber.searchSubscriber(33);
		System.out.print(s3.getId() + "  " + s3.getAccount().Email() + "  " + s3.getAccount().Name());
	}
	@Test
	public void testAddWineNote()
	{
		Subscriber s4;
		s4 = Subscriber.searchSubscriber(33);
		s4.setMS(new AR());
		Note n = new Note("Hello", YearMonth.now());
		Wine[] ws =s4.getWines();
		List<Note> ns = s4.getNotes();
		for(int i = 0; i< ws.length; i++)
			System.out.println(ws[i].getId() + " " +ws[i].getLabelName());
		for(int i = 0; i< ns.size(); i++)
			System.out.println(ns.get(i).getId() + " " +ns.get(i).getContent());
		
		
	}

}
