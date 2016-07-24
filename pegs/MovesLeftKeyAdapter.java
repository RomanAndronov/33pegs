package pegs;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
        
class MovesLeftKeyAdapter
	extends KeyAdapter
{
	public MovesLeftKeyAdapter( Pegs pegs )
	{
		super();

		this.pegs = pegs;
	}

	public void
	keyPressed( KeyEvent ke )
	{
		int			i;
		MovesLeftItem		mli;

		if ( ke.getKeyCode() != KeyEvent.VK_ENTER )
		{
			return;
		}

		i = pegs.lstMovesLeft.getSelectedIndex();
		if ( i < 0 )
		{
			return;
		}

		mli = ( MovesLeftItem )pegs.lmMovesLeft.get( i );
		pegs.moveThisPeg( mli.src, mli.over, mli.dest );
	}


	private Pegs			pegs;
}
