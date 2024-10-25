import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class enemy {
    
    String origin;
    String enemyName;
    int speed ,hitpoints , armourClass;
    int coordX , coordY;
    int enemyGoldValue;
    texture tex;
    int travelDistance;
    boolean isInsideGrid;

    public enemy( int coordX, int coordY, int type){
        travelDistance = 0;
        tex = new texture();
        this.coordX = coordX;
        this.coordY = coordY;
        isInsideGrid = false;

        switch (type) {
            case 1:
                speed =1;
                break;
            case 2:
            speed =2;
                break;

            case 3:
            speed =1;
                break;
            case 4:
            speed =3;
                break;
            default:
                break;
        }
    
    }

    

    public void takeDamage(int damage){
        hitpoints -= damage;
        if(hitpoints <= 0){
            //remove enemy

        }
    }

    public boolean isDead() {
        return hitpoints <= 0;
    }

    public void removeDeadEnemies() {
    
       // remove(enemy::isDead);
    }

    void render(Graphics2D g2d){


        g2d.drawImage(tex.getTexture("end"), coordX, coordY, null);

    }

    void update(){

        
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }
    public BufferedImage getTex() {
        return tex.getTexture("down");
    }
    /*  public void removeDeadEnemies() {
        enemies.removeIf(Enemy::isDead);
    }*/



    public void checkIfInside(int width , int height ,int squareSize) {
        if(coordX>=0 && coordX<=width-squareSize && coordY>=0 && coordY<=height-squareSize){
            isInsideGrid=true;
        }else{
            isInsideGrid = false;
        }
    }
}
