package pegs;

/*
  Legal moves, specific for each cell
 */
class Move
{
	public
	Move( int over, int dest )
	{
		this.over = over;
		this.dest = dest;
	}

	public final int		over;
	public final int		dest;
}
