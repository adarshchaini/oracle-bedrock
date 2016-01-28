/*
 * File: SessionBuilder.java
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

package com.oracle.tools.junit;

import com.oracle.tools.runtime.LocalPlatform;
import com.oracle.tools.runtime.Platform;

import com.oracle.tools.runtime.coherence.CoherenceCacheServerSchema;

import com.tangosol.net.ConfigurableCacheFactory;

import net.sf.cglib.core.Local;

/**
 * A mechanism to build local Coherence Session (represented as a {@link ConfigurableCacheFactory})
 * for a {@link CoherenceClusterOrchestration}.
 * <p>
 * Copyright (c) 2015. All Rights Reserved. Oracle Corporation.<br>
 * Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
 *
 * @author Brian Oliver
 */
public interface SessionBuilder
{
    /**
     * Creates a {@link ConfigurableCacheFactory} based on a {@link CoherenceCacheServerSchema}.
     *
     * @param platform       the {@link Local} on which the {@link ConfigurableCacheFactory} will be established
     * @param orchestration  the {@link CoherenceClusterOrchestration} establishing the session
     * @param schema         the {@link CoherenceCacheServerSchema} on which to base the
     *                       newly created {@link ConfigurableCacheFactory}
     *
     * @return a {@link ConfigurableCacheFactory}
     */
    public ConfigurableCacheFactory realize(LocalPlatform                 platform,
                                            CoherenceClusterOrchestration orchestration,
                                            CoherenceCacheServerSchema    schema);
}
