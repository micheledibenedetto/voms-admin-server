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

package org.glite.security.voms.admin.notification;

import java.util.List;

import org.glite.security.voms.admin.event.Event;
import org.glite.security.voms.admin.event.EventMask;
import org.glite.security.voms.admin.event.EventType;
import org.glite.security.voms.admin.event.registration.GroupMembershipApprovedEvent;
import org.glite.security.voms.admin.event.registration.GroupMembershipRejectedEvent;
import org.glite.security.voms.admin.event.registration.GroupMembershipRequestEvent;
import org.glite.security.voms.admin.event.registration.GroupMembershipSubmittedEvent;
import org.glite.security.voms.admin.notification.messages.HandleRequest;
import org.glite.security.voms.admin.notification.messages.RequestApproved;
import org.glite.security.voms.admin.notification.messages.RequestRejected;
import org.glite.security.voms.admin.operations.VOMSContext;
import org.glite.security.voms.admin.operations.VOMSPermission;
import org.glite.security.voms.admin.persistence.model.request.GroupMembershipRequest;

public class GroupMembershipNotificationDispatcher extends BaseNotificationDispatcher{

	
	private static GroupMembershipNotificationDispatcher instance = null;
	
	public static GroupMembershipNotificationDispatcher instance(){
		
		if (instance == null)
			instance = new GroupMembershipNotificationDispatcher();
		
		return instance;
	}
	
	private GroupMembershipNotificationDispatcher() {
		super(new EventMask(EventType.GroupMembershipRequestEvent));
	}
											
	public void fire(Event event) {
		
		GroupMembershipRequestEvent e = (GroupMembershipRequestEvent) event;
		
		GroupMembershipRequest req = e.getRequest();
		
		if (e instanceof GroupMembershipSubmittedEvent){
			
			GroupMembershipSubmittedEvent ee = (GroupMembershipSubmittedEvent)e;
			
			VOMSContext context = VOMSContext.instance(ee.getRequest().getGroupName());
			
			List<String> admins = NotificationUtil.getAdministratorsEmailList(context, VOMSPermission.getRequestsRWPermissions());
			
			HandleRequest msg = new HandleRequest(req,ee.getManagementURL());
					
			NotificationService.instance().send(msg);
			
		}
		
		if (e instanceof GroupMembershipApprovedEvent){
			
			RequestApproved msg = new RequestApproved(req);
			NotificationService.instance().send(msg);
		
		}
		
		if (e instanceof GroupMembershipRejectedEvent){
		
			RequestRejected msg = new RequestRejected(req, null);
			
			NotificationService.instance().send(msg);
		}
	}
}
