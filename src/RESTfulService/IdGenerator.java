package RESTfulService;
import java.util.concurrent.atomic.AtomicInteger;
public class IdGenerator {
	private static AtomicInteger nextID = new AtomicInteger();
	public static int newID()
	{
		return nextID.getAndIncrement();
	}
}
