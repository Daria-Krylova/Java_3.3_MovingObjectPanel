import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MovingObjectPanel extends JPanel implements ActionListener {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int OBJECT_SIZE = 300; // Увеличим размер объекта
    private static final int OBJECT_SPEED = 5;  // Уменьшим скорость для более плавного движения

    private Image objectImage;
    private int objectX, objectY;
    private int xDirection, yDirection; // Направление движения объекта

    public MovingObjectPanel() {
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setFocusable(true);

        try {
            objectImage = ImageIO.read(getClass().getResource("ocean.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        objectX = (WINDOW_WIDTH - OBJECT_SIZE) / 2;
        objectY = (WINDOW_HEIGHT - OBJECT_SIZE) / 2;

        xDirection = 0;
        yDirection = 0;

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                startMoving(keyCode);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                stopMoving();
            }
        });

        Timer timer = new Timer(100, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(objectImage, objectX, objectY, OBJECT_SIZE, OBJECT_SIZE, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveObject();
        repaint();
    }

    private void startMoving(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                xDirection = -OBJECT_SPEED;
                break;
            case KeyEvent.VK_RIGHT:
                xDirection = OBJECT_SPEED;
                break;
            case KeyEvent.VK_UP:
                yDirection = -OBJECT_SPEED;
                break;
            case KeyEvent.VK_DOWN:
                yDirection = OBJECT_SPEED;
                break;
        }
    }

    private void stopMoving() {
        xDirection = 0;
        yDirection = 0;
    }

    private void moveObject() {
        int newX = objectX + xDirection;
        int newY = objectY + yDirection;

        if (newX >= 0 && newX + OBJECT_SIZE <= WINDOW_WIDTH &&
                newY >= 0 && newY + OBJECT_SIZE <= WINDOW_HEIGHT) {
            objectX = newX;
            objectY = newY;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Движение объекта");
        MovingObjectPanel movingObjectPanel = new MovingObjectPanel();
        frame.add(movingObjectPanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
