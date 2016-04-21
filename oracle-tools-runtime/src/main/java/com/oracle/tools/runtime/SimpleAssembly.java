/*
 * File: SimpleAssembly.java
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

package com.oracle.tools.runtime;

import com.oracle.tools.Options;

import java.util.List;

/**
 * A simple implementation of an {@link Assembly}
 * <p>
 * Copyright (c) 2014. All Rights Reserved. Oracle Corporation.<br>
 * Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
 *
 * @author Jonathan Knight
 *
 * @param <A> the type of {@link Application} contained within this {@link SimpleAssembly}
 */
public class SimpleAssembly<A extends Application> extends AbstractAssembly<A>
{
    /**
     * Constructs a {@link SimpleAssembly}
     *
     * @param applications  the {@link Application}s for the {@link Assembly}
     * @param options       the shared / common {@link Options} used to launch the {@link Application}s
     */
    public SimpleAssembly(List<? extends A> applications,
                          Options           options)
    {
        super(applications, options);
    }
}
