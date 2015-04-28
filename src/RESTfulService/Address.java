package RESTfulService;

public class Address {
	 private String street, city, state, zip;
	    
	    public Address() {
	    	 this.street = "123 Main ST, Apt 2F";
	         this.city = "Anytown";
	         this.state = "Anystate";
	         this.zip = "12345";
	    }
	    
	    public Address(String street, String city, String state, String zip) {
	    	this.street = street;
	    	this.city = city;
	    	this.state = state;
	    	this.zip = zip;
	    }
	    public String Street()
	    {
	    	return this.street;
	    }
	    public String City()
	    {
	    	return this.city;
	    }
	    public String State()
	    {
	    	return this.state;
	    }
	    public String Zip()
	    {
	    	return this.zip;
	    }
	    
	    public String toString()
	    {
	    	return this.street + ", " + this.city + ", " + this.state + ", " + this.zip;
	    }
}
