package util.customcomponents;

import java.awt.Graphics;

import javax.swing.JToggleButton;


/**
 * Custom Rounded Toggle Button extending JButton
 * @author bkaym
 *
 */
public class RoundedJToggleButton extends JToggleButton {

	private int arcR;
	
	
	public RoundedJToggleButton(int arcR) {
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
		g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, arcR, arcR);
		super.paintComponent(g);
	}
	
	
	/**
	 * Disable PaintBorder method to make it borderless
	 */
	@Override
	protected void paintBorder(Graphics g) {}
	
}