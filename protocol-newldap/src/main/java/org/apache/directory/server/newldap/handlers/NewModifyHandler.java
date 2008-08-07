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
package org.apache.directory.server.newldap.handlers;


import org.apache.directory.server.core.entry.ClonedServerEntry;
import org.apache.directory.server.newldap.LdapSession;
import org.apache.directory.shared.ldap.message.LdapResult;
import org.apache.directory.shared.ldap.message.ModifyRequest;
import org.apache.directory.shared.ldap.message.ResultCodeEnum;
import org.apache.directory.shared.ldap.name.LdapDN;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A single reply handler for {@link ModifyRequest}s.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 * @version $Rev: 664302 $
 */
public class NewModifyHandler extends ReferralAwareRequestHandler<ModifyRequest>
{
    private static final Logger LOG = LoggerFactory.getLogger( NewModifyHandler.class );


    /**
     * @see ReferralAwareRequestHandler#handleIgnoringReferrals(LdapSession, LdapDN, ClonedServerEntry, 
     * org.apache.directory.shared.ldap.message.SingleReplyRequest)
     */
    @Override
    public void handleIgnoringReferrals( LdapSession session, LdapDN reqTargetDn, 
        ClonedServerEntry entry, ModifyRequest req )
    {
        LOG.debug( "Handling modify request while ignoring referrals: {}", req );
        LdapResult result = req.getResultResponse().getLdapResult();

        try
        {
            session.getCoreSession().modify( req );
            result.setResultCode( ResultCodeEnum.SUCCESS );
            session.getIoSession().write( req.getResultResponse() );
        }
        catch ( Exception e )
        {
            handleException( session, req, e );
        }
    }
}