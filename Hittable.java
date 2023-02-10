import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class Hittable extends Rectangle{
    public Hittable(int x, int y, int w, int h, Color c) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public void draw(Graphics g, Color c) {
        g.setColor(c);
        g.fillRect(x, y, width, height);
    }
}
