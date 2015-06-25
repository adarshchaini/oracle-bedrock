/*
 * File: HttpBasedAuthentication.java
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

package com.oracle.tools.runtime.remote.http;

import com.oracle.tools.Options;
import com.oracle.tools.runtime.remote.Authentication;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A specialized {@link com.oracle.tools.runtime.remote.Authentication} that defines HTTP specific callbacks for
 * {@link com.oracle.tools.runtime.remote.Authentication}s.
 * <p>
 * Copyright (c) 2015. All Rights Reserved. Oracle Corporation.<br>
 * Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
 *
 * @author Jonathan Knight
 */
public interface HttpBasedAuthentication extends Authentication
{
    /**
     * Open a connection to the specified URL and configure the connection with the
     * correct authentication mechanism.
     *
     * @param url       the {@link URL} to connect to
     * @param userName  the name of the user to use to open the connection
     * @param options   an {@link Options} to use to configure authentication
     *
     * @return an authenticated {@link HttpURLConnection} to the specified {@link URL}
     */
    HttpURLConnection openConnection(URL       url,
                                     String    userName,
                                     Options   options) throws IOException;
}
