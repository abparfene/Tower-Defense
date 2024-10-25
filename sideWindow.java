import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;




public class sideWindow extends JPanel{

    final int sidePanelWidth = 400;
    final int squareSize = 48;
    final int heigth = 12*squareSize;

    gameWindow gameInstance; 
    JButton startButton , saveButton , exiButton;
    mouseInput ms;
    texture tex;

    public String brush;
        
    public Rectangle startPoint, endPoint ,goLeft, goRight,goUp,goDown , currBrush;
    public sideWindow(gameWindow gameInstance) {
        this.gameInstance = gameInstance; 
        tex = new texture();
        
        this.setPreferredSize(new Dimension(sidePanelWidth, heigth));
        this.setBackground(Color.green);
        this.setDoubleBuffered(true);
        
        initButtons();
        initRects();

        this.add(startButton);
        this.add(saveButton);
        this.add(exiButton);

        ms = new mouseInput(this);
        this.addMouseListener(ms);
        this.revalidate();
        this.repaint();
    }

    void initRects(){

        startPoint = new Rectangle(100, 200,squareSize, squareSize);
        endPoint = new Rectangle(sidePanelWidth-squareSize-100, 200,squareSize, squareSize);
        goDown = new Rectangle(100, 300,squareSize, squareSize);
        goUp = new Rectangle(sidePanelWidth-squareSize-100, 300,squareSize, squareSize);
        goLeft = new Rectangle(100, 400,squareSize, squareSize);
        goRight = new Rectangle(sidePanelWidth-squareSize-100, 400,squareSize, squareSize);
        currBrush = new Rectangle(250 , 100 , squareSize,squareSize);

    }

    void initButtons(){
        //start button
        startButton = new JButton("New game");
        startButton.setBounds(100, 200, 100, 100);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //  System.out.println(1);
                // Toggle between game states on button click
                switch (gameInstance.getState()) {
                    case startScreen:
                        gameInstance.state = gameWindow.STATE.levelBuilder;
                        break;
                    case levelBuilder:
                        //run check alogirthm
                        //save the grid
                        gameInstance.getGamePanel().startNewLevel();
                        gameInstance.state = gameWindow.STATE.gamePlay;
                        

                            break;
                    default:
                        break;
                }
            }
        });
        //saveButton
        saveButton = new JButton("Create level");
        saveButton.setBounds(100, 300, 100, 100);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle between game states on button click
                switch (gameInstance.getState()) {
                    case startScreen:
                        gameInstance.state = gameWindow.STATE.levelBuilder;
                        break;
                    case levelBuilder:
                        //run check alogirthm
                        //save the grid
                        gameInstance.getGamePanel().checkGrid();

                            break;
                    //case gamePlay:
                      //  gameInstance.gameState = gameWindow.GameState.paused;
                        //break;
                    default:
                        break;
                }
            }
        });    
        //france button
        exiButton = new JButton("Exit");
        exiButton.setBounds(100, 450, 100, 100);
        exiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle between game states on button click
                
                switch (gameInstance.getState()) {
                    case startScreen:
                        System.exit(0);
                        break;
                    case levelBuilder:
                        gameInstance.state = gameWindow.STATE.startScreen;
                            break;
                    case gamePlay:
                        gameInstance.state = gameWindow.STATE.levelBuilder;
                            break;
                    default:
                        break;
                }
            }
        });
    }

   /* public void addKeyListener(new KeyAdapter() {
            @Override
            keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_P) {
                      // Toggle pause state
                }
            }
    });*/

   
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_P) {
            gameInstance.gameState = gameWindow.GameState.paused;
        }
    }
    


        


    public void update() {

        switch (gameInstance.getState()) {
            case startScreen:
                startButton.setText("New game");
                saveButton.setText("Create level");
                exiButton.setText("Exit");
                break;
            case levelBuilder:
                startButton.setText("Play level");
                saveButton.setText("Save level");
                exiButton.setText("Back");
                break;
            case gamePlay:
                startButton.setText("Start wave");
                saveButton.setText("Pause");
                exiButton.setText("Exit");                
            
                break;
            default:
                break;
        }

    }


    public void paintComponent(Graphics g){


        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        switch (gameInstance.getState()) {
            case startScreen:
                
                break;
            case levelBuilder:
            
             
             g2d.drawImage(tex.getTexture("start"),startPoint.x,startPoint.y,startPoint.width,startPoint.height,this);
             
             
             g2d.drawImage(tex.getTexture("end"),endPoint.x,endPoint.y,endPoint.width,endPoint.height,this);

             
             g2d.drawImage(tex.getTexture("down"),goDown.x,goDown.y,goDown.width,goDown.height,this);

             
             g2d.drawImage(tex.getTexture("up"),goUp.x,goUp.y,goUp.width,goUp.height,this);

             
             g2d.drawImage(tex.getTexture("left"),goLeft.x,goLeft.y,goLeft.width,goLeft.height,this);
             
             
             g2d.drawImage(tex.getTexture("right"),goRight.x,goRight.y,goRight.width,goRight.height,this);

             if(ms.brush!=null){
                g2d.drawImage(tex.getTexture(ms.brush),currBrush.x,currBrush.y,currBrush.width,currBrush.height,this);
                
             }
             

            g2d.draw(startPoint);
            g2d.draw(endPoint);
            g2d.draw(goDown);
            g2d.draw(goUp);
            g2d.draw(goLeft);
            g2d.draw(goRight);
            g2d.draw(currBrush);

                break;
            default:
                break;
        }
    
     //   g2d.dispose();

    }

    Rectangle[] getRect(){

        return new Rectangle[]{startPoint,endPoint,goDown,goUp,goLeft,goRight};
    }
    
    String getBrush(){

        return ms.brush;
    }


}