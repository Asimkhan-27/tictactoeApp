package com.example.tictactoe;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // 9 buttons (grid)
    Button[] buttons = new Button[9];

    // Status text
    TextView statusText;

    // Reset button
    Button resetBtn;

    // Game variables
    boolean playerXTurn = true;
    int moveCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connect layout
        setContentView(R.layout.activity_main);

        // Link UI elements
        statusText = findViewById(R.id.statusText);
        resetBtn = findViewById(R.id.resetBtn);

        // Initialize grid buttons
        for (int i = 0; i < 9; i++) {
            String btnID = "btn" + i;
            int resID = getResources().getIdentifier(btnID, "id", getPackageName());
            buttons[i] = findViewById(resID);

            int index = i;

            buttons[i].setOnClickListener(v -> handleClick(index));
        }

        // Reset button
        resetBtn.setOnClickListener(v -> resetGame());
    }

    // Handle button click
    void handleClick(int index) {

        // Prevent overwrite
        if (!buttons[index].getText().toString().equals("")) return;

        // Set X or O
        if (playerXTurn) {
            buttons[index].setText("X");
            statusText.setText("Player O Turn");
        } else {
            buttons[index].setText("O");
            statusText.setText("Player X Turn");
        }

        moveCount++;

        // Check winner
        if (checkWin()) {
            statusText.setText("Player " + (playerXTurn ? "X" : "O") + " Wins!");
            disableButtons();
        }
        // Check draw
        else if (moveCount == 9) {
            statusText.setText("Draw!");
        }

        // Switch turn
        playerXTurn = !playerXTurn;
    }

    // Win logic
    boolean checkWin() {

        String[][] board = new String[3][3];

        for (int i = 0; i < 9; i++) {
            board[i / 3][i % 3] = buttons[i].getText().toString();
        }

        // Rows & Columns
        for (int i = 0; i < 3; i++) {
            if (equals(board[i][0], board[i][1], board[i][2])) return true;
            if (equals(board[0][i], board[1][i], board[2][i])) return true;
        }

        // Diagonals
        if (equals(board[0][0], board[1][1], board[2][2])) return true;
        if (equals(board[0][2], board[1][1], board[2][0])) return true;

        return false;
    }

    // Helper function
    boolean equals(String a, String b, String c) {
        return !a.equals("") && a.equals(b) && b.equals(c);
    }

    // Disable all buttons after win
    void disableButtons() {
        for (Button btn : buttons) {
            btn.setEnabled(false);
        }
    }

    // Reset game
    void resetGame() {
        for (Button btn : buttons) {
            btn.setText("");
            btn.setEnabled(true);
        }

        playerXTurn = true;
        moveCount = 0;
        statusText.setText("Player X Turn");
    }
}