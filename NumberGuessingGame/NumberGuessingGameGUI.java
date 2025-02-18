package NumberGuessingGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NumberGuessingGameGUI {
    private static int randomNumber;
    private static int attempts = 0;

    public static void main(String[] args) {
        // Generate a random number between 1 and 100
        randomNumber = new Random().nextInt(100) + 1;

        // Create a frame for the game
        JFrame frame = new JFrame("Number Guessing Game");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set background color
        frame.getContentPane().setBackground(Color.CYAN);

        // Create components
        JLabel instructionLabel = new JLabel("Guess a number between 1 and 100:");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        JTextField inputField = new JTextField(10);
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton guessButton = new JButton("Guess");
        guessButton.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel feedbackLabel = new JLabel("");
        feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel attemptsLabel = new JLabel("Attempts: 0");
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // Layout setup
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        panel.add(instructionLabel);
        panel.add(inputField);
        panel.add(guessButton);
        panel.add(feedbackLabel);
        panel.add(attemptsLabel);
        
        frame.add(panel);
        frame.setVisible(true);

        // Action listener for the Guess button
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText();
                try {
                    int guess = Integer.parseInt(userInput);
                    attempts++;

                    // Update attempts label
                    attemptsLabel.setText("Attempts: " + attempts);

                    // Compare guess to random number
                    if (guess < randomNumber) {
                        feedbackLabel.setText("Too low! Try again.");
                    } else if (guess > randomNumber) {
                        feedbackLabel.setText("Too high! Try again.");
                    } else {
                        feedbackLabel.setText("Congratulations! You guessed the number!");
                    }
                } catch (NumberFormatException ex) {
                    feedbackLabel.setText("Invalid input! Please enter a number.");
                }
            }
        });
    }
}
