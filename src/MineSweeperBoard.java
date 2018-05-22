import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

/**
 *  The playing board for MineSweeper, showing a grid of squares.  Each
 *  square might or might not contain a mine.  When the user clicks a
 *  square one of two things can happen:  If the square is mined, then
 *  the user gets blown up and loses the game.  If the square is not
 *  mined, then the square is "revealed"; that is, the color is changed
 *  to light green and the number of mines in neighboring squares is
 *  shown.  (If the number of mines in neighboring squares is zero, then
 *  no number is shown in the light green square.)
 *     If the user shift-clicks or right-clicks a dark green square, the square
 *  is flagged as mined.  That is, the user believes that there is a mine
 *  there.  The color of the square changes to light red.  (If the user
 *  clicks a red square, nothing happens; if the user right-clicks or
 *  shift-clicks a red square, the square changes back to dark green.)
 *    The user wins the game if the user gets rid of all the dark green
 *  squares without getting blown up.
 */
public class MineSweeperBoard extends JPanel implements MouseListener {

   private static final int BOARD_SIZE = 10;  // The grid has this many squares on a side.
   private static final int NUMBER_OF_MINES = 10;  // The number of mines to be place on the board.

   private static final Color LIGHT_GREEN = new Color(180,255,180);  // Color of a "revealed" square.
   private static final Color LIGHT_RED = new Color(255,100,100);    // Color of a "flagged-as-mined" square.

   private static final int HIDDEN = 0;            // Constants to be used as a value in the state array.
   private static final int REVEALED = 1;
   private static final int FLAGGED_AS_MINED = 2;

   private int[][] state;  // state[row][col] tells the state of the grid square in the given row and column.
   // The value has to be one of HIDDEN, REVEALED,or FLAGGED_AS_MINED.
   // Initially, all the values are 0 (HIDDEN).  The value can change when the
   // user clicks the square.  Squares with different states are shown in different colors.

   private boolean gameInProgress;  // This variable is true when a game is in progress and becomes false after
   // the game ends.  (It is reset to true whenever the newGame() method is called.)

   private JLabel message;  // Displays a message to the user.  Use message.setText(string) to change the message.

   private ArrayList<Box> boxes = new ArrayList<Box>();


   /**
    *  Constructor.  Does some setup and calls newGame() to start the first game.
    */

   public MineSweeperBoard() {
      setPreferredSize( new Dimension( 30 * BOARD_SIZE, 30 * BOARD_SIZE ) );
      addMouseListener(this);
      message = new JLabel();
      message.setBackground(Color.LIGHT_GRAY);
      message.setOpaque(true);
      message.setForeground(Color.RED);
      message.setFont(new Font("SansSerif", Font.BOLD, 18));
      message.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
      setFont(new Font("Serif", Font.BOLD, 16));
      newGame();
   }


   /**
    *  Used by the main program to get a reference to the message label,
    *  so that it can add the message label to the top of the game.
    */
   public JLabel getMessageLabel() {
      return message;
   }


   /**
    *  This routine is called to start a new game.  It should set up all the
    *  instance variables with appropriate values for the start of a game.
    *  In particular, all the squares are HIDDEN and the mines are planted
    *  in the minefield.  Also, gameInProgress is set to true.  This method
    *  is called by the constructor to start the first game; after that, it
    *  is called when the user clicks the "New Game" button.
    */
   public void newGame() {  // TODO:  There's a lot more to add to this method!
      message.setText("Flags Left:  " + NUMBER_OF_MINES + "/" + NUMBER_OF_MINES);
      generateBoard((int) Math.pow(BOARD_SIZE, 2), NUMBER_OF_MINES);
      state = new int[BOARD_SIZE][BOARD_SIZE];
      gameInProgress = true;
      repaint();  // Redraw the board to show the new, empty gameboard.
   }


   /**
    *  Draws the board, using all the state variables.  Note that if the
    *  game is over, then the positions of all the mines should be revealed.
    */
   protected void paintComponent(Graphics g) {

      double squareWidth = (double) getWidth() / BOARD_SIZE;
      double squareHeight = (double) getHeight() / BOARD_SIZE;

      for (int row = 0; row < BOARD_SIZE; row++) {
         for (int col = 0; col < BOARD_SIZE; col++) {

            int y1 = (int)(row*squareHeight);
            int x1 = (int)(col*squareWidth);
            int y2 = (int)((row+1)*squareHeight);
            int x2 = (int)((col+1)*squareWidth);

            int width = x2 - x1;
            int height = y2 - y1;

            if (state[row][col] == REVEALED)
               g.setColor(LIGHT_GREEN);
            else if (state[row][col] == HIDDEN)
               g.setColor(Color.GREEN);
            else
               g.setColor(LIGHT_RED);
            g.fillRect(x1, y1, width, height);
            g.setColor(Color.GRAY);
            g.drawRect(x1, y1, width, height);
            g.setColor(Color.BLACK);

            int position = (row * 10) + col;

            /* Check for mine containing position */
            for (int i = 0; i < boxes.size(); i++) {
               if (boxes.get(i).getPosition() == position) {
                  g.drawString("X",x1 + 6,y1 + 21);
               }
            }

         }
      }

   }


    protected void generateBoard(int count, int returnCount) {

      ArrayList<Integer> numbers = new ArrayList<Integer>();
      Random randomGenerator = new Random();

      boxes = new ArrayList<Box>();

      while (numbers.size() < returnCount) {

         int random = randomGenerator.nextInt((count - 0) + 1) + 0; /* Board size (example: 100) */

         if (!numbers.contains(random)) {
            numbers.add(random);
         }

      }

      System.out.println("Mine Positions: " + numbers);

      for (int i = 0; i < numbers.size(); i++) {
         boxes.add(new Box("X", numbers.get(i), (int) Math.pow(BOARD_SIZE, 2)));
      }

   }


   /**
    *  Called when the user clicks the mouse.  Figure out what the user wants to do and do it.
    */
   public void mousePressed(MouseEvent evt) {
      int row, col;  // The row and column where the user clicked.

      /* Figure out the row and column, based on the (x,y) point where the user clicked. */

      double squareWidth = (double) getWidth() / BOARD_SIZE;
      double squareHeight = (double) getHeight() / BOARD_SIZE;

      row = (int)( evt.getY() / squareHeight );
      col = (int)( evt.getX() / squareWidth );

      /* Now process the click.  This is where a lot of the game logic is implemented. */
      // TODO:  This is a lot more complicated!

      if (evt.isShiftDown() || evt.isMetaDown()) {
         // The user has shift-clicked or right-clicked.
         state[row][col] = FLAGGED_AS_MINED;
      }
      else {
         // A plain click.
         state[row][col] = REVEALED;
      }

      /* Test if the game is over, and update the message shown on the message label. */
      // TODO: Implement this!

      /* Finally, make sure that the board is redrawn to show the effect of this move. */

      repaint();
   }

   protected void setBoxFlag(int pos) {
      for (int i = 0; i < boxes.size(); i++) {
         if (pos == boxes.get(i).getPosition()) {
            boxes.get(i).setFlag();
         }
      }
   }


   /*
    * The following four methods are required for a MouseListener, but
    * are not used in this program.
    */
   public void mouseReleased(MouseEvent evt) {
   }
   public void mouseClicked(MouseEvent evt) {
   }
   public void mouseEntered(MouseEvent evt) {
   }
   public void mouseExited(MouseEvent evt) {
   }

}
