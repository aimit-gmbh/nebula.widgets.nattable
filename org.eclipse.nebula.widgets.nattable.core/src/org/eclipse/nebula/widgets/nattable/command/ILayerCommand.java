/*******************************************************************************
 * Copyright (c) 2012 Original authors and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Original authors and others - initial API and implementation
 ******************************************************************************/
package org.eclipse.nebula.widgets.nattable.command;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;

/**
 * Commands are fired by NatTable in response to user actions.
 * Commands flow down the layer stack until they are processed by an
 * {@link ILayer} or an associated {@link ILayerCommandHandler}. 
 * Commands can be fired from code by invoking {@link NatTable#doCommand(ILayerCommand)} 
 */
public interface ILayerCommand {
	
	/**
	 * Convert the row/column coordinates the command might be carrying from the source layer
	 * to the destination (target) layer.<br/> 
	 * 
	 * @return true if the command is valid after conversion, false if the command is no longer valid.
	 * 	Note: most commands are not processed if they fail conversion.  
	 */
	public boolean convertToTargetLayer(ILayer targetLayer);

	/**
	 * Same semantics as {@link Object#clone()}
	 * Used to make a copies of the command if has to passed to different layer stacks.
	 * 
	 *  @see GridLayer#doCommand(ILayerCommand)
	 */
	public ILayerCommand cloneCommand();
	
}