/*
  By Roman Andronov
 */

package pegs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

import java.text.DecimalFormat;
import java.util.Date;

import javax.swing.border.Border;
import javax.swing.border.BevelBorder;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;


public class Pegs
	extends JFrame
	implements ActionListener
{
	public 
	Pegs()
	{
		init();

		/*
		  Centers the window on the screen
		 */
		setLocationRelativeTo( null );
	}

	public void
	actionPerformed( ActionEvent ae )
	{
		Object		o = ae.getSource();

		if ( o instanceof JMenuItem )
		{
			JMenuItem	mni  = ( JMenuItem )o;

			if ( mni == mniNew )
			{
				newGame();
			}
			else if ( mni == mniExit )
			{
				exitGame();
			}
			else if ( mni == mniCfgCellColor )
			{
				cfgColor( CELL, clrSchm.cellColor );
			}
			else if ( mni == mniCfgPegColor )
			{
				cfgColor( PEG, clrSchm.pegColor );
			}
			else if ( mni == mniCfgCellNumColor )
			{
				cfgColor( CELL_NUM, clrSchm.cellNumColor );
			}
			else if ( mni == mniCfgMvsColor )
			{
				cfgColor( MOVES, clrSchm.mvsColor );
			}
			else if ( mni == mniCfgBckgColor )
			{
				cfgColor( BCKGRND, clrSchm.bckgColor );
			}
		}
	}
    
	public static void
	main( String[] args )
	{
		try
		{
			String lnf = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel( lnf );
		}
		catch ( Exception e )
		{
		}

		EventQueue.invokeLater( new Runnable() 
		{
			public void run() 
			{
				new Pegs().setVisible( true );
			}
		});
	}
    
	private void
	init()
	{
		setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );

		setTitle( TITLE );

		clrSchm = new ColorScheme( DFLT_BG_CLR,
			DFLT_CELL_CLR,
			DFLT_CELL_NUM_CLR,
			DFLT_PEG_CLR,
			DFLT_MOVES_CLR );
	
		/*
		  Menu
		 */
		mnbrMain = new JMenuBar();
		setJMenuBar( mnbrMain );

		mnuGame = new JMenu( MENU_GAME );
		mnbrMain.add( mnuGame );
		mnuGame.setMnemonic( KeyEvent.VK_G );

		mniNew = new JMenuItem( MENU_NEW );
		mnuGame.add( mniNew );
		mniNew.setMnemonic( KeyEvent.VK_N );
		mniNew.addActionListener( this );

		mnuGame.addSeparator();

		mniExit = new JMenuItem( MENU_EXIT );
		mnuGame.add( mniExit );
		mniExit.setMnemonic( KeyEvent.VK_X );
		mniExit.addActionListener( this );

		mnuCfg = new JMenu( MENU_CHANGE );
		mnbrMain.add( mnuCfg );
		mnuCfg.setMnemonic( KeyEvent.VK_C );

		mniCfgCellColor = new JMenuItem( MENU_CELLCOLOR );
		mnuCfg.add( mniCfgCellColor );
		mniCfgCellColor.setMnemonic( KeyEvent.VK_C );
		mniCfgCellColor.addActionListener( this );

		mniCfgPegColor = new JMenuItem( MENU_PEGCOLOR );
		mnuCfg.add( mniCfgPegColor );
		mniCfgPegColor.setMnemonic( KeyEvent.VK_P );
		mniCfgPegColor.addActionListener( this );

		mniCfgCellNumColor = new JMenuItem( MENU_TXTCOLOR );
		mnuCfg.add( mniCfgCellNumColor );
		mniCfgCellNumColor.setMnemonic( KeyEvent.VK_N );
		mniCfgCellNumColor.addActionListener( this );

		mniCfgMvsColor = new JMenuItem( MENU_MOVESCOLOR );
		mnuCfg.add( mniCfgMvsColor );
		mniCfgMvsColor.setMnemonic( KeyEvent.VK_M );
		mniCfgMvsColor.addActionListener( this );

		mniCfgBckgColor = new JMenuItem( MENU_BCKGCOLOR );
		mnuCfg.add( mniCfgBckgColor );
		mniCfgBckgColor.setMnemonic( KeyEvent.VK_B );
		mniCfgBckgColor.addActionListener( this );


		setLayout( new GridBagLayout() );

		/*
		  Panels
		 */
		GridBagConstraints		gbc = new GridBagConstraints();
		Insets				nstsBig = new Insets( 7, 7, 7, 7 );
		Insets				nstsSmall = new Insets( 0, 0, 0, 0 );
		Insets				nstsLabels = new Insets( 7, 7, 0, 7 );
		Insets				nstsBoard = new Insets( 10, 10, 10, 10 );

		pnlMovesLeft = new JPanel();
		pnlBoard = new JPanel();
		pnlBoardBase = new JPanel();
		pnlMovesMade = new JPanel();

		gbc.insets = nstsLabels;
		gbc.anchor = GridBagConstraints.LINE_START;

		lblMovesLeft = new JLabel( "Moves left: " );
		add( lblMovesLeft, gbc );

		lblBoard = new JLabel( "Board: " );
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.weightx = 1.0;
		add( lblBoard, gbc );
		gbc.weightx = 0.0;
		gbc.fill = GridBagConstraints.NONE;

		lblMovesMade = new JLabel( "Moves made: " );
		gbc.gridx = 2;
		add( lblMovesMade, gbc );

		/*
		  Common for all panels
		 */
		gbc.weighty = 1.0;
		gbc.gridy = 1;
		gbc.insets = nstsBig;

		/*
		  Moves Left panel is on the left
		 */
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.VERTICAL;
		pnlMovesLeft.setMinimumSize( DIM_MOVES_LEFT );
		pnlMovesLeft.setMaximumSize( DIM_MOVES_LEFT );
		pnlMovesLeft.setPreferredSize( DIM_MOVES_LEFT );
		add( pnlMovesLeft, gbc );

		/*
		  Board Base panel is in the center.
		  Board panel lives on top of its base
		 */
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		add( pnlBoardBase, gbc );

		/*
		  Moves Made panel is on the right
		 */
		gbc.gridx = 2;
		gbc.weightx = 0.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		add( pnlMovesMade, gbc );

		gbc.gridx = gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = nstsSmall;


		/*
		  Keyboard interface panel
		 */
		pnlMovesLeft.setLayout( new GridBagLayout() );

		lmMovesLeft = new DefaultListModel();
		lstMovesLeft = new JList( lmMovesLeft );
		lstMovesLeft.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		lstMovesLeft.addKeyListener( new MovesLeftKeyAdapter( this ) );
		lstMovesLeft.setBorder( BRDR_BEV_LO );

		scrlpnMovesLeft = new JScrollPane( lstMovesLeft );

		pnlMovesLeft.add( scrlpnMovesLeft, gbc );


		/*
		  The Board
		 */
		pnlBoardBase.setLayout( new GridBagLayout() );
		pnlBoardBase.setBorder( BRDR_BEV_HI );

		/*
		  To avoid cell-specific insets
		 */
		gbc.insets = nstsBoard;
		pnlBoard.setOpaque( false );
		pnlBoard.setLayout( new GridBagLayout() );
		pnlBoardBase.add( pnlBoard, gbc );

		/*
		  The following two arrays are used only to
		  calculate the x and y coordinates of the cells'
		  view layout:
		   x:
		  	for rows 1 and 2 is [2-4]
		  	for rows 3, 4, 5 is [0-6]
		  	for rows 6 and 7 is [2-4]

		   y: [0-6], a separate function will manufacture this number

		  To calculate x use this formula:
		  	x = ( i + offset ) % limit

		*/
		int[]		o = { 2, -1, -6, -13, -20, -25, -28 };
		int[]		l = { 5, 5, 7, 7, 7, 5, 5 };

		/*
		  Insets, fill and weight are the same for all the
		  cell views. Only gridx and gridy vary
		 */
		gbc.insets = nstsSmall;

		cells = new Cell[ CELL_COUNT ];
		for ( int i = 0; i < cells.length; i++ )
		{
			cells[ i ] = new Cell( i );

			gbc.gridy = mkY( i );
			gbc.gridx = ( i + o[ gbc.gridy ] ) % l[ gbc.gridy ];

			/*
			  Bind each logical cell to its visual counterpart
			 */
			cells[ i ].setView( new CellView( i, this, gbc ) );
		}


		/*
		  Moves Made panel
		 */
		pnlMovesMade.setLayout( new GridBagLayout() );

		txtarMovesMade = new JTextArea();
		txtarMovesMade.setEditable( false );
		txtarMovesMade.setBorder( BorderFactory.createBevelBorder( BevelBorder.LOWERED ) );

		scrlpnMovesMade = new JScrollPane( txtarMovesMade );

		gbc.gridx = gbc.gridy = 0;
		gbc.insets = nstsSmall;
		pnlMovesMade.setMinimumSize( DIM_MOVES_MADE );
		pnlMovesMade.setMaximumSize( DIM_MOVES_MADE );
		pnlMovesMade.setPreferredSize( DIM_MOVES_MADE );
		pnlMovesMade.add( scrlpnMovesMade, gbc );


		lblGame = new JLabel( "Game: on" );
		lblGame.setOpaque( true );
		lblGame.setBorder( BorderFactory.createCompoundBorder( BRDR_BEV_LO, BRDR_PAD ) );
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.insets = nstsBig;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add( lblGame, gbc );


		/*
		  Legal moves for each cell
		 */
		setMoves();

		newGame();


		/*
		  Set the glass pane to handle custom painting during valid drags.
		  Order of calls IS important: first, set frame's glass, second
		  make it visible (it won't work the other way around)
		 */
		glassPane = new PegsGlassPane( this );
		setGlassPane( glassPane );
		Toolkit.getDefaultToolkit().addAWTEventListener( glassPane,
			AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK );
		glassPane.setVisible( true );


		setDfltColors();

		pack();
	}
    
	/*
	  Should be called only once
	 */
	private void
	setMoves()
	{
		moves = new Move[ cells.length ][];

		moves[ 0 ] = new Move[] { new Move( 1, 2 ), new Move( 3, 8 ) };
		moves[ 1 ] = new Move[] { new Move( 4, 9 ) };
		moves[ 2 ] = new Move[] { new Move( 1, 0 ), new Move( 5, 10 ) };
		moves[ 3 ] = new Move[] { new Move( 4, 5 ), new Move( 8, 15 ) };
		moves[ 4 ] = new Move[] { new Move( 9, 16 ) };
		moves[ 5 ] = new Move[] { new Move( 4, 3 ), new Move( 10, 17 ) };
		moves[ 6 ] = new Move[] { new Move( 7, 8 ), new Move( 13, 20 ) };
		moves[ 7 ] = new Move[] { new Move( 8, 9 ), new Move( 14, 21 ) };
		moves[ 8 ] = new Move[] { new Move( 7, 6 ), new Move( 3, 0 ),
			new Move( 9, 10 ), new Move( 15, 22 ) };
		moves[ 9 ] = new Move[] { new Move( 8, 7 ), new Move( 4, 1 ),
			new Move( 10, 11 ), new Move( 16, 23 ) };
		moves[ 10 ] = new Move[] { new Move( 9, 8 ), new Move( 5, 2 ),
			new Move( 11, 12 ), new Move( 17, 24 ) };
		moves[ 11 ] = new Move[] { new Move( 10, 9 ), new Move( 18, 25 ) };
		moves[ 12 ] = new Move[] { new Move( 11, 10 ), new Move( 19, 26 ) };
		moves[ 13 ] = new Move[] { new Move( 14, 15 ) };
		moves[ 14 ] = new Move[] { new Move( 15, 16 ) };
		moves[ 15 ] = new Move[] { new Move( 14, 13 ), new Move( 8, 3 ),
			new Move( 16, 17 ), new Move( 22, 27 ) };
		moves[ 16 ] = new Move[] { new Move( 15, 14 ), new Move( 9, 4 ),
			new Move( 17, 18 ), new Move( 23, 28 ) };
		moves[ 17 ] = new Move[] { new Move( 16, 15 ), new Move( 10, 5 ),
			new Move( 18, 19 ), new Move( 24, 29 ) };
		moves[ 18 ] = new Move[] { new Move( 17, 16 ) };
		moves[ 19 ] = new Move[] { new Move( 18, 17 ) };
		moves[ 20 ] = new Move[] { new Move( 13, 6 ), new Move( 21, 22 ) };
		moves[ 21 ] = new Move[] { new Move( 14, 7 ), new Move( 22, 23 ) };
		moves[ 22 ] = new Move[] { new Move( 21, 20 ), new Move( 15, 8 ),
			new Move( 23, 24 ), new Move( 27, 30 ) };
		moves[ 23 ] = new Move[] { new Move( 22, 21 ), new Move( 16, 9 ),
			new Move( 24, 25 ), new Move( 28, 31 ) };
		moves[ 24 ] = new Move[] { new Move( 23, 22 ), new Move( 17, 10 ),
			new Move( 25, 26 ), new Move( 29, 32 ) };
		moves[ 25 ] = new Move[] { new Move( 24, 23 ), new Move( 18, 11 ) };
		moves[ 26 ] = new Move[] { new Move( 25, 24 ), new Move( 19, 12 ) };
		moves[ 27 ] = new Move[] { new Move( 22, 15 ), new Move( 28, 29 ) };
		moves[ 28 ] = new Move[] { new Move( 23, 16 ) };
		moves[ 29 ] = new Move[] { new Move( 28, 27 ), new Move( 24, 17 ) };
		moves[ 30 ] = new Move[] { new Move( 27, 22 ), new Move( 31, 32 ) };
		moves[ 31 ] = new Move[] { new Move( 28, 23 ) };
		moves[ 32 ] = new Move[] { new Move( 31, 30 ), new Move( 29, 24 ) };
	}
    
	private void
	addMove( int src, int over, int dest )
	{
		String		s = " " +
			dfrmt.format( ++mvNumber ) +
			") " + 
			dfrmt.format( src + 1 ) +
			" to " +
			dfrmt.format( dest + 1 ) +
			"\n";

		txtarMovesMade.setText( txtarMovesMade.getText() + s );
	}
    
	/*
	  Finding an 'over' peg estableshes the legalaty of the move:
	    - source cell must have a peg
 	    - 'over' cell must have a peg
 	    - destination cell must not have a peg
 	    - destination cell is within the legal range relative to source
	 */
	int
	findOverPeg( int src, int dest )
	{
		int		over = -1;

		if ( !cells[ src ].hasPeg() )
		{
			return over;
		}

		if ( cells[ dest ].hasPeg() )
		{
			return over;
		}

		for ( int i = 0; i < moves[ src ].length; i++ )
		{
			if ( moves[ src ][ i ].dest == dest )
			{
				if ( cells[ moves[ src ][ i ].over ].hasPeg() )
				{
					over = moves[ src ][ i ].over;
				}
				break;
			}
		}

		return over;
	}
    
	void
	moveThisPeg( int src, int over, int dest )
	{
		Date		d;

		/*
		  Start the timer on the first move
		 */
		if ( tmStart < 0 )
		{
			d = new Date();
			tmStart = d.getTime();
		}

		/*
		  Move this peg
		 */
		cells[ src ].rmPeg();
		cells[ src ].getCellView().deSelect();
		cells[ over ].rmPeg();
		cells[ dest ].addPeg();
		cells[ dest ].getCellView().select();
		addMove( src, over, dest );

		/*
		  Update the keyboard interface
		 */
		fillMovesLeft();

		/*
		  End the game if needed
		 */
		if ( gameIsOver() )
		{
			d = new Date();
			tmFinish = d.getTime();
			endOfGameMsg();
		}
	}
    
	boolean
	canMove( int cnmbr )
	{
		if ( !cells[ cnmbr ].hasPeg() )
		{
			return false;
		}

		for ( int i = 0; i < moves[ cnmbr ].length; i++ )
		{
			if ( cells[ moves[ cnmbr ][ i ].over ].hasPeg() )
			{
				if ( !cells[ moves[ cnmbr ][ i ].dest ].hasPeg() )
				{
					return true;
				}
			}
		}

		return false;
	}
    
	private boolean
	gameIsOver()
	{
		/*
		  Any legal moves left?
		 */
		for ( int i = 0; i < cells.length; i++ )
		{
			if ( !cells[ i ].hasPeg() )
			{
				continue;
			}

			/*
			  Source cell has a peg
			 */
			for ( int j = 0; j < moves[ i ].length; j++ )
			{
				if ( !cells[ moves[ i ][ j ].over ].hasPeg() )
				{
					continue;
				}

				/*
				  An 'over' cell has a peg
				 */
				if ( !cells[ moves[ i ][ j ].dest ].hasPeg() )
				{
					/*
					  Some leagal moves are still left
					 */
					return false;
				}
			}
		}

		return true;
	}
    
	private void
	fillMovesLeft()
	{
		int			o;
		int			d;
		MovesLeftItem		mli;

		lmMovesLeft.clear();
		for ( int i = 0; i < cells.length; i++ )
		{
			if ( !cells[ i ].hasPeg() )
			{
				continue;
			}

			for ( int j = 0; j < moves[ i ].length; j++ )
			{
				o = moves[ i ][ j ].over;
				if ( !cells[ o ].hasPeg() )
				{
					continue;
				}

				d = moves[ i ][ j ].dest;
				if ( cells[ d ].hasPeg() )
				{
					continue;
				}

				mli = new MovesLeftItem( i, o, d, dfrmt );
				lmMovesLeft.addElement( mli );
			}
		}

		if ( lmMovesLeft.getSize() > 0 )
		{
			lstMovesLeft.setSelectedIndex( 0 );
			lstMovesLeft.ensureIndexIsVisible( 0 );
		}
	}
    
	private int
	pegCount()
	{
		int		pc = 0;

		for ( int i = 0; i < cells.length; i++ )
		{
			if ( cells[ i ].hasPeg() )
			{
				pc++;
				solePeg = i;
			}
		}

		return pc;
	}
    
	private int
	mkY( int i )
	{
		int		y = -1;

		if ( i < 3 )
		{
			y = 0;
		}
		else if ( i < 6 )
		{
			y = 1;
		}
		else if ( i < 13 )
		{
			y = 2;
		}
		else if ( i < 20 )
		{
			y = 3;
		}
		else if ( i < 27 )
		{
			y = 4;
		}
		else if ( i < 30 )
		{
			y = 5;
		}
		else
		{
			y = 6;
		}

		return y;
	}
    
	private void
	endOfGameMsg()
	{
		int		pc = pegCount();
		String		s = "over: pegs left: " + pc;

		if ( pc == 1 )
		{
			int		cellNum = cells[ solePeg ].getCellNumber();

			if ( cellNum != 16 )
			{
				s += " off";
			}
			else
			{
				s += " in dead";
			}
			s += " center";
			
		}
		s += ", ";

		s += "moves: " + mvNumber + ", " +
			"time: " + mkDuration();

		lblGame.setText( "Game: " + s );
	}

	private String
	mkDuration()
	{
		long		TS = ( tmFinish - tmStart ) / 1000;
		long		d = TS / ( 24 * 60 * 60 );
		long		h = ( TS - d * 24 ) / 3600;
		long		m = ( TS - h * 3600 ) / 60;
		long		s = TS - h *3600 - m * 60;
		String		r = "";

		if ( d > 0 )
		{
			r += Long.toString( h ) + " day(s) ";
		}

		if ( h > 0 )
		{
			r += Long.toString( h ) + " hour(s) ";
		}

		if ( m > 0 )
		{
			r += Long.toString( m ) + " minute(s) ";
		}

		if ( s > 0 )
		{
			r += Long.toString( s ) + " second(s)";
		}

		return r;
	}

	private void
	newGame()
	{
		tmStart = -1;
		tmFinish = -1;
		mvNumber = 0;
		lblGame.setText( "Game: on" );
		txtarMovesMade.setText( "" );

		/*
		  All the cells but the center one have
		  pegs at the beginning of a new game
		 */
		for ( int i = 0; i < cells.length; i++ )
		{
			cells[ i ].addPeg();
		}
		cells[ 16 ].rmPeg();

		/*
		  Keyboard interface
		 */
		fillMovesLeft();
	}
 
	private void
	exitGame()
	{
		String	    m = "\n\nExit this game?\n\n";
		int	    r = JOptionPane.showConfirmDialog( this, m, TITLE,
				JOptionPane.YES_NO_OPTION );

		if ( r != JOptionPane.YES_OPTION )
		{
			return;
		}

		System.exit( 0 );
	}
    
	private void
	cfgColor( String item, Color itemColor )
	{
		Color		nc;

		nc = JColorChooser.showDialog( this,
			"Select new " + item + " color",
			itemColor );

		if ( nc == null )
		{
			return;
		}

		if ( item == CELL )
		{
			clrSchm.cellColor = nc;
			for ( int i = 0; i < cells.length; i++ )
			{
				cells[ i ].getCellView().setBgColor( clrSchm.cellColor );
			}
		}
		else if ( item == PEG )
		{
			clrSchm.pegColor = nc;
			for ( int i = 0; i < cells.length; i++ )
			{
				cells[ i ].getCellView().setPegColor( clrSchm.pegColor );
			}

			glassPane.setPegColor( nc );
		}
		else if ( item == CELL_NUM )
		{
			clrSchm.cellNumColor = nc;
			for ( int i = 0; i < cells.length; i++ )
			{
				cells[ i ].getCellView().setTextColor( clrSchm.cellNumColor );
			}
		}
		else if ( item == MOVES )
		{
			clrSchm.mvsColor = nc;
			txtarMovesMade.setForeground( clrSchm.mvsColor );
			lstMovesLeft.setForeground( clrSchm.mvsColor );
			lblGame.setForeground( clrSchm.mvsColor );
		}
		else if ( item == BCKGRND )
		{
			clrSchm.bckgColor = nc;
			pnlBoardBase.setBackground( clrSchm.bckgColor );
			txtarMovesMade.setBackground( clrSchm.bckgColor );
			lstMovesLeft.setBackground( clrSchm.bckgColor );
			lblGame.setBackground( clrSchm.bckgColor );
		}
	}

	private void
	setDfltColors()
	{
		pnlBoardBase.setBackground( clrSchm.bckgColor );

		lstMovesLeft.setBackground( clrSchm.bckgColor );
		lstMovesLeft.setForeground( clrSchm.mvsColor );

		for ( int i = 0; i < cells.length; i++ )
		{
			cells[ i ].getCellView().setBgColor( clrSchm.cellColor );
			cells[ i ].getCellView().setTextColor( clrSchm.cellNumColor );
			cells[ i ].getCellView().setPegColor( clrSchm.pegColor );
		}

		txtarMovesMade.setBackground( clrSchm.bckgColor );
		txtarMovesMade.setForeground( clrSchm.mvsColor );

		lblGame.setBackground( clrSchm.bckgColor );
		lblGame.setForeground( clrSchm.mvsColor );

		glassPane.setPegColor( clrSchm.pegColor );
	}
    

	/*
	  Visible across the package
	 */
	int					draggedPeg = -1;
	boolean					inDragMode = false;
	JPanel					pnlBoard = null;
	JList					lstMovesLeft = null;
	DefaultListModel			lmMovesLeft = null;
	Cell[]					cells = null;
	DecimalFormat				dfrmt = new DecimalFormat( "00" );

	/*
	  Peg dragging support
	 */
	private PegsGlassPane			glassPane;


	/*	
	  Menu
	 */
	private JMenuBar			mnbrMain;

	private JMenu				mnuGame;
	private JMenuItem			mniNew;
	private JMenuItem			mniExit;

	private JMenu				mnuCfg;
	private JMenuItem			mniCfgCellColor;
	private JMenuItem			mniCfgPegColor;
	private JMenuItem			mniCfgCellNumColor;
	private JMenuItem			mniCfgMvsColor;
	private JMenuItem			mniCfgBckgColor;


	/*
	  Panels
	 */
	private JPanel				pnlMovesLeft;
	private JPanel				pnlBoardBase;
	private JPanel				pnlMovesMade;

	private JLabel				lblMovesLeft;
	private JLabel				lblBoard;
	private JLabel				lblMovesMade;


	/*
	  Keyboard interface panel
	 */
	private JScrollPane			scrlpnMovesLeft;


	/*
	  Moves panel
	 */
	private JScrollPane			scrlpnMovesMade;
	private JTextArea			txtarMovesMade;
	private int				mvNumber = 0;


	private ColorScheme			clrSchm;

	private int				solePeg;

	private JLabel				lblGame;

	/*
	  Legal moves
	 */
	private Move[][]			moves;


	/*
	  Timer
	 */
	private long				tmStart;
	private long				tmFinish;


	/*
	  Constants
	 */
	private static final int		CELL_COUNT = 33;
	private static final Dimension		DIM_MOVES_LEFT = new Dimension( 105, 374 );
	private static final Dimension		DIM_MOVES_MADE = new Dimension( 130, 374 );

	private static final String		TITLE = "Pegs";

	private static final String		MENU_GAME = "Game";
	private static final String		MENU_EXIT = "Exit";
	private static final String		MENU_NEW = "New Game";
	private static final String		MENU_TIME = "Time This Game";
	private static final String		MENU_CHANGE = "Change";
	private static final String		MENU_CELLCOLOR = "Cell Color ...";
	private static final String		MENU_PEGCOLOR = "Peg Color ...";
	private static final String		MENU_TXTCOLOR = "Cell Number Color ...";
	private static final String		MENU_MOVESCOLOR = "Moves Color ...";
	private static final String		MENU_BCKGCOLOR = "Background Color ...";

	private static final String		CELL = "cell";
	private static final String		PEG = "peg";
	private static final String		CELL_NUM = "cell number";
	private static final String		MOVES = "moves";
	private static final String		BCKGRND = "background";

	private static final Color		DFLT_BG_CLR = new Color( 197, 213, 203 );
	private static final Color		DFLT_CELL_CLR = new Color( 227, 224, 207 );
	private static final Color		DFLT_PEG_CLR = new Color( 114, 120, 116 );
	private static final Color		DFLT_CELL_NUM_CLR = DFLT_PEG_CLR;
	private static final Color		DFLT_MOVES_CLR = new Color( 85, 90, 87 );

	private static final Border		BRDR_BEV_LO = BorderFactory.createBevelBorder( BevelBorder.LOWERED );
	private static final Border		BRDR_BEV_HI = BorderFactory.createBevelBorder( BevelBorder.RAISED );
	private static final Border		BRDR_PAD = BorderFactory.createEmptyBorder( 3, 3, 3, 3 );
}
