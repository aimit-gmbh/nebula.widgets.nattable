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
package org.eclipse.nebula.widgets.nattable.test.integration;

import static org.junit.Assert.assertEquals;

import org.eclipse.nebula.widgets.nattable.columnRename.RenameColumnHeaderCommand;
import org.eclipse.nebula.widgets.nattable.columnRename.event.RenameColumnHeaderEvent;
import org.eclipse.nebula.widgets.nattable.coordinate.Range;
import org.eclipse.nebula.widgets.nattable.layer.stack.DummyGridLayerStack;
import org.eclipse.nebula.widgets.nattable.reorder.command.ColumnReorderCommand;
import org.eclipse.nebula.widgets.nattable.test.fixture.NatTableFixture;
import org.eclipse.nebula.widgets.nattable.test.fixture.layer.LayerListenerFixture;
import org.junit.Before;
import org.junit.Test;

public class RenameColumnIntegrationTest {

    private static final String TEST_COLUMN_NAME = "Test column name";

    DummyGridLayerStack grid = new DummyGridLayerStack();
    NatTableFixture natTableFixture;
    LayerListenerFixture listener;

    @Before
    public void setup() {
        this.natTableFixture = new NatTableFixture(this.grid);
        this.listener = new LayerListenerFixture();
        this.natTableFixture.addLayerListener(this.listener);
    }

    @Test
    public void shouldRenameColumnHeader() {
        String originalColumnHeader = this.natTableFixture.getDataValueByPosition(2, 0).toString();
        assertEquals("Column 2", originalColumnHeader);

        this.natTableFixture.doCommand(
                new RenameColumnHeaderCommand(
                        this.natTableFixture,
                        2,
                        TEST_COLUMN_NAME));
        String renamedColumnHeader = this.natTableFixture.getDataValueByPosition(2, 0).toString();
        assertEquals(TEST_COLUMN_NAME, renamedColumnHeader);

        assertEquals(1, this.listener.getEventsCount());
        RenameColumnHeaderEvent event = (RenameColumnHeaderEvent) this.listener.getReceivedEvent(RenameColumnHeaderEvent.class);
        assertEquals(new Range(2, 3), event.getColumnPositionRanges().iterator().next());
    }

    @Test
    public void shouldRenameColumnHeaderForReorderedColumn() {
        String originalColumnHeader = this.natTableFixture.getDataValueByPosition(2, 0).toString();
        assertEquals("Column 2", originalColumnHeader);

        this.natTableFixture.doCommand(new ColumnReorderCommand(this.natTableFixture, 1, 5));

        originalColumnHeader = this.natTableFixture.getDataValueByPosition(2, 0).toString();
        assertEquals("Column 3", originalColumnHeader);

        this.natTableFixture.doCommand(
                new RenameColumnHeaderCommand(
                        this.natTableFixture,
                        2,
                        TEST_COLUMN_NAME));
        String renamedColumnHeader = this.natTableFixture.getDataValueByPosition(2, 0).toString();
        assertEquals(TEST_COLUMN_NAME, renamedColumnHeader);

        assertEquals(2, this.listener.getEventsCount());
        RenameColumnHeaderEvent event = (RenameColumnHeaderEvent) this.listener.getReceivedEvent(RenameColumnHeaderEvent.class);
        assertEquals(new Range(2, 3), event.getColumnPositionRanges().iterator().next());
    }

    @Test
    public void shouldRenameColumnHeaderForReorderedColumnProgrammatically() {
        String originalColumnHeader = this.natTableFixture.getDataValueByPosition(2, 0).toString();
        assertEquals("Column 2", originalColumnHeader);

        this.natTableFixture.doCommand(new ColumnReorderCommand(this.natTableFixture, 1, 5));

        originalColumnHeader = this.natTableFixture.getDataValueByPosition(2, 0).toString();
        assertEquals("Column 3", originalColumnHeader);

        this.grid.getColumnHeaderLayer().renameColumnIndex(2, TEST_COLUMN_NAME);
        String renamedColumnHeader = this.natTableFixture.getDataValueByPosition(2, 0).toString();
        assertEquals(TEST_COLUMN_NAME, renamedColumnHeader);

        assertEquals(2, this.listener.getEventsCount());
        RenameColumnHeaderEvent event = (RenameColumnHeaderEvent) this.listener.getReceivedEvent(RenameColumnHeaderEvent.class);
        assertEquals(new Range(2, 3), event.getColumnPositionRanges().iterator().next());
    }
}
