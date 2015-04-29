package RESTfulService;

public class AR extends MonthlySelection {
	public AR()
	{
		super.mst = MonthlySelectionType.AR;
	}
	
	@Override
	public void addWine(Wine w)
	{
		for(int i = this.getWines().size(); i<6; i=this.getWines().size() )
			if(w.getWineType().equals(WineType.RED))
				this.insertWine(w);
			else
				System.out.println("This is not red wine.");
	}
	@Override
	public Wine[] Wines()
	{
		Wine[] ws = new Wine[6];
		for(int i = 0; i<6; i++)
			ws[i] = new  Wine(MonthlySelectionType.AR);
		return ws;
	}
}
