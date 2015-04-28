package RESTfulService;

public class AR extends MonthlySelection {
	public AR()
	{
		super.mst = MonthlySelectionType.AR;
	}
	
	@Override
	public void addWine(Wine w)
	{
		for(int i = this.getWines().size(); i<6; this.getWines().size() )
			this.insertWine(w);
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
