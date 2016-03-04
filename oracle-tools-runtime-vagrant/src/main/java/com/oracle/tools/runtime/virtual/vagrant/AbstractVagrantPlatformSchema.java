/*
 * File: AbstractVagrantPlatformSchema.java
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

package com.oracle.tools.runtime.virtual.vagrant;

import com.oracle.tools.Option;

import com.oracle.tools.options.Timeout;
import com.oracle.tools.runtime.virtual.CloseAction;
import com.oracle.tools.runtime.virtual.VirtualPlatformSchema;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * A base {@link com.oracle.tools.runtime.PlatformSchema} to represent the definition
 * of a Vagrant platform.
 * <p>
 * Copyright (c) 2014. All Rights Reserved. Oracle Corporation.<br>
 * Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
 *
 * @author Jonathan Knight
 */
public abstract class AbstractVagrantPlatformSchema<S extends AbstractVagrantPlatformSchema<S>>
    extends VirtualPlatformSchema<VagrantPlatform, S>
{
    /** The Vagrant working directory; the directory containing the vagrantFile */
    private final File workingDirectory;

    /** The port to use on the VM for SSH connections */
    private int remotePort = 22;

    /**
     * Construct a new AbstractVagrantPlatformSchema.
     *
     * @param name              the name of the Vagrant VM
     * @param isSingleton       flag indicating whether this schema can
     *                          be used to realize multiple VMs
     * @param workingDirectory  the directory to contain the vagrantFile
     */
    protected AbstractVagrantPlatformSchema(String  name,
                                            boolean isSingleton,
                                            File    workingDirectory)
    {
        super(name, isSingleton);
        this.workingDirectory = workingDirectory != null ? workingDirectory : new File(".");

        addOption(CloseAction.Destroy);
        addOption(Timeout.after(5, TimeUnit.MINUTES));
    }


    /**
     * Set the remote port to use to SSH into the VM.
     *
     * @param remotePort  the remote port to use to SSH into the VM
     */
    @SuppressWarnings("unchecked")
    public S setRemotePort(int remotePort)
    {
        this.remotePort = remotePort;

        return (S) this;
    }

    /**
     * Obtain the remote port to use to SSH into the VM.
     *
     * @return the remote port to use to SSH into the VM
     */
    public int getRemotePort()
    {
        return remotePort;
    }

    /**
     * Ensure that the directory that will contain the
     * vagrantFile configuration file exists.
     *
     * @return the directory that will contain the vagrantFile
     */
    protected File ensureWorkingDirectory(String name)
    {
        File dir = new File(workingDirectory, name);

        if (!dir.exists())
        {
            if (!dir.mkdirs())
            {
                throw new RuntimeException("Could not create working directory: " + workingDirectory);
            }
        }

        return dir;
    }


    /**
     * Write the vagrantFile configuration using the Vagrant VM
     * definition in this schema to the working directory and instantiate
     * a {@link VagrantPlatform} based on the configuration.
     *
     * @param name     the name to assign to the {@link VagrantPlatform}
     * @param options  the {@link Option}s to use
     */
    public VagrantPlatform realize(String name, Option... options)
    {
        File workingDir = ensureWorkingDirectory(name);

        try
        {
            File vagrantFile = new File(workingDir, "Vagrantfile");

            return realize(name, vagrantFile, options);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error writing vagrantFile", e);
        }
    }


    /**
     * Write the vagrantFile configuration using the Vagrant VM
     * definition in this schema to the working directory and instantiate
     * a {@link VagrantPlatform} based on the configuration.
     *
     * @param name         the name to assign to the {@link VagrantPlatform}
     * @param vagrantFile  the {@link java.io.File} to write the Vagrant configuration to
     * @param options      the {@link Option}s to use
     */
    protected abstract VagrantPlatform realize(String    name,
                                               File      vagrantFile,
                                               Option... options) throws IOException;


    /**
     * Instantiate a new {@link VagrantPlatform} instance.
     *
     * @param name            the name for the {@link VagrantPlatform}
     * @param workingDir      the working directory for the {@link VagrantPlatform}
     * @param publicHostName  the public hostname for the {@link VagrantPlatform}
     * @param port            the port to use to connect to the {@link VagrantPlatform}
     * @param options         the {@link Option}s for the {@link VagrantPlatform}
     *
     * @return a new {@link VagrantPlatform}
     */
    protected VagrantPlatform instantiatePlatform(String    name,
                                                  File      workingDir,
                                                  String    publicHostName,
                                                  int       port,
                                                  Option... options)
    {
        return new VagrantPlatform(name, workingDir, publicHostName, port, options);
    }
}
