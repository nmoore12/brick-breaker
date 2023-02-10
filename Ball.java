import java.awt.Color;
import java.awt.Graphics2D;

public class Ball extends Hittable {
    double vX;
    double vY;
   
    public Ball(int x, int y, int w, int h, Color c) {
        super(x,y,w,h,c);
        this.vX = 0;
        this.vY = 1;
    }

    public boolean isMoving() {
        return(vX !=0 && vY !=0);
    }

    public void move() {
        this.x += this.vX;
        this.y += this.vY;
    }

    public double getvX() {
        return vX;
    }

    public void setvX(double vX) {
        this.vX = vX;
    }

    public double getvY() {
        return vY;
    }

    public void setvY(double vY) {
        this.vY = vY;
    }

    public void paint(Graphics2D g) {
        g.drawOval(x, y, width, height);
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, 10, 10);
    }
}

