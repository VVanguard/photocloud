package util.customcomponents;

import java.awt.Graphics;

import javax.swing.JButton;


/**
 * Custom Rounded Button extending JButton
 * @author bkaym
 *
 */
public class RoundedJButton extends JButton {

	private int arcR;
	
	
	public RoundedJButton(int arcR) {
		super();
		this.arcR = arcR;
		setOpaque(false);
	}
	
	
	/**
	 * Override paint components method to paint a round rectangle with arc radius 
	 */
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRoundRect(0, 0, getWidth(), getHeight(), arcR, arcR);
		super.paintComponent(g);
	}
	
	
	/**
	 * Disable PaintBorder method to make it borderless
	 */
	@Override
	protected void paintBorder(Graphics g) {}
	
}
