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
package org.apache.directory.shared.kerberos.codec.EncKdcRepPart.actions;


import org.apache.directory.api.asn1.DecoderException;
import org.apache.directory.api.asn1.ber.Asn1Decoder;
import org.apache.directory.api.asn1.ber.grammar.GrammarAction;
import org.apache.directory.api.asn1.ber.tlv.TLV;
import org.apache.directory.api.i18n.I18n;
import org.apache.directory.shared.kerberos.codec.EncKdcRepPart.EncKdcRepPartContainer;
import org.apache.directory.shared.kerberos.codec.lastReq.LastReqContainer;
import org.apache.directory.shared.kerberos.components.LastReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The action used to set the LastReq
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public class StoreLastReq extends GrammarAction<EncKdcRepPartContainer>
{
    /** The logger */
    private static final Logger LOG = LoggerFactory.getLogger( StoreLastReq.class );

    /** Speedup for logs */
    private static final boolean IS_DEBUG = LOG.isDebugEnabled();


    /**
     * Instantiates a new StoreLastReq action.
     */
    public StoreLastReq()
    {
        super( "Store the EncKDCRepPart LastReq" );
    }


    /**
     * {@inheritDoc}
     */
    public final void action( EncKdcRepPartContainer encKdcRepPartContainer ) throws DecoderException
    {
        TLV tlv = encKdcRepPartContainer.getCurrentTLV();

        // The Length should not be null
        if ( tlv.getLength() == 0 )
        {
            LOG.error( I18n.err( I18n.ERR_01308_ZERO_LENGTH_TLV ) );

            // This will generate a PROTOCOL_ERROR
            throw new DecoderException( I18n.err( I18n.ERR_01309_EMPTY_TLV ) );
        }

        // Now, let's decode the LastReq
        LastReqContainer lastReqContainer = new LastReqContainer();

        // Decode the LastReq PDU
        Asn1Decoder.decode( encKdcRepPartContainer.getStream(), lastReqContainer );

        LastReq lastReq = lastReqContainer.getLastReq();

        if ( IS_DEBUG )
        {
            LOG.debug( "LastReq : {}", lastReq );
        }

        encKdcRepPartContainer.getEncKdcRepPart().setLastReq( lastReq );

        // Update the expected length for the current TLV
        tlv.setExpectedLength( tlv.getExpectedLength() - tlv.getLength() );

        // Update the parent
        encKdcRepPartContainer.updateParent();
    }
}
