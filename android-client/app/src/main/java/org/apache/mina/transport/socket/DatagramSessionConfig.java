/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.apache.mina.transport.socket;

import java.net.DatagramSocket;
import java.net.PortUnreachableException;

import org.apache.mina.core.session.IoSessionConfig;

/**
 * An {@link org.apache.mina.core.session.IoSessionConfig} for datagram transport type.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public interface DatagramSessionConfig extends IoSessionConfig {
    /**
     * @see java.net.DatagramSocket#getBroadcast()
     */
    boolean isBroadcast();

    /**
     * @see java.net.DatagramSocket#setBroadcast(boolean)
     */
    void setBroadcast(boolean broadcast);

    /**
     * @see java.net.DatagramSocket#getReuseAddress()
     */
    boolean isReuseAddress();

    /**
     * @see java.net.DatagramSocket#setReuseAddress(boolean)
     */
    void setReuseAddress(boolean reuseAddress);

    /**
     * @see java.net.DatagramSocket#getReceiveBufferSize()
     */
    int getReceiveBufferSize();

    /**
     * @see java.net.DatagramSocket#setReceiveBufferSize(int)
     */
    void setReceiveBufferSize(int receiveBufferSize);

    /**
     * @see java.net.DatagramSocket#getSendBufferSize()
     */
    int getSendBufferSize();

    /**
     * @see java.net.DatagramSocket#setSendBufferSize(int)
     */
    void setSendBufferSize(int sendBufferSize);

    /**
     * @see java.net.DatagramSocket#getTrafficClass()
     */
    int getTrafficClass();

    /**
     * @see java.net.DatagramSocket#setTrafficClass(int)
     */
    void setTrafficClass(int trafficClass);

    /**
     * If method returns true, it means session should be closed when a
     * {@link java.net.PortUnreachableException} occurs.
     */
    boolean isCloseOnPortUnreachable();

    /**
     * Sets if the session should be closed if an {@link java.net.PortUnreachableException}
     * occurs.
     */
    void setCloseOnPortUnreachable(boolean closeOnPortUnreachable);
}
