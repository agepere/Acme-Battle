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

<security:authorize access="hasRole('PLAYER')">
	<jstl:if test="${!hasOrganization}">
		<acme:action code="organization.create"  url="organization/edit.do"/>
	</jstl:if>
</security:authorize>

<jstl:if test="${hasOrganization}">
	<acme:action code="organization.enter"  url="organization/membersList.do?organizationId=${organizationId}"/>
</jstl:if>


<display:table pagesize="${pageSize}" class="displaytag" 
	name="organizations" requestURI="${requestURI}" id="row">

	<spring:message code="master.page.name" var="nameH" />
	<display:column title="${nameh}">
		<jstl:out value="${row.name}"/> 
	</display:column>
	
	<spring:message code="master.page.description" var="descriptionH" />
	<display:column property="description" title="${descriptionH}">
		<jstl:out value="${row.description}"/> 
	</display:column>
	
	<spring:message code="master.page.creationDate" var="dateH" />
	<display:column property="creationDate" title="${dateH}">
		<jstl:out value="${row.creationDate}"/> 
	</display:column>
	
	<spring:message code="organization.members" var="membersH" />
	<display:column title="${memebersH}">
		<acme:action code="organization.members"  url="organization/membersList.do?organizationId=${row.id}"/>
		<security:authorize access="hasRole('ADMIN')">
			<acme:action code="organization.delete"  url="organization/delete.do?organizationId=${row.id}"/>
		</security:authorize>
	</display:column>
	
	

</display:table>