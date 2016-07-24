package pegs;

/*
  Algorithmic cell representation
 */
class Cell
{
	public
	Cell( int n )
	{
		cellNumber = n;
	}

	public int
	getCellNumber()
	{
		return cellNumber;
	}

	public boolean
	hasPeg()
	{
		return hasPeg;
	}

	public void
	addPeg()
	{
		hasPeg = true;
		view.addPeg();
	}

	public void
	rmPeg()
	{
		hasPeg = false;
		view.rmPeg();
	}

	public CellView
	getCellView()
	{
		return view;
	}

	public void
	setView( CellView cv )
	{
		view = cv;
	}

	private final int		cellNumber;
	private boolean			hasPeg;
	private CellView		view;
}
