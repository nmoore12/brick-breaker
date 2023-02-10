import java.awt.Color;
import java.awt.Graphics2D;

public class Powerup extends Hittable implements Comparable<Powerup> {
    public final double vY;
    public Color c;

    public Powerup(int x, int y, int w, int h, double vY, Color c) {
        super(x,y,w,h, c);
        this.vY = vY;
        this.c = c;
    }

    public boolean isGone(int height) {
        return (this.y > height);
    }

    public double getvY() {
        return vY;
    }

    public void fall() {
        this.y += this.vY;
    }

    @Override
    public int compareTo(Powerup o) {
        return 0;
    }
}

