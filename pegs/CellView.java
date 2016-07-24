package pegs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.event.MouseInputAdapter;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


/*
  Visual cell representation
 */
class CellView extends JLabel
{
	public
	CellView( int n, Pegs pegs, Object layout )
	{
		super();

		this.pegs = pegs;

		cellNumber = n;

		setMaximumSize( DIM_CELL );
		setPreferredSize( DIM_CELL );
		setHorizontalAlignment( SwingConstants.LEFT );
		setVerticalAlignment( SwingConstants.TOP );

		setBorder( BorderFactory.createLineBorder( new Color( 0, 0, 0 ) ) );

		lnBorder = getBorder();
		rbvlBorder = BorderFactory.createRaisedBevelBorder();

		fntPlain = getFont();
		setFont( new Font( fntPlain.getName(), Font.PLAIN, fntPlain.getSize() - 3 ) );
		fntPlain = getFont();
		fntBold = new Font( fntPlain.getFontName(), Font.BOLD, fntPlain.getSize() );

		setText( " " + this.pegs.dfrmt.format( n + 1 ) );

		setOpaque( true );

		CellViewMouseHandler    cvmh = new CellViewMouseHandler();

		addMouseListener( cvmh );
		addMouseMotionListener( cvmh );

		this.pegs.pnlBoard.add( this, layout );
	}

	public int
	getCellNumber()
	{
		return cellNumber;
	}

	public Color
	getBgColor()
	{
		return bgColor;
	}

	public void
	setBgColor( Color c )
	{
		bgColor = c;
		repaint();
	}

	public Color
	getPegColor()
	{
		return pegColor;
	}

	public void
	setPegColor( Color c )
	{
		pegColor = c;
		repaint();
	}

	public Color 
	getTextColor()
	{
		return txtColor;
	}

	public void
	setTextColor( Color c )
	{
		txtColor = c;
		setForeground( txtColor );
	}

	public void
	addPeg()
	{
		repaint();
	}

	public void
	rmPeg()
	{
		repaint();
		deSelect();
	}

	public void
	select()
	{
		if ( !pegs.canMove( cellNumber ) )
		{
			return;
		}
		setFont( fntBold );
		setBorder( rbvlBorder );
	}

	public void
	deSelect()
	{
		setFont( fntPlain );
		setBorder( lnBorder );
	}

	public int
	getRadius()
	{	    
		int		w = getWidth();
		int		h = getHeight();
		int		r = ( w > h ? h : w ) / 3;

		return r;
	}

	public void
	paintComponent( Graphics g )
	{
		setBackground( bgColor );
		super.paintComponent( g );

		int		w = getWidth();
		int		h = getHeight();
		int		x = w / 3;
		int		y = h / 3;
		int		r = getRadius();

		if ( pegs.cells[ cellNumber ].hasPeg() == false )
		{
			/*
			  No peg - make peg's color
			  match cell's bg color
			 */
			g.setColor( bgColor );
		}
		else
		{
			if ( pegs.inDragMode == true && pegs.draggedPeg == cellNumber )
			{
				/*
				  This peg is dragged away - leave a small hint
				  in the cell - a thin circle. Fill the circle
				  with cell's bg color, draw the circle with
				  peg's color
				 */
				g.setColor( bgColor );
				g.fillOval( x, y, r, r );
				g.setColor( pegColor );
				g.drawOval( x, y, r, r );
				return;
			}
			else
			{
				/*
				  Peg by itself, not dragged
				 */
				g.setColor( pegColor );
			}
		}

		g.fillOval( x, y, r, r );
	}

	private class CellViewMouseHandler extends MouseInputAdapter
	{
		public void
		mousePressed( MouseEvent me )
		{
			if ( !pegs.canMove( cellNumber ) )
			{
				return;
			}

			pegs.draggedPeg = cellNumber;
		}

		public void
		mouseReleased( MouseEvent me )
		{
			int			drgdpg;
			boolean			indrgmd;
			CellView		docv;
			Point			osp;
			Point			fp;
			Component		comp;
			CellView		destcv;
			int			destcn;
			int			over;

			if ( pegs.draggedPeg < 0 || pegs.inDragMode == false )
			{
				return;
			}

			/*
			  Instance variables will be reset on
			  mouse release mo matter what
			 */
			drgdpg = pegs.draggedPeg;
			indrgmd = pegs.inDragMode;

			pegs.draggedPeg = -1;
			pegs.inDragMode = false;

			/*
			  Was mouse released over a good cell?
			 */
			docv = pegs.cells[ drgdpg ].getCellView();

			/*
			   Point in originating cell's coordinates,
			   the one that originated the drag mode
			 */
			osp = me.getPoint();

			/*
			  Point in Board panel coordinates
			 */
			fp = SwingUtilities.convertPoint( docv, osp, pegs.pnlBoard );

			/*
			  Over what component was mouse released?
			 */
			comp = SwingUtilities.getDeepestComponentAt( pegs.pnlBoard, fp.x, fp.y );
			if ( comp == null )
			{
				return;
			}
			if ( !( comp instanceof CellView ) )
			{
				return;
			}

			/*
			  Destination cell
			 */
			destcv = ( CellView )comp;

			/*
			  No op if releasing mouse over itself
			 */
			destcn = destcv.getCellNumber();
			if ( drgdpg == destcn )
			{
				return;
			}

			over = pegs.findOverPeg( drgdpg, destcn );
			if ( over < 0 )
			{
				return;
			}

			/*
			  Ok to move this peg
			 */
			pegs.moveThisPeg( drgdpg, over, destcn );
			destcv.select();
		}

		public void
		mouseEntered( MouseEvent me )
		{
			select();    
		}

		public void
		mouseExited( MouseEvent me )
		{
			deSelect();
		}

		public void
		mouseDragged( MouseEvent me )
		{
			if ( pegs.draggedPeg >= 0 )
			{
				pegs.inDragMode = true;
			}
		}
	}

	private Pegs			pegs;

	private final int		cellNumber;
	private Font			fntPlain;
	private Font			fntBold;
	private Border			lnBorder;
	private Border			rbvlBorder;
	private Color			txtColor;
	private Color			bgColor;
	private Color			pegColor;

	private static final int	CELL_SIZE = 50;
	private static final Dimension	DIM_CELL = new Dimension( CELL_SIZE, CELL_SIZE );
}
