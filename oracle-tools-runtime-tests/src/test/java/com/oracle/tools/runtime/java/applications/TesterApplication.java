/*
 * File: TesterApplication.java
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

package com.oracle.tools.runtime.java.applications;

import com.oracle.tools.runtime.java.SimpleTester;
import com.oracle.tools.runtime.java.Tester;

import java.io.IOException;

import java.net.UnknownHostException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * A simple application defines a {@link Tester} instance that can be
 * interacted with remotely.
 * <p>
 * Copyright (c) 2014. All Rights Reserved. Oracle Corporation.<br>
 * Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
 *
 * @author Brian Oliver
 */
public class TesterApplication
{
    /**
     * The {@link Tester} for the application.
     */
    private static Tester tester;


    /**
     * Obtains the {@link Tester} for the application.
     *
     * @return  the {@link Tester}
     */
    public static Tester getTester()
    {
        return tester;
    }


    /**
     * Entry Point of the Application.
     *
     * @param arguments
     */
    public static void main(String[] arguments) throws UnknownHostException, IOException, InterruptedException
    {
        System.out.printf("%s started\n", TesterApplication.class.getName());

        System.out.printf("Using java.home: %s\n", System.getProperty("java.home"));

        // determine the number of seconds to sleep
        int secondsToSleep = 5;

        if (arguments.length == 1)
        {
            try
            {
                secondsToSleep = Integer.parseInt(arguments[0]);
            }
            catch (NumberFormatException e)
            {
                System.out.println("Argument [" + arguments[0]
                                   + "] is not a number representing seconds, defaulting to 5 seconds");
                secondsToSleep = 5;
            }
        }

        System.out.println("Now sleeping for " + secondsToSleep + " seconds");

        Thread.sleep(TimeUnit.SECONDS.toMillis(secondsToSleep));

        System.out.println("Finished sleeping... now terminating");
    }


    static
    {
        tester = new SimpleTester();
    }
}
