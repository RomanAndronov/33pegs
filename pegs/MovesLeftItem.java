package pegs;

import java.text.DecimalFormat;

/*
  Stored in the keyboard interface list
 */
class MovesLeftItem
{
	public
	MovesLeftItem( int src, int over, int dest, DecimalFormat dfrmt )
	{
		this.src = src;
		this.over = over;
		this.dest = dest;
		this.dfrmt = dfrmt;
	}

	public String
	toString()
	{
		return " " +
			dfrmt.format( src + 1 ) + 
			" to " + 
			dfrmt.format( dest + 1 ) +
			" ";
	}

	public final int		src;
	public final int		over;
	public final int		dest;

	private DecimalFormat		dfrmt;
}
