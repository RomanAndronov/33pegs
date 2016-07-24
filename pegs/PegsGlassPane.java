package pegs;

import java.awt.AlphaComposite;
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class PegsGlassPane
	extends JPanel
	implements AWTEventListener
{
	public
	PegsGlassPane( Pegs pegs )
	{
		super( null );
		this.pegs = pegs;
		setOpaque( false );
	}

	public void
	setPegColor( Color pc )
	{
		pegColor = pc;
	}

	public void
	paintComponent( Graphics g )
	{
		int			r;
		Graphics2D		g2;

		super.paintComponent( g );

		if ( !pegs.inDragMode )
		{
			return;
		}

		if ( gpmp == null )
		{
			return;
		}


		r = pegs.cells[ pegs.draggedPeg ].getCellView().getRadius();
		g2 = ( Graphics2D )g;

		g2.setColor( pegColor );
		g2.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.5f ) );
		g2.fillOval( gpmp.x, gpmp.y, r, r );
		g2.dispose();
	}

	public void 
	eventDispatched( AWTEvent ae )
	{
		MouseEvent		me;
		MouseEvent		converted;

		if ( !( ae instanceof MouseEvent ) )
		{
			return;
		}

		me = ( MouseEvent )ae;
		if ( !SwingUtilities.isDescendingFrom( me.getComponent(), pegs ) )
		{
			return;
		}

		if ( me.getID() == MouseEvent.MOUSE_EXITED && me.getComponent() == pegs )
		{
			gpmp = null;
		}
		else
		{
			converted = SwingUtilities.convertMouseEvent(
				me.getComponent(),
				me,
				this ); 
			gpmp = converted.getPoint();
		}

		repaint();
	}

	public boolean 
	contains( int x, int y ) 
	{
		int		nml = getMouseListeners().length;
		int		nmml = getMouseMotionListeners().length;
		int		nmwl = getMouseWheelListeners().length;
		Cursor		dfltc = Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR );
		Cursor		curc = getCursor();

		if ( nml == 0 && nmml == 0 && nmwl == 0 && curc == dfltc )
		{ 
			return false; 
		}

		return super.contains( x, y ); 
	} 

	private Pegs			pegs;
	private Point			gpmp;
	private Color			pegColor;
}
