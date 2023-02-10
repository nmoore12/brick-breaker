import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class Main {
    public static void main(String[] args) {
        JFrame object = new JFrame("Brick breaker!");
        JFrame levelSel = new JFrame("Level Select");

        //start screen
        JFrame start = new JFrame();
        JButton play = new JButton("play");
        JButton rules = new JButton("rules");
        JButton lvlSelect = new JButton("level selection");
        JLabel intro = new JLabel("welcome to brick breaker!", SwingConstants.CENTER);
        intro.setFont(new Font("Comic Sans", 1, 20));

        JFrame end = new JFrame();

        play.addActionListener(lister ->{
            start.setVisible(false);
            object.setVisible(true);
        });

        rules.addActionListener(lister -> {
            showRules(start);
        });

        lvlSelect.addActionListener(lister -> {
            start.setVisible(false);
            levelSel.setVisible(true);
        });

        //panel for the main gameplay (not visible on starup)
        JLabel score = new JLabel("Score 0", SwingConstants.CENTER);
        object.getContentPane().add(score, BorderLayout.PAGE_START);
        GamePanel panel = new GamePanel(object, end, start, score);

        object.getContentPane().add(panel);
        object.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        object.setVisible(false);
        object.setSize(700,600);
        object.setResizable(false);

        //start panel
        GridLayout startLayout = new GridLayout(0, 1);
        start.setLayout(startLayout);
        lvlSelect.setLayout(startLayout);
        end.setLayout(startLayout);
        //buttons
        start.getContentPane().add(intro);
        start.getContentPane().add(play);
        start.getContentPane().add(rules);
        start.getContentPane().add(lvlSelect);
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.setVisible(true);
        start.setSize(700,600);
        start.setResizable(false);

        //level select panel
        GridLayout levelLayout = new GridLayout(0,1);
        levelSel.setLayout(levelLayout);
        levelSel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        levelSel.setVisible(false);
        levelSel.setSize(700,600);
        levelSel.setResizable(false);
        //buttons
        JButton levelOne = new JButton("level one");
        JButton levelTwo = new JButton("level two");
        levelSel.getContentPane().add(levelOne);
        levelSel.getContentPane().add(levelTwo);

        //end panel
        end.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        end.setVisible(false);
        end.setSize(700,600);
        end.setResizable(false);
    }

    private static void showRules(JFrame parent) {
        String info = "Welcome to Brick Breaker! here is a guide: \n\n"
        + "GREEN powerup - shrink paddle size\n"
        + "CYAN powrup - increase paddle size\n"
        + "MAGENTA powerup - no ball/block collision\n"
        + "DARK BLUE powerup - advance to next level\n\n"
        + "RED BLOCKS - 2 hits to destroy";

        JOptionPane.showMessageDialog(parent, info, "rules",1);
    }

    
}
