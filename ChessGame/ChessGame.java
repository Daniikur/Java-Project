import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChessGame extends JFrame {
    private static final int BOARD_SIZE = 8;
    private JButton[][] squares = new JButton[BOARD_SIZE][BOARD_SIZE];
    private String selectedPiece = null;
    private int selectedRow = -1, selectedCol = -1;
    private boolean isWhiteTurn = true;
    private boolean singlePlayerMode = true;

    private final String[][] initialBoard = {
        {"♜", "♞", "♝", "♛", "♚", "♝", "♞", "♜"},
        {"♟", "♟", "♟", "♟", "♟", "♟", "♟", "♟"},
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""},
        {"♙", "♙", "♙", "♙", "♙", "♙", "♙", "♙"},
        {"♖", "♘", "♗", "♕", "♔", "♗", "♘", "♖"}
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChessGame());
    }

    public ChessGame() {
        setTitle("Chess Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        initializeBoard();

        setVisible(true);
    }

    private void initializeBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                squares[row][col] = new JButton(initialBoard[row][col]);
                squares[row][col].setFont(new Font("Arial", Font.BOLD, 40));
                squares[row][col].setFocusPainted(false);
                
                if ((row + col) % 2 == 0) {
                    squares[row][col].setBackground(Color.WHITE);
                } else {
                    squares[row][col].setBackground(Color.DARK_GRAY);
                }
                
                final int r = row;
                final int c = col;
                squares[row][col].addActionListener(_ -> handleSquareClick(r, c));
                
                add(squares[row][col]);
            }
        }
    }

    private void handleSquareClick(int row, int col) {
        String piece = initialBoard[row][col];

        if (selectedPiece == null && !piece.isEmpty() && isPieceColor(piece)) {
            selectedPiece = piece;
            selectedRow = row;
            selectedCol = col;
            highlightValidMoves(row, col);
        } else if (selectedPiece != null) {
            if (isValidMove(selectedRow, selectedCol, row, col)) {
                initialBoard[row][col] = selectedPiece;
                initialBoard[selectedRow][selectedCol] = "";
                updateBoard();
                selectedPiece = null;
                isWhiteTurn = !isWhiteTurn;
                resetBoardColors();
                if (singlePlayerMode && !isWhiteTurn) {
                    aiMove();
                }
            }
        }
    }

    private void aiMove() {
        List<int[]> possibleMoves = new ArrayList<>();
        
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (!initialBoard[row][col].isEmpty() && !isPieceColor(initialBoard[row][col])) {
                    for (int r = 0; r < BOARD_SIZE; r++) {
                        for (int c = 0; c < BOARD_SIZE; c++) {
                            if (isValidMove(row, col, r, c)) {
                                possibleMoves.add(new int[]{row, col, r, c});
                            }
                        }
                    }
                }
            }
        }

        if (!possibleMoves.isEmpty()) {
            Random rand = new Random();
            int[] move = possibleMoves.get(rand.nextInt(possibleMoves.size()));
            initialBoard[move[2]][move[3]] = initialBoard[move[0]][move[1]];
            initialBoard[move[0]][move[1]] = "";
            updateBoard();
            isWhiteTurn = true;
        }
    }

    private boolean isPieceColor(String piece) {
        return (isWhiteTurn && "♙♖♘♗♕♔".contains(piece)) || (!isWhiteTurn && "♟♜♞♝♛♚".contains(piece));
    }

    private boolean isValidMove(int startRow, int startCol, int endRow, int endCol) {
        return true;
    }

    private void updateBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                squares[row][col].setText(initialBoard[row][col]);
            }
        }
    }

    private void highlightValidMoves(int row, int col) {
        resetBoardColors();
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if (isValidMove(row, col, r, c)) {
                    squares[r][c].setBackground(Color.GREEN);
                }
            }
        }
    }

    private void resetBoardColors() {
        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                if ((r + c) % 2 == 0) {
                    squares[r][c].setBackground(Color.WHITE);
                } else {
                    squares[r][c].setBackground(Color.DARK_GRAY);
                }
            }
        }
    }
}
