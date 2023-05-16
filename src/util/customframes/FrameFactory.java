package util.customframes;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.FrameStatus;
import util.Colors;
import util.ComponentConfiguration;


public abstract class FrameFactory extends JFrame {
	
	protected FrameStatus status = FrameStatus.HIDE;
	protected JPanel contentPane;
	
	public FrameFactory(int xSize, int ySize, Type type) {
		
		// Window and Title Configurations
		setType(type);
		setResizable(false);

		// Set Size and Center Panel
		setSize(xSize, ySize);
		setLocationRelativeTo(null);
		
		// Generate Pane
		contentPane = new JPanel();
		contentPane.setBackground(Colors.GHOST_WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
	}
	
	
	/**
	 * Adds the component configuration to the jpanel
	 *  
	 * @param jPanel
	 * @param componentConfiguration	
	 */
	protected void addComponent(JPanel jPanel, ComponentConfiguration componentConfiguration) {
		jPanel.add(componentConfiguration.getComponent(), componentConfiguration.getGridBagConstraints());
	}


	/**
	 * Initialize components on a desired JPanel
	 * @param jPanel
	 */
	protected abstract void initializeComponents(JPanel jPanel);
	
	
	//
	// Getters & Setters
	//
	
	
	public FrameStatus getFrameStatus() {
		return status;
	}
	

	public void setFrameStatus(FrameStatus status) {
		this.status = status;
	}
}
