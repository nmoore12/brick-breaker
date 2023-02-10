import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

public class Levels {
    List<Block[][]> levels = new LinkedList<Block[][]>();
    int width, height;

    private Block create(int i, int j, int hits, Color c) {
        return new Block(j*width, i*height, width, height, hits, c);
    }

    public Levels(int width, int height) {
        this.width = width/10;
        this.height = height/24;

        //level one
        Block[][] levelOne = new Block[10][10];
        levelOne[0][4] = create(0,4,1, Color.WHITE);
        levelOne[0][5] = create(0,5,1,Color.WHITE);
        levelOne[9][4] = create(9,4,1,Color.WHITE);
        levelOne[9][5] = create(9,5,1,Color.WHITE);

        for(int i=3; i<7; i++) {
            levelOne[1][i] = create(1,i,1,Color.WHITE);
            levelOne[8][i] = create(8,i,1,Color.WHITE);
        }
        for(int i=2; i<8; i++) {
            levelOne[2][i] = create(2,i,1,Color.PINK);
            levelOne[7][i] = create(7,i,1,Color.PINK);
        }
        for(int i=1; i<9; i++) {
            levelOne[3][i] = create(3,i,1,Color.PINK);
            levelOne[6][i] = create(6,i,1,Color.PINK);
        }
        for(int i=0; i<10; i++) {
            levelOne[4][i] = create(4,i,1,Color.WHITE);
            levelOne[5][i] = create(5,i,1,Color.WHITE);
        }
        levels.add(levelOne);

        //level two
        Block[][] levelTwo = new Block[10][10];

        for(int i=0; i<10; i++){
            levelTwo[0][i] = create(0, i, 1,Color.WHITE);
            levelTwo[2][i] = create(2, i, 1,Color.WHITE);
            levelTwo[4][i] = create(4, i, 1,Color.WHITE);
            levelTwo[6][i] = create(6, i, 1,Color.WHITE);
            levelTwo[8][i] = create(8, i, 1,Color.WHITE);
        }
        for(int i=0; i<10; i+=2){
            levelTwo[1][i] = create(1, i, 2,Color.RED);
            levelTwo[3][i] = create(3, i+1, 2,Color.RED);
            levelTwo[5][i] = create(5, i, 2,Color.RED);
            levelTwo[7][i] = create(7, i+1, 2,Color.RED);
        }
        levels.add(levelTwo);
    }

    public Block[][] getLevel(int i) {
        return levels.get(i);
    }

    public int numOfLevels(){
        return levels.size();
    }
}


