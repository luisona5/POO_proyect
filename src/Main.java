import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("ingreso");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setPreferredSize(new Dimension(600, 400));
        frame.setContentPane(new LOGIN().inicio);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }



}