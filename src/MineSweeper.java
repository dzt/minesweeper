import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MineSweeper extends JPanel implements ActionListener {

   public static void main(String[] args) {
      JFrame window = new JFrame("MineSweeper");
      window.setContentPane(new MineSweeper());
      window.pack();
      window.setResizable(false);
      window.setLocation(200,100);
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setVisible(true);
   }

   private MineSweeperBoard board;

   public MineSweeper() {
      board = new MineSweeperBoard();
      JButton newGame = new JButton("New Game");
      newGame.addActionListener(this);
      JPanel bottom = new JPanel();
      bottom.setBackground(Color.LIGHT_GRAY);
      bottom.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
      bottom.add(newGame);
      setLayout(new BorderLayout(4,4));
      setBackground(Color.GRAY);
      setBorder(BorderFactory.createLineBorder(Color.GRAY,3));
      add(board, BorderLayout.CENTER);
      add(bottom, BorderLayout.SOUTH);
      add(board.getMessageLabel(), BorderLayout.NORTH);
   }

   public void actionPerformed(ActionEvent evt) {
      board.newGame();
   }

}
