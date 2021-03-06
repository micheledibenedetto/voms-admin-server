<%--

    Copyright (c) Members of the EGEE Collaboration. 2006-2009.
    See http://www.eu-egee.org/partners/ for details on the copyright holders.

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
    	Andrea Ceccanti (INFN)

--%>
<%@include file="/WEB-INF/p/shared/taglibs.jsp"%>
<s:url action="manage" namespace="/acl" var="editACLURL">
  <s:param name="aclRoleId" value="id"/>
</s:url>

<h1>Role <span class="rolename"><s:property value="name"/></span><s:a href="%{editACLURL}" cssClass="actionLink" cssStyle="margin-left:1em">View ACL</s:a></h1>



<div class="info-tab">
  <h2><span>Membership information</span></h2>
  <voms:div id="role-membership-content" cssClass="content">
  
    <div>
      <s:form theme="simple" action="search-member" namespace="/role" onsubmit="ajaxSubmit(this,'mmResults'); return false;">
        Search members with role '<s:property value="name"/>' in group:
        <s:select name="searchData.groupId" list="voGroups" listKey="id" listValue="name" />
        <s:hidden name="searchData.roleId" value="%{id}"/>
        <s:textfield name="searchData.text" size="20"/>
        <s:submit value="%{'Search'}" cssClass="submitButton"/>
      </s:form>
      
      <div id="mmResults">
        <s:action name="search-member" namespace="/role" executeResult="true">
        <s:param name="searchData.roleId" value="id"/>
        <s:param name="searchData.groupId" value="voGroups[0].id"/>
      </s:action>
        
      </div>
      
    </div>
  </voms:div>
</div>

<div class="info-tab">
  <h2><span>Attributes information</span></h2>
  <voms:div id="generic-attrs-content" cssClass="content">
    <tiles2:insertTemplate template="attributes.jsp"/>
  </voms:div>
</div>


