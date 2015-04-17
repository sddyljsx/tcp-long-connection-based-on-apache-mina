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
package org.apache.mina.filter.ssl;

import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * A factory that creates and configures a new {@link javax.net.ssl.SSLContext}.
 * <p>
 * If no properties are set the returned {@link javax.net.ssl.SSLContext} will
 * be equivalent to what the following creates:
 * <pre>
 *      SSLContext c = SSLContext.getInstance( "TLS" );
 *      c.init(null, null, null);
 * </pre>
 * </p>
 * <p>
 * Use the properties prefixed with <code>keyManagerFactory</code> to control
 * the creation of the {@link javax.net.ssl.KeyManager} to be used.
 * </p>
 * <p>
 * Use the properties prefixed with <code>trustManagerFactory</code> to control
 * the creation of the {@link javax.net.ssl.TrustManagerFactory} to be used.
 * </p>
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class SslContextFactory {

    private String provider = null;

    private String protocol = "TLS";

    private SecureRandom secureRandom = null;

    private KeyStore keyManagerFactoryKeyStore = null;

    private char[] keyManagerFactoryKeyStorePassword = null;

    private KeyManagerFactory keyManagerFactory = null;

    private String keyManagerFactoryAlgorithm = null;

    private String keyManagerFactoryProvider = null;

    private boolean keyManagerFactoryAlgorithmUseDefault = true;

    private KeyStore trustManagerFactoryKeyStore = null;

    private TrustManagerFactory trustManagerFactory = null;

    private String trustManagerFactoryAlgorithm = null;

    private String trustManagerFactoryProvider = null;

    private boolean trustManagerFactoryAlgorithmUseDefault = true;

    private ManagerFactoryParameters trustManagerFactoryParameters = null;

    private int clientSessionCacheSize = -1;

    private int clientSessionTimeout = -1;

    private int serverSessionCacheSize = -1;

    private int serverSessionTimeout = -1;

    public SSLContext newInstance() throws Exception {
        KeyManagerFactory kmf = this.keyManagerFactory;
        TrustManagerFactory tmf = this.trustManagerFactory;

        if (kmf == null) {
            String algorithm = keyManagerFactoryAlgorithm;
            if (algorithm == null && keyManagerFactoryAlgorithmUseDefault) {
                algorithm = KeyManagerFactory.getDefaultAlgorithm();
            }
            if (algorithm != null) {
                if (keyManagerFactoryProvider == null) {
                    kmf = KeyManagerFactory.getInstance(algorithm);
                } else {
                    kmf = KeyManagerFactory.getInstance(algorithm, keyManagerFactoryProvider);
                }
            }
        }

        if (tmf == null) {
            String algorithm = trustManagerFactoryAlgorithm;
            if (algorithm == null && trustManagerFactoryAlgorithmUseDefault) {
                algorithm = TrustManagerFactory.getDefaultAlgorithm();
            }
            if (algorithm != null) {
                if (trustManagerFactoryProvider == null) {
                    tmf = TrustManagerFactory.getInstance(algorithm);
                } else {
                    tmf = TrustManagerFactory.getInstance(algorithm, trustManagerFactoryProvider);
                }
            }
        }

        KeyManager[] keyManagers = null;
        if (kmf != null) {
            kmf.init(keyManagerFactoryKeyStore, keyManagerFactoryKeyStorePassword);
            keyManagers = kmf.getKeyManagers();
        }
        TrustManager[] trustManagers = null;
        if (tmf != null) {
            if (trustManagerFactoryParameters != null) {
                tmf.init(trustManagerFactoryParameters);
            } else {
                tmf.init(trustManagerFactoryKeyStore);
            }
            trustManagers = tmf.getTrustManagers();
        }

        SSLContext context = null;
        if (provider == null) {
            context = SSLContext.getInstance(protocol);
        } else {
            context = SSLContext.getInstance(protocol, provider);
        }

        context.init(keyManagers, trustManagers, secureRandom);

        if (clientSessionCacheSize >= 0) {
            context.getClientSessionContext().setSessionCacheSize(clientSessionCacheSize);
        }

        if (clientSessionTimeout >= 0) {
            context.getClientSessionContext().setSessionTimeout(clientSessionTimeout);
        }

        if (serverSessionCacheSize >= 0) {
            context.getServerSessionContext().setSessionCacheSize(serverSessionCacheSize);
        }

        if (serverSessionTimeout >= 0) {
            context.getServerSessionContext().setSessionTimeout(serverSessionTimeout);
        }

        return context;
    }

    /**
     * Sets the provider of the new {@link javax.net.ssl.SSLContext}. The default value is
     * <tt>null</tt>, which means the default provider will be used.
     * 
     * @param provider the name of the {@link javax.net.ssl.SSLContext} provider
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }

    /**
     * Sets the protocol to use when creating the {@link javax.net.ssl.SSLContext}. The
     * default is <code>TLS</code>.
     *
     * @param protocol the name of the protocol.
     */
    public void setProtocol(String protocol) {
        if (protocol == null) {
            throw new IllegalArgumentException("protocol");
        }
        this.protocol = protocol;
    }

    /**
     * If this is set to <code>true</code> while no {@link javax.net.ssl.KeyManagerFactory}
     * has been set using {@link #setKeyManagerFactory(javax.net.ssl.KeyManagerFactory)} and
     * no algorithm has been set using
     * {@link #setKeyManagerFactoryAlgorithm(String)} the default algorithm
     * return by {@link javax.net.ssl.KeyManagerFactory#getDefaultAlgorithm()} will be used.
     * The default value of this property is <tt>true<tt/>.
     *
     * @param useDefault
     *            <code>true</code> or <code>false</code>.
     */
    public void setKeyManagerFactoryAlgorithmUseDefault(boolean useDefault) {
        this.keyManagerFactoryAlgorithmUseDefault = useDefault;
    }

    /**
     * If this is set to <code>true</code> while no {@link javax.net.ssl.TrustManagerFactory}
     * has been set using {@link #setTrustManagerFactory(javax.net.ssl.TrustManagerFactory)} and
     * no algorithm has been set using
     * {@link #setTrustManagerFactoryAlgorithm(String)} the default algorithm
     * return by {@link javax.net.ssl.TrustManagerFactory#getDefaultAlgorithm()} will be used.
     * The default value of this property is <tt>true<tt/>.
     *
     * @param useDefault <code>true</code> or <code>false</code>.
     */
    public void setTrustManagerFactoryAlgorithmUseDefault(boolean useDefault) {
        this.trustManagerFactoryAlgorithmUseDefault = useDefault;
    }

    /**
     * Sets the {@link javax.net.ssl.KeyManagerFactory} to use. If this is set the properties
     * which are used by this factory bean to create a {@link javax.net.ssl.KeyManagerFactory}
     * will all be ignored.
     *
     * @param factory the factory.
     */
    public void setKeyManagerFactory(KeyManagerFactory factory) {
        this.keyManagerFactory = factory;
    }

    /**
     * Sets the algorithm to use when creating the {@link javax.net.ssl.KeyManagerFactory}
     * using {@link javax.net.ssl.KeyManagerFactory#getInstance(String)} or
     * {@link javax.net.ssl.KeyManagerFactory#getInstance(String, String)}.
     * <p>
     * This property will be ignored if a {@link javax.net.ssl.KeyManagerFactory} has been
     * set directly using {@link #setKeyManagerFactory(javax.net.ssl.KeyManagerFactory)}.
     * </p>
     * <p>
     * If this property isn't set while no {@link javax.net.ssl.KeyManagerFactory} has been
     * set using {@link #setKeyManagerFactory(javax.net.ssl.KeyManagerFactory)} and
     * {@link #setKeyManagerFactoryAlgorithmUseDefault(boolean)} has been set to
     * <code>true</code> the value returned
     * by {@link javax.net.ssl.KeyManagerFactory#getDefaultAlgorithm()} will be used instead.
     * </p>
     *
     * @param algorithm the algorithm to use.
     */
    public void setKeyManagerFactoryAlgorithm(String algorithm) {
        this.keyManagerFactoryAlgorithm = algorithm;
    }

    /**
     * Sets the provider to use when creating the {@link javax.net.ssl.KeyManagerFactory}
     * using
     * {@link javax.net.ssl.KeyManagerFactory#getInstance(String, String)}.
     * <p>
     * This property will be ignored if a {@link javax.net.ssl.KeyManagerFactory} has been
     * set directly using {@link #setKeyManagerFactory(javax.net.ssl.KeyManagerFactory)}.
     * </p>
     * <p>
     * If this property isn't set and no {@link javax.net.ssl.KeyManagerFactory} has been set
     * using {@link #setKeyManagerFactory(javax.net.ssl.KeyManagerFactory)}
     * {@link javax.net.ssl.KeyManagerFactory#getInstance(String)} will be used
     * to create the {@link javax.net.ssl.KeyManagerFactory}.
     * </p>
     *
     * @param provider the name of the provider.
     */
    public void setKeyManagerFactoryProvider(String provider) {
        this.keyManagerFactoryProvider = provider;
    }

    /**
     * Sets the {@link java.security.KeyStore} which will be used in the call to
     * {@link javax.net.ssl.KeyManagerFactory#init(java.security.KeyStore, char[])} when
     * the {@link javax.net.ssl.SSLContext} is created.
     *
     * @param keyStore the key store.
     */
    public void setKeyManagerFactoryKeyStore(KeyStore keyStore) {
        this.keyManagerFactoryKeyStore = keyStore;
    }

    /**
     * Sets the password which will be used in the call to
     * {@link javax.net.ssl.KeyManagerFactory#init(java.security.KeyStore, char[])} when
     * the {@link javax.net.ssl.SSLContext} is created.
     *
     * @param password the password. Use <code>null</code> to disable password.
     */
    public void setKeyManagerFactoryKeyStorePassword(String password) {
        if (password != null) {
            this.keyManagerFactoryKeyStorePassword = password.toCharArray();
        } else {
            this.keyManagerFactoryKeyStorePassword = null;
        }
    }

    /**
     * Sets the {@link javax.net.ssl.TrustManagerFactory} to use. If this is set the
     * properties which are used by this factory bean to create a
     * {@link javax.net.ssl.TrustManagerFactory} will all be ignored.
     *
     * @param factory
     *            the factory.
     */
    public void setTrustManagerFactory(TrustManagerFactory factory) {
        this.trustManagerFactory = factory;
    }

    /**
     * Sets the algorithm to use when creating the {@link javax.net.ssl.TrustManagerFactory}
     * using {@link javax.net.ssl.TrustManagerFactory#getInstance(String)} or
     * {@link javax.net.ssl.TrustManagerFactory#getInstance(String, String)}.
     * <p>
     * This property will be ignored if a {@link javax.net.ssl.TrustManagerFactory} has been
     * set directly using {@link #setTrustManagerFactory(javax.net.ssl.TrustManagerFactory)}.
     * </p>
     * <p>
     * If this property isn't set while no {@link javax.net.ssl.TrustManagerFactory} has been
     * set using {@link #setTrustManagerFactory(javax.net.ssl.TrustManagerFactory)} and
     * {@link #setTrustManagerFactoryAlgorithmUseDefault(boolean)} has been set to
     * <code>true</code> the value returned
     * by {@link javax.net.ssl.TrustManagerFactory#getDefaultAlgorithm()} will be used instead.
     * </p>
     *
     * @param algorithm the algorithm to use.
     */
    public void setTrustManagerFactoryAlgorithm(String algorithm) {
        this.trustManagerFactoryAlgorithm = algorithm;
    }

    /**
     * Sets the {@link java.security.KeyStore} which will be used in the call to
     * {@link javax.net.ssl.TrustManagerFactory#init(java.security.KeyStore)} when
     * the {@link javax.net.ssl.SSLContext} is created.
     * <p>
     * This property will be ignored if {@link javax.net.ssl.ManagerFactoryParameters} has been
     * set directly using {@link #setTrustManagerFactoryParameters(javax.net.ssl.ManagerFactoryParameters)}.
     * </p>
     *
     * @param keyStore the key store.
     */
    public void setTrustManagerFactoryKeyStore(KeyStore keyStore) {
        this.trustManagerFactoryKeyStore = keyStore;
    }

    /**
     * Sets the {@link javax.net.ssl.ManagerFactoryParameters} which will be used in the call to
     * {@link javax.net.ssl.TrustManagerFactory#init(javax.net.ssl.ManagerFactoryParameters)} when
     * the {@link javax.net.ssl.SSLContext} is created.
     *
     * @param parameters describing provider-specific trust material.
     */
    public void setTrustManagerFactoryParameters(ManagerFactoryParameters parameters) {
        this.trustManagerFactoryParameters = parameters;
    }

    /**
     * Sets the provider to use when creating the {@link javax.net.ssl.TrustManagerFactory}
     * using
     * {@link javax.net.ssl.TrustManagerFactory#getInstance(String, String)}.
     * <p>
     * This property will be ignored if a {@link javax.net.ssl.TrustManagerFactory} has been
     * set directly using {@link #setTrustManagerFactory(javax.net.ssl.TrustManagerFactory)}.
     * </p>
     * <p>
     * If this property isn't set and no {@link javax.net.ssl.TrustManagerFactory} has been set
     * using {@link #setTrustManagerFactory(javax.net.ssl.TrustManagerFactory)}
     * {@link javax.net.ssl.TrustManagerFactory#getInstance(String)} will be used
     * to create the {@link javax.net.ssl.TrustManagerFactory}.
     * </p>
     *
     * @param provider the name of the provider.
     */
    public void setTrustManagerFactoryProvider(String provider) {
        this.trustManagerFactoryProvider = provider;
    }

    /**
     * Sets the {@link java.security.SecureRandom} to use when initializing the
     * {@link javax.net.ssl.SSLContext}. The JVM's default will be used if this isn't set.
     *
     * @param secureRandom the {@link java.security.SecureRandom} or <code>null</code> if the
     *        JVM's default should be used.
     * @see javax.net.ssl.SSLContext#init(javax.net.ssl.KeyManager[], javax.net.ssl.TrustManager[], java.security.SecureRandom)
     */
    public void setSecureRandom(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
    }

    /**
     * Sets the SSLSession cache size for the {@link javax.net.ssl.SSLSessionContext} for use in client mode.
     *
     * @param size the new session cache size limit; zero means there is no limit.
     * @see javax.net.ssl.SSLSessionContext#setSessionCacheSize(int size)
     */
    public void setClientSessionCacheSize(int size) {
        this.clientSessionCacheSize = size;
    }

    /**
     * Set the SSLSession timeout limit for the {@link javax.net.ssl.SSLSessionContext} for use in client mode.
     *
     * @param seconds the new session timeout limit in seconds; zero means there is no limit.
     * @see javax.net.ssl.SSLSessionContext#setSessionTimeout(int seconds)
     */
    public void setClientSessionTimeout(int seconds) {
        this.clientSessionTimeout = seconds;
    }

    /**
     * Sets the SSLSession cache size for the {@link javax.net.ssl.SSLSessionContext} for use in server mode.
     *
     * @param serverSessionCacheSize the new session cache size limit; zero means there is no limit.
     * @see javax.net.ssl.SSLSessionContext#setSessionCacheSize(int)
     */
    public void setServerSessionCacheSize(int serverSessionCacheSize) {
        this.serverSessionCacheSize = serverSessionCacheSize;
    }

    /**
     * Set the SSLSession timeout limit for the {@link javax.net.ssl.SSLSessionContext} for use in server mode.
     *
     * @param serverSessionTimeout the new session timeout limit in seconds; zero means there is no limit.
     * @see javax.net.ssl.SSLSessionContext#setSessionTimeout(int)
     */
    public void setServerSessionTimeout(int serverSessionTimeout) {
        this.serverSessionTimeout = serverSessionTimeout;
    }
}
