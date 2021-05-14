
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public  class GuiMain extends JFrame implements KeyListener {

    //main method
    public static void main(String[] args) {
        new GuiMain();
    }

    //misc variables
        JPanel panel;
        int[] enemyX = new int[34];
        int[] enemyY = new int[34];
        int currentVertical = 50;
        int userX = 300;
        int enemyMovement;

    //user missile variables
        int[] userMissileX = new int[50000];
        int[] userMissileY = new int[50000];
        int currentMissile = 0;

    //enemy missile variables
        int[] enemyMissileX = new int[50000];
        int[] enemyMissileY = new int[50000];
        int count = 0;
        int currentEnemyMissile = 0;

        int[] living = new int[34];
        JLabel score;
        int currentScore = 0;
        boolean lost = false;

    public GuiMain() {

        super("Galaxian");
        init();
        setVisible(true);

    }

    //contents in the game
    public void init() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);

        for (int x = 0; x < living.length; x++) {
            living[x] = 1;
        }

        panel = new JPanel() {

            protected void paintComponent(Graphics g) {

                BufferedImage img;

            if (lost) {

                } else {

                    try {

                        img = ImageIO.read(new File("/Users/johnspro/Desktop/GalaxianImages/maxresdefault.jpeg"));
                        g.drawImage(img, 0, 0, 700, 600, null);
                        currentVertical = 50;

                    for (int i = 0; i < 33; i++) {
                            enemyX[i + 1] = enemyX[i] + 40;
                            enemyY[i] = currentVertical;
                    if (i == 10 || i == 21) {
                                currentVertical += 40;
                                enemyX[i + 1] = enemyX[0];
                            }
                    //creating enemy
                    BufferedImage enemy = ImageIO.read(new File("/Users/johnspro/Desktop/GalaxianImages/enemy.png"));
                    if (enemyX[0] <= 10) {
                                enemyMovement = 10;
                    } else if (enemyX[0] >= 150) {
                                enemyMovement = -10;
                            }

                            enemyX[i] = enemyX[i] + enemyMovement;

                    //if the enemy is alive - the program will draw it
                    if (living[i] == 1)
                    g.drawImage(enemy, enemyX[i], enemyY[i], 26, 26, null);

                        }
                    //creating spaceship
                     BufferedImage user = ImageIO.read(new File("/Users/johnspro/Desktop/GalaxianImages/spaceship.png"));
                    g.drawImage(user, userX, 400, 80, 80, null);

                    if (userX <= 20) {
                            userX = 0;
                     } else if (userX >= 900) {
                            userX = 900;

                    }

                    //creating the user missile and its function
                    for (int j = 0; j < currentMissile; j++) {
                            g.setColor(Color.white);
                            g.fillRect(userMissileX[j], userMissileY[j], 4, 9);
                            //make missile go up
                            userMissileY[j] = userMissileY[j] - 25;
                        }
                     //creating enemy missile
                        if (count == 10) {
                            Random rando = new Random();
                            int randomEnemy = rando.nextInt(33);

                        if (living[randomEnemy] == 1) {
                                enemyMissileX[currentEnemyMissile] = enemyX[randomEnemy];
                                enemyMissileY[currentEnemyMissile] = enemyY[randomEnemy];
                            }

                            currentEnemyMissile++;
                            count = 0;

                        }

                        //drawing missile
                        for (int k = 0; k < currentEnemyMissile; k++) {
                            g.setColor(Color.GREEN);
                            g.fillRect(enemyMissileX[k], enemyMissileY[k], 10, 5);
                            //make missile go down
                            enemyMissileY[k] = enemyMissileY[k] + 25;

                        }

                        for (int y = 0; y < 33; y++) {
                            for (int z = 0; z < currentMissile; z++) {

                                //if user hit enemy
                        if (living[y] == 1 && userMissileX[z] >= enemyX[y] && userMissileX[z] <= enemyX[y] + 20 && userMissileY[z] >= enemyY[y] && userMissileY[z] <= enemyY[y]) {

                                    living[y] = 0;
                                    userMissileY[z] = -20;
                                    currentScore += 2;
                                    updateScore();

                                }
                            }
                        }

                        for (int w = 0; w < currentEnemyMissile; w++) {
                         if (enemyMissileX[w] >= userX && enemyMissileX[w] <= userX + 60 && enemyMissileY[w] >= 420 && enemyMissileY[w] <= 440) {
                                lost = true;
                                enemyMissileY[w] = -20;
                            }
                        }
                        count++;

                        repaint();

                    } catch (IOException e) {
                        e.printStackTrace();
                }
            }
            }
        };

    //creating point counter
        score = new JLabel("POINTS: " + currentScore);
        score.setFont(score.getFont().deriveFont(13.0f));
        score.setForeground(Color.white);

        panel.add(score);
        add(panel);
        setSize(700, 600);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == 37){
            userX -= 5;
        }else if(e.getKeyCode()==39){
            userX += 5;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if(e.getKeyCode() == 32){
            userMissileX[currentMissile] = userX + 36;
            userMissileY[currentMissile] = 400;
            currentMissile ++;
            repaint();
        }
    }

    public void updateScore(){
        score.setText("POINTS: " + currentScore);
    }
}

