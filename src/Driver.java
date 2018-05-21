import java.awt.EventQueue;
import javax.swing.*;

public class Driver extends JFrame {

    public Driver() {

        GamePanel g = new GamePanel();
        add(g);

        //Shoudl not be resized while playing
        setResizable(false);

        //To enable layout managers to handle frame size
        //available only in JSwing
        //for platform independencies
        pack();

        setTitle("Snake Game");

        //Where the frame should appear on the screen
        //relative to null represents center.
        setLocationRelativeTo(null);

        //This game exits when the user presses the close button on the frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {

        //to isolate swing backend processing and GUI in seperate threads
        //Event runs in EDT(Event Dispatcher Thread) by default.
        EventQueue.invokeLater(() -> {

            //calls the constructor
            JFrame ex = new Driver();
            ex.setVisible(true);
        });
    }
}