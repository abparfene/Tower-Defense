import javax.swing.*;
import java.awt.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class gamePanel extends JPanel {

    final int squareSize = 48;
    final int gridLen = 16;
    final int gridH = 12;

    final int width = gridLen * squareSize;
    final int heigth = gridH * squareSize;
    Color c;
    mouseInput ms;
    backgroudGrid grid;
    texture tex;
    gameWindow gameInstance;
    boolean hasSetStart = false, hasSetEnd = false;

    Point startPoint;
    Point endPoint;
    int pathLenght;
    ArrayList<String> instrutions;
    ArrayList<int[]> path;
    LinkedList<enemy> listOfEnemies;
    LinkedList<tower> listOfTowers;
    private static String brush;
    int wave;
    int waveLenght;
    int moveX = 0;
    int moveY = 0;
    int distanceTraveled = 0;
    Random rand = new Random();

    public gamePanel(gameWindow gameInstance) {

        this.gameInstance = gameInstance;
        listOfEnemies = new LinkedList<enemy>();
        listOfTowers = new LinkedList<tower>();
        waveLenght = rand.nextInt(15, 25);
        listOfTowers.add(new tower(5, 5, 1, squareSize));

        // wave creations
        //
        // list of all enemies

        ms = new mouseInput();
        this.setPreferredSize(new Dimension(width, heigth));
        this.setBackground(Color.gray);
        this.setDoubleBuffered(true);
        c = Color.BLACK;
        this.addMouseListener(ms);
        grid = new backgroudGrid(gridLen, gridH, squareSize);
        tex = new texture();
        startPoint = new Point(0, 0);
        endPoint = new Point(0, 0);
        pathLenght = 0;
        instrutions = new ArrayList<String>();
        path = new ArrayList<int[]>();
    }

    void fillEnemies() {
        System.out.println((int) startPoint.getX());
        for (int i = 0; i < waveLenght; i++) {

            int typeOfEnemy = rand.nextInt(5);
            enemy newEnemy = new enemy(-200, -200, typeOfEnemy);
            switch ((int) startPoint.getX()) {
                case 0:
                    // go right
                     newEnemy = new enemy((int) startPoint.getX() * squareSize- 60 * i,
                    (int) startPoint.getY() * squareSize , typeOfEnemy);
                    break;
                case 15:
                    // go left

                     newEnemy = new enemy((int) startPoint.getX() * squareSize+ 60 * i,
                    (int) startPoint.getY() * squareSize , typeOfEnemy);
                    break;

                default:
                    // go up or down
                    switch ((int) startPoint.getY()) {
                        case 0:
                            // go down

                             newEnemy = new enemy((int) startPoint.getX() * squareSize,
                    (int) startPoint.getY() * squareSize - 60 * i, typeOfEnemy);
                            break;

                        case 11:
                            // go up
                             newEnemy = new enemy((int) startPoint.getX() * squareSize,
                    (int) startPoint.getY() * squareSize + 60 * i, typeOfEnemy);
                            break;
                    }
                    break;
            }
            

            listOfEnemies.add(newEnemy);
            //System.out.println("Curretn number of enemies in comign " + listOfEnemies.size());
            if (wave == 5) {
                // add ziggs

            }

        }

    }
    void startNewLevel(){

        instrutions.clear();
        listOfEnemies.clear();
        checkGrid();
    }
    public void update() {

        switch (gameInstance.getState()) {
            case startScreen:

                break;
            case levelBuilder:

                if (ms.clicked) {
                    int[] coords = ms.getCoordinates();
                    if (grid.grid[coords[0] / squareSize][coords[1] / squareSize] == null && brush != null) {
                        grid.grid[coords[0] / squareSize][coords[1] / squareSize] = brush;
                        if (brush.equals("start")) {
                            grid.grid[(int) startPoint.getX()][(int) startPoint.getY()] = null;
                            startPoint = new Point(coords[0] / squareSize, coords[1] / squareSize);
                            pathLenght--;
                            if (startPoint.getX() == 0 || startPoint.getX() == 15 || startPoint.getY() == 0
                                    || startPoint.getY() == 11) {

                            } else {
                                JOptionPane.showMessageDialog(null, "You cant park there mate");
                                grid.grid[(int) startPoint.getX()][(int) startPoint.getY()] = null;

                            }
                        }
                        if (brush.equals("end")) {
                            grid.grid[(int) endPoint.getX()][(int) endPoint.getY()] = null;
                            endPoint = new Point(coords[0] / squareSize, coords[1] / squareSize);
                            pathLenght--;
                            if (endPoint.getX() == 0 || endPoint.getX() == 15 || endPoint.getY() == 0
                                    || endPoint.getY() == 11) {

                            } else {
                                JOptionPane.showMessageDialog(null, "You cant park there mate");
                                grid.grid[(int) endPoint.getX()][(int) endPoint.getY()] = null;
                            }
                        }
                        pathLenght++;

                    } else {

                        grid.grid[coords[0] / squareSize][coords[1] / squareSize] = null;
                        pathLenght--;
                    }

                    ms.resetClick();
                }
                break;
            case gamePlay:

                if (listOfEnemies.size() == 0 && wave < 5) {
                    wave++;
                    fillEnemies();
                }
                brush = null;

                for (enemy e : listOfEnemies) {

                    if (!e.isInsideGrid) {
                        switch ((int) startPoint.getX()) {
                            case 0:
                                // go right
                                e.setCoordX(e.getCoordX() + e.speed);
                                break;
                            case 15:
                                // go left

                                e.setCoordX(e.getCoordX() - e.speed);
                                break;

                            default:
                                // go up or down
                                switch ((int) startPoint.getY()) {
                                    case 0:
                                        // go down

                                        e.setCoordY(e.getCoordY() + e.speed);
                                        break;

                                    case 11:
                                        // go up
                                        e.setCoordY(e.getCoordY() - e.speed);
                                        break;
                                }
                                break;
                        }
                    e.checkIfInside(width,heigth,squareSize);                        
                    } else {

                        if (e.travelDistance / squareSize <= pathLenght) {
                            String currPosition = instrutions.get(e.travelDistance / squareSize);
                            switch (currPosition) {
                                case "down":
                                    e.setCoordY(e.getCoordY() + e.speed);
                                    break;

                                case "up":
                                    e.setCoordY(e.getCoordY() - e.speed);
                                    break;
                                case "left":
                                    e.setCoordX(e.getCoordX() - e.speed);
                                    break;
                                case "right":
                                    e.setCoordX(e.getCoordX() + e.speed);
                                    break;
                                default:
                                    break;
                            }
                            e.travelDistance+=e.speed;
                        }

                    }
                }
                for(tower t:listOfTowers){
                    t.checkInRange(listOfEnemies);
                }    
            default:
                break;
        }

        // System.out.println(pathLenght);
    }

    public void checkGrid() {
        instrutions = new ArrayList<String>();
        switch ((int) startPoint.getX()) {
            case 0:
                // go right
                instrutions.add("right");
                break;
            case 15:
                // go left

                instrutions.add("left");
                break;

            default:
                // go up or down
                switch ((int) startPoint.getY()) {
                    case 0:
                        // go down

                        instrutions.add("down");
                        break;

                    case 11:
                        // go up

                        instrutions.add("up");
                        break;

                    default:
                        // error
                        break;
                }
                break;
        }
        int PointX = (int) startPoint.getX();
        int PointY = (int) startPoint.getY();
        String previousTile = "start";

        String currTile = instrutions.get(0);
        for (int i = 0; i < pathLenght + 1; i++) {

            currTile = instrutions.get(i);
            // System.out.println(currTile);
            switch (currTile) {
                case "left":
                    if (previousTile.equals("right")) {

                        // error
                    } else {

                        previousTile = currTile;
                        PointX--;
                        currTile = grid.grid[PointX][PointY];
                        instrutions.add(currTile);
                    }

                    break;
                case "right":
                    if (previousTile.equals("left")) {

                        // error
                    } else {

                        previousTile = currTile;
                        PointX++;
                        currTile = grid.grid[PointX][PointY];
                        instrutions.add(currTile);
                    }
                    break;
                case "up":
                    if (previousTile.equals("down")) {

                        // error
                    } else {

                        previousTile = currTile;
                        PointY--;
                        currTile = grid.grid[PointX][PointY];
                        instrutions.add(currTile);
                    }
                    break;

                case "down":
                    if (previousTile.equals("up")) {

                        // error
                    } else {

                        previousTile = currTile;
                        PointY++;
                        currTile = grid.grid[PointX][PointY];
                        System.out.println(currTile);
                        instrutions.add(currTile);
                    }
                    break;
                case "end":
                    System.out.println("success");
                    break;
                default:
                    break;
            }
        }
        System.out.println(instrutions);

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < gridLen; i++) {

            for (int j = 0; j < gridH; j++) {
                g2d.setColor(c);
                g2d.drawRect(i * squareSize, j * squareSize, squareSize, squareSize);
                if (grid.grid[i][j] != null) {

                    g2d.drawImage(tex.getTexture(grid.grid[i][j]), i * squareSize, j * squareSize, this);

                }

            }
        }

        if (gameInstance.state == gameWindow.STATE.gamePlay) {

            g2d.setColor(Color.BLACK);

            for (enemy e : listOfEnemies) {

                e.render(g2d);
            }

            for(tower t :listOfTowers){
                t.render(g2d);
            }
        }
    }

    public static void setBrush(String newBrush) {
        brush = newBrush;
    }
}
