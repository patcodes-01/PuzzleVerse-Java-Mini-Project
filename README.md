# PuzzleVerse

## Overview
Puzzle Adventure is a Java-based desktop game that challenges players with two puzzle levels:
1. **Jigsaw Puzzle** - Fix the scrambled picture by placing the pieces correctly. 
2. **Number Puzzle (8-Puzzle)** - Move the tiles around to get them in the right order.

Built using **Java Swing**, this game provides an interactive GUI for players to solve these puzzles in a fun and engaging way.

## Features
- **Jigsaw Puzzle Level:**
  - Loads an image and splits it into pieces.
  - Players swap pieces to complete the original image.
  - Only adjacent pieces can be swapped. 
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


## Contributions
Future improvements could include:
- More difficulty levels.
- Timer and score tracking.
- Additional puzzle types.

## Demo Video
Demo Video Link: https://drive.google.com/file/d/1VA2xyyT7I9AzfnlVsrU_QKauG-DLEWVV/view?usp=drive_link
