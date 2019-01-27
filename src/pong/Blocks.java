package pong;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Blocks {
    private int bonusChance = 9;
    public int map[][];
    public void setMapValue(int value, int row, int cols){
        map[row][cols] = value;
    }
    public int getMapValue(int row, int cols){
        return map[row][cols];
    }
    public Color bonusMap[][];
    public Color getBonusMap(int row, int cols){
        return bonusMap[row][cols];
    }
    public void setBonusMap(Color color, int row, int cols) {
        this.bonusMap[row][cols] = color;
    }
    public final int bonusXPosition[][];
    public int getBonusXPosition(int row, int cols) {
        return bonusXPosition[row][cols];
    }
    public void setBonusXPosition(int bonusYPosition, int row, int cols) {
        this.bonusXPosition[row][cols] = bonusYPosition;
    }
    private final int bonusYPosition[][];
    public int getBonusYPosition(int row, int cols) {
        return bonusYPosition[row][cols];
    }
    public void setBonusYPosition(int bonusYPosition, int row, int cols) {
        this.bonusYPosition[row][cols] = bonusYPosition;
    }
    private int bonusWidth;
    public int getBonusWidth() {
        return bonusWidth;
    }
    private int bonusHeight;
    public int getBonusHeight() {
        return bonusHeight;
    }
    private final int blockHeight;
    public int getBlockHeight(){
        return blockHeight;
    }
    private final int blockWidth;
    public int getBlockWidth(){
        return blockWidth;
    }
    public Blocks(int row, int cols){
        map = new int[row][cols];
        bonusMap = new Color[row][cols];
        bonusXPosition = new int[row][cols];
        bonusYPosition = new int[row][cols];
        blockWidth = 600/cols;
        blockHeight = 150/row;
        Random rand = new Random();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                switch (rand.nextInt(bonusChance)+1) {
                    case 3:
                        bonusMap[i][j] = Color.BLUE;
                        break;
                    case 4:
                        bonusMap[i][j] = Color.RED;
                        break;
                    default:
                        break;
                }
                if (rand.nextInt(5)+1 == 3){
                    map[i][j] = 2;
                }else{
                    map[i][j] = 1;
                }
                bonusXPosition[i][j] = (j * blockWidth + 99)+(blockWidth/3);
                bonusYPosition[i][j] = (i * blockHeight + 100)+(blockHeight/3);
            }
        }
    }
    public void drawBonus(Graphics2D g){
        for (int i = 0; i < bonusMap.length; i++){
            for (int j = 0; j < bonusMap[0].length; j++){
                drawBonusObject(g, bonusMap[i][j], i, j);
            }
        }
    }
    private void drawBonusObject(Graphics2D g, Color type, int row, int cols){
        if (type == Color.BLUE || type == Color.RED){
            g.setColor(type);
            bonusWidth = blockWidth/3;
            bonusHeight = blockHeight/3;
            g.fillRect(bonusXPosition[row][cols],
                       bonusYPosition[row][cols],
                       bonusWidth,
                       bonusHeight);
        }
    }
    public void drawBlocks(Graphics2D g){
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[0].length; j++){
                drawBlockObject(g, i, j);
            }
        }
    }
    private void drawBlockObject(Graphics2D g, int row, int cols){
        if (map[row][cols] > 0){
                    if (map[row][cols] == 2){
                        g.setColor(Color.GRAY);
                    }else if (map[row][cols] == 1){
                        g.setColor(Color.WHITE);
                    }
                    g.fillRect(cols * blockWidth + 99,
                               row * blockHeight + 100,
                               blockWidth,
                               blockHeight);
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(cols * blockWidth + 99,
                               row * blockHeight + 100,
                               blockWidth,
                               blockHeight);
                }
    }
}
