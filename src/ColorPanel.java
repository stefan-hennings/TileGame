import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Julia Wigenstedt
 * Date: 2020-10-28
 * Time: 14:13
 * Project: Inlämning3GUI
 * Copyright: MIT
 */
public class ColorPanel extends JPanel {

    private final String[] textforButtons = {"Orginal", "Gyllene", "Ocean", "Plommon","Tillbaka"};
    private final int xCoordinate = 175;
    private static int button;
    private static int paintNumber;

    private  static final GradientPaint[] gradientPaints = {new GradientPaint(0, 25, GameFrame.getForegroundColor(), 150,
            25, new Color(0x6C3082), true),
            new GradientPaint(0, 25, new Color(0xFFFFE0), 150,
                    25, new Color(0xC8A957), true),
            new GradientPaint(0, 25, new Color(0xAFEEEE), 150,
            25, new Color(0x16064), true),
            new GradientPaint(0, 25, new Color(0x7a4988), 150,
            25, new Color(0x2C041C), true)};


    public ColorPanel() {

        int margin = 30;
        int dimension = 600;

        setPreferredSize(new Dimension(dimension, dimension + margin));
        setFont(new Font("Bell MT", Font.BOLD, 25));

        setBackground(GameFrame.getForegroundColor());
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {

                int clickX = e.getX();
                int clickY = e.getY();

                if (clickX >= xCoordinate && clickX <= 425 && clickY >= 125 && clickY <= 200) {
                    button = 0;
                    paintNumber = 0;
                    paintGameFrame();
                    setBackground(GamePanel.getColor()[button+1]);
                    repaint();

                } else if (clickX >= xCoordinate && clickX <= 425 && clickY >= 225 && clickY <= 300) {
                    button = 2;
                    paintNumber = 1;
                    paintGameFrame();
                    setBackground(GamePanel.getColor()[button+1]);
                    repaint();

                } else if (clickX >= xCoordinate && clickX <= 425 && clickY >= 325 && clickY <= 400) {
                    button = 4;
                    paintNumber = 2;
                    paintGameFrame();
                    setBackground(GamePanel.getColor()[button+1]);
                    repaint();
                    GameFrame.getTopPanel().repaint();
                    GameFrame.getMenuPanel().repaint();
                    GameFrame.startNewGame();

                } else if (clickX >= xCoordinate && clickX <= 425 && clickY >= 425 && clickY <= 500) {
                    button = 6;
                    paintNumber = 3;
                    paintGameFrame();
                    setBackground(GamePanel.getMenuColor()[paintNumber]);
                    repaint();
                } else if (clickX >= xCoordinate && clickX <= 425 && clickY >= 525 && clickY <= 600) {
                    paintGameFrame();
                    GameFrame.getCardLayout().show(GameFrame.getCardPanel(), "2");
                    setBackground(GamePanel.getColor()[button+1]);
                    repaint();
                }
            }
        });
    }

    private void paintGameFrame() {
        for (Component component : GameFrame.getMenuPanel().getComponents()) {
            component.setBackground(GamePanel.getMenuColor()[paintNumber]);
        }
        for (Component component : GameFrame.getStatusPanel().getComponents()) {
            component.setForeground(GamePanel.getMenuColor()[paintNumber]);
        }
    }

    public void buildMenu(Graphics2D graphics2D) {
        int yCoordinate = 125;

        for (int i = 0; i < 5; i ++) {

            graphics2D.setPaint(gradientPaints[paintNumber]);
            graphics2D.fillRoundRect(xCoordinate, yCoordinate, 250, 75, 50, 50);

            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRoundRect(xCoordinate, yCoordinate, 250, 75, 50, 50);

            drawCenteredString(graphics2D, textforButtons[i], yCoordinate);

            yCoordinate += 100;
        }
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

    public static int getColor() {
        return button +1;
    }


    public static int getPaintNumber() {
        return paintNumber;
    }

    public static int getButton() {
        return button;
    }
}