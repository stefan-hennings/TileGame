import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julia Wigenstedt
 * Date: 2020-10-27
 * Time: 21:21
 * Project: Inlämning3GUI
 * Copyright: MIT
 */
public class OptionsPanel extends JPanel {

    private final String[] textforButtons = {"Visa High-Score", "Ändra färgschema", "Tillbaka"};
    private GradientPaint gradientPaint = new GradientPaint(0, 25, GameFrame.getForegroundColor(), 150,
            25, new Color(0x6C3082), true);
    private final int xCoordinate = 175;
    private static List<Integer> highscore = new ArrayList<>();
    private static int button;

    private  static GradientPaint[] gradientPaints = {new GradientPaint(0, 25, GameFrame.getForegroundColor(), 150,
            25, new Color(0x6C3082), true),
            new GradientPaint(0, 25, new Color(0xFFFFE0), 150,
                    25, new Color(0xC8A957), true), new GradientPaint(0, 25, new Color(0xAFEEEE), 150,
            25, new Color(0x16064), true),
            new GradientPaint(0, 25, new Color(0x7a4988), 150,
            25, new Color(0x2C041C), true)};

    public OptionsPanel() {

        int margin = 30;
        int dimension = 600;

        setPreferredSize(new Dimension(dimension, dimension + margin));
        setFont(new Font("Bell MT", Font.BOLD, 25));

        setBackground(GamePanel.getColor()[ColorPanel.getColor()]);
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {

                int clickX = e.getX();
                int clickY = e.getY();

                if (clickX >= xCoordinate && clickX <= 425 && clickY >= 125 && clickY <= 200) {
                    button = 0;
                    HighScore.deSerialize();
                    GameFrame.getCardLayout().show(GameFrame.getCardPanel(), "4");
                    GameFrame.getTopPanel().repaint();
                    GameFrame.getMenuPanel().repaint();
                    GameFrame.startNewGame();
                } else if (clickX >= xCoordinate && clickX <= 425 && clickY >= 275 && clickY <= 350) {
                    button = 2;
                    GameFrame.getCardLayout().show(GameFrame.getCardPanel(), "3");
                    GameFrame.getTopPanel().repaint();
                    GameFrame.getMenuPanel().repaint();
                    GameFrame.startNewGame();

                } else if (clickX >= xCoordinate && clickX <= 425 && clickY >= 425 && clickY <= 500) {
                    button = 4;
                    GameFrame.getCardLayout().show(GameFrame.getCardPanel(), "1");
                    GameFrame.startNewGame();
                }
            }
        });
    }

    public void buildMenu(Graphics2D graphics2D) {

        int yCoordinate = 125;

        setBackground(GamePanel.getColor()[ColorPanel.getColor()]);

        for (int i = 0; i < 3; i ++) {

            graphics2D.setPaint(gradientPaints[ColorPanel.getPaintNumber()]);
            graphics2D.setPaint(graphics2D.getPaint());
            graphics2D.fillRoundRect(xCoordinate, yCoordinate, 250, 75, 50, 50);

            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRoundRect(xCoordinate, yCoordinate, 250, 75, 50, 50);

            drawCenteredString(graphics2D, textforButtons[i], yCoordinate);

            yCoordinate += 150;
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        buildMenu(graphics2D);
    }

    private void drawCenteredString(Graphics2D graphics, String string, int yCoordinate) {

        FontMetrics fontMetrics = graphics.getFontMetrics();
        graphics.drawString(string, xCoordinate + (250 - fontMetrics.stringWidth(string)) / 2,
                yCoordinate + (fontMetrics.getAscent() + (75 - (fontMetrics.getAscent() + fontMetrics.getDescent())) / 2));
    }

    @SuppressWarnings("unchecked")
    public static void deSerialize() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("highscores.ser"));
            highscore = (List<Integer>) in.readObject();
            in.close();
        } catch (Exception e) {
            System.out.println("Fil skapad");
        }
    }

    public static int getButton() {
        return button;
    }
}
