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

package org.glite.security.voms.admin.view.actions.user;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.glite.security.voms.admin.operations.users.SetUserAttributeOperation;
import org.glite.security.voms.admin.persistence.error.AttributeValueAlreadyAssignedException;
import org.glite.security.voms.admin.view.actions.BaseAction;

@ParentPackage("base")
@Results( { @Result(name = BaseAction.SUCCESS, location = "attributes.jsp"),
		@Result(name = BaseAction.INPUT, location ="attributes.jsp") })
public class SetAttributeAction extends AttributeActions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	@Override
	public String execute() throws Exception {
		
		try{
			SetUserAttributeOperation.instance(getModel(), attributeName,
					attributeValue).execute();
		
		}catch (AttributeValueAlreadyAssignedException e){
			addFieldError("attributeValue", "The attribute value '"+attributeValue+"' is already assigned to another user!");
			return INPUT;
		}
		
		addActionMessage(getText("confirm.user.attribute_set", new String[]{attributeName, getModel().getShortName()}));
		
		return SUCCESS;
	}

}
