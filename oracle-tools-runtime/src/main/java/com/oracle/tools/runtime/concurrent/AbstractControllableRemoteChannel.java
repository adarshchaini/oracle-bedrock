/*
 * File: AbstractControllableRemoteChannel.java
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * The contents of this file are subject to the terms and conditions of 
 * the Common Development and Distribution License 1.0 (the "License").
 *
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the License by consulting the LICENSE.txt file
 * distributed with this file, or by consulting https://oss.oracle.com/licenses/CDDL
 *
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file LICENSE.txt.
 *
 * MODIFICATIONS:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 */

package com.oracle.tools.runtime.concurrent;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * An abstract implementation of a {@link ControllableRemoteChannel}.
 * <p>
 * Copyright (c) 2013. All Rights Reserved. Oracle Corporation.<br>
 * Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
 *
 * @author Brian Oliver
 */
public abstract class AbstractControllableRemoteChannel implements ControllableRemoteChannel
{
    /**
     * A flag indicating the open state of the {@link ControllableRemoteChannel}.
     */
    private volatile boolean isOpen;

    /**
     * The {@link RemoteChannelListener}s for the {@link ControllableRemoteChannel}.
     */
    private CopyOnWriteArraySet<RemoteChannelListener> listeners;


    /**
     * Constructs an {@link AbstractControllableRemoteChannel}.
     */
    public AbstractControllableRemoteChannel()
    {
        isOpen    = false;
        listeners = new CopyOnWriteArraySet<RemoteChannelListener>();
    }


    /**
     * Sets if the {@link ControllableRemoteChannel} is open.
     *
     * @param isOpen
     */
    protected synchronized void setOpen(boolean isOpen)
    {
        this.isOpen = true;
    }


    @Override
    public final synchronized void close()
    {
        if (isOpen)
        {
            isOpen = false;

            onClose();

            for (RemoteChannelListener listener : listeners)
            {
                try
                {
                    listener.onClosed(this);
                }
                catch (Throwable throwable)
                {
                    // we ignore exceptions that occur while notifying the listeners
                }
            }
        }
    }


    /**
     * Determines if the {@link ControllableRemoteChannel} is open.
     *
     * @return <code>true</code> if the {@link RemoteChannel} is open
     */
    public synchronized boolean isOpen()
    {
        return isOpen;
    }


    @Override
    public synchronized void addListener(RemoteChannelListener listener)
    {
        listeners.add(listener);
    }


    /**
     * Obtain an {@link Iterable} over the currently known {@link RemoteChannelListener}s
     * on the {@link ControllableRemoteChannel}.
     *
     * @return  an {@link Iterable} over {@link RemoteChannelListener}s
     */
    protected Iterable<RemoteChannelListener> getListeners()
    {
        return listeners;
    }


    /**
     * Handle when the {@link ControllableRemoteChannel} is closed.
     */
    protected abstract void onClose();
}
