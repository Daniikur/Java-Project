import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener {
    // Game constants
    private final int WIDTH = 600, HEIGHT = 400;
    private final int UNIT_SIZE = 20;
    private final int GAME_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private final int DELAY = 100;

    private final LinkedList<Point> snake = new LinkedList<>();
    private Point food;
    private char direction = 'R'; // 'U' = up, 'D' = down, 'L' = left, 'R' = right
    private boolean gameOver = false;
    private boolean foodEaten = false;

    private Timer timer;

    public SnakeGame() {
        // Set up the game panel
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);

        // Add key listener to handle user input
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT && direction != 'R') {
                    direction = 'L';
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && direction != 'L') {
                    direction = 'R';
                } else if (e.getKeyCode() == KeyEvent.VK_UP && direction != 'D') {
                    direction = 'U';
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN && direction != 'U') {
                    direction = 'D';
                }
            }
        });
        initGame();
    }

    // Initialize the game state
    private void initGame() {
        snake.clear();
        snake.add(new Point(100, 100)); // Initial snake position
        generateFood();
        timer = new Timer(DELAY, this); // Timer for controlling game speed
        timer.start();
    }

    // Generate food at a random position
    private void generateFood() {
        Random rand = new Random();
        food = new Point(rand.nextInt(WIDTH / UNIT_SIZE) * UNIT_SIZE, rand.nextInt(HEIGHT / UNIT_SIZE) * UNIT_SIZE);
        foodEaten = false;
    }

    // Action to be performed every game tick (timer event)
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            moveSnake();
            checkCollisions();
            repaint();
        }
    }

    // Move the snake based on current direction
    private void moveSnake() {
        Point head = snake.getFirst();
        Point newHead = null;

        switch (direction) {
            case 'U':
                newHead = new Point(head.x, head.y - UNIT_SIZE);
                break;
            case 'D':
                newHead = new Point(head.x, head.y + UNIT_SIZE);
                break;
            case 'L':
                newHead = new Point(head.x - UNIT_SIZE, head.y);
                break;
            case 'R':
                newHead = new Point(head.x + UNIT_SIZE, head.y);
                break;
        }

        if (!foodEaten) {
            snake.removeLast();
        } else {
            foodEaten = false;
        }

        snake.addFirst(newHead);

        if (newHead.equals(food)) {
            foodEaten = true;
            generateFood();
        }
    }

    // Check if the snake has collided with walls or itself
    private void checkCollisions() {
        Point head = snake.getFirst();

        // Check if the snake hits the wall
        if (head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT) {
            gameOver = true;
        }

        // Check if the snake hits itself
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                gameOver = true;
                break;
            }
        }
    }

    // Render the game (draw the snake, food, and score)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!gameOver) {
            // Draw snake
            g.setColor(Color.green);
            for (Point p : snake) {
                g.fillRect(p.x, p.y, UNIT_SIZE, UNIT_SIZE);
            }

            // Draw food
            g.setColor(Color.red);
            g.fillRect(food.x, food.y, UNIT_SIZE, UNIT_SIZE);
        } else {
            // Game over message
            String message = "Game Over! Press 'R' to Restart";
            g.setColor(Color.white);
            g.drawString(message, WIDTH / 2 - 60, HEIGHT / 2);
        }

        // Display score
        String score = "Score: " + (snake.size() - 1);
        g.setColor(Color.white);
        g.drawString(score, 10, 20);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame game = new SnakeGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Restart the game when 'R' is pressed
        game.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R && game.gameOver) {
                    game.gameOver = false;
                    game.initGame();
                }
            }
        });
    }
}
