package RESTfulService;

import java.io.File;
import java.time.YearMonth;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class test {
	public static void main(String[] args)
	{
//		Shipment sht = new Shipment();
//		Note n = new Note("Hello", YearMonth.now());
//		sht.addNote(n);
//		sht.setAddress(new Address());
//		System.out.println(sht.getId() + sht.getAddress().toString()+ sht.getSM()+ sht.getStatus());
//		for(int i = 0; i<sht.getWines().length;i++)
//		{
//			System.out.println(sht.getWines()[i].getLabelName());
//
//		}
//		System.out.println(sht.getNotes().size());
//		sht.saveShipment();
//		sht.saveShipmentNote(n);
//		Shipment sh = Shipment.searchShipment("0");
//		Note n1 = new Note("World", YearMonth.now());
//		sh.addNote(n1);
//		sh.saveShipmentNote(n1);
//		Subscriber s = new Subscriber("rchen00@gamil.com", "John Washon", "1231321223", new Address());
//		Subscriber s1 = new Subscriber("rchen00@gamil.com", "John Washon", "1231321223", new Address());
//		Subscriber s2 = new Subscriber("rchen00@gamil.com", "John Washon", "1231321223", new Address());
//		Subscriber s0 = new Subscriber(33);
//		SubInfo a = new SubInfo("rchen99@gamil.com", "Sherlock Holmes", "9999999999");
//		Shipment sh = new Shipment();
//		s.saveSubInfo();
//		s1.saveSubInfo();
//		s2.saveSubInfo();
//		s0.updateAccount();
//		s0.addShipment(sh);
//		s0.addShipmentHistory();
//		Wine w =new Wine();
//		w.saveWineList();
//		Note n = new Note("Hello",YearMonth.now());
//		w.addWineNote(n);
//		String s = "Email=rchen37@hawk.iit.edu&Name=Ren Chen&Phone=3129873456&Street=3201 S State St&City=Chicago&State=IL&Zip=60616";
//		JSONArray arr = JSONArray.fromObject(s);
//        JSONObject object = JSONObject.fromObject(arr.get(0));
//        System.out.println(object.get("Email"));
		System.out.println(Subscriber.searchSubscriber(1).getAccount().Email());
		Subscriber s = Subscriber.searchSubscriber(1);
		System.out.println(s.getAccount().Name());
		
	}
}
