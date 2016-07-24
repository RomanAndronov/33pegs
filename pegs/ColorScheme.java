package pegs;

import java.awt.Color;

class ColorScheme
{
	public
	ColorScheme( Color b, Color c, Color cn, Color p, Color m )
	{
		bckgColor = b;
		cellColor = c;
		cellNumColor = cn;
		pegColor = p;
		mvsColor = m;
	}

	public Color			bckgColor;
	public Color			cellColor;
	public Color			cellNumColor;
	public Color			pegColor;
	public Color			mvsColor;
}
