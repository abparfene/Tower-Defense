public class projectile {
    private int x, y;  // Current position of the projectile
    private int speed;
    private int damage;
    private enemy target;  // The enemy this projectile is targeting
    private boolean isActive;  // Whether the projectile is still active (not hit anything yet)

    public projectile(int x, int y, int speed, int damage, enemy target) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.damage = damage;
        this.target = target;
    
    }

    // Move projectile towards the target
    public void move() {
        if (isActive && target != null) {
            // Calculate direction to the target
            int targetX = target.coordX;
            int targetY = target.coordY;
            double angle = Math.atan2(targetY - y, targetX - x);

            // Update projectile's position based on speed and direction
            x += (int) (speed * Math.cos(angle));
            y += (int) (speed * Math.sin(angle));
        }
    }
}