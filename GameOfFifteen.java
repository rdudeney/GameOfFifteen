

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * FifteenPuzzle.java
 *
 * Class implements Game of Fifteen, where the user where the user attempts to
 * unscramble tiles on the board to put in the correct. Board starts in ordered
 * position, however the shuffle function shuffles the board in such a way as
 * to be solvable.
 *
 */

public class GameOfFifteen {
    
    final static int DIM_BD = 4; //this is the dimension of the board
    static JFrame jfPuzzle = new JFrame("Fifteen Puzzle");
    static JPanel jpBoard = new JPanel ();
    static JPanel jpShuffle = new JPanel ();
    static JButton[][] board = new JButton[DIM_BD][DIM_BD];
    static JButton shuffle = new JButton("Shuffle");
    static JButton cancel = new JButton("Cancel");
    
    public static void main(String[] args) {
        
        //board is set up in JFrame
        jfPuzzle.setSize(500, 500);
        jfPuzzle.setLayout(new BorderLayout());
        jfPuzzle.setVisible(true);
        jfPuzzle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //JPanel added for tiles
        jpBoard.setLayout(new GridLayout(4, 4, 2, 2));
        jpShuffle.setLayout(new FlowLayout());
        
        //board tiles inialized
        init();
        jfPuzzle.add(jpBoard, BorderLayout.CENTER);
        
        //shuffle and cancel functions added to board
        shuffle.setFont(new Font("Times", Font.BOLD, 18));
        cancel.setFont(new Font("Times", Font.BOLD, 18));
        
        jpShuffle.add(shuffle);
        jpShuffle.add(cancel);
        jfPuzzle.add(jpShuffle, BorderLayout.SOUTH);    
        
        //action listener added to shuffle buttons
        shuffle.addActionListener(new Shuffle());
        cancel.addActionListener(new Cancel());
    } 
    
    /*
    * method initializes the board 
    *              
    */
    
    private static void init() {
        
        int largest = 1;
        
        //each button initialized and action listener added
        for (int i = 0; i < DIM_BD; i++)
        {
            for (int j = 0; j < DIM_BD; j++)
            {
                if (i == DIM_BD - 1 && j == DIM_BD - 1) {
                    board[i][j] = new JButton(" ");
                    board[i][j].setFont(new Font("Times", Font.BOLD, 24));
                    jpBoard.add(board[i][j]);
                    board[i][j].addActionListener(new Clicker());
                }
                
                else {
                    board[i][j] = new JButton(String.valueOf(largest));
                    board[i][j].setFont(new Font("Times", Font.BOLD, 24));
                    board[i][j].addActionListener(new Clicker());
                    jpBoard.add(board[i][j]);
                    largest += 1;
                }
            }
        }
        
    }
    
    /*
    * method implements actionlistener, when button clicked, program runs
    * through all buttons to confirm if in order, if not, program confirms
    * if clicked button is adjacent to empty button, and if so, switches the
    * text of the two buttons            
    */
    
    static class Clicker implements ActionListener {
     
        public void actionPerformed (ActionEvent e) {
            
             // check for win
            if (won()){
                System.out.println("You won!");
                System.exit(0);
            } 
            
            /*return text from clickes button and switch with adjacent button if
            * button is empty
            */
            
            String tile = e.getActionCommand();
            boolean abort = false;
         
            for (int i = 0; i < DIM_BD && !abort; i++){
                for ( int j = 0; j < DIM_BD && !abort; j++) {
                    
                    if (board[i][j].getText().equals(tile)){
                        if (j - 1 >=0 && board[i][j - 1].getText().equals(" "))
                        {
                            
                            board[i][j].setText(" ");
                            board[i][j - 1].setText(tile);
                            abort = true;
                        }
                        else if (j + 1 < DIM_BD && board[i][j + 1].getText().equals(" "))
                        {
                            board[i][j].setText(" ");
                            board[i][j + 1].setText(tile);
                            abort = true;
                    
                        }
            
                        else if (i - 1 >= 0 && board[i - 1][j].getText().equals(" "))
                        {
                            board[i][j].setText(" ");
                            board[i - 1][j].setText(tile);
                            abort = true;
                        }
                        else if (i + 1 < DIM_BD && board[i + 1][j].getText().equals(" "))
                        {
                            board[i][j].setText(" ");
                            board[i + 1][j].setText(tile);
                            abort = true;
                        }
                    } 
                }   
            }
        }
    }

    
    /*
    * method implements actionlistener, when button clicked, program moves 
    * empty button around the board until it reaches the top right corner, this
    * ensures that the shuffled board is solvable
    */
    
