import javax.swing.*;
import java.awt.*;
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
    private final GradientPaint gradientPaint = new GradientPaint(0, 25, GameFrame.getForegroundColor(), 150,
            25, new Color(0x6C3082), true);
    private final int xCoordinate = 175;
    private static List<Integer> highscore = new ArrayList<>();

    //            new Color(0x2C041C)
    //            new Color(0x7a4988)
    //            new Color(0x6C3082)

    public OptionsPanel() {

        int margin = 30;
        int dimension = 600;

        setPreferredSize(new Dimension(dimension, dimension + margin));
        setFont(new Font("Bell MT", Font.BOLD, 25));

        setBackground(GameFrame.getForegroundColor());
    }

    public void buildMenu(Graphics2D graphics2D) {
        int text = 0;
        int yCoordinate = 125;

        for (int i = 200; i < 650; i += 150) {

            graphics2D.setPaint(gradientPaint);
            graphics2D.setPaint(graphics2D.getPaint());
            graphics2D.fillRoundRect(xCoordinate, yCoordinate, 250, 75, 50, 50);

            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRoundRect(xCoordinate, yCoordinate, 250, 75, 50, 50);

            drawCenteredString(graphics2D, textforButtons[text++] , yCoordinate);

            yCoordinate += 150;
            graphics2D.setPaint(Color.GRAY);
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


    public static void main(String[] args) {
        JFrame frame = new JFrame("OptionsMenu");
        frame.add(new OptionsPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }


}
