import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DigitalClock extends JFrame implements Runnable {
    private JLabel clockLabel, stopwatchLabel;
    private JButton startButton, stopButton, resetButton;
    private boolean running = false;
    private long startTime = 0, elapsedTime = 0;
    private String alarmTime = "07:00 AM";

    public DigitalClock() {
        setTitle("Digital Clock & Stopwatch");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1));

        // Digital Clock Label
        clockLabel = new JLabel();
        clockLabel.setFont(new Font("Courier New", Font.BOLD, 60));
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        clockLabel.setOpaque(true);
        clockLabel.setBackground(Color.BLACK);
        clockLabel.setForeground(Color.GREEN);
        add(clockLabel);

        // Stopwatch Label
        stopwatchLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        stopwatchLabel.setFont(new Font("Courier New", Font.BOLD, 40));
        stopwatchLabel.setOpaque(true);
        stopwatchLabel.setBackground(Color.DARK_GRAY);
        stopwatchLabel.setForeground(Color.CYAN);
        add(stopwatchLabel);

        // Stopwatch Buttons
        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        resetButton = new JButton("Reset");

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(resetButton);
        add(buttonPanel);

        // Button Actions
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startStopwatch();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopStopwatch();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetStopwatch();
            }
        });

        setVisible(true);

        // Start the clock thread
        Thread clockThread = new Thread(this);
        clockThread.start();
    }

    // Clock Thread
    @Override
    public void run() {
        try {
            while (true) {
                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
                String currentTime = formatter.format(new Date());
                clockLabel.setText(currentTime);

                // Check for alarm
                if (currentTime.startsWith(alarmTime)) {
                    JOptionPane.showMessageDialog(this, "⏰ Alarm! Time's up!", "Alarm", JOptionPane.WARNING_MESSAGE);
                }

                if (running) {
                    updateStopwatch();
                }

                Thread.sleep(100); // Update every 100ms for stopwatch
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Stopwatch Methods
    private void startStopwatch() {
        if (!running) {
            running = true;
            startTime = System.currentTimeMillis() - elapsedTime;
        }
    }

    private void stopStopwatch() {
        running = false;
        elapsedTime = System.currentTimeMillis() - startTime;
    }

    private void resetStopwatch() {
        running = false;
        elapsedTime = 0;
        stopwatchLabel.setText("00:00:00");
    }

    private void updateStopwatch() {
        elapsedTime = System.currentTimeMillis() - startTime;
        long hours = elapsedTime / 3600000;
        long minutes = (elapsedTime / 60000) % 60;
        long seconds = (elapsedTime / 1000) % 60;
        stopwatchLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

    public static void main(String[] args) {
        new DigitalClock();
    }
}