    static class Shuffle implements ActionListener {
        
        public void actionPerformed (ActionEvent e) {
            
            int column = 0;
            int row = 0;
            int max = 0; // max's out the number of moves in shuffle
            
            //program finds the empty button and records location in 2D array
            for(int i = 0; i < DIM_BD; i++) {
                for (int j =0; j < DIM_BD; j++) {
                    if (board[i][j].getText().equals(" ")) {
                        column = j;
                        row = i;
                    }
                }
            }
            
            String previous = ""; 
            
            //empty button moves around board until reaches top right corner
            while (!board[0][0].getText().equals(" ") && max != 10) {
                
                //empty button moves randomly
                int random = rint(1, 4);
                
                //if button able to move in random direction, does so
                switch(random) {
                    case 1 : 
                        if (row - 1 >= 0 && !board[row-1][column].getText().equals(previous)) {
                            board[row][column].setText(board[row-1][column].getText());
                            board[row-1][column].setText(" ");
                            previous = board[row][column].getText();
                            row -= 1;
                        }
                        break;
                    
                    case 2: 
                        if (row + 1 < DIM_BD && !board[row+1][column].getText().equals(previous)) {
                            board[row][column].setText(board[row+1][column].getText());
                            board[row+1][column].setText(" ");
                            previous = board[row][column].getText();
                            row += 1;
                        } 
                        break;
                    
                    case 3: 
                        
                        if (column - 1 >= 0 && !board[row][column - 1].getText().equals(previous)) {
                            board[row][column].setText(board[row][column-1].getText());
                            board[row][column-1].setText(" ");
                            previous = board[row][column].getText();
                            column -= 1;
                        }
                        break;
                        
                    case 4:
                        if (column + 1 < DIM_BD && !board[row][column + 1].getText().equals(previous)) {
                            board[row][column].setText(board[row][column+1].getText());
                            board[row][column+1].setText(" ");
                            previous = board[row][column].getText();
                            column += 1;
                        }
                        break;
                        
                }
                max++;
            }
        }
    }
    
    /*
    * method implements actionlistener, exits program when clicked         
    */
    
    static class Cancel implements ActionListener {
        
        public void actionPerformed (ActionEvent e) {
        
            System.exit(0);
        }
    }
/*** Returns true if game is won (i.e., board is in winning configuration), 
 * else false.*/
 
    private static boolean won()
    {
        
        //program runs through 2D array to confirm if buttons in order
        int counter = 0;
        for (int i = 0; i < DIM_BD; i++)
        {
            for (int j = 0; j < DIM_BD; j++)
            {
                if (board[i][j].getText().equals(" ")) {
                    counter++;
                }
                
                else if (j + 1 < DIM_BD && !board[i][j + 1].getText().equals(" ") && 
                        Integer.parseInt(board[i][j].getText()) > Integer.parseInt(board[i][j + 1].getText()))
                {
                    counter += 1;
                }
                else if (i + 1 < DIM_BD && j == DIM_BD - 1 && !board[i + 1][0].getText().equals(" ") &&
                        Integer.parseInt(board[i][j].getText()) > Integer.parseInt(board[i + 1][0].getText()))
                {
                    counter += 1;
                }
            
            }
        }
        
        if (counter == 1 && board[DIM_BD -  1][DIM_BD - 1].getText().equals(" "))
        {
            return true;
        }
        return false;
    }
    
    /*
    * helper method is random number generator          
    */
    private static int rint (int a, int b) {
        return a + (int) ((b-a+1) * Math.random());
    }
}
        
        
   
