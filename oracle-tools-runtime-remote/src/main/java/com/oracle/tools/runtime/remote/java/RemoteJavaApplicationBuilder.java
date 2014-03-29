/*
 * File: RemoteJavaApplicationBuilder.java
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

package com.oracle.tools.runtime.remote.java;

import com.oracle.tools.runtime.ApplicationConsole;

import com.oracle.tools.runtime.concurrent.ControllableRemoteExecutor;
import com.oracle.tools.runtime.concurrent.RemoteExecutor;

import com.oracle.tools.runtime.java.ClassPath;
import com.oracle.tools.runtime.java.JavaApplication;
import com.oracle.tools.runtime.java.JavaApplicationBuilder;
import com.oracle.tools.runtime.java.JavaApplicationSchema;
import com.oracle.tools.runtime.java.JavaProcess;

import com.oracle.tools.runtime.remote.AbstractRemoteApplicationBuilder;
import com.oracle.tools.runtime.remote.Authentication;
import com.oracle.tools.runtime.remote.RemoteApplicationProcess;

import com.oracle.tools.util.CompletionListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.HashSet;

import java.util.concurrent.Callable;

/**
 * A {@link JavaApplicationBuilder} that realizes {@link JavaApplication}s on
 * a remote server.
 * <p>
 * Copyright (c) 2014. All Rights Reserved. Oracle Corporation.<br>
 * Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
 *
 * @author Brian Oliver
 */
