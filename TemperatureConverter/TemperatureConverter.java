package TemperatureConverter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TemperatureConverter {
    public static void main(String[] args) {
        // Create frame
        JFrame frame = new JFrame("Temperature Converter");
        frame.setSize(450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 2, 10, 10)); // More spacing

        // Set background color
        JPanel panel = new JPanel();
        panel.setBackground(new Color(230, 240, 255)); // Light blue
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        frame.setContentPane(panel);

        // Create components
        JLabel label1 = new JLabel("Enter Temperature:", SwingConstants.CENTER);
        JTextField tempInput = new JTextField();
        JLabel label2 = new JLabel("Converted Temperature:", SwingConstants.CENTER);
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        JButton toCelsius = new JButton("To Celsius");
        JButton toFahrenheit = new JButton("To Fahrenheit");
        JButton toKelvin = new JButton("To Kelvin");
        JButton fromKelvin = new JButton("From Kelvin");

        // Style buttons
        JButton[] buttons = {toCelsius, toFahrenheit, toKelvin, fromKelvin};
        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setBackground(new Color(50, 150, 250)); // Blue
            button.setForeground(Color.WHITE);
        }

        // Celsius Conversion
        toCelsius.addActionListener(e -> {
            try {
                double fahrenheit = Double.parseDouble(tempInput.getText());
                double celsius = (fahrenheit - 32) * 5 / 9;
                resultLabel.setText(String.format("%.2f °C", celsius));
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid input!");
            }
        });

        // Fahrenheit Conversion
        toFahrenheit.addActionListener(e -> {
            try {
                double celsius = Double.parseDouble(tempInput.getText());
                double fahrenheit = (celsius * 9 / 5) + 32;
                resultLabel.setText(String.format("%.2f °F", fahrenheit));
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid input!");
            }
        });

        // Convert to Kelvin
        toKelvin.addActionListener(e -> {
            try {
                double celsius = Double.parseDouble(tempInput.getText());
                double kelvin = celsius + 273.15;
                resultLabel.setText(String.format("%.2f K", kelvin));
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid input!");
            }
        });

        // Convert from Kelvin to Celsius
        fromKelvin.addActionListener(e -> {
            try {
                double kelvin = Double.parseDouble(tempInput.getText());
                double celsius = kelvin - 273.15;
                resultLabel.setText(String.format("%.2f °C", celsius));
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid input!");
            }
        });

        // Add components to panel
        panel.add(label1);
        panel.add(tempInput);
        panel.add(label2);
        panel.add(resultLabel);
        panel.add(toCelsius);
        panel.add(toFahrenheit);
        panel.add(toKelvin);
        panel.add(fromKelvin);

        // Show frame
        frame.setVisible(true);
    }
}
