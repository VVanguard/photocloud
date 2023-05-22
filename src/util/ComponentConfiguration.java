package util;

import java.awt.Component;
import java.awt.GridBagConstraints;


/**
 * Custom configuration pairing with the component and its constraints
 * @author bkaym
 *
 * @param <T1>	component extending Component
 * @param <T2>	constraint extending Constraints
 */
public class ComponentConfiguration<T extends Component> {

	private T component;
	private GridBagConstraints gridBagConstraints;
	
	
	/**
	 * Constructor that pairs component and constraint
	 * 
	 * @param component
	 * @param constraints
	 */
	public ComponentConfiguration(T component, GridBagConstraints gridBagConstraints) {
		this.component = component;
		this.gridBagConstraints = gridBagConstraints;
	}
	
	
	/**
	 * @return component
	 */
	public T getComponent() {
		return component;
	}
	
	
	/**
	 * @return gridBagConstraints
	 */
	public GridBagConstraints getGridBagConstraints() {
		return gridBagConstraints;
	}
	
}
