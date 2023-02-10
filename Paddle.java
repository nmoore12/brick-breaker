import java.awt.Color;
import java.awt.Graphics2D;

public class Paddle extends Hittable{
    private boolean big, small;

    public Paddle(int x, int y, int w, int h, Color c) {
        super(x,y,w,h, c);
    }

    public void grow() {
        if(!big) {
            this.width *= 2;
            if(small) {
                small = false;
                big = false;
            } else {
                small = false;
                big = true;
            }
        }
    }

    public void shrink() {
        if(!small) {
            this.width *= 0.5;
            if(big) {
                small = false;
                big = false;
            }
        }
    }

    public boolean isBig() {
        return big;
    }

    public boolean isSmall() {
        return small;
    }

    public void paint(Graphics2D g) {
        g.setColor(Color.green);
        g.fillRect(x, y, width, height);
    }
}


