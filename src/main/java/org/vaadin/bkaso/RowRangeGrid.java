package org.vaadin.bkaso;

import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.grid.Grid;


/**
 * 
 * @author bharatk
 *
 * Its extended to register key press listener
 * @param <T>
 */

public class RowRangeGrid<T> extends Grid<T> implements KeyNotifier{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RowRangeGrid(Class<T> beanType) {
		super(beanType, true);
	}
	
	
}
