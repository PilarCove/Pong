package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameLogic extends JPanel implements KeyListener,ActionListener{
    private final Timer timer;
    private final double screenWidth;
    private final double delay = 8;
    private double score = 0;
    private Blocks blocks;
    private final int blocksRows = 4;
    private final int blocksCols = 8;
    private int blocksCount = blocksRows * blocksCols;
    private boolean[][] activatedBonus;
    private boolean gameStarted = false;
    private boolean win = false;
    private boolean defeat = false;
    private double paddleWidth = 100;
    private final double startPaddleWidth = 100;
    private final double paddleHeight = 15;
    private final double ballWidth = 20;
    private final double ballHeight = 20;
    private double ballXDirection = 1;
    public double getBallXDirection(){
        return ballXDirection;
    }
    public void setBallXDirection(double direction){
        this.ballXDirection = direction;
    }
    private double ballYDirection = 1;
    public double getBallYDirection(){
        return ballYDirection;
    }
    public void setBallYDirection(double direction){
        this.ballYDirection = direction;
    }
    private double ballX;
    private double ballY;
    private double paddleX;
    public double getPaddleX(){
        return paddleX;
    }
    private final double paddleY;
    public double getPaddleY(){
        return paddleY;
    }
    private final double paddleSpeed = 14;
    private double ballXSpeed = 1;
    private final double startBallXSpeed = 1;
    private double ballYSpeed = 1;
    private final double startBallYSpeed = 1;
    public GameLogic(double screenWidth, double screenHeight){
        this.screenWidth = screenWidth;
        this.paddleX = (screenWidth/2)-(paddleWidth/2);
        this.paddleY = (screenHeight-27)-(paddleHeight*3);
        this.ballY = paddleY-ballHeight;
        this.ballX = paddleX+(paddleWidth/2)-(ballWidth/2);
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
        blocks = new Blocks(blocksRows, blocksCols);
        activatedBonus = new boolean[blocksRows][blocksCols];
        for (boolean[] activatedBonu : activatedBonus) {
            for (int j = 0; j < activatedBonus[0].length; j++) {
                activatedBonu[j] = false;
            }
        }
        timer = new Timer((int)delay, this);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        blocks.drawBlocks((Graphics2D)g);
        blocks.drawBonus((Graphics2D)g);
        g.setColor(Color.WHITE);
        g.fillRect((int)paddleX,
                   (int)paddleY,
                   (int)paddleWidth,
                   (int)paddleHeight);
        g.setColor(Color.YELLOW);
        g.fillOval((int)ballX,
                   (int)ballY,
                   (int)ballWidth,
                   (int)ballHeight);
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString(""+(int)score, 5, 25);
        if (defeat){
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 50));
            g.drawString("YOU LOSE!", 255, 500);
        }
        if (win){
            g.setColor(Color.GREEN);
            g.setFont(new Font("serif", Font.BOLD, 50));
            g.drawString("YOU WIN!", 260, 500);
        }
        g.dispose();
    }
    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                paddleToLeft();
                break;
            case KeyEvent.VK_RIGHT:
                paddleToRight();
                break;
            case KeyEvent.VK_SPACE:
                gameStarted = true;
                defeat = false;
                win = false;
                timer.start();
                break;
            default:
                break;
        }
        repaint();
    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                paddleToLeft();
                break;
            case KeyEvent.VK_RIGHT:
                paddleToRight();
                break;
            case KeyEvent.VK_SPACE:
                gameStarted = true;
                defeat = false;
                win = false;
                timer.start();
                break;
            default:
                break;
        }
        repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (new Rectangle((int)ballX,
                          (int)ballY,
                          (int)ballWidth,
                          (int)ballHeight)
                .intersects(new Rectangle((int)paddleX,
                                          (int)paddleY,
                                          (int)paddleWidth/5,
                                          (int)paddleHeight))){
            if (ballXSpeed <= ballYSpeed+0.79 &&
                ballXSpeed >= ballYSpeed+0.3){
                ballXSpeed+=0.5;
            }else if (ballXSpeed <= ballYSpeed+0.29 &&
                      ballXSpeed >= ballYSpeed-0.29){
                ballXSpeed+=1;
            }else if (ballXSpeed <= ballYSpeed+1.2 &&
                      ballXSpeed >= ballYSpeed+0.8){
                
            }
            if (getBallXDirection() < 0){
                setBallXDirection((getBallXDirection())*(-1));
            }
            playSound();
            setBallYDirection((getBallYDirection())*(-1));
        }else if(new Rectangle((int)ballX,
                               (int)ballY,
                               (int)ballWidth,
                               (int)ballHeight)
                .intersects(new Rectangle((int)paddleX+(4*((int)paddleWidth/5)),
                                          (int)paddleY,
                                          (int)paddleWidth/5,
                                          (int)paddleHeight))){
            if (ballXSpeed <= ballYSpeed+0.79 &&
                ballXSpeed >= ballYSpeed+0.3){
                ballXSpeed+=0.5;
            }else if (ballXSpeed <= ballYSpeed+0.29 &&
                      ballXSpeed >= ballYSpeed-0.29){
                ballXSpeed+=1;
            }else if (ballXSpeed <= ballYSpeed+1.2 &&
                      ballXSpeed >= ballYSpeed+0.8){
            }
            if (getBallXDirection() > 0){
                setBallXDirection((getBallXDirection())*(-1));
            }
            playSound();
            setBallYDirection((getBallYDirection())*(-1));
        }else if (new Rectangle((int)ballX,
                                (int)ballY,
                                (int)ballWidth,
                                (int)ballHeight)
                  .intersects(new Rectangle((int)paddleX+((int)paddleWidth/5),
                                          (int)paddleY,
                                          (int)paddleWidth/5,
                                          (int)paddleHeight)) ||
                  new Rectangle((int)ballX,
                                (int)ballY,
                                (int)ballWidth,
                                (int)ballHeight)
                .intersects(new Rectangle((int)paddleX+(3*((int)paddleWidth/5)),
                                            (int)paddleY,
                                            (int)paddleWidth/5,
                                            (int)paddleHeight))){
            if (ballXSpeed <= ballYSpeed+0.79 &&
                ballXSpeed >= ballYSpeed+0.3){
            }else if (ballXSpeed <= ballYSpeed+0.29 &&
                      ballXSpeed >= ballYSpeed-0.29){
                ballXSpeed+=0.5;
            }else if (ballXSpeed <= ballYSpeed+1.2 &&
                      ballXSpeed >= ballYSpeed+0.8){
                ballXSpeed-=0.5;
            }
            playSound();
            setBallYDirection((getBallYDirection())*(-1));
        }else if (new Rectangle((int)ballX,
                                (int)ballY,
                                (int)ballWidth,
                                (int)ballHeight)
                .intersects(new Rectangle((int)paddleX+(2*((int)paddleWidth/5)),
                                          (int)paddleY,
                                          (int)paddleWidth/5,
                                          (int)paddleHeight))){
            if (ballXSpeed <= ballYSpeed+0.79 &&
                ballXSpeed >= ballYSpeed+0.3){
                ballXSpeed-=0.5;
            }else if (ballXSpeed <= ballYSpeed+0.29 &&
                      ballXSpeed >= ballYSpeed-0.29){
            }else if (ballXSpeed <= ballYSpeed+1.2 &&
                      ballXSpeed >= ballYSpeed+0.8){
                ballXSpeed-=1;
            }
            playSound();
            setBallYDirection((getBallYDirection())*(-1));
        }
        A: for (int i = 0; i < blocks.map.length; i++) {
            for (int j = 0; j < blocks.map[0].length; j++) {
                if (activatedBonus[i][j] && blocks.getMapValue(i, j) == 0){
                    blocks.setBonusYPosition(blocks.getBonusYPosition(i, j)+1,
                                             i, j);
                    if (new Rectangle(blocks.getBonusXPosition(i, j),
                                      blocks.getBonusYPosition(i, j),
                                      blocks.getBonusWidth(),
                                      blocks.getBonusHeight())
                        .intersects(new Rectangle((int)paddleX,
                                                  (int)paddleY,
                                                  (int)paddleWidth,
                                                  (int)paddleHeight))){
                        if (blocks.getBonusMap(i, j) == Color.BLUE){
                            score+=15;
                            paddleX-=10;
                            paddleWidth+=20;
                        }else if (blocks.getBonusMap(i, j) == Color.RED){
                            score+=15;
                            ballXSpeed-=0.4;
                            ballYSpeed-=0.4;
                        }
                        activatedBonus[i][j] = false;
                        blocks.setBonusMap(Color.BLACK, i, j);
                    }else if (blocks.getBonusYPosition(i, j) > 1000){
                        if (blocks.getBonusMap(i, j) == Color.BLUE || 
                            blocks.getBonusMap(i, j) == Color.RED){
                            score-=5;
                        }
                        activatedBonus[i][j] = false;
                        blocks.setBonusMap(Color.BLACK, i, j);
                    }
                }
                if (blocks.map[i][j] > 0){
                    int blockX = j * blocks.getBlockWidth() + 99;
                    int blockY = i * blocks.getBlockHeight() + 100;
                    if (new Rectangle((int)ballX,
                                      (int)ballY,
                                      (int)ballWidth,
                                      (int)ballHeight)
                        .intersects(new Rectangle(blockX,
                                                  blockY,
                                                  blocks.getBlockWidth(),
                                                  blocks.getBlockHeight()))){
                        if (blocks.getMapValue(i, j) > 0){
                            blocks.setMapValue(blocks.getMapValue(i, j)-1,
                                                    i, j);
                            activatedBonus[i][j] = true;
                            score+=5;
                        }else{
                            blocks.setMapValue(0, i, j);
                            activatedBonus[i][j] = true;
                        }
                        if (blocks.getMapValue(i, j) == 0){
                            if (blocksCount > 0){
                                this.blocksCount-=1;
                            }
                            if (blocksCount == 0){
                                timer.stop();
                                win();
                            }
                        }
                        if ((ballX + 19 <= blockX+3 && ballX + 19 >= blockX-4)||
                            (ballX >= blockX - 4 + blocks.getBlockWidth() &&
                             ballX <= blockX + 3 + blocks.getBlockWidth())){
                            setBallXDirection((getBallXDirection())*(-1));
                            playSound();
                            ballXSpeed+=0.05;
                            ballYSpeed+=0.05;
                        }else{
                            setBallYDirection((getBallYDirection())*(-1));
                            playSound();
                            ballXSpeed+=0.05;
                            ballYSpeed+=0.05;
                        }
                        break A;
                    }
                }
            }
        }
        ballX = ballX - (1*ballXSpeed)*(getBallXDirection());
        ballY = ballY - (1*ballYSpeed)*(getBallYDirection());
        if (ballX < 0){
            setBallXDirection(-1);
            playSound();
        }
        if (ballX > 771){
            setBallXDirection(1);
            playSound();
        }
        if (ballY < 0){
            setBallYDirection(-1);
            playSound();
        }
        if (ballY > 948){
            timer.stop();
            setBallYDirection(1);
            lose();
        }
        repaint();
    }
    private void paddleToLeft(){
        if (paddleX <= 0){
            paddleX = 0;
            if (!gameStarted){
                ballX = paddleX + 40 - (ballWidth/2);
            }
        }else{
            paddleX = paddleX - (1*paddleSpeed);
            if (!gameStarted){
                ballX = ballX - (1*paddleSpeed);
            }
        }
    }
    private void paddleToRight(){
        if (paddleX >= screenWidth-paddleWidth){
            paddleX = screenWidth-paddleWidth;
            if (!gameStarted){
                ballX = paddleX + 40 - (ballWidth/2);
            }
        }else{
            paddleX = paddleX + (1*paddleSpeed);
            if (!gameStarted){
                ballX = ballX + (1*paddleSpeed);
            }
        }
    }
    private void win(){
        paddleX = (screenWidth/2)-(paddleWidth/2);
        ballY = paddleY-ballHeight;
        ballX = paddleX+(paddleWidth/2)-(ballWidth/2);
        blocks = new Blocks(blocksRows, blocksCols);
        activatedBonus = new boolean[blocksRows][blocksCols];
        win = true;
        gameStarted = false;
        score = 0;
        ballXSpeed = startBallXSpeed;
        ballYSpeed = startBallYSpeed;
        paddleWidth = startPaddleWidth;
    }
    private void lose(){
        paddleX = (screenWidth/2)-(paddleWidth/2);
        ballY = paddleY-ballHeight;
        ballX = paddleX+(paddleWidth/2)-(ballWidth/2);
        blocks = new Blocks(blocksRows, blocksCols);
        activatedBonus = new boolean[blocksRows][blocksCols];
        defeat = true;
        gameStarted = false;
        score = 0;
        ballXSpeed = startBallXSpeed;
        ballYSpeed = startBallYSpeed;
        paddleWidth = startPaddleWidth;
    }
    public void playSound() {
    }
}
