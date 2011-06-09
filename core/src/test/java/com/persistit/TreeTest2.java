/**
 * Copyright (C) 2011 Akiban Technologies Inc.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses.
 */

package com.persistit;

import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import com.persistit.unit.PersistitUnitTestCase;

public class TreeTest2 extends PersistitUnitTestCase {

    final static int COUNT = 10000;
    
    @Test
    public void testLotsOfTrees() throws Exception {
        Volume volume = _persistit.getVolume("persistit");
        Transaction txn = _persistit.getTransaction();
        assertEquals(1, volume.directoryExchange().getTree().getRootPageAddr());
        for (int counter = 0; counter < COUNT; counter++) {
            txn.begin();
            final Exchange exchange = _persistit.getExchange("persistit",
                    "TreeTest1_" + counter, true);
            for (int k = 0; k < 10; k++) {
                exchange.to(k).store();
            }
            txn.commit();
            txn.end();
        }
        assertTrue( 1 != volume.directoryExchange().getTree().getRootPageAddr());

        for (int counter = 0; counter < COUNT; counter++) {
            txn.begin();
            final Exchange exchange = _persistit.getExchange("persistit",
                    "TreeTest1_" + counter, false);
            exchange.removeTree();
            txn.commit();
            txn.end();
        }
        
        Properties properties = _persistit.getProperties();
        _persistit.getJournalManager().flush();
        _persistit.crash();
        _persistit = new Persistit();
        _persistit.initialize(properties);
        
        volume = _persistit.getVolume("persistit");
        assertTrue( 1 != volume.directoryExchange().getTree().getRootPageAddr());
        
        String[] treeNames = volume.getTreeNames();
        for (final String t : treeNames) {
            assertTrue(!t.startsWith("TreeTest1"));
        }
    }

    public static void pause(final String prompt) {
        System.out.print(prompt + "  Press ENTER to continue");
        System.out.flush();
        try {
            while (System.in.read() != '\r') {
            }
        } catch (final IOException ioe) {
        }
        System.out.println();
    }

    public static void main(final String[] args) throws Exception {
        new TreeTest2().initAndRunTest();
    }

    public void runAllTests() throws Exception {
        testLotsOfTrees();
    }

}