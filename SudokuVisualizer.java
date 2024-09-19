import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SudokuVisualizer extends JFrame {
    static final int SIZE = 9;
    private JTextField[][] cells;
    private SudokuSolver solver;
    private JPanel gridPanel;
    private boolean isSolved = false;
    private SolverWorker currentSolverWorker; // Field to track the ongoing solver

    public SudokuVisualizer() {
        solver = new SudokuSolver(this);
        cells = new JTextField[SIZE][SIZE];
        setTitle("Sudoku Solver");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK); // Set background color

        gridPanel = new JPanel(new GridLayout(SIZE, SIZE, 8, 8)); // Add 8-pixel gaps between cells
        gridPanel.setBackground(Color.BLACK); // Set background color for grid panel
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the grid panel

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setBackground(Color.BLACK); // Set cell background color
                cells[row][col].setForeground(Color.WHITE); // Set text color to white
                cells[row][col].setFont(new Font("Arial", Font.BOLD, 30)); // Set font
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // White border with thin
                                                                                           // line
                gridPanel.add(cells[row][col]);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.BLACK); // Set background color for button panel

        JButton solveButton = new JButton("Solve");
        solveButton.setBackground(Color.CYAN); // Set button background color
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] board = readBoard();
                if (currentSolverWorker != null && !currentSolverWorker.isDone()) {
                    currentSolverWorker.cancel(true); // Cancel the ongoing solver
                }
                currentSolverWorker = new SolverWorker(board);
                currentSolverWorker.execute();
            }
        });
        buttonPanel.add(solveButton);

        JButton randomButton = new JButton("Random Board");
        randomButton.setBackground(Color.CYAN); // Set button background color
        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRandomBoard();
            }
        });
        buttonPanel.add(randomButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBackground(Color.CYAN); // Set button background color
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearBoard();
            }
        });
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private int[][] readBoard() {
        int[][] board = new int[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                String text = cells[row][col].getText();
                if (!text.isEmpty()) {
                    board[row][col] = Integer.parseInt(text);
                }
            }
        }
        return board;
    }

    public void updateBoard(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                String currentText = cells[row][col].getText();
                String newText = board[row][col] == 0 ? "" : String.valueOf(board[row][col]);
                if (!currentText.equals(newText)) {
                    if (newText.isEmpty()) {
                        cells[row][col].setBackground(Color.BLACK);
                    } else if (solver.isSafe(board, row, col, board[row][col])) {
                        cells[row][col].setBackground(Color.GREEN); // Correct number turns green
                    } else {
                        blinkCell(cells[row][col]); // Blink red for incorrect attempts
                    }
                    cells[row][col].setText(newText);
                }
            }
        }
        // Set entire grid background to green upon solving
        if (!isSolved && solver.isSudokuSolved(board)) {
            gridPanel.setBackground(Color.GREEN);
            isSolved = true;
        }
    }

    private void blinkCell(JTextField cell) {
        final Color originalColor = cell.getBackground();
        cell.setBackground(Color.RED);
        Timer timer = new Timer(500, new ActionListener() {
            boolean toggled = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (toggled) {
                    cell.setBackground(Color.RED);
                } else {
                    cell.setBackground(originalColor);
                }
                toggled = !toggled;
            }
        });
        timer.setRepeats(false); // Blink only once
        timer.start();
    }

    private void generateRandomBoard() {
        Random random = new Random();
        clearBoard(); // Clear the board before generating a new random board
        for (int i = 0; i < 9; i++) {
            int num = random.nextInt(SIZE) + 1; // Random number between 1 to SIZE
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            cells[row][col].setText(String.valueOf(num));
        }
    }

    private void clearBoard() {
        if (currentSolverWorker != null && !currentSolverWorker.isDone()) {
            currentSolverWorker.cancel(true); // Attempt to cancel the ongoing solving operation
        }

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col].setText("");
                cells[row][col].setBackground(Color.BLACK); // Reset background color
            }
        }
        gridPanel.setBackground(Color.BLACK); // Reset grid panel background color
        isSolved = false; // Reset solved status
    }

    private class SolverWorker extends SwingWorker<Boolean, Void> {
        private int[][] board;

        public SolverWorker(int[][] board) {
            this.board = board;
        }

        @Override
        protected Boolean doInBackground() throws Exception {
            // Check if the worker is cancelled before each step
            if (isCancelled())
                return false;
            return solver.solveSudoku(board);
        }

        @Override
        protected void done() {
            try {
                if (isCancelled()) {
                    // Handle cancellation (optional)
                    JOptionPane.showMessageDialog(null, "Operation cancelled.");
                } else if (get()) {
                    JOptionPane.showMessageDialog(null, "Sudoku solved successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Sudoko!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SudokuVisualizer().setVisible(true));
    }
}
