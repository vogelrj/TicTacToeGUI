import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame {
    private TicTacToeTile[][] board;
    private String currentPlayer;
    private int moveCount;
    private static final int MOVES_FOR_WIN = 5;
    private static final int MOVES_FOR_TIE = 7;

    public TicTacToe() {
        setTitle("Tic-Tac-Toe");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 3));
        board = new TicTacToeTile[3][3];

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = new TicTacToeTile(row, col);
                board[row][col].setFont(new Font("Arial", Font.BOLD, 50));
                board[row][col].addActionListener(new ButtonClickListener());
                gamePanel.add(board[row][col]);
            }
        }

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        JPanel controlPanel = new JPanel();
        controlPanel.add(quitButton);

        add(gamePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        startNewGame();
    }

    private void startNewGame() {
        currentPlayer = "X";
        moveCount = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col].setText("");
            }
        }
    }

    private boolean promptNewGame() {
        int response = JOptionPane.showConfirmDialog(this, "Would you like to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
        return response == JOptionPane.YES_OPTION;
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0].getText().equals(currentPlayer) && board[i][1].getText().equals(currentPlayer) && board[i][2].getText().equals(currentPlayer))
                return true;
            if (board[0][i].getText().equals(currentPlayer) && board[1][i].getText().equals(currentPlayer) && board[2][i].getText().equals(currentPlayer))
                return true;
        }
        if (board[0][0].getText().equals(currentPlayer) && board[1][1].getText().equals(currentPlayer) && board[2][2].getText().equals(currentPlayer))
            return true;
        return board[0][2].getText().equals(currentPlayer) && board[1][1].getText().equals(currentPlayer) && board[2][0].getText().equals(currentPlayer);
    }

    private boolean checkTie() {
        if (moveCount < MOVES_FOR_TIE) return false;
        boolean xFlag, oFlag;
        for (int col = 0; col < 3; col++) {
            xFlag = oFlag = false;
            for (int row = 0; row < 3; row++) {
                if (board[row][col].getText().equals("X")) xFlag = true;
                if (board[row][col].getText().equals("O")) oFlag = true;
            }
            if (!(xFlag && oFlag)) return false;
        }
        return true;
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TicTacToeTile clickedTile = (TicTacToeTile) e.getSource();
            if (!clickedTile.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invalid move! Try again.");
                return;
            }
            clickedTile.setText(currentPlayer);
            moveCount++;

            if (moveCount >= MOVES_FOR_WIN && checkWin()) {
                JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                if (promptNewGame()) {
                    startNewGame();
                } else {
                    System.exit(0);
                }
                return;
            }

            if (moveCount >= MOVES_FOR_TIE && checkTie()) {
                JOptionPane.showMessageDialog(null, "It's a tie!");
                if (promptNewGame()) {
                    startNewGame();
                } else {
                    System.exit(0);
                }
                return;
            }
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
        }
    }
}
