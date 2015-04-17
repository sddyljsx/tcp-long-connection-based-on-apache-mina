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

import java.net.Socket;

import org.apache.mina.core.session.IoSessionConfig;

/**
 * An {@link org.apache.mina.core.session.IoSessionConfig} for socket transport type.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public interface SocketSessionConfig extends IoSessionConfig {
    /**
     * @see java.net.Socket#getReuseAddress()
     */
    boolean isReuseAddress();

    /**
     * @see java.net.Socket#setReuseAddress(boolean)
     */
    void setReuseAddress(boolean reuseAddress);

    /**
     * @see java.net.Socket#getReceiveBufferSize()
     */
    int getReceiveBufferSize();

    /**
     * @see java.net.Socket#setReceiveBufferSize(int)
     */
    void setReceiveBufferSize(int receiveBufferSize);

    /**
     * @see java.net.Socket#getSendBufferSize()
     */
    int getSendBufferSize();

    /**
     * @see java.net.Socket#setSendBufferSize(int)
     */
    void setSendBufferSize(int sendBufferSize);

    /**
     * @see java.net.Socket#getTrafficClass()
     */
    int getTrafficClass();

    /**
     * @see java.net.Socket#setTrafficClass(int)
     */
    void setTrafficClass(int trafficClass);

    /**
     * @see java.net.Socket#getKeepAlive()
     */
    boolean isKeepAlive();

    /**
     * @see java.net.Socket#setKeepAlive(boolean)
     */
    void setKeepAlive(boolean keepAlive);

    /**
     * @see java.net.Socket#getOOBInline()
     */
    boolean isOobInline();

    /**
     * @see java.net.Socket#setOOBInline(boolean)
     */
    void setOobInline(boolean oobInline);

    /**
     * Please note that enabling <tt>SO_LINGER</tt> in Java NIO can result
     * in platform-dependent behavior and unexpected blocking of I/O thread.
     *
     * @see java.net.Socket#getSoLinger()
     * @see <a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6179351">Sun Bug Database</a>
     */
    int getSoLinger();

    /**
     * Please note that enabling <tt>SO_LINGER</tt> in Java NIO can result
     * in platform-dependent behavior and unexpected blocking of I/O thread.
     *
     * @param soLinger Please specify a negative value to disable <tt>SO_LINGER</tt>.
     *
     * @see java.net.Socket#setSoLinger(boolean, int)
     * @see <a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6179351">Sun Bug Database</a>
     */
    void setSoLinger(int soLinger);

    /**
     * @see java.net.Socket#getTcpNoDelay()
     */
    boolean isTcpNoDelay();

    /**
     * @see java.net.Socket#setTcpNoDelay(boolean)
     */
    void setTcpNoDelay(boolean tcpNoDelay);
}
