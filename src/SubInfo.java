

public class SubInfo {
	private String email, name, twitter, facebook, phone;
	
	public SubInfo() 
	{
		
	}
	
	public SubInfo(String email, String name, String phone)
	{
		this.email = email;
		this.name = name;
		this.phone = phone;
	}
	
	
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
}
