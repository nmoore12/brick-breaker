import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class Block extends Hittable {
    private int hits;
    public Color c;
    public int h;
    public int w;

    public Block(int x, int y, int w, int h, int hits, Color c) {
        super(x,y,w,h, c);
        this.hits = hits;
        this.c = c;
        this.h = h;
        this.w = w;
    }

    public void hit() {
        if(getHits() > 0) {
            this.hits--;
        }
    }

    public int getHits() {
        return hits;
    }

    public boolean isDestroyed() {
        return (hits < 1);
    }

    public void paint(Graphics2D g) {
        g.setColor(Color.white);
        g.fillRect(x, y, width, height);
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);
    }
}

