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

package org.apache.directory.server.dns.io.encoder;


import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import org.apache.directory.api.util.Network;
import org.apache.directory.server.dns.store.DnsAttribute;
import org.apache.mina.core.buffer.IoBuffer;


/**
 * Tests for the A record encoder.
 * 
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public class AddressRecordEncoderTest extends AbstractResourceRecordEncoderTest
{
    InetAddress address;


    @Override
    protected void setUpResourceData()
    {
        address = Network.LOOPBACK;
    }


    @Override
    protected Map<String, Object> getAttributes()
    {
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put( DnsAttribute.IP_ADDRESS, address );
        return attributes;
    }


    @Override
    protected ResourceRecordEncoder getEncoder()
    {
        return new AddressRecordEncoder();
    }


    @Override
    protected void putExpectedResourceData( IoBuffer expectedData )
    {
        expectedData.put( ( byte ) address.getAddress().length );
        expectedData.put( address.getAddress() );
    }
}
