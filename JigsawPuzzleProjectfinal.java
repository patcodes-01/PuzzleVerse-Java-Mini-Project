import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class JigsawPuzzleProjectfinal extends JPanel 
{
    private final int rows = 3;
    private final int cols = 3;
    private ArrayList<JigsawPiece> pieces = new ArrayList<>();
    private BufferedImage image;
    private int pieceWidth, pieceHeight;
    private JigsawPiece selectedPiece = null;

    public JigsawPuzzleProjectfinal(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
            pieceWidth = image.getWidth() / cols;
            pieceHeight = image.getHeight() / rows;
            initializePieces();
            shufflePieces(); 
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    handleMousePressed(e);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializePieces() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                BufferedImage subImage = image.getSubimage(col * pieceWidth, row * pieceHeight, pieceWidth, pieceHeight);
                pieces.add(new JigsawPiece(subImage, row, col));
            }
        }
    }

    private void shufflePieces() {
        Collections.shuffle(pieces);
        int index = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                pieces.get(index++).setCurrentPosition(col, row); 
            }
        }
    }

    private void handleMousePressed(MouseEvent e) 
    {
        int x = e.getX();
        int y = e.getY();
        for (JigsawPiece piece : pieces) {
            if (piece.contains(x, y)) {
                if (selectedPiece == null) {
                    selectedPiece = piece;
                    piece.setHighlight(true); 
                } else if (selectedPiece != piece) {
                    if (areAdjacent(selectedPiece, piece)) {
                        swapPieces(selectedPiece, piece); 
                        selectedPiece.setHighlight(false); 
                        selectedPiece = null;
                        repaint(); 
                        if (isSolved()) 
                        {
                            JOptionPane.showMessageDialog(null, "Congratulations! You solved the puzzle.");
                        }
                    }
                }
                break;
            }
        }
    }

    private boolean areAdjacent(JigsawPiece p1, JigsawPiece p2) 
    {
        return (Math.abs(p1.curX - p2.curX) == 1 && p1.curY == p2.curY) || (Math.abs(p1.curY - p2.curY) == 1 && p1.curX == p2.curX);
    }

    private void swapPieces(JigsawPiece p1, JigsawPiece p2) {
        int tempX = p1.curX, tempY = p1.curY;
        p1.setCurrentPosition(p2.curX, p2.curY);
        p2.setCurrentPosition(tempX, tempY);
    }

    public boolean isSolved() {
        for (JigsawPiece piece : pieces) {
            if (!piece.isCorrectPosition()) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (JigsawPiece piece : pieces) {
            piece.draw(g);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Jigsaw Puzzle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setResizable(false);
        frame.add(new JigsawPuzzleProjectfinal("C:\\Users\\cthaw\\Desktop\\Java\\Rough\\Image.jpg")); 
        frame.setVisible(true);
    }
}

class JigsawPiece {
    private BufferedImage image;
    private int originalX, originalY;
    int curX, curY;
    private boolean highlighted = false;

    public JigsawPiece(BufferedImage image, int originalX, int originalY) {
        this.image = image;
        this.originalX = originalX;
        this.originalY = originalY;
        this.curX = originalX;
        this.curY = originalY;
    }

    public void setCurrentPosition(int x, int y) {
        this.curX = x;
        this.curY = y;
    }

    public boolean isCorrectPosition() {
        return curX == originalX && curY == originalY;
    }

    public void setHighlight(boolean highlight) {
        this.highlighted = highlight;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void draw(Graphics g) {
        g.drawImage(image, curX * image.getWidth(), curY * image.getHeight(), null);
        g.setColor(Color.BLACK);
        g.drawRect(curX * image.getWidth(), curY * image.getHeight(), image.getWidth(), image.getHeight());
        if (highlighted) {
            g.setColor(Color.RED);
            g.drawRect(curX * image.getWidth(), curY * image.getHeight(), image.getWidth(), image.getHeight());
        }
    }

    public boolean contains(int x, int y) {
        return x >= curX * image.getWidth() && x < (curX + 1) * image.getWidth() &&
               y >= curY * image.getHeight() && y < (curY + 1) * image.getHeight();
    }
}
