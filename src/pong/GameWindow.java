package pong;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class GameWindow{
    private final int WIDTH = 800;
    private final int HEIGHT = 1000;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int screenWidth = (int)screenSize.getWidth();
    private final int screenHeight = (int)screenSize.getHeight();
    private final int X = (screenWidth/2)-(WIDTH/2);
    private final int Y = (screenHeight/2)-(HEIGHT/2);
    private final GameLogic player = new GameLogic(WIDTH, HEIGHT);
    public GameWindow() {
        JFrame frame= new JFrame();
        frame.setTitle("Pong");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(X, Y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(player);
        frame.setVisible(true);
    }
}
