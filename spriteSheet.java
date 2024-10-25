import java.awt.image.BufferedImage;

public class spriteSheet {

	private BufferedImage image;
	
	public spriteSheet(BufferedImage TheImage ) {
		this.image=TheImage;
		
	}
	
	public BufferedImage grabImage(int row, int col ,int sqruarSize) {
		
		BufferedImage img = image.getSubimage((row * sqruarSize) - sqruarSize, (col * sqruarSize ) - sqruarSize, sqruarSize, sqruarSize);
		return img;
	}
}
