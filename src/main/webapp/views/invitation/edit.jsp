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


<form:form action="organization/invitation/edit.do" modelAttribute="invitation">
	
	<form:hidden path="version" />
	<form:hidden path="id" />
	<form:hidden path="keybladeWielder" />
	<input type="hidden" name="invitedId" value="${invitedId}" />
	
	<%-- <form:hidden path="keybladeWielder" />
	<form:hidden path="organization" />
	<form:hidden path="invitationStatus" /> --%>
	
	
	
	<acme:textbox code="master.page.content" path="content" />
	<br>
	<form:select path="orgRange">
		<form:option label="GUEST" value="${'GUEST'}"/>
		<form:option label="OFFICER" value="${'OFFICER'}"/>
	</form:select>
	<br>
	<br>
		
	
	
	<acme:submit name="save" code="master.page.save"/>
	<acme:cancel url="organization/invitation/list.do" code="master.page.cancel"/>

</form:form>