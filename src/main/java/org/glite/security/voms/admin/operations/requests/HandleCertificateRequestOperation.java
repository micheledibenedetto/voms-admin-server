/**
 * Copyright (c) Members of the EGEE Collaboration. 2006-2009.
 * See http://www.eu-egee.org/partners/ for details on the copyright holders.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Authors:
 * 	Andrea Ceccanti (INFN)
 */

package org.glite.security.voms.admin.operations.requests;

import org.glite.security.voms.admin.event.EventManager;
import org.glite.security.voms.admin.event.registration.CertificateRequestApprovedEvent;
import org.glite.security.voms.admin.event.registration.CertificateRequestRejectedEvent;
import org.glite.security.voms.admin.operations.VOMSContext;
import org.glite.security.voms.admin.operations.VOMSPermission;
import org.glite.security.voms.admin.operations.users.AddUserCertificateOperation;
import org.glite.security.voms.admin.persistence.model.VOMSUser;
import org.glite.security.voms.admin.persistence.model.request.CertificateRequest;
import org.glite.security.voms.admin.persistence.model.request.Request.STATUS;

public class HandleCertificateRequestOperation extends BaseHandleRequestOperation<CertificateRequest> {
	
	public HandleCertificateRequestOperation(CertificateRequest request,
			DECISION decision) {
		super(request, decision);
	}

	@Override
	protected void approve() {
		
		checkRequestStatus(STATUS.SUBMITTED);
		VOMSUser u = getRequesterAsVomsUser();
		
		AddUserCertificateOperation.instance(u, request.getCertificateSubject(), request.getCertificateIssuer(), null).execute();
		request.approve();
		EventManager.dispatch(new CertificateRequestApprovedEvent(request));
		
		
	}

	@Override
	protected void reject() {
		
		checkRequestStatus(STATUS.SUBMITTED);
		request.reject();
		EventManager.dispatch(new CertificateRequestRejectedEvent(request));
		
	}

	@Override
	protected void setupPermissions() {
		addRequiredPermission(VOMSContext.getVoContext(), VOMSPermission
				.getContainerRWPermissions().setMembershipRWPermission()
				.setRequestsReadPermission().setRequestsWritePermission());

	}

	
}
