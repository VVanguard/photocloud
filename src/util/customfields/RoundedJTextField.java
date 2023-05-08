package util.customfields;

import java.awt.Graphics;

import javax.swing.JTextField;


/**
 * Custom Rounded Text Field extending JTextField
 * @author bkaym
 *
 */
public class RoundedJTextField extends JTextField{

	private int arcR;
	
	
	public RoundedJTextField(int arcR) {
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
