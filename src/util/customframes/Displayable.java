package util.customframes;

import javax.swing.JPanel;

import util.ComponentConfiguration;

/**
 * Displayable interface that is implemented by GUI Classes that have sisplayable content
 * @author bkaym
 *
 */
public interface Displayable {
	
	
	/**
	 * Initializes components on the pane
	 * 
	 * @param jPanel		content pane
	 */
	public void initializeComponents(JPanel jPanel);
	
	
	/**
	 * Adds components to the pane
	 * 
	 * @param jPanel					content pane
	 * @param componentConfiguration	configuration of the component
	 */
	public void addComponent(JPanel jPanel, ComponentConfiguration componentConfiguration);
}
