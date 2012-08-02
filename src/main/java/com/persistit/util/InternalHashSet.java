/**
 * Copyright © 2005-2012 Akiban Technologies, Inc.  All rights reserved.
 * 
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * This program may also be available under different license terms.
 * For more information, see www.akiban.com or contact licensing@akiban.com.
 * 
 * Contributors:
 * Akiban Technologies, Inc.
 */

package com.persistit.util;

/**
 * A minimal HashSet-like collection that allows lookup by just hash code. This
 * lets us find candidate objects in the collection without actually creating a
 * new instance of the object. All objects in this collection must extend the
 * inner class InternalHashSet.Entry.
 * 
 * @author pbeaman
 * @version 1.0
 */
public class InternalHashSet {
    //
    // Must always be power of 2
    //
    private final static int INITIAL_SIZE = 64; // TODO
    //
    // If size is greater than this on clear operation, then reduce to
    // this size.
    //
    private final static int TRIM_SIZE = 64; // TODO
    //
    // Must always be _headEntries.length - 1;
    //
    private int _mask = INITIAL_SIZE - 1;
    //
    // Count of entries
    //
    private int _count;
    //
    // Length must be power of two
    //
    private Entry[] _entries = new Entry[INITIAL_SIZE];

    public abstract static class Entry {
        private Entry _next;
        private int _hashCode = -1;

        public Entry getNext() {
            return _next;
        }

        private int getHashCode() {
            if (_hashCode == -1)
                _hashCode = hashCode() & 0x7FFFFFFF;
            return _hashCode;
        }

        @Override
        public abstract int hashCode();
    }

    public Entry lookup(int hashCode) {
        return _entries[hashCode & _mask];
    }

    public void put(Entry newEntry) {
        int index = newEntry.getHashCode() & _mask;
        Entry entry = _entries[index];
        newEntry._next = entry;
        _entries[index] = newEntry;
        _count++;
        if (_count > _entries.length / 2) {
            grow();
        }
    }

    void grow() {
        Entry[] temp = new Entry[_entries.length * 2];
        _mask = temp.length - 1;
        for (int index = 0; index < _entries.length; index++) {
            Entry entry = _entries[index];
            while (entry != null) {
                int newIndex = entry.getHashCode() & _mask;
                Entry newEntry = entry;

                entry = entry._next;
                newEntry._next = temp[newIndex];
                temp[newIndex] = newEntry;
            }
        }
        _entries = temp;
    }

    public Entry next(Entry entry) {
        if (entry != null && entry._next != null) {
            return entry._next;
        } else {
            int index = -1;
            if (entry != null) {
                index = (entry.getHashCode() & 0x7FFFFFFF) % _entries.length;
            }
            while (++index < _entries.length) {
                if (_entries[index] != null)
                    return _entries[index];
            }
            return null;
        }
    }

    public int size() {
        return _count;
    }

    public void clear() {
        if (_count == 0) {
            return;
        }
        if (_entries.length > TRIM_SIZE) {
            _entries = new Entry[TRIM_SIZE];
        } else {
            for (int index = 0; index < _entries.length; index++) {
                _entries[index] = null;
            }
        }
        _count = 0;
        _mask = _entries.length - 1;
    }
}
