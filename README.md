# PuzzleVerse

## Overview
Puzzle Adventure is a Java-based desktop game that challenges players with two puzzle levels:
1. **Jigsaw Puzzle** - Restore the ancient artifact by arranging image fragments.
2. **Number Puzzle (8-Puzzle)** - Arrange numbers in order to unlock the mystic code.

Built using **Java Swing**, this game provides an interactive GUI for players to solve these puzzles in a fun and engaging way.

## Features
- **Jigsaw Puzzle Level:**
  - Loads an image and splits it into pieces.
  - Players swap pieces to complete the original image.
  - Upon completion, players proceed to Level 2.
- **8-Puzzle Level:**
  - Players arrange numbered tiles in the correct order.
  - Ensures only solvable puzzles are generated.
  - Completing the puzzle ends the game with a congratulatory message.
- **User-Friendly Interface:**
  - Simple and engaging UI.
  - Click-based interactions for swapping puzzle pieces.
  - Game progression with messages and level transitions.

## Installation and Setup
### Prerequisites
- **Java Development Kit (JDK) 8+** installed
- **Git (optional)** for version control

### Steps to Run
1. **Clone the Repository** (if applicable):
   ```bash
   git clone https://github.com/your-repo-name/puzzle-adventure.git
   cd puzzle-adventure
   ```
2. **Compile the Game:**
   ```bash
   javac PuzzleGameManager.java
   ```
3. **Run the Game:**
   ```bash
   java PuzzleGameManager
   ```

## How to Play
1. **Start the game:** Run the program to see the welcome screen.
2. **Level 1: Jigsaw Puzzle:**
   - Click on puzzle pieces to select them.
   - Swap pieces by clicking on an adjacent piece.
   - Complete the image to unlock Level 2.
3. **Level 2: Number Puzzle (8-Puzzle):**
   - Click on a tile adjacent to the empty space to move it.
   - Arrange the numbers in order to win.
4. **Winning the Game:** Completing Level 2 displays a final success screen with an option to restart.

## File Structure
```
/puzzle-adventure
│── PuzzleGameManager.java   # Main game manager
│── JigsawPuzzlePanel.java   # Jigsaw puzzle logic
│── EightPuzzlePanel.java    # 8-puzzle logic
│── JigsawPiece.java         # Jigsaw piece class
│── assets/                  # (Optional) Images for jigsaw puzzle
```

## Customization
- To change the **Jigsaw Puzzle Image**, update the file path in:
  ```java
  new JigsawPuzzlePanel("C:\\path\\to\\your\\image.jpg");
  ```
- Modify **Grid Size** for different challenge levels.

## Contributions
Feel free to contribute by submitting **pull requests** or reporting issues. Future improvements could include:
- More difficulty levels.
- Timer and score tracking.
- Additional puzzle types.

## License
This project is open-source under the **MIT License**.

---
Enjoy solving the puzzles! 🚀

