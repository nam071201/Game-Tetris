import java.awt.*;

public abstract class GameBoard {
    TetrisWindow window;
    String gameState;

    // Constants
    public static int
            BLOCK_WIDTH = 30,
            TETRION_WIDTH = 10,
            TETRION_HEIGHT = 20;

    // State variables consistent with both 1P and 2P
    public boolean haveHeldThisTurn = false;
    public Block[][] Tetrion = new Block[TETRION_HEIGHT][TETRION_WIDTH];
    public boolean movingLeft = false;
    public boolean movingRight = false;
    public boolean movingDown = false;
    public int movingDelay = 0;
    public boolean oneMoveDone = false;
    public double tetrominoDelay = 1;
    public Tetromino[] queue = new Tetromino[3];
    public Tetromino currentHand;
    public Tetromino hold;
    public int linesBroken = 0;

    public GameBoard(TetrisWindow window) {
        this.window = window;

        for (int i = 0; i < queue.length; i ++) {
            queue[i] = new Tetromino(Tetromino.getRandomTetromino(), window);
        }
        currentHand = new Tetromino(Tetromino.getRandomTetromino(), window);
    }

    public void tick() {
        currentHand.tick();

        if (!oneMoveDone) {
            if (movingLeft) {
                currentHand.moveLeft();
                movingDelay = 200;
                oneMoveDone = true;
            }
            if (movingRight) {
                currentHand.moveRight();
                movingDelay = 200;
                oneMoveDone = true;
            }
            if (movingDown) {
                currentHand.moveDown();
                movingDelay = 50;
                oneMoveDone = true;
            }
        } else {
            if (movingDelay <= 0) {
                if (movingLeft) {
                    currentHand.moveLeft();
                    movingDelay = 50;
                    oneMoveDone = true;
                }
                if (movingRight) {
                    currentHand.moveRight();
                    movingDelay = 50;
                    oneMoveDone = true;
                }
                if (movingDown) {
                    currentHand.moveDown();
                    movingDelay = 50;
                    oneMoveDone = true;
                }
            }
            else {
                movingDelay -= TetrisRunner.FPS_DELAY;
            }
        }
    }

    public void hold() {
        if (haveHeldThisTurn) {
            window.musicUtils.playSoundEffect("noHold.wav");
            return;
        }
        window.musicUtils.playSoundEffect("hold.wav");
        haveHeldThisTurn = true;
        if (hold == null) {
            hold = currentHand;
            currentHand = queue[0];
            queue[0] = queue[1];
            queue[1] = queue[2];
            queue[2] = new Tetromino(Tetromino.getRandomTetromino(), window);
        } else {
            Tetromino oldHold = hold;
            hold = currentHand;
            currentHand = new Tetromino(oldHold.type, window);
        }
    }

    public void nextCycle() {
        currentHand = queue[0];
        queue[0] = queue[1];
        queue[1] = queue[2];
        do {
            queue[2] = new Tetromino(Tetromino.getRandomTetromino(), window);
        } while (queue[2].equals(queue[1]));

        while (!currentHand.isValidAtCoordinates()) {
            currentHand.moveUp();
        }

        breakLines();
    }

    public int breakLines() {
        int lines = 0;
        for (int row = Tetrion.length - 1; row >= 0; row --) {
            boolean rowFull = true;
            boolean isGray = true;
            for (int col = 0; col < Tetrion[0].length; col ++) {
                if (Tetrion[row][col] == null) rowFull = false;
                else if (!Tetrion[row][col].getColor().equals(Block.GRAY)) isGray = false;
            }
            if (rowFull && !isGray) {
                breakLine(row++);
                lines ++;
            }
        }
        if (lines > 0) window.musicUtils.playSoundEffect("breakLines.wav");
        linesBroken += lines;
        return lines;
    }

    // Private method to break a specific line at a row, used by breakLines()
    private void breakLine(int row) {
        for (int i = row; i > 0; i --) {
            Tetrion[i] = Tetrion[i - 1];
        }
        Tetrion[0] = new Block[Tetrion[0].length];
    }

    public abstract void paintGame(Graphics g);
    public abstract void lose();
}
