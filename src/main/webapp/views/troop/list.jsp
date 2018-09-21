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



<jstl:forEach var="troop" items="${troops}" >
	<fieldset>
		<legend title="${troop.name }"><jstl:out value="${troop.name }"/></legend>
		
		<spring:message code="attack" var="attack" />
		<jstl:out value="${attack}: ${troop.attack }"></jstl:out>
		<br>
		
		<spring:message code="defense" var="defense" />
		<jstl:out value="${defense}: ${troop.defense }"></jstl:out>
		<br>
		
		<spring:message code="cost" var="materialCosts" />
		<jstl:out value="${materialCosts}: ${troop.cost }"></jstl:out>
		<br>
		
		<spring:message code="timeToRecruit" var="timeToRecruit" />
		<jstl:out value="${timeToRecruit}: ${troop.timeToRecruit }"></jstl:out>
		<br>
		
		<spring:message code="recruiterRequiredLvl" var="level" />
		<jstl:out value="${level}: ${troop.recruiterRequiredLvl }"></jstl:out>
		<br>
		
		<jstl:if test="${troop.soyDueno()}">
			<acme:action code="troop.edit"  url="troop/contentManager/edit.do?recruiterId=${troop.recruiter.id}&troopId=${troop.id}"/>
			<acme:action code="troop.delete"  url="troop/contentManager/delete.do?troopId=${troop.id}"/>
		</jstl:if>
	
		
	</fieldset>
</jstl:forEach>
<br><br>
