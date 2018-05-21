/**
 * Created by amruthaa on 5/20/18.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//Panel for the snake game's java frame. It extends JPanel (Abstract class) and implements ActionListener(Interface)
public class GamePanel extends JPanel implements ActionListener {

    //Initializing the board properties
    private final int BOARD_WIDTH_IN_PIXEL = 300;
    private final int BOARD_HEIGHT_IN_PIXEL = 300;

    //We use a pixel size of 10
    private final int PIXEL_SIZE = 10;

    //total_pixel_units = (300 * 300) / (10 * 10) : (boardwidth * boardheight)/pixel_dimensions
    private final int TOTAL_PIXEL_UNITS = 900;

    //Array to keep track of snake co-ordinates in board. Snake body can have max length of 900(total_pixel_units).
    private final int SNAKE_X[] = new int[TOTAL_PIXEL_UNITS];
    private final int SNAKE_Y[] = new int[TOTAL_PIXEL_UNITS];

    //snake size in pixel units
    private int SNAKE_SIZE=4;

    //Co-ordinate of food in pixel units
    private int FOOD_X;
    private int FOOD_Y;

    private char direction='L';

    //Assigning images for snake and food
    private Image body;
    private Image food;
    private Image head;

    private Timer timer;

    //constructor
    public GamePanel() {
        //Initialization of Panel

        //Setting panel parameters
        setBackground(Color.white);
        setFocusable(true);
        setDoubleBuffered(true);
        setPreferredSize(new Dimension(BOARD_WIDTH_IN_PIXEL, BOARD_HEIGHT_IN_PIXEL));

        //Adding listener for direction keys
        addKeyListener(new Direction());

        //loading images for snake and food
        ImageIcon iid = new ImageIcon("src/resources/square.png");
        body = iid.getImage();
        ImageIcon iia = new ImageIcon("src/resources/square.png");
        food = iia.getImage();
        ImageIcon iih = new ImageIcon("src/resources/square.png");
        head = iih.getImage();

        //Start with initializing the snake game
        initializeSnakeGame();
    }


    //Determining start food positon and initial snake body
    private void initializeSnakeGame() {

        //For snake body co-ordinates positioning
        //Moving from right to left
        for (int i = 0; i < SNAKE_SIZE; i++) {
            SNAKE_X[i] = 150 + i * PIXEL_SIZE;
            SNAKE_Y[i] = 150;
        }

        //Randomly initializing food co-ordinate position
        FOOD_X = ((int) (Math.random() * 29)) * PIXEL_SIZE;
        FOOD_Y = ((int) (Math.random() * 29)) * PIXEL_SIZE;

        //Starting the timer for the game
        //Timer is used to move the snake across the board
        timer = new Timer(150, this);
        timer.start();
    }

    //Actionperformed event handler
    //Trigerred after timer delay
    //Basic operation of the event: when trigerred check for collision and check if food is reached.
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!isCollision())
        {
            if ((SNAKE_X[0] == FOOD_X) && (SNAKE_Y[0] == FOOD_Y))
            {
                SNAKE_SIZE++;
                FOOD_X = ((int) (Math.random() * 29)) * PIXEL_SIZE;
                FOOD_Y = ((int) (Math.random() * 29)) * PIXEL_SIZE;
            }
            navigate();
        }
        repaint();
    }

    //Overriding the inbuilt function paintComponent with our required implementation
    //executed when repaint() is called
    //used to paint the frame everytime after fixing the co-ordinates of snake and food
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    //used to draw the snake and food everytime the snake navigates
    private void draw(Graphics g)
    {
        if (!isCollision())
        {
            g.drawImage(food, FOOD_X, FOOD_Y, this);
            for (int i = 0; i < SNAKE_SIZE; i++) {
                if (i == 0) {
                    g.drawImage(head, SNAKE_X[i], SNAKE_Y[i], this);
                } else {
                    g.drawImage(body, SNAKE_X[i], SNAKE_Y[i], this);
                }
            }
        }
        else
        {
            //when collision occurs : game over
            g.setColor(Color.black);
            g.drawString("Game Over", (BOARD_WIDTH_IN_PIXEL) / 3, BOARD_HEIGHT_IN_PIXEL / 2);
            timer.stop();
        }
    }

    //to move the snake up,down,left or right.
    private void navigate() {
        for (int i = SNAKE_SIZE; i > 0; i--) {
            SNAKE_X[i] = SNAKE_X[i - 1];
            SNAKE_Y[i] = SNAKE_Y[i - 1];
        }

        switch (direction)
        {
            case 'L':
                SNAKE_X[0] -= PIXEL_SIZE;
                break;
            case 'R':
                SNAKE_X[0] += PIXEL_SIZE;
                break;
            case 'U':
                SNAKE_Y[0] -= PIXEL_SIZE;
                break;
            case 'D':
                SNAKE_Y[0] += PIXEL_SIZE;
                break;
        }

    }

    //To check if there occured a collision:
    //Check if x and y co-ordinate is within board range
    //Check if snake head collided with the body
    private boolean isCollision()
    {
        if (SNAKE_X[0] < 0 || SNAKE_X[0] >= BOARD_WIDTH_IN_PIXEL || SNAKE_Y[0] < 0 || SNAKE_Y[0] >= BOARD_HEIGHT_IN_PIXEL)
        {
            return true;
        }
        for (int i = 1; i<=SNAKE_SIZE; i++)
        {
            if ((i > 4) && (SNAKE_X[0] == SNAKE_X[i]) && (SNAKE_Y[0] == SNAKE_Y[i])) {
                return true;
            }
        }
        return false;
    }


    //class for receiving keyboard events
    //Extends the KeyAdapter class (Abstract class)
    private class Direction extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (direction!='R')) {
                direction='L';
            }

            if ((key == KeyEvent.VK_RIGHT) && (direction!='L')) {
                direction='R';
            }

            if ((key == KeyEvent.VK_UP) && (direction!='U')) {
                direction='U';
            }

            if ((key == KeyEvent.VK_DOWN) && (direction!='D')) {
                direction='D';
            }
        }
    }
}
