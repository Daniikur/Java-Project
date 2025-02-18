import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeAI {
    private JFrame frame;
    private JButton[][] buttons;
    private char currentPlayer;
    private char[][] board;

    public TicTacToeAI() {
        frame = new JFrame("Tic Tac Toe");
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 3));

        buttons = new JButton[3][3];
        board = new char[3][3];
        currentPlayer = 'X';

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton(" ");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                buttons[i][j].setFocusPainted(false);
                final int row = i, col = j;
                buttons[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (board[row][col] == '\0' && currentPlayer == 'X') {
                            board[row][col] = currentPlayer;
                            buttons[row][col].setText(String.valueOf(currentPlayer));
                            if (checkWin()) {
                                JOptionPane.showMessageDialog(frame, "Player " + currentPlayer + " wins!");
                                resetBoard();
                            } else if (isBoardFull()) {
                                JOptionPane.showMessageDialog(frame, "It's a draw!");
                                resetBoard();
                            } else {
                                currentPlayer = 'O';
                                aiMove();
                            }
                        }
                    }
                });
                frame.add(buttons[i][j]);
                board[i][j] = '\0';
            }
        }

        frame.setVisible(true);
    }

    private void aiMove() {
        int[] bestMove = minimax(board, 'O');
        board[bestMove[1]][bestMove[2]] = 'O';
        buttons[bestMove[1]][bestMove[2]].setText("O");

        if (checkWin()) {
            JOptionPane.showMessageDialog(frame, "AI wins!");
            resetBoard();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(frame, "It's a draw!");
            resetBoard();
        } else {
            currentPlayer = 'X';
        }
    }

    private int[] minimax(char[][] board, char player) {
        int bestScore = (player == 'O') ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int[] bestMove = {-1, -1, -1};

        if (checkWin()) return new int[] {player == 'O' ? -1 : 1, -1, -1};
        if (isBoardFull()) return new int[] {0, -1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '\0') {
                    board[i][j] = player;
                    int score = minimax(board, (player == 'O') ? 'X' : 'O')[0];
                    board[i][j] = '\0';

                    if ((player == 'O' && score > bestScore) || (player == 'X' && score < bestScore)) {
                        bestScore = score;
                        bestMove = new int[] {score, i, j};
                    }
                }
            }
        }
        return bestMove;
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '\0' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) return true;
            if (board[0][i] != '\0' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) return true;
        }
        if (board[0][0] != '\0' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) return true;
        if (board[0][2] != '\0' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) return true;
        return false;
    }

    private boolean isBoardFull() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == '\0') return false;
            }
        }
        return true;
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '\0';
                buttons[i][j].setText(" ");
            }
        }
        currentPlayer = 'X';
    }

    public static void main(String[] args) {
        new TicTacToeAI();
    }
}