public class RemoteJavaApplicationBuilder<A extends JavaApplication<A>, S extends JavaApplicationSchema<A, S>>
    extends AbstractRemoteApplicationBuilder<A, S, RemoteJavaApplicationEnvironment<A, S>,
                                             RemoteJavaApplicationBuilder<A, S>> implements JavaApplicationBuilder<A, S>
{
    /**
     * Are remote {@link JavaApplication}s produced by this builder allowed
     * to become orphans (when their parent application process is destroyed/killed)?
     * <p>
     * The default is <code>false</code>.
     */
    private boolean areOrphansPermitted;

    /**
     * Should the builder deploy the {@link JavaApplication} artifacts?
     * (including the specified {@link ClassPath} to the remote server)
     * <p>
     * The default is <code>true</code>
     */
    private boolean isAutoDeployEnabled;

    /**
     * The {@link HashSet} of filenames that must not be deployed
     * (if auto-deploy is enabled).
     */
    private HashSet<String> doNotDeployFileNames;


    /**
     * Constructs an {@link RemoteJavaApplicationBuilder} (using the default port).
     *
     * @param hostName       the remote host name
     * @param userName       the user name on the remote host
     * @param authentication the {@link Authentication} for connecting to the host
     */
    public RemoteJavaApplicationBuilder(String         hostName,
                                        String         userName,
                                        Authentication authentication)
    {
        this(hostName, DEFAULT_PORT, userName, authentication);
    }


    /**
     * Constructs an {@link RemoteJavaApplicationBuilder}.
     *
     * @param hostName       the remote host name
     * @param port           the remote port
     * @param userName       the user name on the remote host
     * @param authentication the {@link Authentication} for connecting to the host
     */
    public RemoteJavaApplicationBuilder(String         hostName,
                                        int            port,
                                        String         userName,
                                        Authentication authentication)
    {
        super(hostName, port, userName, authentication);

        // by default orphan remote processes are not permitted
        areOrphansPermitted = false;

        // by default the builder deploys the application classpath artifacts
        isAutoDeployEnabled = true;

        // set the default files not to deploy
        doNotDeployFileNames = new HashSet<String>();

        doNotDeployFileNames.add("classes.jar");
        doNotDeployFileNames.add("charsets.jar");
        doNotDeployFileNames.add("jsse.jar");
        doNotDeployFileNames.add("jce.jar");
        doNotDeployFileNames.add("javaws.jar");
        doNotDeployFileNames.add("sunjce_provider.jar");
        doNotDeployFileNames.add("jconsole.jar");
        doNotDeployFileNames.add("plugin.jar");
        doNotDeployFileNames.add("sunpkcs11.jar");
    }


    /**
     * Sets if {@link JavaApplication}s produced by this {@link JavaApplicationBuilder}
     * can be orphaned (left running without their parent running).  The default
     * is <code>false</code>.
     *
     * @param areOrphansPermitted  <code>true</code> to allow for orphaned applications
     *
     * @return  the {@link RemoteJavaApplicationBuilder} to allow fluent-method calls
     */
    public RemoteJavaApplicationBuilder<A, S> setOrphansPermitted(boolean areOrphansPermitted)
    {
        this.areOrphansPermitted = areOrphansPermitted;

        return this;
    }


    /**
     * Determines if {@link JavaApplication}s produced by this {@link JavaApplicationBuilder}
     * are allowed to be orphaned (to keep running if their parent is not running).
     *
     * @return  <code>true</code> if applications can be orphaned, <code>false</code> otherwise
     */
    public boolean areOrphansPermitted()
    {
        return areOrphansPermitted;
    }


    /**
     * Sets if {@link JavaApplication}s produced by this {@link RemoteJavaApplicationBuilder}
     * are automatically deployed onto the remote server prior to starting.
     *
     * @param isAutoDeployEnabled  <code>true</code> to have the {@link RemoteJavaApplicationBuilder}
     *                             deploy the {@link JavaApplication}
     *
     * @return  the {@link RemoteJavaApplicationBuilder} to allow fluent-method calls
     */
    public RemoteJavaApplicationBuilder<A, S> setAutoDeployEnabled(boolean isAutoDeployEnabled)
    {
        this.isAutoDeployEnabled = isAutoDeployEnabled;

        return this;
    }


    /**
     * Determines if the {@link RemoteJavaApplicationBuilder} will automatically deploy
     * an {@link JavaApplication} on the remote server prior to executing it as part of
     * the realization process.
     *
     * @return  <code>true</code> if {@link JavaApplication}s will be deployed
     */
    public boolean isAutoDeployEnabled()
    {
        return isAutoDeployEnabled;
    }


    /**
     * Adds a file to the set of files that should not be deployed (when autodeploy is enabled)
     *
     * @param fileName  the filename not to deploy
     *
     * @return  the {@link RemoteJavaApplicationBuilder} to allow fluent-method calls
     */
    public RemoteJavaApplicationBuilder<A, S> addDoNotDeployFileName(String fileName)
    {
        doNotDeployFileNames.add(fileName);

        return this;
    }


    /**
     * Obtains an {@link Iterable} over the filenames that must not be deployed.
     *
     * @return  the files not to be deployed
     */
    public Iterable<String> getDoNoDeployFileNames()
    {
        return doNotDeployFileNames;
    }


    @Override
    protected RemoteJavaApplicationEnvironment<A, S> getRemoteApplicationEnvironment(S schema) throws IOException
    {
        return new RemoteJavaApplicationEnvironment(schema,
                                                    remoteFileSeparatorChar,
                                                    remotePathSeparatorChar,
                                                    areOrphansPermitted,
                                                    isAutoDeployEnabled,
                                                    doNotDeployFileNames);
    }


    @Override
    protected A createApplication(S                                      schema,
                                  RemoteJavaApplicationEnvironment<A, S> environment,
                                  String                                 applicationName,
                                  RemoteApplicationProcess               process,
                                  ApplicationConsole                     console)
    {
        // create a JavaProcess representation of the RemoteApplicationProcess
        RemoteJavaProcess remoteJavaProcess = new RemoteJavaProcess(process, environment.getRemoteExecutor());

        return schema.createJavaApplication(remoteJavaProcess,
                                            applicationName,
                                            console,
                                            environment.getRemoteEnvironmentVariables(),
                                            environment.getRemoteSystemProperties());
    }


    /**
     * A {@link RemoteJavaProcess} is an adapter for a {@link RemoteApplicationProcess},
     * specifically for Java-based applications.
     */
    public static class RemoteJavaProcess implements JavaProcess
    {
        /**
         * The {@link RemoteApplicationProcess} being adapted.
         */
        private RemoteApplicationProcess process;

        /**
         * The {@link RemoteExecutor} for the {@link RemoteJavaProcess}.
         */
        private ControllableRemoteExecutor remoteExecutor;


        /**
         * Constructs a {@link RemoteJavaProcess}.
         *
         * @param process         the underlying {@link RemoteApplicationProcess}
         * @param remoteExecutor  the {@link RemoteExecutor} for executing remote requests
         */
        public RemoteJavaProcess(RemoteApplicationProcess   process,
                                 ControllableRemoteExecutor remoteExecutor)
        {
            this.process        = process;
            this.remoteExecutor = remoteExecutor;
        }


        @Override
        public long getId()
        {
            return process.getId();
        }


        @Override
        public void destroy()
        {
            process.destroy();
        }


        @Override
        public void close()
        {
            process.close();
            remoteExecutor.close();
        }


        @Override
        public int exitValue()
        {
            return process.exitValue();
        }


        @Override
        public InputStream getErrorStream()
        {
            return process.getErrorStream();
        }


        @Override
        public InputStream getInputStream()
        {
            return process.getInputStream();
        }


        @Override
        public OutputStream getOutputStream()
        {
            return process.getOutputStream();
        }


        @Override
        public int waitFor() throws InterruptedException
        {
            return process.waitFor();
        }


        @Override
        public <T> void submit(Callable<T>           callable,
                               CompletionListener<T> listener) throws IllegalStateException
        {
            remoteExecutor.submit(callable, listener);
        }


        @Override
        public void submit(Runnable runnable) throws IllegalStateException
        {
            remoteExecutor.submit(runnable);
        }
    }
}
