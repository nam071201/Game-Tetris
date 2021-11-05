import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TetrisWindow extends JPanel implements ActionListener {
    // JFrame
    JFrame frame;

    // State variables
    public String gameState = "menu";
    public GameBoard gameBoard;
    public MusicUtils musicUtils = new MusicUtils();
    private int howToPlayPage = 1;

    // Game buttons
    public GameButtons buttons = new GameButtons(this);
    private GameButton
            menu1PButton = new GameButton(this, 420, 290, 360, 100, "menu") {
                @Override
                public void click() {
                    super.click();
                    if (gameBoard == null) gameState = "1p";
                }
            },
            menu2PButton = new GameButton(this, 420, 400, 360, 100, "menu") {
                @Override
                public void click() {
                    super.click();
                    if (gameBoard == null) gameState = "2p";
                }
            },
            menuHowToPlayButton = new GameButton(this, 420, 510, 360, 100, "menu") {
                @Override
                public void click() {
                    super.click();
                    gameState = "how_to_play";
                    howToPlayPage = 1;
                }
            },
            unpauseButton = new GameButton(this, 420, 290, 360, 100, "paused") {
                @Override
                public void click() {
                    super.click();
                    gameState = "1p";
                }
            },
            backButton = new GameButton(this, 10, 10, 50, 50, "how_to_play") {
                @Override
                public void click() {
                    super.click();
                    gameState = "menu";
                }
            },
            nextButton = new GameButton(this, TetrisRunner.WIDTH - 80, (TetrisRunner.HEIGHT / 2) - 25, 50, 50, "how_to_play") {
                @Override
                public void click() {
                    super.click();
                    gameState = "how_to_play";
                    howToPlayPage = howToPlayPage == 1 ? 2 : 1;
                }
            };

    Timer timer = new Timer(TetrisRunner.FPS_DELAY, this);

    public TetrisWindow(JFrame frame) {
        this.frame = frame;

        timer.start();

        addKeyListener(new TetrisKeyListener(this));
        addFocusListener(new TetrisFocusListener(this));

        TetrisMouseListener tml = new TetrisMouseListener(this);
        addMouseMotionListener(tml);
        addMouseListener(tml);

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        // Music
        musicUtils.playMusic("tetris.wav");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameState.equals("menu")) {
            gameBoard = null;
        }
        if (gameState.equals("1p")) {
            if (gameBoard == null) gameBoard = new Tetris1P(this);
            else gameBoard.tick();
        } else if (gameState.equals("2p")) {
            if (gameBoard == null) gameBoard = new Tetris2P(this);
            else gameBoard.tick();
        }

        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameState.equals("menu")) {
            paintMenu(g);
        }
        else if (gameState.equals("how_to_play")) {
            paintHowToPlay(g);
        }
        else if (gameState.equals("2p") || gameState.equals("1p")) {
            if (gameBoard != null) gameBoard.paintGame(g);
        }
        else if (gameState.equals("paused")) {
            paintPaused(g);
        }
    }

    private void paintMenu(Graphics g) {
        // Background
        g.setColor(Color.white);
        g.fillRect(0, 0, TetrisRunner.WIDTH, TetrisRunner.HEIGHT);

        // Menu
        g.setColor(new Color(50,50,50));
        g.fillRect(380, 80, 400, TetrisRunner.HEIGHT - 80);

        g.setColor(new Color(25,25,25));
        g.fillRect(400, 100, 400, TetrisRunner.HEIGHT - 100);

        // Logo
        g.setFont(new Font("Sans Serif", Font.BOLD, 100));
        g.setColor(new Color(255, 0, 0));
        g.drawString("T", 420, 200);
        g.setColor(new Color(255, 214, 43));
        g.drawString("E", 485, 200);
        g.setColor(Color.cyan);
        g.drawString("T", 550, 200);
        g.setColor(new Color(106, 238, 54));
        g.drawString("R", 615, 200);
        g.setColor(new Color(161,17,150));
        g.drawString("I", 680, 200);
        g.setColor(Color.orange);
        g.drawString("S", 715, 200);

        // Byline
        g.setFont(new Font("Sans Serif", Font.PLAIN, 32));
        g.setColor(Color.white);
        g.drawString("BY JAMSHEED MISTRI", 432, 260);

        // Buttons
            // 1P
        if (menu1PButton.isHighlighted()) {
            g.setColor(new Color(37,223,37));
        } else {
            g.setColor(new Color(27,142,27));
        }

        g.fillRect(420, 290, 360, 100);

        g.setFont(new Font("Sans Serif", Font.PLAIN, 40));
        g.setColor(Color.white);
        GraphicsUtils.centerString(g, "PLAY 1P", TetrisRunner.WIDTH / 2, 355);

            // 2P
        if (menu2PButton.isHighlighted()) {
            g.setColor(new Color(37,223,37));
        } else {
            g.setColor(new Color(27,142,27));
        }

        g.fillRect(420, 400, 360, 100);

        g.setFont(new Font("Sans Serif", Font.PLAIN, 40));
        g.setColor(Color.white);
        GraphicsUtils.centerString(g, "PLAY 2P", TetrisRunner.WIDTH / 2, 465);

            // How to play
        if (menuHowToPlayButton.isHighlighted()) {
            g.setColor(new Color(37,223,37));
        } else {
            g.setColor(new Color(27,142,27));
        }

        g.fillRect(420, 510, 360, 100);

        g.setFont(new Font("Sans Serif", Font.PLAIN, 40));
        g.setColor(Color.white);
        GraphicsUtils.centerString(g, "HOW TO PLAY", TetrisRunner.WIDTH / 2, 575);
    }

    private void paintPaused(Graphics g) {
        // Background
        g.setColor(Color.white);
        g.fillRect(0, 0, TetrisRunner.WIDTH, TetrisRunner.HEIGHT);

        // Menu
        g.setColor(new Color(50,50,50));
        g.fillRect(380, 80, 400, TetrisRunner.HEIGHT - 160);

        g.setColor(new Color(25,25,25));
        g.fillRect(400, 100, 400, TetrisRunner.HEIGHT - 160);

        // Text
        g.setFont(new Font("Sans Serif", Font.BOLD, 90));
        g.setColor(Color.white);
        g.drawString("PAUSED", 420, 200);

        // Buttons
        if (unpauseButton.isHighlighted()) {
            g.setColor(new Color(37,223,37));
        } else {
            g.setColor(new Color(27,142,27));
        }

        g.fillRect(420, 290, 360, 100);

        g.setFont(new Font("Sans Serif", Font.PLAIN, 40));
        g.setColor(Color.white);
        g.drawString("RESUME", 525, 355);
    }

    private void paintHowToPlay(Graphics g) {
        // Background
        g.setColor(Color.white);
        g.fillRect(0, 0, TetrisRunner.WIDTH, TetrisRunner.HEIGHT);

        // Title
        g.setFont(new Font("Sans Serif", Font.BOLD, 50));
        g.setColor(Color.black);
        GraphicsUtils.centerString(g, "HOW TO PLAY " + howToPlayPage + "P", TetrisRunner.WIDTH / 2, 55);

        // Text
        g.setFont(new Font("Sans Serif", Font.PLAIN, 28));
        if (howToPlayPage == 1) GraphicsUtils.multilineString(g, "1. Move by hitting the LEFT and RIGHT arrow keys.\n2. Rotate by hitting the UP arrow key.\n3. Hit the DOWN key to move down by one space. Alternatively, hit\nthe SPACE BAR to move the piece all the way down.\n4. When one full row is full of blocks, it will disappear and you will\ngain points. Try to beat your high score!\n5. The more lines you destroy at once, the more points you gain! For\nexample, if you break one line in one move, you will get less points\nthan if you break four lines in one move.\n6. For every 10 lines you break, your level increases by 1!\n7. You get more points for breaking lines as your level increases.\n8. You can hold a block for later use by hitting the C key. Swap it out\nfor your current block by hitting C again. You can only hold ONCE\nper turn.\n9. To pause, hit the ESCAPE key or hit the pause button at the top left.", 70, 100);
        else if (howToPlayPage == 2) GraphicsUtils.multilineString(g, "1. All of the same rules as 1P apply. However, there is no score or level,\nand the Tetrominoes do not increase speed as time progresses.\n2. Once two people are in a room with the same room key, the match will\nautomatically begin. Whichever competitor breaks the least lines will\nhave temporary gray lines on the bottom of their Tetrion.\n3. The first person to lose (by getting any blocks above the top of the\nTetrion) loses the battle, and the room will be closed automatically.", 70, 100);

            // Back Button
        if (backButton.isHighlighted()) {
            g.setColor(new Color(50,50,50));
        } else {
            g.setColor(Color.black);
        }

        g.fillRect(10, 10, 50, 50);

        g.setFont(new Font("Sans Serif", Font.BOLD, 30));
        g.setColor(Color.white);
        g.drawString("◀", 21, 45);

        // Next page button
        if (nextButton.isHighlighted()) {
            g.setColor(new Color(50,50,50));
        } else {
            g.setColor(Color.black);
        }

        g.fillRect(TetrisRunner.WIDTH - 80, (TetrisRunner.HEIGHT / 2) - 25, 50, 50);

        g.setFont(new Font("Sans Serif", Font.BOLD, 30));
        g.setColor(Color.white);
        g.drawString((howToPlayPage == 1 ? 2 : 1) + "P", TetrisRunner.WIDTH - 75, (TetrisRunner.HEIGHT / 2) + 10);
    }
}
