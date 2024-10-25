import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
public class texture {
    

    spriteSheet gameTiles;
    bufferedImageLoader loader;
    texture (){

        loader = new bufferedImageLoader();
        try {
    		gameTiles = new spriteSheet(loader.loadImage("baseSprites.png"));
    		
    	}catch(IOException e) {
    		
    	}

    }

    BufferedImage getTexture(String type){

        BufferedImage img = gameTiles.grabImage(2,1,48);
        switch(type){

            case "start":
                img = gameTiles.grabImage(1,1,48);
                break;
            case "end":
                img = gameTiles.grabImage(2,2,48);
                break;
            case "up":
                img = gameTiles.grabImage(1,2,48);
                break;
            case "down":
                img = gameTiles.grabImage(1,3,48);
                break;

            case "left":
                img = gameTiles.grabImage(2,3,48);
                break;
            case "right":
                img = gameTiles.grabImage(1,4,48);
                break;
            case "tower":
            try {                
                img = ImageIO.read(new File("lolTower.jpg"));
             } catch (IOException ex) {
                  // handle exception...
             }
            default:
                break;
        }
        

        return img;
    }
}
