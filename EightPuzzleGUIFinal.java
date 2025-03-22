import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EightPuzzleGUIFinal extends JFrame 
{
    private static final int SIZE = 3;
    private int[][] board;
    private JButton[][] buttons;
    private int emptyRow, emptyCol;
    private static final int[][] TARGET_PUZZLE = 
    {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 0}
    };

    public EightPuzzleGUIFinal() 
    {
        setTitle("8-Puzzle Game");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(SIZE, SIZE));

        board = new int[SIZE][SIZE];
        buttons = new JButton[SIZE][SIZE];

        initializeBoard();
        initializeButtons();

        setVisible(true);
    }

    private void initializeBoard() 
    {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < SIZE * SIZE; i++) numbers.add(i);

        do {
            Collections.shuffle(numbers);
        } while (!isSolvable(numbers));

        int index = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = numbers.get(index++);
                if (board[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                }
            }
        }
    }

    private void initializeButtons() 
    {
        for (int i = 0; i < SIZE; i++) 
        {
            for (int j = 0; j < SIZE; j++) 
            {
                buttons[i][j] = new JButton(board[i][j] == 0 ? "" : String.valueOf(board[i][j]));
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].setFocusable(false); 
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                add(buttons[i][j]);
            }
        }
    }

    private boolean isSolvable(List<Integer> numbers) 
    {
        int inversions = 0;
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                if (numbers.get(i) > numbers.get(j) && numbers.get(i) != 0 && numbers.get(j) != 0) {
                    inversions++;
                }
            }
        }
        return inversions % 2 == 0;
    }

    private class ButtonClickListener implements ActionListener 
    {
        private int row, col;

        public ButtonClickListener(int row, int col) 
        {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if (isAdjacentToEmpty(row, col)) 
            {
                swapTiles(row, col);
                updateBoard();
                if (isSolved()) {
                    JOptionPane.showMessageDialog(EightPuzzleGUIFinal.this, "Congratulations! You solved the puzzle!");
                }
            }
        }
    }

    private boolean isAdjacentToEmpty(int row, int col) 
    {
        return (Math.abs(row - emptyRow) == 1 && col == emptyCol) || (Math.abs(col - emptyCol) == 1 && row == emptyRow);
    }

    private void swapTiles(int row, int col) 
    {
        int temp = board[row][col];
        board[row][col] = board[emptyRow][emptyCol];
        board[emptyRow][emptyCol] = temp;

        emptyRow = row;
        emptyCol = col;
    }

    private void updateBoard() 
    {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buttons[i][j].setText(board[i][j] == 0 ? "" : String.valueOf(board[i][j]));
            }
        }
    }

    private boolean isSolved() 
    {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] != TARGET_PUZZLE[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) 
    {
        new EightPuzzleGUIFinal();
    }
}
