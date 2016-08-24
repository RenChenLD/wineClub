

import java.text.ParseException;
import java.time.YearMonth;

public class Note {
	private int id;
	private String content;
	private YearMonth date;
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
}
