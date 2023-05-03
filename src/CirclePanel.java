import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import javax.swing.*;

/**
 *         Our panel for the game that shows everything on the frame.
 *         Bonus: - when you don't hit the circle, the crosshair becomes
 *         red and you can't click again.(you only have one chance)
 *         - the circle explodes when clicked on!
 *         @author bbabai00
 */
public class CirclePanel extends JPanel {
	private int circleX, circleY, mouseX, mouseY, speed, hit, miss, diameter;
	private boolean isInsideMouse; // when the mouse is inside the panel
	private boolean isInsideCircle; // when the mouse is clicked inside the circle
	private boolean isClicked;
	private Timer t;
	public int explodeX, explodeY; // public global variables for our explosion!
	
	/**
	 *         Our panel's constructor.
	 *         @author bbabai00
	 */
	public CirclePanel() {
		this.setPreferredSize(new Dimension(500,500));
		this.setFocusable(true);
		this.setBackground(Color.BLACK);
		this.hit = 0;
		this.miss = 0;
		this.speed = 5;
		this.diameter = 60;
		this.circleX = -100;
		this.circleY = 250;
		this.isInsideCircle = false;
		this.isInsideMouse = false;
		this.isClicked = false;
		this.t = new Timer(100, new TimerListener());
		t.start();
		this.addKeyListener(new keyListener());
		this.addMouseMotionListener(new MotionListener());
		this.addMouseListener(new mouseListener());
	}
	
	/**
	 *         Our paint component for our panel that draws everything! 
	 *         @author bbabai00
	 */
	public void paintComponent(Graphics g) {
		String hits = ("hits: " + hit), misses = ("misses: " + miss);
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.WHITE);
		Font font = new Font("sans-serif", Font.ITALIC, 18);
		g2.setFont(font);
		g2.drawString(hits, 75, 25);
		g2.drawString(misses, 150, 25);
		g2.setColor(Color.GRAY);
		Ellipse2D.Double circle = new Ellipse2D.Double(circleX, circleY, diameter, diameter);
		g2.fill(circle);
		// making the crosshair for shooting (when the mouse is inside the panel)
		if(isInsideMouse) {
			// the crosshair becomes red when unsuccessfully shooting and we get a message (bonus)
			if(isClicked) {
				g2.setColor(Color.RED);
				Line2D.Double rTick1 = new Line2D.Double(mouseX, mouseY-12, mouseX, mouseY-4);
				Line2D.Double rTick2 = new Line2D.Double(mouseX, mouseY+12, mouseX, mouseY+4);
				Line2D.Double rTick3 = new Line2D.Double(mouseX-12, mouseY, mouseX-4, mouseY);
				Line2D.Double rTick4 = new Line2D.Double(mouseX+12, mouseY, mouseX+4, mouseY);
				Ellipse2D.Double dot2 = new Ellipse2D.Double(mouseX-2, mouseY-2, 4, 4);
				g2.draw(rTick1);
				g2.draw(rTick2);
				g2.draw(rTick3);
				g2.draw(rTick4);
				g2.fill(dot2);
				g2.setColor(Color.RED);
				g2.drawString("You Lost Your Chance!", 250, 25);
			} else { // the crosshair is green at default
				g2.setColor(Color.GREEN);
				Line2D.Double tick1 = new Line2D.Double(mouseX, mouseY-12, mouseX, mouseY-4);
				Line2D.Double tick2 = new Line2D.Double(mouseX, mouseY+12, mouseX, mouseY+4);
				Line2D.Double tick3 = new Line2D.Double(mouseX-12, mouseY, mouseX-4, mouseY);
				Line2D.Double tick4 = new Line2D.Double(mouseX+12, mouseY, mouseX+4, mouseY);
				Ellipse2D.Double dot = new Ellipse2D.Double(mouseX-2, mouseY-2, 4, 4);
				g2.draw(tick1);
				g2.draw(tick2);
				g2.draw(tick3);
				g2.draw(tick4);
				g2.fill(dot);
			}
		}
		// explosion!(bonus)
		if(isInsideCircle) {
			Ellipse2D.Double ex1 = new Ellipse2D.Double(explodeX+10, explodeY+10, diameter-20, diameter-20);
			Ellipse2D.Double ex2 = new Ellipse2D.Double(explodeX-10, explodeY-10, diameter+20, diameter+20);
			g2.setColor(Color.RED);
			g2.fill(ex2);
			g2.setColor(Color.YELLOW);
			g2.fill(ex1);
			isInsideCircle = false;
		}
	}
	
	/**
	 *         the action listener for our timer. 
	 *         @author bbabai00
	 */
	private class TimerListener implements ActionListener {
		/**
		 *         function that moves the circle by adding the speed to its x value.
		 *         when the circle is out of bounds, it moves it back so that 
		 *         we would have another target to shoot at! 
		 *         @author bbabai00
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			circleX += speed;
			if(circleX > 500) {
				isClicked = false;
				miss++;
				circleX = (int)((Math.random()*-200)-30);
				circleY = (int)((Math.random()*400)+30);
			}
			repaint();
		}
	}
	
	/**
	 *         the key listener for our panel. 
	 *         @author bbabai00
	 */
	private class keyListener implements KeyListener {
		/**
		 *         function that is triggered when we press the buttons
		 *         1, 2, 3, 4, 5 which then changes the size of the circle
		 *         to a different size depending on the button pressed!
		 *         @author bbabai00
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_1) {
				diameter = 20;
			}else if(e.getKeyCode() == KeyEvent.VK_2) {
				diameter = 30;
			}else if(e.getKeyCode() == KeyEvent.VK_3) {
				diameter = 40;
			}else if(e.getKeyCode() == KeyEvent.VK_4) {
				diameter = 50;
			}else if(e.getKeyCode() == KeyEvent.VK_5) {
				diameter = 60;
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
	/**
	 *         the mouse motion listener for our panel. 
	 *         @author bbabai00
	 */
	private class MotionListener implements MouseMotionListener {
		/**
		 *         function that is triggered when the mouse is moved.
		 *         it updates our x and y coordinates for the mouse and
		 *         indicates whether the mouse is inside the panel or not.
		 *         it also indicates whether the mouse is inside the circle or not.
		 *         @author bbabai00
		 */
		@Override
		public void mouseMoved(MouseEvent e) {
			isInsideMouse = true;
			mouseX = e.getX();
			mouseY = e.getY();
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
	/**
	 *         the mouse listener for our panel. 
	 *         @author bbabai00
	 */
	private class mouseListener implements MouseListener {
		/**
		 *         function that is triggered when the mouse exits the panel.
		 *         @author bbabai00
		 */
		@Override
		public void mouseExited(MouseEvent e) {
			isInsideMouse = false;
		}
		
		/**
		 *         function that is triggered when the mouse is clicked.
		 *         you can only click the circle once or you have to wait
		 *         until it goes out of bounds.(bonus)
		 *         @author bbabai00
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			if(! isClicked) {
				if(Math.sqrt(Math.pow(mouseX-(circleX+diameter/2),2)+Math.pow(mouseY-(circleY+diameter/2),2)) <= diameter/2) {
				//if((mouseX >= circleX && mouseX <= circleX + diameter) && (mouseY >= circleY && mouseY <= circleY + diameter )) {
					hit++;
					speed += 1;
					explodeX = circleX;
					explodeY = circleY;
					circleX = (int)((Math.random()*-200)-30);
					circleY = (int)((Math.random()*400)+30);
					isInsideCircle = true;
				} else {
					isClicked = true;
				}
			}
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}
	}
	
}
