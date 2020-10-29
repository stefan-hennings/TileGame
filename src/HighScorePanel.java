import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Julia Wigenstedt
 * Date: 2020-10-28
 * Time: 16:04
 * Project: Inlämning3GUI
 * Copyright: MIT
 */
public class HighScorePanel extends JPanel {
    private final int xCoordinate = 150;

    private String[] textForButtons;
    private  static final GradientPaint[] gradientPaints = {new GradientPaint(0, 25, GameFrame.getForegroundColor(), 150,
            25, new Color(0x6C3082), true),
            new GradientPaint(0, 25, new Color(0xFFFFE0), 150,
                    25, new Color(0xC8A957), true), new GradientPaint(0, 25, new Color(0xAFEEEE), 150,
            25, new Color(0x16064), true), new GradientPaint(0, 25, new Color(0x7a4988), 150,
            25, new Color(0x2C041C), true)};


    public HighScorePanel() {
        HighScore.deSerialize();
        setFont(new Font("Bell MT", Font.BOLD, 25));
        createButtonText();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                int clickX = e.getX();
                int clickY = e.getY();

                if (clickX >= xCoordinate && clickX <= 455 && clickY >= 630 && clickY <= 680) {
                    GameFrame.getCardLayout().show(GameFrame.getCardPanel(), "2");
                }
            }
        });
    }

    public void buildMenu(Graphics2D graphics2D) {
        HighScore.deSerialize();
        createButtonText();

        setBackground(GamePanel.getColor()[ColorPanel.getButton()+1]);
        int yCoordinate = 30;

        for (int i = 0; i < 11; i ++) {

            graphics2D.setPaint(gradientPaints[ColorPanel.getPaintNumber()]);
            graphics2D.fillRect(xCoordinate, yCoordinate, 300, 50);

            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRect(xCoordinate, yCoordinate, 300, 50);

            drawCenteredString(graphics2D, textForButtons[i], yCoordinate);

            yCoordinate += 60;
        }
    }

    private void createButtonText() {
        textForButtons = new String[11];
        if (!HighScore.getHighScoreList().isEmpty()) {
            for (int i = 0; i < textForButtons.length - 1; i++) {
                if (i < HighScore.getHighScoreList().size()) {
                    textForButtons[i] = (i + 1) + ". " + HighScore.getHighScoreList().get(i).getName() + ": " +
                            HighScore.getHighScoreList().get(i).getScore();
                } else {
                    textForButtons[i] = i + 1 + ". Göransson";
                }

            }
        } else {
            for (int i = 0; i < textForButtons.length - 1; i++) {
                textForButtons[i] = i + 1 + ". Göransson";
            }

        }
        textForButtons[10] = "Tillbaka";
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
        graphics.drawString(string, xCoordinate + (300 - fontMetrics.stringWidth(string)) / 2,
                yCoordinate + (fontMetrics.getAscent() + (50 - (fontMetrics.getAscent() + fontMetrics.getDescent())) / 2));
    }

}
