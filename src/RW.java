

public class RW extends MonthlySelection {
	public RW()
	{
		super.mst = MonthlySelectionType.RW;
	}
	private int numOfRed()
	{
		int j =0;
		for(int i = 0; i<super.getWines().size(); i++)
		{
			if(super.getWines().get(i).getWineType() == WineType.RED)
				j++;
		}
		return j;
	}
	@Override
	public void addWine(Wine w)
	{
		int size = this.getWines().size();
		if(size<6)
			{
				if(numOfRed()<3 && w.getWineType()==WineType.RED)
				{
					this.insertWine(w);
				}else if(w.getWineType() == WineType.WHITE)
				{
					this.insertWine(w);
				}
			}
	}
	@Override
	public Wine[] Wines()
	{
		Wine[] ws = new Wine[6];
		for(int i = 0; i<6; i++)
			ws[i] = new  Wine(MonthlySelectionType.RW);
		return ws;
	}
}
