/*
 * File: JavaApplicationProcess.java
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

package com.oracle.tools.runtime.java;

import com.oracle.tools.runtime.ApplicationProcess;

import com.oracle.tools.runtime.concurrent.RemoteChannel;

import java.util.Properties;

/**
 * A {@link ApplicationProcess} specifically for managing Java-based
 * Applications at runtime.
 * <p>
 * Copyright (c) 2013. All Rights Reserved. Oracle Corporation.<br>
 * Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
 *
 * @author Brian Oliver
 */
public interface JavaApplicationProcess extends ApplicationProcess, RemoteChannel
{
    /**
     * Obtains the resolved System {@link Properties} provided to the {@link JavaApplicationProcess}
     * when it was launched.
     *
     * @return  the resolved System {@link Properties}
     */
    Properties getSystemProperties();
}
