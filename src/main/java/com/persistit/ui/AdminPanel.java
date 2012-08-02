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

package com.persistit.ui;

import java.rmi.RemoteException;
import java.util.Map;

import javax.swing.JPanel;

public abstract class AdminPanel extends JPanel {

    protected abstract void setup(AdminUI ui) throws NoSuchMethodException, RemoteException;

    protected abstract void refresh(boolean reset) throws RemoteException;

    protected abstract Map getMenuMap();

    protected abstract void setDefaultButton();

    protected void setIsShowing(boolean isShowing) {
    }
}
