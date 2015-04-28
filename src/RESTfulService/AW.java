package RESTfulService;

public class AW extends MonthlySelection{
	public AW()
	{
		super.mst = MonthlySelectionType.AW;
	}
	
	@Override
	public void addWine(Wine w)
	{
		for(int i = this.getWines().size(); i<6; i=this.getWines().size() )
			if(w.getWineType().equals(WineType.WHITE))
				this.insertWine(w);
			else
				System.out.println("This is not white wine.");
	}
	@Override
	public Wine[] Wines()
	{
		Wine[] ws = new Wine[6];
		for(int i = 0; i<6; i++)
			ws[i] = new  Wine(MonthlySelectionType.AW);
		return ws;
	}
}
