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


<jstl:forEach var="chatty" items="${chattys}" >
	<fieldset>
		<legend title="${chatty.invitation.keybladeWielder.name }"><jstl:out value="${chatty.invitation.keybladeWielder.name }"/></legend>
		<jstl:out value="${chatty.content }"></jstl:out>
	</fieldset>
</jstl:forEach>
<br><br>
<acme:action code="organization.members"  url="organization/membersList.do?organizationId=${organizationId}"/>
<acme:action code="chatty.create"  url="organization/chatty/edit.do"/>