/*
 * File: ThreadFactories.java
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

package com.oracle.bedrock.lang;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Helper methods to produce {@link ThreadFactory}s.
 * <p>
 * Copyright (c) 2015. All Rights Reserved. Oracle Corporation.<br>
 * Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
 *
 * @author Brian Oliver
 */
public class ThreadFactories
{
    /**
     * A {@link ThreadFactory} enabling choice of {@link Thread} types.
     *
     * @param isDaemonRequired  <code>true</code> when daemon {@link Thread}s are
     *                          required, <code>false</code> otherwise
     * @return an appropriate {@link ThreadFactory}
     */
    public static ThreadFactory usingDaemonThreads(final boolean isDaemonRequired)
    {
        return new ThreadFactory()
        {
            public Thread newThread(Runnable r)
            {
                Thread t = Executors.defaultThreadFactory().newThread(r);

                t.setDaemon(isDaemonRequired);

                return t;
            }
        };
    }
}
