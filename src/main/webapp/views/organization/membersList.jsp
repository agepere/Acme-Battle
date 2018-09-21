<%--
 * action-2.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${canChat}">
	<acme:action code="organization.chat"  url="organization/chatty/list.do?organizationId=${algo}"/>
	<acme:action code="organization.invitations"  url="organization/invitation/orgList.do?organizationId=${algo}"/>
	<jstl:if test="${iAmMaster}">
		<acme:action code="organization.edit"  url="organization/edit.do?organizationId=${algo}"/>
	</jstl:if>
	<acme:action code="organization.leave"  url="organization/leaveOrganization.do"/>
</jstl:if>

<display:table pagesize="5" class="displaytag" 
	name="membersInvitations" requestURI="${requestURI}" id="row">

	<spring:message code="master.page.name" var="nameH" />
	<display:column title="${nameH}">
		<jstl:out value="${row.keybladeWielder.name}"/> 
	</display:column>
	
	<spring:message code="invitation.orgRange" var="orgRangeH" />
	<display:column title="${orgRangeH}" sortable="true">
		<jstl:out value="${row.orgRange}"/> 
	</display:column>
	
	<spring:message code="master.page.actions" var="actionsH" />
	<display:column title="${actionsH}">
		<jstl:if test="${iAmMaster and row.orgRange.toString() !='MASTER'}">
			<acme:action code="invitation.changeRange" url="/organization/invitation/changeRange.do?invitationId=${row.id}"/>
			<acme:action code="invitation.interchangeRange" url="/organization/invitation/interchangeRange.do?invitationId=${row.id}"/>
		</jstl:if>
		<acme:action code="master.page.profile"  url="/profile/actor/display.do?username=${row.keybladeWielder.userAccount.username }"/> 
	</display:column>
	

</display:table>

