import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;

public class PuzzleGameManager 
{
    private static int currentLevel = 1;
    private static JFrame currentFrame;

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> showWelcomeScreen());
    }

    private static void showWelcomeScreen() 
    {
        JFrame frame = new JFrame("Java Mini Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("PuzzleVerse", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JTextArea desc = new JTextArea
        (
            "Level 1: Jigsaw Puzzle\nPiece the picture back together!\n\n" +
            "Rules:\n1. Click on a piece to select it.\n2. Click on any ADJACENT piece to swap.\n\n" +
            "Complete the image to unlock Level 2 !\n\n"
        );
        desc.setEditable(false);
        desc.setMargin(new Insets(15, 15, 15, 15));

        JButton startBtn = new JButton("Start Level 1");
        startBtn.addActionListener(e -> {
            frame.dispose();
            startLevel1();
        });

        panel.add(title, BorderLayout.NORTH);
        panel.add(desc, BorderLayout.CENTER);
        panel.add(startBtn, BorderLayout.SOUTH);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void startLevel1() 
    {
        currentFrame = new JFrame("Level 1: Jigsaw Puzzle");
        currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currentFrame.setSize(720, 720);

        JigsawPuzzlePanel puzzle = new JigsawPuzzlePanel("C:\\Users\\cthaw\\Desktop\\Java\\Rough\\Image.jpg") {
            @Override
            protected void onLevelComplete() {
                SwingUtilities.invokeLater(() -> {
                    currentFrame.dispose();
                    JOptionPane.showMessageDialog(null, "You successfully completed Level 1!\n\n" +
                             "Get ready for Level 2: Number Puzzle!\n\n" +
                             "Rules:\n" +
                             "1️. Click on a tile adjacent to the empty space to move it.\n" +
                             "2. Arrange the numbers in ascending order.\n" +
                             "3. Complete the puzzle to win the game!\n\n" +
                             "Click OK to start Level 2. Good luck! ");
                    currentLevel = 2;
                    startLevel2();
                });
            }
        };

        currentFrame.add(puzzle);
        currentFrame.setLocationRelativeTo(null);
        currentFrame.setVisible(true);
    }

    private static void startLevel2() 
    {
        currentFrame = new JFrame("Level 2: Number Puzzle");
        currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currentFrame.setSize(400, 400);

        EightPuzzlePanel puzzle = new EightPuzzlePanel() {
            @Override
            protected void onLevelComplete() {
                SwingUtilities.invokeLater(() -> {
                    currentFrame.dispose();
                    showFinalScreen();
                });
            }
        };

        currentFrame.add(puzzle);
        currentFrame.setLocationRelativeTo(null);
        currentFrame.setVisible(true);
    }

    private static void showFinalScreen() 
    {
        JFrame frame = new JFrame("Quest Complete!");
        frame.setSize(400, 200);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Congratulations! You've mastered both the levels!", SwingConstants.CENTER), BorderLayout.CENTER);

        JButton restartBtn = new JButton("Restart Quest");
        restartBtn.addActionListener(e -> {
            frame.dispose();
            currentLevel = 1;
            showWelcomeScreen();
        });

        panel.add(restartBtn, BorderLayout.SOUTH);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static class JigsawPuzzlePanel extends JPanel 
    {
        private final int rows = 3, cols = 3;
        private ArrayList<JigsawPiece> pieces = new ArrayList<>();
        private BufferedImage image;
        private int pieceWidth, pieceHeight;
        private JigsawPiece selectedPiece;

        public JigsawPuzzlePanel(String imagePath) 
        {
            try {
                image = ImageIO.read(new File(imagePath));
                pieceWidth = image.getWidth() / cols;
                pieceHeight = image.getHeight() / rows;
                initializePieces();
                shufflePieces();
                addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        handleClick(e.getX(), e.getY());
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void initializePieces() 
        {
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    BufferedImage sub = image.getSubimage(
                        col * pieceWidth, row * pieceHeight, pieceWidth, pieceHeight
                    );
                    pieces.add(new JigsawPiece(sub, col, row));
                }
            }
        }

        private void shufflePieces() 
        {
            Collections.shuffle(pieces);
            int i = 0;
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    pieces.get(i++).setPosition(x, y);
                }
            }
        }

        private void handleClick(int x, int y) 
        {
            for (JigsawPiece p : pieces) {
                if (p.contains(x, y)) {
                    if (selectedPiece == null) {
                        selectedPiece = p;
                        p.highlight(true);
                    } else if (areAdjacent(selectedPiece, p)) {
                        swapPieces(selectedPiece, p);
                        selectedPiece.highlight(false);
                        selectedPiece = null;
                        if (isSolved()) onLevelComplete();
                    }
                    repaint();
                    break;
                }
            }
        }

        protected void onLevelComplete() 
        {
            // To be overridden by GameManager
            JOptionPane.showMessageDialog(this, "Congratulations! You solved the puzzle.");
        }

        private boolean areAdjacent(JigsawPiece p1, JigsawPiece p2) {
            return (Math.abs(p1.curX - p2.curX) == 1 && p1.curY == p2.curY) || 
                   (Math.abs(p1.curY - p2.curY) == 1 && p1.curX == p2.curX);
        }

        private void swapPieces(JigsawPiece p1, JigsawPiece p2) {
            int tempX = p1.curX, tempY = p1.curY;
            p1.setPosition(p2.curX, p2.curY);
            p2.setPosition(tempX, tempY);
            repaint();
       }

       public boolean isSolved() {
           for (JigsawPiece piece : pieces) {
               if (!piece.isCorrectPosition()) return false;
           }
           return true;
       }

       @Override
       protected void paintComponent(Graphics g) {
           super.paintComponent(g);
           for (JigsawPiece piece : pieces) piece.draw(g);
       }
   }

   static class EightPuzzlePanel extends JPanel 
   {
       private final int SIZE = 3;
       private int[][] board;
       private JButton[][] buttons;
       private int emptyRow, emptyCol;

       public EightPuzzlePanel() {
           setLayout(new GridLayout(SIZE, SIZE));
           initializeBoard();
           createButtons();
       }

       private void initializeBoard() {
           List<Integer> nums = new ArrayList<>();
           for (int i=0; i<SIZE*SIZE; i++) nums.add(i);

           do { Collections.shuffle(nums); } 
           while (!isSolvable(nums));

           int idx=0;
           board = new int[SIZE][SIZE];
           for (int i=0; i<SIZE; i++) {
               for (int j=0; j<SIZE; j++) {
                   board[i][j] = nums.get(idx++);
                   if(board[i][j] == 0) { 
                       emptyRow = i; 
                       emptyCol = j; 
                   }
               }
           }
       }

       private void createButtons() 
       {
           buttons = new JButton[SIZE][SIZE];
           for (int i=0; i<SIZE; i++) {
               for (int j=0; j<SIZE; j++) {
                   final int row = i;
                   final int col = j;
                   JButton btn = new JButton(board[row][col] == 0 ? "" : "" + board[row][col]);
                   btn.setFont(new Font("Arial", Font.BOLD, 32));
                   btn.addActionListener(e -> handleMove(btn, row, col));
                   add(btn);
                   buttons[row][col] = btn;
               }
           }
       }

       private void handleMove(JButton btn, int row, int col) 
       {
           if(Math.abs(row-emptyRow) + Math.abs(col-emptyCol) == 1) { 
               buttons[emptyRow][emptyCol].setText(btn.getText());
               btn.setText("");
               board[emptyRow][emptyCol] = board[row][col];
               board[row][col] = 0;

               emptyRow = row;
               emptyCol = col;

               if(isSolved()) onLevelComplete();
           }
       }

       protected void onLevelComplete() { 
           JOptionPane.showMessageDialog(this,"Congratulations! You solved the puzzle!"); 
       }

       private boolean isSolvable(List<Integer> numbers) { 
           int inversions = 0; 
           for(int i=0;i<numbers.size();i++) { 
               for(int j=i+1;j<numbers.size();j++) { 
                   if(numbers.get(i)>numbers.get(j)&&numbers.get(i)!=0&&numbers.get(j)!=0){ 
                       inversions++; 
                   } 
               } 
           } 
           return inversions % 2 == 0; 
       }

       public boolean isSolved() { 
           int[][] targetPuzzle =
               {{1,2,3},{4,5,6},{7,8,0}};
           
           for(int i=0;i<SIZE;i++) { 
               for(int j=0;j<SIZE;j++) { 
                   if(board[i][j]!=targetPuzzle[i][j]) return false; 
               } 
           } 
           return true; 
       }
   }

   static class JigsawPiece 
   {
       BufferedImage image;
       int originalX, originalY;
       int curX, curY;

       public JigsawPiece(BufferedImage img,int origX,int origY){
          this.image=img;
          this.originalX=origX;
          this.originalY=origY;
          this.curX=origX;
          this.curY=origY;
       }

       public boolean isCorrectPosition(){ return curX==originalX && curY==originalY; }
       
       public void setPosition(int x,int y){ curX=x; curY=y; }

       public boolean contains(int x,int y){
          return x >= curX * image.getWidth() && x < (curX + 1) * image.getWidth()
              && y >= curY * image.getHeight() && y < (curY + 1) * image.getHeight();
      }

    private boolean isHighlighted = false; // Track highlight state

    public void highlight(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }
    
    public void draw(Graphics g) 
    {
        int padding = 2; 
        int x = curX * (image.getWidth() + padding);
        int y = curY * (image.getHeight() + padding);
    
        g.drawImage(image, x, y, null);
    
        if (isHighlighted) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.MAGENTA); 
            g2.setStroke(new BasicStroke(4));
            g2.drawRect(x, y, image.getWidth() - 1, image.getHeight() - 1);
        }
    }

   }

}
