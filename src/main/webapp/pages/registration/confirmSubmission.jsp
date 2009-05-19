<%--
 Copyright (c) Members of the EGEE Collaboration. 2006.
 See http://www.eu-egee.org/partners/ for details on the copyright
 holders.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 Authors:
     Andrea Ceccanti - andrea.ceccanti@cnaf.infn.it
--%>
<%@ taglib
	uri="http://java.sun.com/jsp/jstl/core"
	prefix="c"%>

<%@ taglib
	uri="http://java.sun.com/jsp/jstl/fmt"
	prefix="fmt"%>
	
<%@ taglib
	uri="http://java.sun.com/jsp/jstl/functions"
	prefix="fn"%>
<%@ taglib
	uri="http://org.glite.security.voms.tags"
	prefix="voms"%>
<%@ taglib
	uri="http://struts.apache.org/tags-tiles"
	prefix="tiles"%>
<%@ taglib
	uri="http://struts.apache.org/tags-html"
	prefix="html"%>

<div class="header1">Confirmation required.</div>

<fmt:setLocale value="en_US"/>

<p>An email has been sent to you with instructions on how to proceed
with the registration for the ${voName } VO.</p>

<div>
Please follow the instructions before the following date:
<ul>
	<li> <span style="font-weight: bold"><fmt:formatDate type="both" value="${requestExpirationDate}" dateStyle="full"/></span></li>
</ul>
or your request will be discarded by voms-admin.
</div>