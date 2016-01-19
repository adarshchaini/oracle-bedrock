/*
 * File: JavaApplicationSchema.java
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

import com.oracle.tools.Options;

import com.oracle.tools.runtime.Application;
import com.oracle.tools.runtime.ApplicationConsole;
import com.oracle.tools.runtime.ApplicationSchema;
import com.oracle.tools.runtime.Platform;
import com.oracle.tools.runtime.PropertiesBuilder;
import com.oracle.tools.runtime.java.options.SystemProperties;

import java.util.Properties;

/**
 * An {@link ApplicationSchema} specifically for Java-based {@link Application}s.
 * <p>
 * Copyright (c) 2011. All Rights Reserved. Oracle Corporation.<br>
 * Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
 *
 * @author Brian Oliver
 *
 * @param <A>  the type of {@link JavaApplication} that can be configured by the {@link JavaApplicationSchema}
 */
public interface JavaApplicationSchema<A extends JavaApplication> extends ApplicationSchema<A>
{
    /**
     * Obtains the {@link SystemProperties} that will be used as a basis for configuring the Java System Properties
     * of the realized {@link JavaApplication}s from an {@link JavaApplicationBuilder}.
     *
     * @return {@link PropertiesBuilder}
     */
    public SystemProperties getSystemProperties();


    /**
     * Obtains the {@link ClassPath} to be used for the {@link JavaApplication}.
     *
     * @return {@link ClassPath}
     */
    public ClassPath getClassPath();


    /**
     * Obtain the fully-qualified class name of the class containing the main method
     * for the desired application.
     *
     * @return  the fully-qualified class name of the "main" class
     */
    public String getApplicationClassName();


    /**
     * Obtain if IPv4 is preferred for the {@link JavaApplication}.
     *
     * @return  <code>true</code> if IPv4 is preferred or <code>false</code> if not
     */
    public boolean isIPv4Preferred();


    /**
     * Instantiates a suitable {@link JavaApplication} to control the underlying
     * {@link JavaApplicationProcess}.  It's through this {@link JavaApplication}
     * that developers will interact with the underlying Java {@link Process}.
     *
     * @param process               the {@link JavaApplicationProcess} representing the {@link JavaApplication}
     * @param name                  the name of the {@link JavaApplication}
     * @param platform              the {@link Platform} that this {@link JavaApplication} is running on
     * @param options               the {@link Options} for the {@link JavaApplication}
     * @param console               the {@link ApplicationConsole} that will be used for I/O by the
     *                              realized {@link Application}. This may be <code>null</code> if not required
     * @param environmentVariables  the environment variables used when starting the {@link JavaApplication}
     * @param systemProperties      the system properties provided to the {@link JavaApplication}
     * @param remoteDebuggingPort   the port this process is listening on for remote debugger connections if
     *                              enabled, or <= 0 if disabled
     *
     * @return a {@link JavaApplication}
     */
    public A createJavaApplication(JavaApplicationProcess process,
                                   String                 name,
                                   Platform               platform,
                                   Options                options,
                                   ApplicationConsole     console,
                                   Properties             environmentVariables,
                                   Properties             systemProperties,
                                   int                    remoteDebuggingPort);
}
