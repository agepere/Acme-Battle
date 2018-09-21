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


<form:form action="troop/contentManager/edit.do" modelAttribute="troop">
	
	<form:hidden path="version" />
	<form:hidden path="id" />
	<form:hidden path="recruiter" />
	
	
	<acme:textbox code="name" path="name" />
	<br>
	<acme:textbox code="attack" path="attack" />
	<br>
	<acme:textbox code="defense" path="defense" />
	<br>
	<spring:message code="legendTimeToRecruit" var="TTRMinutes" />
	<i><jstl:out value="${TTRMinutes}"></jstl:out></i>
	<acme:textbox code="timeToRecruit" path="timeToRecruit" />
	<br>
	<acme:textbox code="costMunny" path="cost.munny" />
	<br>
	<acme:textbox code="costMythril" path="cost.mytrhil" />
	<br>
	<acme:textbox code="costGummiCoal" path="cost.gummiCoal" />
	<br>
	<acme:textbox code="recruiterRequiredLvl" path="recruiterRequiredLvl" />
	<br>
	
	
	
	<acme:submit name="save" code="master.page.save"/>
	<acme:cancel url="/troop/contentManager/list.do" code="master.page.cancel"/>

</form:form>