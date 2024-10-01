public class SudokuSolver {
    private static final int SIZE = 9;
    private SudokuVisualizer visualizer;

    public SudokuSolver(SudokuVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    public boolean solveSudoku(int[][] board) {
        if (!isValidBoard(board)) {
            return false; // Invalid board, cannot solve
        }
        return solveSudokuRecursive(board, 0, 0);
    }

    private boolean solveSudokuRecursive(int[][] board, int row, int col) {
        if (row == SIZE) {
            return true; // Reached end of the board
        }

        int nextRow = (col == SIZE - 1) ? row + 1 : row;
        int nextCol = (col == SIZE - 1) ? 0 : col + 1;

        if (board[row][col] != 0) {
            return solveSudokuRecursive(board, nextRow, nextCol);
        }

        for (int num = 1; num <= SIZE; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;
                visualizer.updateBoard(board); // Update the GUI
                delay(2); // Add a delay to visualize the process
                if (solveSudokuRecursive(board, nextRow, nextCol)) {
                    return true;
                }
                board[row][col] = 0;
                visualizer.updateBoard(board); // Update the GUI
                delay(2); // Add a delay to visualize the process
            }
        }
        return false;
    }

    public boolean isSafe(int[][] board, int row, int col, int num) {
        // Check row and column constraints
        for (int x = 0; x < SIZE; x++) {
            if (board[row][x] == num || board[x][col] == num) {
                return false;
            }
        }
        // Check 3x3 box constraint
        int boxRowStart = row - row % 3;
        int boxColStart = col - col % 3;
        for (int i = boxRowStart; i < boxRowStart + 3; i++) {
            for (int j = boxColStart; j < boxColStart + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isSudokuSolved(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    return false; // If any cell is empty, Sudoku is not solved
                }
            }
        }
        return true;
    }

    public boolean isValidBoard(int[][] board) {
        // check for input greater than 9 or less than 1
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] != 0) {
                    if (board[i][j] > 9 || board[i][j] < 1) {
                        return false;
                    }
                }
            }
        }
        // Check rows, columns, and 3x3 boxes
        for (int i = 0; i < SIZE; i++) {
            boolean[] rowCheck = new boolean[SIZE];
            boolean[] colCheck = new boolean[SIZE];
            boolean[] boxCheck = new boolean[SIZE];
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] != 0) {
                    if (rowCheck[board[i][j] - 1])
                        return false;
                    rowCheck[board[i][j] - 1] = true;
                }
                if (board[j][i] != 0) {
                    if (colCheck[board[j][i] - 1])
                        return false;
                    colCheck[board[j][i] - 1] = true;
                }
                int boxRow = (i / 3) * 3 + j / 3;
                int boxCol = (i % 3) * 3 + j % 3;
                if (board[boxRow][boxCol] != 0) {
                    if (boxCheck[board[boxRow][boxCol] - 1])
                        return false;
                    boxCheck[board[boxRow][boxCol] - 1] = true;
                }
            }
        }
        return true;
    }

    private void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
