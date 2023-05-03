import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *         The class that displays the game!
 *         @author bbabai00
 */
public class DisplayLab8 {
	/**
	 *         our main function that starts the program!
	 *         @author bbabai00
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Get Those Circles");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new CirclePanel());
		frame.pack();
		frame.setVisible(true);
		frame.setCursor(frame.getToolkit().createCustomCursor(
				 new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), null));
	}
}