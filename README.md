# Sudoku Solver

A simple **Sudoku Solver** built using **Java** and **Swing** for the graphical user interface. This application allows users to input a Sudoku puzzle, and the program solves it using a backtracking algorithm.

## Features

- Interactive 9x9 grid for Sudoku input
- Solves any valid 9x9 Sudoku puzzle using backtracking
- Option to clear the grid or input new puzzles
- Visualizes the solving process in real-time
- Input validation to prevent incorrect puzzle entries

## Technologies Used

- **Java**: For core logic and backtracking algorithm
- **Java Swing**: For building the user interface

## How It Works

The Sudoku Solver uses a **backtracking algorithm** that works as follows:

1. Find the first empty cell in the grid.
2. Try placing numbers 1 through 9 in the empty cell.
3. Check if placing the number violates Sudoku rules (i.e., no duplicates in rows, columns, or 3x3 grids).
4. Recursively attempt to solve the rest of the puzzle.
5. If no valid number can be placed, backtrack to the previous cell and try the next possible number.

## Time and Space Complexity

### Time Complexity

The time complexity of the backtracking algorithm used in the Sudoku solver is quite high due to the large number of possible combinations the algorithm must explore.

- In the worst case, the algorithm tries to fill each of the 81 cells (for a 9x9 Sudoku grid) with numbers between 1 and 9.
- For each empty cell, the algorithm may potentially try all 9 possible numbers, leading to a time complexity of approximately **O(9^(n))**, where `n` is the number of empty cells.

For a completely blank Sudoku grid (all 81 cells empty), the time complexity becomes **O(9^81)**, which is exponential. However, most real-world Sudoku puzzles have many pre-filled cells, reducing the number of recursive calls.

### Space Complexity

The space complexity depends on the number of recursive calls made by the backtracking algorithm, which is proportional to the number of empty cells in the grid.

- In the worst case, the recursion stack can grow up to a depth of `n`, where `n` is the number of empty cells.
- Therefore, the space complexity is **O(n)** due to the depth of the recursion.

Additionally, the space used to store the board (9x9 grid) is **O(1)** since the size of the grid is constant.

## Getting Started

### Prerequisites

Ensure that **Java JDK** is installed on your system. If not, you can download it from [here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

### How to Run

1. Clone this repository to your local machine:

    ```bash
    git clone https://github.com/anikett2109/sudoko-solver.git
    ```

2. Navigate to the project directory:

    ```bash
    cd sudoko-solver
    ```

3. Compile and run the project:

    ```bash
    javac SudokuVisualizer.java
    java SudokuVisualizer
    ```

### Usage

- Enter your Sudoku puzzle by filling the 9x9 grid.
- Click on the **"Solve"** button to solve the puzzle.
- Use the **"Clear"** button to reset the grid for new input.

## Screenshots

![image](https://github.com/user-attachments/assets/8e39f509-8717-403e-84ff-c4def100b402)


![image](https://github.com/user-attachments/assets/55f870db-22d4-42ef-8e6c-6bc02856f1cd)


![image](https://github.com/user-attachments/assets/129a229c-1199-4719-8acd-778cff085964)



## Contributing

Contributions are welcome! If you'd like to improve this project:

1. Fork this repository.
2. Create a new feature branch (`git checkout -b feature/YourFeature`).
3. Commit your changes (`git commit -m 'Add YourFeature'`).
4. Push to the branch (`git push origin feature/YourFeature`).
5. Open a Pull Request.

