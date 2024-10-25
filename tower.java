import java.util.LinkedList;
import java.awt.Graphics2D;
public class tower {

    String towerName;
    double attackSpeed; //in miliseconds
    int attackDamage;
    int towerCoordX, towerCoordY;
    int range;
    long lastAttackTime;
    int cost;
    int level;
    gameWindow game;
    texture tex;

    public tower( int x, int y, int type,int squareSize){
        
        tex =new texture();
        this.towerCoordX = x*squareSize;
        this.towerCoordY = y*squareSize;
        this.level = 1;

        switch (type) {
            case 1:
            //arrow
                this.range = 200;
                this.attackDamage = 5;
                this.cost = 4;
                this.attackSpeed= 0.67*level;               
                break;
            case 2 :
            //canon
                this.range = 200;
                this.attackDamage = 5;
                this.cost = 4;
                this.attackSpeed= 0.67*level;    
                break;
            case 3:
            //wizard
                this.range = 200;
                this.attackDamage = 5;
                this.cost = 4;
                this.attackSpeed= 0.67*level;    
                break;

            default:
                break;
        }

        
    }

    void upgrade(){

        this.level++;
    }
    public boolean isInAttackRange(int enemyX, int enemyY){
        int distance = (int) Math.sqrt(Math.pow(enemyX - towerCoordX, 2) + Math.pow(enemyY - towerCoordY, 2));
        
        if (distance <= range)
            return true;
            else
                return false;
    }

    public void attack(enemy enemy){
        long currentTime = System.currentTimeMillis();
        if((currentTime - lastAttackTime >= attackSpeed )){
            //shoot projectile
            enemy.takeDamage(attackDamage - enemy.armourClass);
        }

        /*
        for loop trough THE array of enemies{
        
            Enemy firstEnemy = getEnemy();


            if(enemy is in range){
            
                while(enemy is inrage and enemy.hp >0){
                
                    shoot at enemy
                    shoot()
                
                }
                if(enemy dead){
                    delete enemy from array and visual;
                }
            
            }
        }
        
        
        
        
        
        */

    }
    void render(Graphics2D g2d){


        g2d.drawImage(tex.getTexture("tower"), towerCoordX, towerCoordY, null);

    }

    public void checkInRange(LinkedList<enemy> EnemyList){

        for(enemy e:EnemyList){

            if(isInAttackRange(e.coordX, e.coordY) && e.hitpoints>0){

                System.out.println("I HAVE ENEMY IN RANGE");
                attack(e);
            }
        }

    }


}
