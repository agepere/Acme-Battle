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


<form:form action="organization/chatty/edit.do" modelAttribute="chatty">
	
	<form:hidden path="version" />
	<form:hidden path="id" />
	<form:hidden path="date" />
	<form:hidden path="invitation" />
	
	
	<acme:textbox code="master.page.content" path="content" />
	<br>
		
	<acme:submit name="save" code="master.page.save"/>
	<acme:cancel url="organization/chatty/list.do?organizationId=${organizationId}" code="master.page.cancel"/>

</form:form>