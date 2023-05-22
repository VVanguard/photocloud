package util.customframes;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import util.Colors;
import util.ComponentConfiguration;


/**
 * Panel Factory class that is the base of all JPanels in this project
 * @author bkaym
 *
 */
public abstract class PanelFactory extends JPanel implements Displayable {
	
	public PanelFactory(int x, int y) {
		
		// Set Size
		setSize(x, y);
		
		// Pane Configurations
		setBackground(Colors.GHOST_WHITE);
		setBorder(new EmptyBorder(0, 0, 0, 0));
	}
	
	
	/**
	 * Adds the component configuration to the content pane
	 *  
	 * @param jPanel
	 * @param componentConfiguration	
	 */
	public void addComponent(JPanel jPanel, ComponentConfiguration<?> componentConfiguration) {
		jPanel.add(componentConfiguration.getComponent(), componentConfiguration.getGridBagConstraints());
	}
		
	/**
	 * Initialize components on a desired JPanel
	 * 
	 * @param jPanel
	 */
	public abstract void initializeComponents(JPanel jPanel);
}
