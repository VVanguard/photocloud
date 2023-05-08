package util.customfields;

import java.awt.Component;
import java.awt.GridBagConstraints;

/**
 * Custom configuration class that stores the component and its gridBagConstraints
 * @author bkaym
 *
 */
public class ComponentConfiguration {

	private Component component;
	private GridBagConstraints gridBagConstraints;
	
	
	/**
	 * Constructor that pairs component and gridBagContraints
	 * @param component
	 * @param gridBagConstraints
	 */
	public ComponentConfiguration(Component component, GridBagConstraints gridBagConstraints) {
		this.component = component;
		this.gridBagConstraints = gridBagConstraints;
	}
	
	
	/**
	 * @return component
	 */
	public Component getComponent() {
		return component;
	}
	
	
	/**
	 * @return gridBagConstraints
	 */
	public GridBagConstraints getGridBagConstraints() {
		return gridBagConstraints;
	}
	
}
