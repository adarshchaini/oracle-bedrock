/*
 * File: ExponentialIterator.java
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

package com.oracle.tools.util;

import java.util.Iterator;

/**
 * An {@link java.util.Iterator} over the
 * <a href="http://en.wikipedia.org/wiki/Exponential_growth">Exponential</a> sequence.
 * <p>
 * Copyright (c) 2013. All Rights Reserved. Oracle Corporation.<br>
 * Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
 *
 * @author Brian Oliver
 */
public class ExponentialIterator implements Iterator<Long>
{
    /**
     * The initial value;
     */
    private double initial;

    /**
     * The rate a which growth should occur.
     */
    private double rate;

    /**
     * The iteration.
     */
    private long iteration;


    /**
     * Constructs a {@link ExponentialIterator}.
     *
     * @param initial     the initial value (usually 0)
     * @param percentage  the rate of growth
     */
    public ExponentialIterator(double initial,
                               double percentage)
    {
        this.initial = initial;
        rate         = 1 + (percentage / 100.0);
        iteration    = 0;
    }


    @Override
    public boolean hasNext()
    {
        return true;
    }


    @Override
    public Long next()
    {
        long result;

        if (iteration == 0)
        {
            result = Math.round(initial);
        }
        else
        {
            result = Math.round(initial + Math.pow(rate, iteration - 1));
        }

        iteration++;

        return result;
    }


    @Override
    public void remove()
    {
        throw new UnsupportedOperationException("Can't remove from a " + this.getClass().getName());
    }
}
