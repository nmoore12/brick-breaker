import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GamePanel extends JPanel implements KeyListener, MouseMotionListener {
    private Ball ball = new Ball(300,350,15,15, Color.YELLOW);
    private Paddle paddle = new Paddle(225,500,150,25, Color.GREEN);

    Powerup nextLev, die, invincible, back, grow, shrink;
    private Set<Powerup> powerups = new TreeSet<Powerup>();
    double speedFactor = -1;
    double cur_X = 0, prev_X = -1, dprev_X = -2;
    double cur_Y = 0, prev_Y = -1, dprev_Y = -2;

    private int currLev = 1;
    boolean started = false;
    Thread thread;
    private Block[][] blocks;

    private int score;

    private Levels l = new Levels(700,600);
    //frames for start and end screen
    JFrame mainFrame, endFrame, startFrame;
    JLabel scoreLabel;
    private boolean end = false, thru = false;
    GamePanel(JFrame mainFrame, JFrame endFrame, JFrame starFrame, JLabel score) {
        //get levels
        loadLevel(currLev - 1);

        addKeyListener(this);
        addMouseMotionListener(this);

        setFocusable(true);
        this.mainFrame = mainFrame;
        this.endFrame = endFrame;
        this.startFrame = starFrame;
        this.scoreLabel = score;
    }

    //update game state
    public void update() {
        if(ball.x >= (getWidth() - ball.width) || ball.x <= 0) {
            cur_X = ball.getvX();
            cur_Y = ball.getvY();

            if(Math.abs(ball.getvX()) < 1) {
                ball.setvX(Math.copySign(1, ball.getvX() * -1));
            } else {
                ball.setvX(ball.getvX()* -1);
            } if(Math.abs(cur_X) == Math.abs(prev_X) && Math.abs(prev_X) == Math.abs(dprev_X)) {
                if(ball.x < 20) {
                    ball.x += 5;
                } else {
                    ball.x -= 5;
                }
            }
            prev_X = cur_X;
            dprev_X = prev_X;

            if(Math.abs(cur_Y) == Math.abs(prev_Y) && Math.abs(prev_Y) == Math.abs(dprev_Y)) {
                ball.y += 5;
            }

            prev_Y = cur_X;
            dprev_Y = prev_X;
        }
        if(ball.y > getHeight()) {
            end(false);
        }
        if(ball.y < 0) {
            ball.setvY(ball.getvY()* -1);
        } else if(paddle.intersects(ball)) {
            if(ball.getvY() < 3) {
                ball.setvY(3);
            }
            ball.setvY(ball.getvY()* -1);
            double xDif = (double) (paddle.x + (paddle.width/2) - (ball.x + (ball.width/2)));
            if(xDif > 0) {
                ball.setvX((Math.abs(xDif))/(paddle.width/2) * -2.0);
            } else {
                ball.setvX((((paddle.width/2) - xDif)) / (paddle.width/2) * 2.0);
            }
        }
        
        for(int i=0; i<blocks.length; i++) {
            for(int j=0; j<blocks[i].length; j++) {
                Block currBlock = blocks[i][j];
                if(currBlock == null) {
                    continue;
                }
                if(ball.intersects(currBlock)) {
                    score += 10;

                    currBlock.hit();

                    if(!thru){
                        ball.setvY(ball.getvY() * -1);
                    }
                    if(currBlock.getHits() < 1) {
                        blocks[i][j] = null;
                    }

                    if(levelStatus()) {
                        nextLevel();
                    } else {
                        powerup(ball.x, ball.y);
                    }
                }
            }
        }

        ball.move();
        scoreLabel.setText("Score " +score);

        for(Powerup p: powerups) {
            if(paddle.intersects(p)) {
                score += 50;
                handle(p);
                powerups.remove(p);
            } else {
                if(p.isGone(getHeight())) {
                    powerups.remove(p);
                } else {
                    p.fall();
                }
            }
        }
        repaint();
    }

    void handle(Powerup p) {
        if(p == nextLev) {
            nextLevel();
        } else if(p == invincible) {
            thru = true;
        } else if(p == back) {
            end(false);
        } else if(p == grow && !paddle.isBig()) {
            paddle.x -= paddle.getWidth()/2;
            paddle.grow();
        } else if(p == shrink && !paddle.isSmall()){
            paddle.x += paddle.getWidth()/2;
            paddle.shrink();
        }
    }

    void powerup(int x, int y) {
        if(!powerups.isEmpty()){
            return;
        }

        double drop = Math.random();

        if(drop < 0.07) {
            double rand = Math.random();
            if(rand < 0.35) {
                shrink = new Powerup(x, y, 30, 30, 2, Color.GREEN);
                powerups.add(shrink);
            } 
        } else if (drop >= 0.93) {
            double rand = Math.random();
            if(rand < 0.3) {
                grow = new Powerup(x, y, 30, 30, 2, Color.CYAN);
                powerups.add(grow);
            } else if(rand <= 0.9) {
                invincible = new Powerup(x, y, 30, 30, 3, Color.MAGENTA);
                powerups.add(invincible);
            } else if(rand > 0.9) {
                nextLev = new Powerup(x, y, 30, 30, 4, Color.BLUE);
                powerups.add(nextLev);
            }
        }
    }

    void loadLevel(int i) {
        blocks = l.getLevel(i);
    }

    boolean levelStatus() {
        for(int i=0; i<blocks.length; i++) {
            for(int j=0; j<blocks[i].length; j++){
                if(blocks[i][j] != null && blocks[i][j].getHits() >0){
                    return false;
                }
            }
        }
        return true;
    }

    void reset() {
        ball = new Ball(300,350,15,15,Color.YELLOW);

        paddle = new Paddle(225, 500, 150, 25, Color.GREEN);
        powerups.removeAll(powerups);
        thru = false;
    }

    void nextLevel() {
        currLev++;

        if(currLev > l.numOfLevels()){
            end(true);
        } try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        loadLevel(currLev - 1);
        reset();
    }

    void end(boolean win) {
        thread = null;

        mainFrame.setVisible(false);
        endFrame.setVisible(true);

        JLabel message;
        if(win) {
        message = new JLabel("you beat all " + currLev + " levels with a score of " + score, SwingConstants.CENTER);
        } else {
            message = new JLabel("you lost on level " + currLev + " Your score was " + score, SwingConstants.CENTER);
        }
        message.setFont(new Font("Comic Sans", 1, 20));

        endFrame.getContentPane().add(message);
        JButton exit = new JButton("exit");
        exit.addActionListener(lister -> {
            endFrame.setVisible(false);
        });
        endFrame.getContentPane().add(exit);
        end = true;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paddle.draw(g, Color.GREEN);
        ball.draw(g, Color.YELLOW);
        for(int i=0; i<blocks.length; i++) {
            for(int j=0; j<blocks[i].length; j++){
                Block currBlock = blocks[i][j];
                if(currBlock != null) {
                    currBlock.draw(g, currBlock.c);

                    g.fillRect(j*currBlock.w, i*currBlock.h, currBlock.w, currBlock.h);

                    ((Graphics2D) g).setStroke(new BasicStroke(2));
                    g.setColor(Color.BLACK);
                    g.drawRect(j*currBlock.w, i*currBlock.h, currBlock.w, currBlock.h);
                }
            }
        }
        for(Powerup powerup : powerups) {
            powerup.draw(g, powerup.c);
        }

    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if(arg0.getKeyCode() == KeyEvent.VK_ENTER && !started) {
            started = true;
            thread = new Thread(() -> {
                while(!end) {
                    update();
                    try{
                        Thread.sleep(8);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

        if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
            startFrame.setVisible(false);
            mainFrame.setVisible(false);
            endFrame.setVisible(false);
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {}
    @Override
    public void keyTyped(KeyEvent arg0) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        int xCoord = (int) (e.getX() + (paddle.getWidth()/2));
        if(!(xCoord < paddle.getWidth()) && !(xCoord > getWidth())) {
            paddle.x = (int) (xCoord - paddle.getWidth());
        }
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {}

    public Block[][] getBlocks() {
        return blocks;
    }
    public void setBlocks(Block[][] b) {
        blocks = b;
    }
    public boolean getThru() {
        return this.thru;
    }
    public Set<Powerup> getPowerups() {
        return this.powerups;
    }
    public Paddle getPaddle() {
        return this.paddle;
    }
    public Ball getBall() {
        return this.ball;
    }
    public int getCurLevel() {
        return this.currLev;
    }
    public Powerup getDie() {
        return die;
    }
    public Powerup getGrow() {
        return grow;
    }
}

